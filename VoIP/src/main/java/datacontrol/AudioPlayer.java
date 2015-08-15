package datacontrol;

import java.util.concurrent.*;
import javax.sound.sampled.*;
import org.apache.commons.logging.Log;
import ui.ClientUI;
import logger.LogSetup;

/**
 * Access the audio device and play data
 * @author Zeeshan
 *
 */
public class AudioPlayer extends Thread {
	
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	private AudioFormat audioFormat;
	private SourceDataLine sourceDataLine; // sound device source line
	/**
	 * Take the block from this queue and play it.
	 * AudioPlayer is consumer, RTPReceiver is producer.
	 */
	private BlockingQueue<AudioDataBlock> CarrierQueue;
	/**
	 * After playing, return the block into this queue.
	 * AudioPlayer is producer, RTPReceiver is consumer.
	 */
	private BlockingQueue<AudioDataBlock> ReturnQueue;

	public AudioPlayer(AudioFormat audioFormat, BlockingQueue<AudioDataBlock> carrierQueue,
			BlockingQueue<AudioDataBlock> returnQueue)
	{
		CarrierQueue = carrierQueue;
		ReturnQueue = returnQueue;
		this.audioFormat = audioFormat;

		// obtain sound device
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, this.audioFormat);
		try
		{
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceDataLine.open(this.audioFormat);
		}
		catch(LineUnavailableException e)
		{
			logger.error("AudioPlayer: Unable to open the audio playback device.");
			logger.error("AudioPlayer: " + e.getMessage());
			System.exit(1);
		}
		catch(Exception e)
		{
			logger.error("AudioPlayer: Unknown error.");
			logger.error("AudioPlayer: " + e.getMessage());
			System.exit(1);
		}
	}

	public void start()
	{
		sourceDataLine.start();
		super.start();
	}
	
	public void stopPlaying()
	{
		sourceDataLine.drain();
		sourceDataLine.close();
	}

	public void run()
	{
		try
		{
			while(true)
			{
				AudioDataBlock block = CarrierQueue.take(); // first take audio block and play it
				if(block.actualDataBlockSize == -1)
					break;
				sourceDataLine.write(block.data, 0, block.actualDataBlockSize);
				ReturnQueue.put(block); // return played block
			}
		}
		catch(Exception e)
		{
			logger.error("AudioPlayer: " + e.getMessage());
		}
	}
}
