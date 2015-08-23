package datacontrol;

import java.util.concurrent.BlockingQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.apache.commons.logging.Log;

import logger.LogSetup;

/**
 * Get the data from microphone
 * 
 * @author Zeeshan
 *
 */

public class AudioRecord extends Thread {

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(AudioRecord.class.getName());

	private AudioFormat audioFormat;
	private TargetDataLine targetDataLine; // microphone line
	private AudioInputStream audioInputStream;
	/**
	 * when an audio block is ready, put it back into this queue. AudioRecord is
	 * the producer, SenderThread is consumer.
	 */
	private BlockingQueue<AudioDataBlock> CarrierQueue;
	/**
	 * AudioRecord is going to sample a block, obtain it first from this queue.
	 * AudioRecord is the consumer, SenderThread is producer.
	 */
	private BlockingQueue<AudioDataBlock> ReturnQueue;

	public AudioRecord(AudioFormat audioFormat, BlockingQueue<AudioDataBlock> carrierQueue,
			BlockingQueue<AudioDataBlock> returnQueue) {
		CarrierQueue = carrierQueue;
		ReturnQueue = returnQueue;
		this.audioFormat = audioFormat;

		// query for the audio device
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, this.audioFormat);
		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(this.audioFormat);
		} catch (LineUnavailableException e) {
			logger.error("AudioRecord: Unable to open the audio capture device.");
			logger.error("AudioRecord: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			logger.error("AudioRecord: Unknown error.");
			logger.error("AudioRecord: " + e.getMessage());
			System.exit(1);
		}
		// further abstract the device as a stream
		audioInputStream = new AudioInputStream(targetDataLine);
	}

	public void start() {
		targetDataLine.start();
		super.start();
	}

	public void stopRecording() {
		// first stop the device
		targetDataLine.stop();
		targetDataLine.close();

		try {
			AudioDataBlock block = ReturnQueue.take();
			block.actualDataBlockSize = -1; // notify the SenderThread that
											// there is no block any more.
			CarrierQueue.put(block);
		} catch (Exception e) {
			logger.error("AudioRecord: " + e.getMessage());
		}
	}

	public void run() {
		try {
			while (true) {
				// check the device status
				if (!targetDataLine.isOpen())
					break;
				AudioDataBlock block = ReturnQueue.take();
				block.actualDataBlockSize = audioInputStream.read(block.data, 0, block.dataBlockSize);
				CarrierQueue.put(block);
			}
		} catch (Exception e) {
			logger.error("AudioRecord: " + e.getMessage());
		}
	}
}