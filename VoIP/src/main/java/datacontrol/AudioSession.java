package datacontrol;

import java.util.concurrent.*;
import javax.sound.sampled.AudioFormat;
import org.apache.commons.logging.Log;
import ui.ClientUI;
import logger.LogSetup;


/**
 * Voice Session over RTP
 * This class connects AudioRecord with SenderThread, AudioPlayer with RTPReceiver.
 * @author Zeeshan
 *
 */
public class AudioSession {

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	public int DataBlockSize = 512;
	public int RecieverPort = 12000; // UDP port used for RTP session
	// Maximum capacity of the queues used for exchanging data between AudioRecord <-> SenderThread
	// and AudioPlayer <-> RTPReceiver. 
	public int QueueCapacity = 4;
	private AudioFormat audioFormat;
	
	// the queue for exchanging data between AudioRecord and SenderThread
	private BlockingQueue<AudioDataBlock> CarrierQueueForSender;
	// queue for exchanging data between AudioRecord and SenderThread
	private BlockingQueue<AudioDataBlock> ReturnQueueForSender;
	// queue for exchanging data between AudioPlayer and RTPReceiver
	private BlockingQueue<AudioDataBlock> CarrierQueueForReceiver;
	// queue for exchanging data between SoundPlayer and RTPReceiver
	private BlockingQueue<AudioDataBlock> ReturnQueueForReceiver;
	
	private AudioRecord Recorder;
	private AudioPlayer Player;
	private RTPReciever Receiver;
	private RTPSender Sender;
	private SenderThread SendThread;
	
	public AudioSession(int dataBlockSize, int recievePort)
	{
		if (dataBlockSize > 0)
			DataBlockSize = dataBlockSize;
		if (recievePort != 0)
			RecieverPort = recievePort; 
		
		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, true);
		CarrierQueueForSender = new ArrayBlockingQueue(QueueCapacity);
		ReturnQueueForSender = new ArrayBlockingQueue(QueueCapacity);
		CarrierQueueForReceiver = new ArrayBlockingQueue(QueueCapacity);
		ReturnQueueForReceiver = new ArrayBlockingQueue(QueueCapacity);
	}

	public void initSession(String participantIPAddress)
	{
		CarrierQueueForSender.clear();
		ReturnQueueForSender.clear();
		CarrierQueueForReceiver.clear();
		ReturnQueueForReceiver.clear();
		
		try
		{
			for(int i = 0; i < QueueCapacity; i++)
			{			
				ReturnQueueForSender.put(new AudioDataBlock(DataBlockSize));
				ReturnQueueForReceiver.put(new AudioDataBlock(DataBlockSize));
			}
		}
		catch(Exception e)
		{
			logger.error("AudioSession: Voice session initialization error.");
			logger.error("AudioSession: " + e.getMessage());
			System.exit(1);
		}
		
		// set up connections
		Recorder = new AudioRecord(audioFormat, CarrierQueueForSender, ReturnQueueForSender);		
		Player = new AudioPlayer(audioFormat, CarrierQueueForReceiver, ReturnQueueForReceiver);
		Receiver = new RTPReciever(RecieverPort, CarrierQueueForReceiver, ReturnQueueForReceiver);
		Sender = new RTPSender(participantIPAddress, RecieverPort);
		SendThread = new SenderThread(Sender, CarrierQueueForSender, ReturnQueueForSender);
	}
	
	public void startSession()
	{
		SendThread.start();
		Recorder.start();
		Player.start();
	}
	
	public void stopSession()
	{
		// stop the recorder and sender
		Recorder.stopRecording();
		Sender.close();
		
		// stop the receiver and player
		Receiver.stopReceiver();
		Player.stopPlaying();
	}
}