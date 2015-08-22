package datacontrol;


import java.net.*;

import jlibrtp.*;

import org.apache.commons.logging.Log;

import crypto.MD5Hash;
import crypto.SessionKeyEstablishment;
import ui.ClientUI;
import voip.ConnectionStatusManager;
import voip.VOIP;
import logger.LogSetup;
import messages.Message;
import messages.MessageFactory;
import messages.MessageFields;

/**
 * Sends out audio data to Sender
 * @author Zeeshan
 *
*/
public class RTPSender implements RTPAppIntf {
	
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());
	
	private RTPSession RTPSession;
	private int ReceiverRtpPort; // UDP port used for RTP communication
	private VOIP voip;

	public RTPSender(VOIP voip, String receiverIPAddr, int receiverRTPport)
	{
		this.voip = voip;
		ReceiverRtpPort = receiverRTPport;
		int ReceiverRtcpPort = ReceiverRtpPort + 1;

		logger.info("RTPSender: RTP port number is" + ReceiverRtpPort);
		logger.info("RTPSender: RTCP port number is" + ReceiverRtcpPort);
		
		// create the UDP sockets for RTP and RTCP
		DatagramSocket rtpSocket = null;
		DatagramSocket rtcpSocket = null;

		try
		{
			rtpSocket = new DatagramSocket();			
			rtcpSocket = new DatagramSocket();			
		}
		catch(Exception e)
		{
			logger.error("RTPSender: RTP session failed to obtain ports.");
			logger.error("RTPSender: " + e.getMessage());
			System.exit(1);
		}

		// create RTP session
		RTPSession = new RTPSession(rtpSocket, rtcpSocket);
		RTPSession.RTPSessionRegister(this, null, null);

		// set the participants for the RTP session
		Participant p = new Participant(receiverIPAddr, ReceiverRtpPort, ReceiverRtcpPort);
		RTPSession.addParticipant(p);
	}

	public int frameSize(int payloadType) {
		return 1;
	}

	public void receiveData(DataFrame frame, Participant participant) {
	}

	public void userEvent(int type, Participant[] participant) {
	}

	public void sendData(byte data[])
	{
		MessageFields fields = new MessageFields();
		MessageFactory msgfac = new MessageFactory();
		ConnectionStatusManager status = voip.getPeerStatus();
		MD5Hash md5Hash = new MD5Hash();
		Message message = msgfac.createGenericMessage("VOIP_MESSAGE");
		fields.setIpv4_address(status.getOwnIPAddress());
		fields.setIpv4_address_ofcallee(status.getIPAddressOfOtherPeer());
		fields.setPseudo_identity_caller(status.GetOwnPseudoIdentity());
		fields.setPseudo_identity_callee(status.GetPseudoIdentityOfOtherPeer());
		fields.setAudio_data(data);
		fields.setHashMD5(md5Hash.getMD5HashOfData(data));
		fields.setPort_number(String.valueOf(status.GetPeerStatusPort()));
		message.SetMessageFields(fields);
		byte[] msg = message.createMessage("MSG_VOIP_CALL_DATA");

		RTPSession.sendData(msg);
	}

	public void close()
	{
		RTPSession.endSession();
	}
}
