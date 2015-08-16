/**
 * 
 */
package messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import messages.Message.MessageType;

/**
 * MessagePacket.java
 * 
 * This class creates the various types of messages, as requested by the
 * specific module classes (DHT, KX and VOIP).
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 * 
 * 
 *         Handles the following messages : DHT_GET, DHT_PUT, DHT_TRACE,
 *         MSG_KX_TN_READY, MSG_VOIP_CALL_INITIATE, MSG_VOIP_CALL_OK,
 *         MSG_VOIP_CALL_BUSY, MSG_VOIP_CALL_WAITING, MSG_VOIP_CALL_INITIATE_OK,
 *         MSG_VOIP_CALL_DATA, MSG_VOIP_CALL_CALL_END, MSG_VOIP_HEART_BEAT,
 *         MSG_VOIP_HEART_BEAT_REPLY
 */
public class MessagePacket {

	public byte[] createMessagePacket(MessageType msgtype) {
		if (msgtype.equals(null)) {
			return null;
		}

		if (msgtype.equals(MessageType.DHT_GET)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_DHT_GET", 16);
			byte[] key = getPaddedByteArray("", 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(key);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.DHT_PUT)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_DHT_PUT", 16);
			byte[] key = getPaddedByteArray("", 32);
			byte[] ttl = getPaddedByteArray("", 16);
			byte[] replication = getPaddedByteArray("", 8);
			byte[] reserveda = getPaddedByteArray("", 16);
			byte[] reservedb = getPaddedByteArray("", 32);
			// TODO : Add here Content -- Message Payload
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(key);
			tempList.add(ttl);
			tempList.add(replication);
			tempList.add(reserveda);
			tempList.add(reservedb);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.DHT_TRACE)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_DHT_TRACE", 16);
			byte[] key = getPaddedByteArray("", 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(key);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_KX_TN_READY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_KX_TN_READY", 16);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			byte[] reserved = getPaddedByteArray("", 32);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] ipv6_address = getPaddedByteArray("", 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(pseudo_identity);
			tempList.add(reserved);
			tempList.add(ipv4_address);
			tempList.add(ipv6_address);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_INITIATE", 16);
			byte[] pseudo_identity_calle = getPaddedByteArray("", 32);
			byte[] num_tries = getPaddedByteArray("", 8);
			byte[] reserved = getPaddedByteArray("", 24);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(pseudo_identity_calle);
			tempList.add(num_tries);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE_OK)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_INITIATE_OK", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			byte[] num_tries = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 16);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			tempList.add(num_tries);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_BUSY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_BUSY", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			byte[] port_number = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 16);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_WAITING)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_WAITING", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			byte[] port_number = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 16);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_DATA)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_DATA", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);

			byte[] audio_data = getPaddedByteArray("", 16000); // 16 kilobyte

			byte[] hashMD5 = getPaddedByteArray("", 16);
			byte[] port_number = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 16);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			tempList.add(audio_data);
			tempList.add(hashMD5);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_CALL_END)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_CALL_END", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			byte[] port_number = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 16);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_HEART_BEAT", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			byte[] port_number = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 16);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT_REPLY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_HEART_BEAT_REPLY", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			byte[] ok_bit = getPaddedByteArray("", 1); // Boolean byte, where 1
														// - OK or Alive and 0
			// otherwise. // 1 byte
			byte[] port_number = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 15);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			tempList.add(ok_bit);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, 64000);
			return msg_final;
		}

		return null;
	}

	/**
	 * @param msg
	 * @param i
	 * @return
	 */
	private byte[] getPaddedByteArrayFinal(byte[] src, int size) {
		// TODO Auto-generated method stub
		byte[] dest = new byte[size];
		System.arraycopy(src, 0, dest, 0, src.length);
		return dest;
	}

	/**
	 * Reads the messaged packet and then decrypts the same and returns a string
	 * with Message Type (As declared in the Messages.java enum)
	 * 
	 * @param bytestream
	 * @return String which is the type of message returned.
	 */
	public String getMessagePacketType(byte[] mpacket) {
		// Code Here
		byte[] msgtype = Arrays.copyOfRange(mpacket, 16, 33);
		String result = printByteArrayContents(msgtype);
		return result;
	}

	/**
	 * Read the contents of the message packet. This function call is preceeded
	 * by a call to readMessagePacketHeader(), to get the type of message.
	 * 
	 * @param msgtype
	 * @return
	 */
	public byte[] readMessagePacket(MessageType msgtype) {

		if (msgtype.equals(MessageType.DHT_GET)) {

		} else if (msgtype.equals(MessageType.DHT_PUT)) {

		} else if (msgtype.equals(MessageType.DHT_TRACE)) {

		} else if (msgtype.equals(MessageType.MSG_KX_TN_READY)) {

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE)) {

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_BUSY)) {

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_WAITING)) {

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE_OK)) {

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_DATA)) {

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_CALL_END)) {

		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT)) {

		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT_REPLY)) {

		}

		return null;
	}

	/**
	 * Returns a padded byte array, the size is the size of the resulting padded
	 * array.
	 * 
	 * @param txt
	 * @param size
	 * @return
	 */
	public byte[] getPaddedByteArray(String txt, int size) {
		byte[] src = txt.getBytes();
		int len = src.length;
		int diff = size - len;
		byte[] dest = new byte[size];
		System.out.println("Difference: " + diff);
		System.arraycopy(src, 0, dest, 0, src.length);
		return dest;
	}

	/**
	 * Concatenate a list of byte arrays and returns the final array.
	 * 
	 * @param blocks
	 * @return byte array of resultant byte arrays.
	 */

	public byte[] concatenateByteArrays(ArrayList<byte[]> blocks) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		for (byte[] b : blocks) {
			os.write(b, 0, b.length);
		}
		byte[] result = os.toByteArray();
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Print the contents of the byte array
	 * 
	 * @param src
	 * @return
	 */
	public String printByteArrayContents(byte[] src) {
		return new String(new BigInteger(new BigInteger(src).toString(2), 2).toByteArray()).trim();
	}

}
