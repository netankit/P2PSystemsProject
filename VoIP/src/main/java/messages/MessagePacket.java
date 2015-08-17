/**
 * 
 */
package messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
	// Size of the final Message Packet = 64KB or 64000 bytes
	public static final int PACKET_SIZE = 64000;

	public byte[] createMessagePacket(MessageType msgtype) {
		if (msgtype.equals(null)) {
			return null;
		}

		if (msgtype.equals(MessageType.MSG_DHT_GET)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_DHT_GET", 16);
			byte[] key = getPaddedByteArray("AnkitKey", 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(key);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_DHT_PUT)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_DHT_PUT", 16);
			byte[] key = getPaddedByteArray("", 32);
			byte[] ttl = getPaddedByteArray("", 16);
			byte[] replication = getPaddedByteArray("", 8);
			byte[] reserveda = getPaddedByteArray("", 16);
			byte[] reservedb = getPaddedByteArray("", 32);
			// TODO : Add here Content -- Message Payload
			byte[] content = getPaddedByteArray("", 120);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(key);
			tempList.add(ttl);
			tempList.add(replication);
			tempList.add(reserveda);
			tempList.add(reservedb);
			tempList.add(content);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_DHT_TRACE)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_DHT_TRACE", 16);
			byte[] key = getPaddedByteArray("AnkitKey", 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(key);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
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
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_INITIATE", 16);
			byte[] pseudo_identity_callee = getPaddedByteArray("", 32);
			byte[] num_tries = getPaddedByteArray("", 8);
			byte[] reserved = getPaddedByteArray("", 24);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(pseudo_identity_callee);
			tempList.add(num_tries);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE_OK)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_INITIATE_OK", 16);
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
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
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
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
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
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
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
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
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
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
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
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT_REPLY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_HEART_BEAT_REPLY", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			byte[] ok_byte = getPaddedByteArray("", 1); // Boolean byte, where 1
														// - OK or Alive and 0
			// otherwise. // 1 byte
			byte[] port_number = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 15);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			tempList.add(ok_byte);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_START)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_START", 16);
			byte[] ipv4_address_bykx = getPaddedByteArray("", 32);
			byte[] ipv4_address_ofcallee = getPaddedByteArray("", 32);
			byte[] pseudo_identity_callee = getPaddedByteArray("", 32);
			byte[] port_number_callee = getPaddedByteArray("", 16);
			byte[] port_number_caller = getPaddedByteArray("", 16);
			byte[] num_tries = getPaddedByteArray("", 8);
			byte[] reserved = getPaddedByteArray("", 24);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address_bykx);
			tempList.add(ipv4_address_ofcallee);
			tempList.add(pseudo_identity_callee);
			tempList.add(port_number_callee);
			tempList.add(port_number_caller);
			tempList.add(num_tries);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_STARTED)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_CALL_STARTED", 16);
			byte[] ipv4_address_bykx = getPaddedByteArray("", 32);
			byte[] ipv4_address_ofcallee = getPaddedByteArray("", 32);
			byte[] pseudo_identity_caller = getPaddedByteArray("", 32);
			byte[] started_byte = getPaddedByteArray("", 1);
			byte[] port_number_caller = getPaddedByteArray("", 16);
			byte[] reserved = getPaddedByteArray("", 15);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address_bykx);
			tempList.add(ipv4_address_ofcallee);
			tempList.add(pseudo_identity_caller);
			tempList.add(started_byte);
			tempList.add(port_number_caller);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_KX_TN_DESTROY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray("64", 16);
			byte[] messageType = getPaddedByteArray("MSG_VOIP_HEART_BEAT", 16);
			byte[] ipv4_address = getPaddedByteArray("", 32);
			byte[] pseudo_identity = getPaddedByteArray("", 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_address);
			tempList.add(pseudo_identity);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
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
	public MessageType getMessagePacketType(byte[] mpacket) {
		// MessageType offset in the given byte array of the message packet.
		// Starting Position: 16
		// Ending position : 33
		byte[] msgtype = Arrays.copyOfRange(mpacket, 16, 32);
		String resultString = printByteArrayContents(msgtype);
		System.out.println("MessageType: " + resultString);
		MessageType result = castStringToMessageType(resultString);
		return result;
	}

	/**
	 * Returns the bytes of the given message filed type as specified in the
	 * 
	 * @param mtype
	 * @param mpacket
	 * @param startOffset
	 * @param endOffset
	 * @return
	 */
	public byte[] getMessageBytes(byte[] mpacket, int startOffset, int endOffset) {
		// MessageType offset in the given byte array of the message packet.
		// Starting Position: startOffset
		// Ending position : endOffset
		byte[] msgbytes = Arrays.copyOfRange(mpacket, startOffset, endOffset);
		return msgbytes;
	}

	/**
	 * Casts and returns a MessageType equivalent of the passed String in
	 * paramter.
	 * 
	 * @param str
	 * @return
	 */
	public MessageType castStringToMessageType(String str) {
		if (str.equalsIgnoreCase("MSG_DHT_GET")) {
			return MessageType.MSG_DHT_GET;
		} else if (str.equalsIgnoreCase("MSG_DHT_PUT")) {
			return MessageType.MSG_DHT_PUT;
		} else if (str.equalsIgnoreCase("MSG_DHT_TRACE")) {
			return MessageType.MSG_DHT_TRACE;
		} else if (str.equalsIgnoreCase("MSG_KX_TN_READY")) {
			return MessageType.MSG_KX_TN_READY;
		} else if (str.equalsIgnoreCase("MSG_VOIP_CALL_INITIATE")) {
			return MessageType.MSG_VOIP_CALL_INITIATE;
		} else if (str.equalsIgnoreCase("MSG_VOIP_CALL_BUSY")) {
			return MessageType.MSG_VOIP_CALL_BUSY;
		} else if (str.equalsIgnoreCase("MSG_VOIP_CALL_WAITING")) {
			return MessageType.MSG_VOIP_CALL_WAITING;
		} else if (str.equalsIgnoreCase("MSG_VOIP_CALL_INITIATE_OK")) {
			return MessageType.MSG_VOIP_CALL_INITIATE_OK;
		} else if (str.equalsIgnoreCase("MSG_VOIP_CALL_DATA")) {
			return MessageType.MSG_VOIP_CALL_DATA;
		} else if (str.equalsIgnoreCase("MSG_VOIP_CALL_CALL_END")) {
			return MessageType.MSG_VOIP_CALL_CALL_END;
		} else if (str.equalsIgnoreCase("MSG_VOIP_HEART_BEAT")) {
			return MessageType.MSG_VOIP_HEART_BEAT;
		} else if (str.equalsIgnoreCase("MSG_VOIP_HEART_BEAT_REPLY")) {
			return MessageType.MSG_VOIP_HEART_BEAT_REPLY;
		} else if (str.equalsIgnoreCase("MSG_VOIP_CALL_START")) {
			return MessageType.MSG_VOIP_CALL_START;
		} else if (str.equalsIgnoreCase("MSG_VOIP_CALL_STARTED")) {
			return MessageType.MSG_VOIP_CALL_STARTED;
		} else if (str.equalsIgnoreCase("MSG_DHT_GET_REPLY")) {
			return MessageType.MSG_DHT_GET_REPLY;
		} else if (str.equalsIgnoreCase("MSG_DHT_TRACE_REPLY")) {
			return MessageType.MSG_DHT_TRACE_REPLY;
		} else if (str.equalsIgnoreCase("MSG_DHT_ERROR")) {
			return MessageType.MSG_DHT_ERROR;
		} else if (str.equalsIgnoreCase("MSG_KX_ERROR")) {
			return MessageType.MSG_KX_ERROR;
		} else if (str.equalsIgnoreCase("MSG_KX_TN_DESTROY")) {
			return MessageType.MSG_KX_TN_DESTROY;
		}
		return null;
	}

	/**
	 * Read the contents of the message packet. This function call is preceeded
	 * by a call to readMessagePacketHeader(), to get the type of message.
	 * 
	 * @param msgtype
	 * @return
	 */
	public HashMap<String, byte[]> readMessagePacket(byte[] mpacket) {
		MessageType msgtype = getMessagePacketType(mpacket);

		if (msgtype.equals(MessageType.MSG_DHT_GET)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("key", getMessageBytes(mpacket, 32, 64));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_DHT_PUT)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("key", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ttl", getMessageBytes(mpacket, 64, 80));
			msgMap.put("replication", getMessageBytes(mpacket, 80, 88));
			msgMap.put("reserveda", getMessageBytes(mpacket, 88, 104));
			msgMap.put("reservedb", getMessageBytes(mpacket, 104, 136));
			msgMap.put("content", getMessageBytes(mpacket, 136, 256));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_DHT_TRACE)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("key", getMessageBytes(mpacket, 32, 64));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_KX_TN_READY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 32, 64));
			msgMap.put("reserved", getMessageBytes(mpacket, 64, 96));
			msgMap.put("ipv4_address", getMessageBytes(mpacket, 96, 128));
			msgMap.put("ipv6_address", getMessageBytes(mpacket, 128, 160));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("pseudo_identity_callee", getMessageBytes(mpacket, 32, 64));
			msgMap.put("num_tries", getMessageBytes(mpacket, 64, 72));
			msgMap.put("reserved", getMessageBytes(mpacket, 72, 96));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE_OK)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address", getMessageBytes(mpacket, 32, 64));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 64, 96));
			msgMap.put("port_number", getMessageBytes(mpacket, 96, 112));
			msgMap.put("reserved", getMessageBytes(mpacket, 112, 128));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_BUSY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address", getMessageBytes(mpacket, 32, 64));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 64, 96));
			msgMap.put("port_number", getMessageBytes(mpacket, 96, 112));
			msgMap.put("reserved", getMessageBytes(mpacket, 112, 128));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_WAITING)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address", getMessageBytes(mpacket, 32, 64));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 64, 96));
			msgMap.put("port_number", getMessageBytes(mpacket, 96, 112));
			msgMap.put("reserved", getMessageBytes(mpacket, 112, 128));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_DATA)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address", getMessageBytes(mpacket, 32, 64));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 64, 96));
			msgMap.put("audio_data", getMessageBytes(mpacket, 96, 16096));
			msgMap.put("hashMD5", getMessageBytes(mpacket, 16096, 16112));
			msgMap.put("port_number", getMessageBytes(mpacket, 16112, 16128));
			msgMap.put("reserved", getMessageBytes(mpacket, 16128, 16144));

			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_CALL_END)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address", getMessageBytes(mpacket, 32, 64));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 64, 96));
			msgMap.put("port_number", getMessageBytes(mpacket, 96, 112));
			msgMap.put("reserved", getMessageBytes(mpacket, 112, 128));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address", getMessageBytes(mpacket, 32, 64));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 64, 96));
			msgMap.put("port_number", getMessageBytes(mpacket, 96, 112));
			msgMap.put("reserved", getMessageBytes(mpacket, 112, 128));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT_REPLY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address", getMessageBytes(mpacket, 32, 64));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 64, 96));
			msgMap.put("ok_byte", getMessageBytes(mpacket, 96, 97));
			msgMap.put("port_number", getMessageBytes(mpacket, 97, 113));
			msgMap.put("reserved", getMessageBytes(mpacket, 113, 128));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_START)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address_bykx", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ipv4_address_ofcallee", getMessageBytes(mpacket, 64, 96));
			msgMap.put("pseudo_identity_callee", getMessageBytes(mpacket, 96, 128));
			msgMap.put("port_number_callee", getMessageBytes(mpacket, 128, 144));
			msgMap.put("port_number_caller", getMessageBytes(mpacket, 144, 160));
			msgMap.put("num_tries", getMessageBytes(mpacket, 160, 168));
			msgMap.put("reserved", getMessageBytes(mpacket, 168, 192));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_STARTED)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_address_bykx", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ipv4_address_ofcallee", getMessageBytes(mpacket, 64, 96));
			msgMap.put("pseudo_identity_caller", getMessageBytes(mpacket, 96, 128));
			msgMap.put("started_byte", getMessageBytes(mpacket, 128, 129));
			msgMap.put("port_number_caller", getMessageBytes(mpacket, 129, 145));
			msgMap.put("reserved", getMessageBytes(mpacket, 145, 160));
			return msgMap;
		}
		// Additional REPLY and ERROR Messages
		else if (msgtype.equals(MessageType.MSG_DHT_GET_REPLY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("key", getMessageBytes(mpacket, 32, 64));
			msgMap.put("dht_get_reply_content", getMessageBytes(mpacket, 64, 64000));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_DHT_TRACE_REPLY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("key", getMessageBytes(mpacket, 32, 64));

			// A maximum of three hops are allowed. Nth hop first and (N-1)th
			// hop later.
			// Nth Hop
			msgMap.put("peer_id_n1", getMessageBytes(mpacket, 64, 96));
			msgMap.put("kx_port_n1", getMessageBytes(mpacket, 96, 112));
			msgMap.put("reserved_n1", getMessageBytes(mpacket, 112, 128));
			msgMap.put("ipv4_address_n1", getMessageBytes(mpacket, 128, 144));
			msgMap.put("ipv6_address_n1", getMessageBytes(mpacket, 144, 160));
			if (mpacket.length > 160) {
				// (N-1)th hop
				msgMap.put("peer_id_n2", getMessageBytes(mpacket, 160, 192));
				msgMap.put("kx_port_n2", getMessageBytes(mpacket, 192, 208));
				msgMap.put("reserved_n2", getMessageBytes(mpacket, 208, 224));
				msgMap.put("ipv4_address_n2", getMessageBytes(mpacket, 224, 240));
				msgMap.put("ipv6_address_n2", getMessageBytes(mpacket, 240, 256));
				if (mpacket.length > 256) {
					// (N-2) th hop
					msgMap.put("peer_id_n3", getMessageBytes(mpacket, 256, 288));
					msgMap.put("kx_port_n3", getMessageBytes(mpacket, 288, 304));
					msgMap.put("reserved_n3", getMessageBytes(mpacket, 304, 320));
					msgMap.put("ipv4_address_n3", getMessageBytes(mpacket, 320, 336));
					msgMap.put("ipv6_address_n3", getMessageBytes(mpacket, 336, 352));
					return msgMap;
				}
			}
		} else if (msgtype.equals(MessageType.MSG_DHT_ERROR)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("request_type", getMessageBytes(mpacket, 32, 48));
			msgMap.put("unused", getMessageBytes(mpacket, 48, 64));
			msgMap.put("request_key", getMessageBytes(mpacket, 64, 96));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_KX_ERROR)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("request_type", getMessageBytes(mpacket, 32, 48));
			msgMap.put("unused", getMessageBytes(mpacket, 48, 64));
			msgMap.put("pseudo_identity_request", getMessageBytes(mpacket, 64, 96));
			return msgMap;
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