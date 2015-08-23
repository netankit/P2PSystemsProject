package voip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.logging.Log;

import common.CommonFunctions;
import logger.LogSetup;
import messages.Message;
import messages.MessageFactory;
import messages.MessageFields;
import messages.MessagePacket;

/**
 * Manages the call status Opens a TCP socket to receive calls from other Peer
 * 
 * @author Zeeshan
 *
 */
public class ConnectionStatusManager extends Thread {

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ConnectionStatusManager.class.getName());

	// TCP port receiving calls
	private int TimeOut = 1000;
	private int status; // current status of peer
	private String ipAddressOfOtherPeer; // tunnel IP Address
	private int PeerStatusPort = 14999;
	private String pseudoIdentityOfOtherPeer;
	private String ownPseudoIdentity;
	private String ownIPAddress; // available in config file under KX section

	public static final int PEER_STATUS_IDLE = 1;
	public static final int PEER_STATUS_SESSION = 2;
	public static final int PEER_STATUS_ERROR = 3;
	public static final int PEER_STATUS_WAITING = 4;

	private CallManager callManager;

	public ConnectionStatusManager(CallManager callManager, int PeerStatusPort, String ownPseudoIdentity,
			String ownIPAddress) {
		super(); // init the super class Thread
		this.callManager = callManager;
		status = PEER_STATUS_IDLE; // set idle
		if (PeerStatusPort != 0)
			this.PeerStatusPort = PeerStatusPort;
		this.ownPseudoIdentity = ownPseudoIdentity;
		this.ownIPAddress = ownIPAddress;
	}

	public CallManager getCallManager() {
		return callManager;
	}

	public void SetPseudoIdentityOfOtherPeer(String pseudoIdentityOfOtherPeer) {
		this.pseudoIdentityOfOtherPeer = pseudoIdentityOfOtherPeer;
	}

	public String GetOwnPseudoIdentity() {
		return this.ownPseudoIdentity;
	}

	public String GetPseudoIdentityOfOtherPeer() {
		return this.pseudoIdentityOfOtherPeer;
	}

	public int CheckStatus(String IPAddr) throws IOException {
		int result = -1;
		Socket s = new Socket();
		s.connect(new InetSocketAddress(IPAddr, PeerStatusPort), TimeOut); // connect
																			// to
																			// the
																			// peer

		// set up TCP stream reader and writer
		InputStream inputStream = s.getInputStream();
		OutputStream ouputStream = s.getOutputStream();

		// create Init message
		MessageFields fields = new MessageFields();
		fields.setIpv4_address(ownIPAddress);
		fields.setIpv4_address_ofcallee(IPAddr);
		fields.setPseudo_identity_caller(ownPseudoIdentity);
		fields.setPseudo_identity_callee(pseudoIdentityOfOtherPeer);
		fields.setNum_tries("1");
		fields.setPort_number("" + PeerStatusPort);

		MessageFactory msgfac = new MessageFactory();
		Message message = msgfac.createGenericMessage("VOIP_MESSAGE");
		message.SetMessageFields(fields);
		byte[] msg = message.createMessage("MSG_VOIP_CALL_INITIATE");

		ouputStream.write(msg);
		ouputStream.flush();
		byte[] recievedBytes = new byte[MessagePacket.PACKET_SIZE];
		inputStream.read(recievedBytes);

		// parse message packets
		MessagePacket packet = new MessagePacket();
		HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedBytes);

		if (hmap == null) {
			inputStream.close();
			ouputStream.close();
			s.close();
			return Message.MessageType.MSG_VOIP_ERROR.getValue();
		}
		// check the replied message status
		int statusMsg = CommonFunctions.ByteArrayToInt(hmap.get("messageType"));

		if (statusMsg == Message.MessageType.MSG_VOIP_CALL_INITIATE_OK.getValue()) {
			result = Message.MessageType.MSG_VOIP_CALL_INITIATE_OK.getValue();
		} else if (statusMsg == Message.MessageType.MSG_VOIP_CALL_BUSY.getValue()) {
			result = Message.MessageType.MSG_VOIP_CALL_BUSY.getValue();
		} else if (statusMsg == Message.MessageType.MSG_VOIP_CALL_WAITING.getValue()) {
			result = Message.MessageType.MSG_VOIP_CALL_WAITING.getValue();
		} else {
			result = Message.MessageType.MSG_VOIP_ERROR.getValue();
		}

		// close socket
		inputStream.close();
		ouputStream.close();
		s.close();
		return result;
	}

	public int InformCalleeToInitiate(String IPAddr) throws IOException {
		int result = 1;
		Socket s = new Socket();
		s.connect(new InetSocketAddress(IPAddr, PeerStatusPort), TimeOut); // connect
																			// to
																			// the
																			// peer

		// set up TCP stream reader and writer
		InputStream inputStream = s.getInputStream();
		OutputStream outputStream = s.getOutputStream();

		MessageFields fields = new MessageFields();
		fields.setIpv4_address(ownIPAddress);
		fields.setIpv4_address_ofcallee(IPAddr);
		fields.setPseudo_identity_caller(ownPseudoIdentity);
		fields.setPseudo_identity_callee(pseudoIdentityOfOtherPeer);
		fields.setPort_number("" + PeerStatusPort);

		MessageFactory msgfac = new MessageFactory();
		Message message = msgfac.createGenericMessage("VOIP_MESSAGE");
		message.SetMessageFields(fields);
		byte[] msg = message.createMessage("MSG_VOIP_CALL_START");

		outputStream.write(msg);
		outputStream.flush();
		// close streams
		inputStream.close();
		outputStream.close();
		// close socket
		s.close();

		return result;
	}

	public int ValidatePeer(String IPAddr) throws IOException {
		int result = Message.MessageType.MSG_VOIP_ERROR.getValue();
		Socket s = new Socket();
		s.connect(new InetSocketAddress(IPAddr, PeerStatusPort), TimeOut); // connect
																			// to
																			// the
																			// peer

		// set up TCP stream reader and writer
		InputStream inputStream = s.getInputStream();
		OutputStream outputStream = s.getOutputStream();

		MessageFields fields = new MessageFields();
		MessageFactory msgfac = new MessageFactory();
		Message message = msgfac.createGenericMessage("VOIP_MESSAGE");
		fields.setPseudo_identity(ownPseudoIdentity);
		message.SetMessageFields(fields);

		byte[] msg = message.createMessage("MSG_VOIP_HEART_BEAT");
		outputStream.write(msg);
		outputStream.flush();

		byte[] recievedBytes = new byte[MessagePacket.PACKET_SIZE];
		int recievedBytesCount = inputStream.read(recievedBytes); // wait and
																	// read the
																	// reply

		// parse message packets
		MessagePacket packet = new MessagePacket();
		HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedBytes);

		if (hmap == null || recievedBytesCount == 0) {
			inputStream.close();
			outputStream.close();
			s.close();
			return result;
		}
		// check the replied message status
		int statusMsg = CommonFunctions.ByteArrayToInt(hmap.get("messageType"));
		String psuedoIdentity = CommonFunctions.ByteArrayToString(hmap.get("pseudo_identity"));
		// check the replied message status
		if (statusMsg == Message.MessageType.MSG_VOIP_HEART_BEAT_REPLY.getValue()) {
			result = 1;
			if (status == PEER_STATUS_SESSION && pseudoIdentityOfOtherPeer.compareToIgnoreCase(psuedoIdentity) != 0) {
				result = Message.MessageType.MSG_VOIP_ERROR.getValue();
			}
		}

		// close streams
		inputStream.close();
		outputStream.close();
		// close socket
		s.close();

		return result;
	}

	public void quitSession(String IPAddress) {
		if (status != PEER_STATUS_SESSION)
			return;
		try {
			Socket s = new Socket(); // create a client socket
			s.connect(new InetSocketAddress(IPAddress, PeerStatusPort)); // connect
																			// to
																			// the
																			// guy
			OutputStream outputStream = s.getOutputStream();

			MessageFields fields = new MessageFields();
			MessageFactory msgfac = new MessageFactory();
			Message message = msgfac.createGenericMessage("VOIP_MESSAGE");
			fields.setIpv4_address(ownIPAddress);
			fields.setIpv4_address_ofcallee(ipAddressOfOtherPeer);
			fields.setPseudo_identity_caller(ownPseudoIdentity);
			fields.setPseudo_identity_callee(pseudoIdentityOfOtherPeer);
			fields.setPort_number("" + PeerStatusPort);
			message.SetMessageFields(fields);

			byte[] msg = message.createMessage("MSG_VOIP_CALL_CALL_END");

			outputStream.write(msg);
			outputStream.flush();
			outputStream.close();
			s.close();
		} catch (Exception e) {
			logger.error("ConnectionStatusManager: Errors occur when sending quitting the session with " + IPAddress);
			logger.error("ConnectionStatusManager: " + e.getMessage());
			System.exit(1);
		}
	}

	public void setStatus(int newStatus) {
		status = newStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setIPAddressOfOtherPeer(String IPAddress) {
		ipAddressOfOtherPeer = IPAddress;
	}

	public String getOwnIPAddress() {
		return ownIPAddress;
	}

	public String getIPAddressOfOtherPeer() {
		return ipAddressOfOtherPeer;
	}

	public void setPeerStatusPort(int PeerStatusPort) {
		this.PeerStatusPort = PeerStatusPort;
	}

	public int GetPeerStatusPort() {
		return this.PeerStatusPort;
	}

	public void run() {
		try {
			// open a server TCP socket receiving the incoming call requests
			ServerSocket ss = new ServerSocket(PeerStatusPort);
			for (;;) {
				Socket client = ss.accept(); // accept a connect from a peer
				// set up read and write streams
				InputStream inputStream = client.getInputStream();
				OutputStream outputStream = client.getOutputStream();

				byte[] recievedBytes = new byte[MessagePacket.PACKET_SIZE];
				inputStream.read(recievedBytes);

				// parse message packets
				MessageFields fields = new MessageFields();
				MessageFactory msgfac = new MessageFactory();
				Message message = null;
				byte[] msg = null;

				MessagePacket packet = new MessagePacket();
				HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedBytes);
				if (hmap == null) {
					message = msgfac.createGenericMessage("VOIP_MESSAGE");
					fields.setMessageType("MSG_VOIP_CALL_INITIATE");
					fields.setPseudo_identity(ownPseudoIdentity);
					message.SetMessageFields(fields);

					msg = message.createMessage("MSG_VOIP_ERROR");
					outputStream.write(msg);
					outputStream.flush();
				}
				// check the replied message status
				int statusMsg = CommonFunctions.ByteArrayToInt(hmap.get("messageType"));

				if (statusMsg == Message.MessageType.MSG_VOIP_CALL_INITIATE.getValue()) // if
																						// the
																						// message
																						// is
																						// a
																						// initiate
				{
					fields.setIpv4_address(CommonFunctions.ByteArrayToString(hmap.get("ipv4_caller")));
					fields.setIpv4_address_ofcallee(ownIPAddress);
					fields.setPseudo_identity_caller(
							CommonFunctions.ByteArrayToString(hmap.get("pseudo_identity_caller")));
					fields.setPseudo_identity_callee(ownPseudoIdentity);
					fields.setPort_number("" + PeerStatusPort);

					switch (status) // check my current status
					{
					case PEER_STATUS_IDLE:
						message = msgfac.createGenericMessage("VOIP_MESSAGE");
						message.SetMessageFields(fields);
						msg = message.createMessage("MSG_VOIP_CALL_INITIATE_OK");

						outputStream.write(msg);
						outputStream.flush();
						break;
					case PEER_STATUS_SESSION:
						message = msgfac.createGenericMessage("VOIP_MESSAGE");
						message.SetMessageFields(fields);
						msg = message.createMessage("MSG_VOIP_CALL_BUSY");

						outputStream.write(msg);
						outputStream.flush();
						break;
					case PEER_STATUS_WAITING:
						message = msgfac.createGenericMessage("VOIP_MESSAGE");
						message.SetMessageFields(fields);
						msg = message.createMessage("MSG_VOIP_CALL_WAITING");

						outputStream.write(msg);
						outputStream.flush();
						break;
					case PEER_STATUS_ERROR:
						message = msgfac.createGenericMessage("VOIP_MESSAGE");
						fields.setMessageType("MSG_VOIP_CALL_INITIATE");
						fields.setPseudo_identity(ownPseudoIdentity);
						message.SetMessageFields(fields);

						msg = message.createMessage("MSG_VOIP_ERROR");
						outputStream.write(msg);
						outputStream.flush();
						break;
					default: // unknown error
						message = msgfac.createGenericMessage("VOIP_MESSAGE");
						fields.setMessageType("MSG_VOIP_CALL_INITIATE");
						fields.setPseudo_identity(ownPseudoIdentity);
						message.SetMessageFields(fields);

						msg = message.createMessage("MSG_VOIP_ERROR");
						outputStream.write(msg);
						outputStream.flush();
						logger.error("ConnectionStatusManager: Unknow Peer status");
						System.exit(1);
						break;
					}
				} else if (statusMsg == Message.MessageType.MSG_VOIP_HEART_BEAT.getValue()) {
					message = msgfac.createGenericMessage("VOIP_MESSAGE");
					fields.setPseudo_identity(ownPseudoIdentity);
					message.SetMessageFields(fields);

					msg = message.createMessage("MSG_VOIP_HEART_BEAT_REPLY");
					outputStream.write(msg);
					outputStream.flush();
				} else if (statusMsg == Message.MessageType.MSG_VOIP_CALL_START.getValue()) // if
																							// the
																							// message
																							// is
																							// an
																							// incoming
																							// call
																							// request
				{
					ipAddressOfOtherPeer = CommonFunctions.ByteArrayToString(hmap.get("ipv4_caller"));
					pseudoIdentityOfOtherPeer = CommonFunctions.ByteArrayToString(hmap.get("pseudo_identity_caller"));
					this.status = PEER_STATUS_SESSION;
					callManager.startCall(ipAddressOfOtherPeer); // start call
				} else if (statusMsg == Message.MessageType.MSG_VOIP_CALL_CALL_END.getValue()) {
					callManager.ForceStopCall();
				} else if (statusMsg == Message.MessageType.MSG_VOIP_PUBLICKEY.getValue()) {
					byte[] dhspecg = hmap.get("dhspecg");
					byte[] dhspecp = hmap.get("dhspecp");
					byte[] dhspecl = hmap.get("dhspecl");
					byte[] dh_public_key_caller = hmap.get("dh_public_key_caller");
					//
					// logger.info("dhspecg " + dhspecg.length);
					// logger.info("dhspecp " + dhspecp.length);
					// logger.info("dhspecl " + dhspecl.length);

					KeyPairGenerator kpg = KeyPairGenerator.getInstance("DH");
					BigInteger bigIntdhspecp = new BigInteger(dhspecp);
					BigInteger bigdhspecg = new BigInteger(dhspecg);

					DHParameterSpec dhSpec = new DHParameterSpec(bigIntdhspecp, bigdhspecg,
							CommonFunctions.ByteArrayToInt(dhspecl));
					kpg.initialize(dhSpec);
					KeyPair kp = kpg.generateKeyPair();

					// get keypair
					/*
					 * PseudoIdentityManager identity = new
					 * PseudoIdentityManager(); byte[] psuedoIdentity = null; if
					 * (!identity.psuedoIdentityArr.isEmpty()) { psuedoIdentity
					 * = identity.psuedoIdentityArr.get(0); } else { message =
					 * msgfac.createGenericMessage("VOIP_MESSAGE");
					 * fields.setMessageType("MSG_VOIP_PUBLICKEY");
					 * fields.setPseudo_identity(ownPseudoIdentity);
					 * message.SetMessageFields(fields);
					 * 
					 * msg = message.createMessage("MSG_VOIP_ERROR");
					 * outputStream.write(msg); outputStream.flush(); }
					 */

					// KeyPair keyPair =
					// identity.cachedMapOwn.get(psuedoIdentity);

					message = msgfac.createGenericMessage("VOIP_MESSAGE");
					fields.setDh_public_key_callee(kp.getPublic().getEncoded());
					message.SetMessageFields(fields);

					msg = message.createMessage("MSG_VOIP_PUBLICKEY_REPLY");
					outputStream.write(msg);
					outputStream.flush();

					// Secret Generate for callee
					// Step 5 part 1: Bob uses his private key to perform the
					// first phase of the protocol
					KeyAgreement ka = KeyAgreement.getInstance("DH");
					ka.init(kp.getPrivate());

					// Step 5 part 2: Bob uses Alice's public key to perform
					// the second phase of the protocol.
					KeyFactory kf = KeyFactory.getInstance("DH");
					X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(dh_public_key_caller);
					PublicKey pk = kf.generatePublic(x509Spec);
					ka.doPhase(pk, true);

					// Step 5 part 3: Bob generates the secret key
					byte secret[] = ka.generateSecret();

					// Step 6: Bob generates a DES key
					SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
					DESKeySpec desSpec = new DESKeySpec(secret);
					this.callManager.getVOIP().setSecretKey(skf.generateSecret(desSpec));
				} else {
					message = msgfac.createGenericMessage("VOIP_MESSAGE");
					fields.setMessageType("MSG_VOIP_CALL_INITIATE");
					fields.setPseudo_identity(ownPseudoIdentity);
					message.SetMessageFields(fields);

					msg = message.createMessage("MSG_VOIP_ERROR");
					outputStream.write(msg);
					outputStream.flush();
				}

				inputStream.close();
				outputStream.close();
			}
		} catch (Exception e) {
			logger.error("ConnectionStatusManager: " + e.getMessage());
			System.exit(1);
		}
	}
}