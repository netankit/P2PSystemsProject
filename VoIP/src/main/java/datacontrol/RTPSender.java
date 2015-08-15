package datacontrol;


import java.net.*;
import jlibrtp.*;
import org.apache.commons.logging.Log;
import ui.ClientUI;
import logger.LogSetup;

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

	public RTPSender(String receiverIPAddr, int receiverRTPport)
	{
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
		RTPSession.sendData(data);
	}

	public void close()
	{
		RTPSession.endSession();
	}
}
