package datacontrol;

import java.util.concurrent.*;
import org.apache.commons.logging.Log;
import ui.ClientUI;
import logger.LogSetup;

/**
 * RTPSender into a thread
 * @author Zeeshan
 *
 */

public class SenderThread extends Thread {
	
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());
	
	private RTPSender Sender;
	/**
	 * Audio block is obtained from this queue.
	 * RTPSender is consumer, SoundRecoder is producer.
	 */
	private BlockingQueue<AudioDataBlock> CarrierQueue;
	/**
	 * RTPSender return the block into this queue.
	 * RTPSender is producer, SoundRecorder is consumer.
	 */
	private BlockingQueue<AudioDataBlock> ReturnQueue;

	public SenderThread(RTPSender sender, BlockingQueue<AudioDataBlock> carrierQueue,
			BlockingQueue<AudioDataBlock> returnQueue)
	{
		Sender = sender;
		CarrierQueue = carrierQueue;
		ReturnQueue = returnQueue;
	}

	public void run()
	{		
		try
		{
			while(true)
			{
				AudioDataBlock block = CarrierQueue.take(); // take an audio block
				if(block.actualDataBlockSize == -1)
				{
					break;
				}
				Sender.sendData(block.data); // send audio data out
				ReturnQueue.put(block); // put the block back
			}
		}
		catch(Exception e)
		{
			logger.error("SenderThread: " + e.getMessage());
		}
	}
}