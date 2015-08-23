package crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Set;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.logging.Log;

import ui.ClientUI;
import logger.LogSetup;
import messages.Message;
import messages.MessageFactory;
import messages.MessageFields;
import messages.MessagePacket;
import common.CommonFunctions;

public class SessionKeyEstablish {
	
	private String ipAddress;
	private int portNumber;
	private int TimeOut = 1000;
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	public SessionKeyEstablish(String ipAddress, int portNumber)
	{
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}

	public SecretKey EstablishSessionKey()
	{
		SecretKey secretKey = null;
		try
		{
			Socket s = new Socket();
			s.connect(new InetSocketAddress(ipAddress, portNumber), TimeOut); // connect to the peer
			
			PseudoIdentityManager identity = new PseudoIdentityManager();
			byte[] psuedoIdentity = null;
			if (!identity.psuedoIdentityArr.isEmpty())
			{
				psuedoIdentity = identity.psuedoIdentityArr.get(0);
			}
			else
			{
				return secretKey;
			}
			
			KeyPair keyPair = identity.cachedMapOwn.get(psuedoIdentity);
			KeyFactory kf = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
			PublicKey publicKey = kf.generatePublic(x509Spec);

			Class dhClass = Class.forName("javax.crypto.spec.DHParameterSpec");
			DHParameterSpec dhSpec = ((DHPublicKey) publicKey).getParams();
			BigInteger dhSpecG = dhSpec.getG();
			BigInteger dhSpecP = dhSpec.getP();
			int dhSpecL = dhSpec.getL();
			byte[] public_key_caller = keyPair.getPublic().getEncoded();

			// set up TCP stream reader and writer
			InputStream inputStream = s.getInputStream();
			OutputStream outputStream = s.getOutputStream();
			
			MessageFields fields = new MessageFields();
			MessageFactory msgfac = new MessageFactory();
			Message message = msgfac.createGenericMessage("VOIP_MESSAGE");
			
			fields.setDhspecg(dhSpecG.toByteArray());
			fields.setDhspecp(dhSpecP.toByteArray());
			fields.setDhspecl(String.valueOf(dhSpecL).getBytes());
			fields.setDh_public_key_caller(public_key_caller);
			message.SetMessageFields(fields);
	
			byte[] msg = message.createMessage("MSG_VOIP_PUBLICKEY");
			outputStream.write(msg);
			outputStream.flush();
	
			byte[] recievedBytes = new byte[MessagePacket.PACKET_SIZE];
			int recievedBytesCount = inputStream.read(recievedBytes); // wait and read the reply
			
			// parse message packets
			MessagePacket packet = new MessagePacket();
			HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedBytes);
			
			if (hmap == null || recievedBytesCount == 0)
			{
				inputStream.close();
				outputStream.close();
				s.close();
				return secretKey;
			}
			// check the replied message status
			int statusMsg = CommonFunctions.ByteArrayToInt(hmap.get("messageType"));
			// check the replied message status
			if(statusMsg ==  Message.MessageType.MSG_VOIP_PUBLICKEY_REPLY.getValue())
			{
				byte[] calleePublicKey = hmap.get("dh_public_key_calle");
				// Secret key for caller
				// Step 4 part 1: Alice performs the first phase of the
				// protocol with her private key
				KeyAgreement ka = KeyAgreement.getInstance("DH");
				ka.init(keyPair.getPrivate());
				
				KeyFactory keyFactory = KeyFactory.getInstance("DH");
				X509EncodedKeySpec x509SpecKey = new X509EncodedKeySpec(calleePublicKey);
				PublicKey pk = keyFactory.generatePublic(x509SpecKey);
				ka.doPhase(pk, true);
				
				byte secret[] = ka.generateSecret();

				// Step 6: Alice generates a DES key
				SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
				DESKeySpec desSpec = new DESKeySpec(secret);
				secretKey = skf.generateSecret(desSpec);
			}
			else if (statusMsg == Message.MessageType.MSG_VOIP_ERROR.getValue())
			{
				logger.error("SessionKeyEstablish: unable to establish key with other peer.");
			}
			
			// close streams
			inputStream.close();
			outputStream.close();
			// close socket
			s.close();
		}catch (Exception ex)
		{
			logger.error("SessionKeyEstablish: unable to establish key with other peer " + ex.getMessage());
		}
		
		return secretKey;
	}
}