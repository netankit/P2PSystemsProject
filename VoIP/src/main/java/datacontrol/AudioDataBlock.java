package datacontrol;

/**
 * Audio data packet
 * @author Zeeshan
 *
 */

public class AudioDataBlock {

	public int dataBlockSize; // data block size
	public byte data[]; // audio data array
	public int actualDataBlockSize; // number of actual bytes stored in the data array i.e. in case data is less than blockSize variable
	
	public AudioDataBlock(int audioDataSizeInBytes)
	{
		dataBlockSize = 512; // default size is 512 bytes
		if (audioDataSizeInBytes > 0)
			dataBlockSize = audioDataSizeInBytes;
		
		data = new byte[dataBlockSize];
	}
}
