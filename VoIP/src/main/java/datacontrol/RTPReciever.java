package datacontrol;

import java.util.HashMap;
import java.util.concurrent.*;
import java.net.*;

import org.apache.commons.logging.Log;

import crypto.MD5Hash;
import ui.ClientUI;
import voip.VOIP;
import jlibrtp.*;
import logger.LogSetup;
import messages.Message;
import messages.MessageFields;
import messages.MessagePacket;

/**
 * Receives data from a RTP session
 * @author Zeeshan
 *
*/

public class RTPReciever  implements RTPAppIntf{

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	private RTPSession RTPSession;
	// Audio block is received, put the block into this queue.
	// RTPReceiver is the producer, AudioPlayer is the consumer.
	private BlockingQueue<AudioDataBlock> CarrierQueue;
	// Audio block is received, RTPReceiver first obtain a blank block from this queue and then write the block.
	// RTPReceiver is the consumer, AudioPlayer is the producer.
	private BlockingQueue<AudioDataBlock> ReturnQueue;
	// UDP port for RTP communication
	private int RTPPort;
	private VOIP voip;

	public RTPReciever(VOIP voip, int rtpPort, BlockingQueue<AudioDataBlock> carrierQueue, BlockingQueue<AudioDataBlock> returnQueue)
	{		
		this.voip = voip;
		RTPPort = rtpPort;
		int rtcpPort = RTPPort + 1; // port for RTCP communication is always the next one  
		CarrierQueue = carrierQueue;
		ReturnQueue = returnQueue;
		
		logger.info("RTPReciever: RTP port number is" + RTPPort);
		logger.info("RTPReciever: RTCP port number is" + rtcpPort);
		// create the UDP sockets for RTP and RTCP
		DatagramSocket rtpSocket = null;
		DatagramSocket rtcpSocket = null;
		try
		{
			rtpSocket = new DatagramSocket(RTPPort);
			rtcpSocket = new DatagramSocket(rtcpPort);
		}
		catch(Exception e)
		{
			logger.error("RTPReciever: RTP session failed to obtain ports.");
			logger.equals("RTPReciever: " + e.getMessage());
			System.exit(1);
		}
		
		// create the RTP session and set up it
		RTPSession = new RTPSession(rtpSocket, rtcpSocket);	
		RTPSession.naivePktReception(true);		
		RTPSession.RTPSessionRegister(this, null, null);
	}

	public int frameSize(int payloadType) {
		return 1;
	}

	public void receiveData(DataFrame frame, Participant participant) {
		byte[] recievedPacket = frame.getConcatenatedData(); // obtain the audio bytes
		MessagePacket packet = new MessagePacket();
		MessageFields fields = new MessageFields();
		fields.setDhSecretKey(this.voip.getSecretKey());
		packet.SetMessageFields(fields);
		HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedPacket);

		byte[] data = hmap.get("audio_data");
		// here we have to do the block verification and etc.
		AudioDataBlock block = null;
		try
		{
			block = ReturnQueue.take();
		}
		catch(Exception e)
		{
			logger.error("RTPReciever: " + e.getMessage());
		}
		
		System.arraycopy(data, 0, block.data, 0, block.dataBlockSize); // copy the audio data
		block.actualDataBlockSize = block.dataBlockSize;
		
		MD5Hash md5Hash = new MD5Hash();
		byte[] recievedBytesHash = md5Hash.getMD5HashOfData(block.data);
		byte[] storedBytesHash = hmap.get("hashMD5");
		if (!md5Hash.CheckMD5HashOfData(recievedBytesHash, storedBytesHash))
		{
			logger.error("RTPReciever: Voice data is corrupted");
			return;
		}
		
		try
		{
			CarrierQueue.put(block); // put the block into the carrier queue 
		}
		catch(Exception e)
		{
			logger.error("RTPReciever: " + e.getMessage());
		}		
	}

	public void userEvent(int type, Participant[] participant) {
	}
		
	public void stopReceiver()
	{
		RTPSession.endSession(); // first close the RTP session
		AudioDataBlock block = null;
		try
		{
			block = ReturnQueue.take();
			block.actualDataBlockSize = -1; // notify the AudioPlayer that there is no audio data
			CarrierQueue.put(block);
		}
		catch(Exception e)
		{
			logger.error("RTPReciever: " + e.getMessage());
		}
	}
}