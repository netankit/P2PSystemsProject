/**
 * 
 */
package messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

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
	// Size of the final Message Packet = 1400 bytes
	public static final int UDP_PACKET_SIZE = 1400;
	public static final int PACKET_SIZE = 64000;
	private MessageFields fields;

	public void SetMessageFields(MessageFields fields) {
		this.fields = fields;
	}

	public byte[] createMessagePacket(MessageType msgtype) {
		if (msgtype.equals(null)) {
			return null;
		}

		if (msgtype.equals(MessageType.MSG_DHT_GET)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_DHT_GET.getValue()), 16);
			byte[] key = getPaddedByteArray(fields.getKey(), 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(key);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_DHT_PUT)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_DHT_PUT.getValue()), 16);
			byte[] key = getPaddedByteArray(fields.getKey(), 32);
			byte[] ttl = getPaddedByteArray(fields.getTtl(), 16);
			byte[] replication = getPaddedByteArray(fields.getReplication(), 8);
			byte[] reserveda = getPaddedByteArray("", 16);
			byte[] reservedb = getPaddedByteArray("", 32);
			// TODO : Add here Content -- Message Payload
			byte[] content = getPaddedByteArray(fields.getContent(), 120);
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
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_DHT_TRACE.getValue()), 16);
			byte[] key = getPaddedByteArray(fields.getKey(), 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(key);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_KX_TN_READY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_KX_TN_READY.getValue()), 16);
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

		} else if (msgtype.equals(MessageType.MSG_KX_TN_BUILD_IN)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_KX_TN_BUILD_IN.getValue()), 16);
			byte[] numberOfHops = getPaddedByteArray(fields.getNumberOfHops(), 8);
			byte[] reserveda = getPaddedByteArray("", 24);
			byte[] pseudo_identity = getPaddedByteArray(fields.getPseudo_identity(), 32);
			byte[] kxPortNumber = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] reservedb = getPaddedByteArray("", 16);
			byte[] peer_identity = getPaddedByteArray(fields.getPeerIdentity(), 32);
			byte[] ipv4Address = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv6Address = getPaddedByteArray(fields.getIpv6_address(), 16);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(numberOfHops);
			tempList.add(reserveda);
			tempList.add(pseudo_identity);
			tempList.add(kxPortNumber);
			tempList.add(reservedb);
			tempList.add(peer_identity);
			tempList.add(ipv4Address);
			tempList.add(ipv6Address);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_KX_TN_BUILD_OUT)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_KX_TN_BUILD_OUT.getValue()), 16);
			byte[] numberOfHops = getPaddedByteArray(fields.getNumberOfHops(), 8);
			byte[] reserveda = getPaddedByteArray("", 24);
			byte[] pseudo_identity = getPaddedByteArray(fields.getPseudo_identity(), 32);
			byte[] kxPortNumber = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] reservedb = getPaddedByteArray("", 16);
			byte[] peer_identity = getPaddedByteArray(fields.getPeerIdentity(), 32);
			byte[] ipv4Address = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv6Address = getPaddedByteArray(fields.getIpv6_address(), 16);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(numberOfHops);
			tempList.add(reserveda);
			tempList.add(pseudo_identity);
			tempList.add(kxPortNumber);
			tempList.add(reservedb);
			tempList.add(peer_identity);
			tempList.add(ipv4Address);
			tempList.add(ipv6Address);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_KX_TN_DESTROY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_KX_TN_DESTROY.getValue()), 16);
			byte[] pseudo_identity = getPaddedByteArray(fields.getPseudo_identity(), 32);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(pseudo_identity);

			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_CALL_INITIATE.getValue()), 16);
			byte[] ipv4_caller = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv4_callee = getPaddedByteArray(fields.getIpv4_address_ofcallee(), 32);
			byte[] pseudo_identity_caller = getPaddedByteArray(fields.getPseudo_identity_caller(), 32);
			byte[] pseudo_identity_callee = getPaddedByteArray(fields.getPseudo_identity_callee(), 32);
			byte[] port_number = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] num_tries = getPaddedByteArray(fields.getNum_tries(), 8);
			byte[] reserved = getPaddedByteArray("", 8);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_caller);
			tempList.add(ipv4_callee);
			tempList.add(pseudo_identity_caller);
			tempList.add(pseudo_identity_callee);
			tempList.add(num_tries);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE_OK)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_CALL_INITIATE_OK.getValue()),
					16);
			byte[] ipv4_caller = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv4_callee = getPaddedByteArray(fields.getIpv4_address_ofcallee(), 32);
			byte[] pseudo_identity_caller = getPaddedByteArray(fields.getPseudo_identity_caller(), 32);
			byte[] pseudo_identity_callee = getPaddedByteArray(fields.getPseudo_identity_callee(), 32);
			byte[] port_number = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] reserved = getPaddedByteArray("", 16);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_caller);
			tempList.add(ipv4_callee);
			tempList.add(pseudo_identity_caller);
			tempList.add(pseudo_identity_callee);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_BUSY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_CALL_BUSY.getValue()), 16);
			byte[] ipv4_caller = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv4_callee = getPaddedByteArray(fields.getIpv4_address_ofcallee(), 32);
			byte[] pseudo_identity_caller = getPaddedByteArray(fields.getPseudo_identity_caller(), 32);
			byte[] pseudo_identity_callee = getPaddedByteArray(fields.getPseudo_identity_callee(), 32);
			byte[] port_number = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] reserved = getPaddedByteArray("", 16);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_caller);
			tempList.add(ipv4_callee);
			tempList.add(pseudo_identity_caller);
			tempList.add(pseudo_identity_callee);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_WAITING)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_CALL_WAITING.getValue()), 16);
			byte[] ipv4_caller = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv4_callee = getPaddedByteArray(fields.getIpv4_address_ofcallee(), 32);
			byte[] pseudo_identity_caller = getPaddedByteArray(fields.getPseudo_identity_caller(), 32);
			byte[] pseudo_identity_callee = getPaddedByteArray(fields.getPseudo_identity_callee(), 32);
			byte[] port_number = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] reserved = getPaddedByteArray("", 16);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_caller);
			tempList.add(ipv4_callee);
			tempList.add(pseudo_identity_caller);
			tempList.add(pseudo_identity_callee);
			tempList.add(port_number);
			tempList.add(reserved);

			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_DATA)) {
			ArrayList<byte[]> tempHeaderList = new ArrayList<byte[]>();
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(UDP_PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_CALL_DATA.getValue()), 16);
			byte[] ipv4_caller = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv4_callee = getPaddedByteArray(fields.getIpv4_address_ofcallee(), 32);
			byte[] pseudo_identity_caller = getPaddedByteArray(fields.getPseudo_identity_caller(), 32);
			byte[] pseudo_identity_callee = getPaddedByteArray(fields.getPseudo_identity_callee(), 32);

			byte[] audio_data = new byte[1024]; // 16 kilobyte
			System.arraycopy(fields.getAudio_data(), 0, audio_data, 0, fields.getAudio_data().length);
			byte[] hashMD5 = new byte[16];
			System.arraycopy(fields.getHashMD5(), 0, hashMD5, 0, fields.getHashMD5().length);
			byte[] port_number = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] reserved = getPaddedByteArray("", 16);

			tempHeaderList.add(size);
			tempHeaderList.add(messageType);
			tempList.add(ipv4_caller);
			tempList.add(ipv4_callee);
			tempList.add(pseudo_identity_caller);
			tempList.add(pseudo_identity_callee);
			tempList.add(audio_data);
			tempList.add(hashMD5);
			tempList.add(port_number);
			tempList.add(reserved);
			byte[] msg_header = concatenateByteArrays(tempHeaderList);
			byte[] msg_content = concatenateByteArrays(tempList);
			// Encrypt Message Content : msg_content only and not the msg_header
			byte[] msg_content_encrypted = getEncryptedByteArray(msg_content, fields.getDhSecretKey());
			byte[] msg = concatenateTwoByteArrays(msg_header, msg_content_encrypted);
			byte[] msg_final = getPaddedByteArrayFinal(msg, UDP_PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_CALL_END)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_CALL_CALL_END.getValue()), 16);
			byte[] ipv4_caller = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv4_callee = getPaddedByteArray(fields.getIpv4_address_ofcallee(), 32);
			byte[] pseudo_identity_caller = getPaddedByteArray(fields.getPseudo_identity_caller(), 32);
			byte[] pseudo_identity_callee = getPaddedByteArray(fields.getPseudo_identity_callee(), 32);
			byte[] port_number = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] reserved = getPaddedByteArray("", 16);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_caller);
			tempList.add(ipv4_callee);
			tempList.add(pseudo_identity_caller);
			tempList.add(pseudo_identity_callee);
			tempList.add(port_number);
			tempList.add(reserved);

			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_HEART_BEAT.getValue()), 16);
			byte[] pseudo_identity = getPaddedByteArray(fields.getPseudo_identity(), 32);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(pseudo_identity);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT_REPLY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_HEART_BEAT_REPLY.getValue()),
					16);
			byte[] pseudo_identity = getPaddedByteArray(fields.getPseudo_identity(), 32);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(pseudo_identity);

			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_START)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);

			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_CALL_START.getValue()), 16);
			byte[] ipv4_caller = getPaddedByteArray(fields.getIpv4_address(), 32);
			byte[] ipv4_callee = getPaddedByteArray(fields.getIpv4_address_ofcallee(), 32);
			byte[] pseudo_identity_caller = getPaddedByteArray(fields.getPseudo_identity_caller(), 32);
			byte[] pseudo_identity_callee = getPaddedByteArray(fields.getPseudo_identity_callee(), 32);
			byte[] port_number = getPaddedByteArray(fields.getPort_number(), 16);
			byte[] reserved = getPaddedByteArray("", 16);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(ipv4_caller);
			tempList.add(ipv4_callee);
			tempList.add(pseudo_identity_caller);
			tempList.add(pseudo_identity_callee);
			tempList.add(port_number);
			tempList.add(reserved);

			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_STARTED)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);

			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_CALL_STARTED.getValue()), 16);
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

		} else if (msgtype.equals(MessageType.MSG_VOIP_ERROR)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_ERROR.getValue()), 16);
			byte[] request_type = getPaddedByteArray(fields.getMessageType(), 16);
			byte[] reserved = getPaddedByteArray("", 16);
			byte[] pseudo_identity_request = getPaddedByteArray(fields.getPseudo_identity(), 32);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(request_type);
			tempList.add(reserved);
			tempList.add(pseudo_identity_request);
			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_VOIP_PUBLICKEY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_PUBLICKEY.getValue()), 16);
			byte[] dhspecg = getPaddedArray(fields.getDhspecg(), 512);
			byte[] dhspecp = getPaddedArray(fields.getDhspecp(), 512);
			byte[] dhspecl = getPaddedArray(fields.getDhspecl(), 512);
			byte[] dh_public_key_caller = getPaddedArray(fields.getDh_public_key_caller(), 1000);

			tempList.add(size);
			tempList.add(messageType);
			tempList.add(dhspecg);
			tempList.add(dhspecp);
			tempList.add(dhspecl);
			tempList.add(dh_public_key_caller);

			byte[] msg = concatenateByteArrays(tempList);
			byte[] msg_final = getPaddedByteArrayFinal(msg, PACKET_SIZE);
			return msg_final;
		} else if (msgtype.equals(MessageType.MSG_VOIP_PUBLICKEY_REPLY)) {
			ArrayList<byte[]> tempList = new ArrayList<byte[]>();
			byte[] size = getPaddedByteArray(String.valueOf(PACKET_SIZE), 16);
			byte[] messageType = getPaddedByteArray(String.valueOf(MessageType.MSG_VOIP_PUBLICKEY_REPLY.getValue()),
					16);
			byte[] dh_public_key_calle = getPaddedArray(fields.getDh_public_key_callee(), 1000);
			tempList.add(size);
			tempList.add(messageType);
			tempList.add(dh_public_key_calle);
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

	private byte[] getEncryptedByteArray(byte[] src, SecretKey key) {
		// TODO Auto-generated method stub
		Cipher c;
		byte[] cipherMessage = null;
		try {
			c = Cipher.getInstance("DES/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, key);
			cipherMessage = c.doFinal(src);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cipherMessage;
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
		if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_DHT_GET.getValue()))) {
			return MessageType.MSG_DHT_GET;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_DHT_PUT.getValue()))) {
			return MessageType.MSG_DHT_PUT;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_DHT_TRACE.getValue()))) {
			return MessageType.MSG_DHT_TRACE;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_KX_TN_READY.getValue()))) {
			return MessageType.MSG_KX_TN_READY;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_CALL_INITIATE.getValue()))) {
			return MessageType.MSG_VOIP_CALL_INITIATE;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_CALL_BUSY.getValue()))) {
			return MessageType.MSG_VOIP_CALL_BUSY;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_CALL_WAITING.getValue()))) {
			return MessageType.MSG_VOIP_CALL_WAITING;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_CALL_INITIATE_OK.getValue()))) {
			return MessageType.MSG_VOIP_CALL_INITIATE_OK;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_CALL_DATA.getValue()))) {
			return MessageType.MSG_VOIP_CALL_DATA;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_CALL_CALL_END.getValue()))) {
			return MessageType.MSG_VOIP_CALL_CALL_END;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_HEART_BEAT.getValue()))) {
			return MessageType.MSG_VOIP_HEART_BEAT;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_HEART_BEAT_REPLY.getValue()))) {
			return MessageType.MSG_VOIP_HEART_BEAT_REPLY;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_CALL_START.getValue()))) {
			return MessageType.MSG_VOIP_CALL_START;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_CALL_STARTED.getValue()))) {
			return MessageType.MSG_VOIP_CALL_STARTED;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_DHT_GET_REPLY.getValue()))) {
			return MessageType.MSG_DHT_GET_REPLY;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_DHT_TRACE_REPLY.getValue()))) {
			return MessageType.MSG_DHT_TRACE_REPLY;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_DHT_ERROR.getValue()))) {
			return MessageType.MSG_DHT_ERROR;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_KX_ERROR.getValue()))) {
			return MessageType.MSG_KX_ERROR;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_KX_TN_DESTROY.getValue()))) {
			return MessageType.MSG_KX_TN_DESTROY;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_ERROR.getValue()))) {
			return MessageType.MSG_VOIP_ERROR;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_PUBLICKEY.getValue()))) {
			return MessageType.MSG_VOIP_PUBLICKEY;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_VOIP_PUBLICKEY_REPLY.getValue()))) {
			return MessageType.MSG_VOIP_PUBLICKEY_REPLY;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_KX_TN_BUILD_IN.getValue()))) {
			return MessageType.MSG_KX_TN_BUILD_IN;
		} else if (str.equalsIgnoreCase(String.valueOf(MessageType.MSG_KX_TN_BUILD_OUT.getValue()))) {
			return MessageType.MSG_KX_TN_BUILD_OUT;
		}

		return null;
	}

	/**
	 * Read the contents of the message packet. This function call is preceeded
	 * by a call to readMessagePacketHeader(), to get the type of message.
	 * 
	 * @param msgtype
	 * @return
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public HashMap<String, byte[]> readMessagePacket(byte[] mpacketTemp) {
		if (mpacketTemp.length == 0)
			return null;
		MessageType msgtype = getMessagePacketType(mpacketTemp);

		byte[] mpacket = mpacketTemp;
		try {
			// DECRYPT MESSAGE if it's any of the VOIP MESSAGES.
			if (msgtype.equals(MessageType.MSG_VOIP_CALL_DATA)) {
				mpacket = decryptMessagePacket(mpacketTemp, fields.getDhSecretKey());
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			msgMap.put("ipv6_address", getMessageBytes(mpacket, 128, 144));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_KX_ERROR)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("request_type", getMessageBytes(mpacket, 32, 48));
			msgMap.put("unused", getMessageBytes(mpacket, 48, 64));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 64, 96));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_caller", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ipv4_callee", getMessageBytes(mpacket, 64, 96));
			msgMap.put("pseudo_identity_caller", getMessageBytes(mpacket, 96, 128));
			msgMap.put("pseudo_identity_callee", getMessageBytes(mpacket, 128, 160));
			msgMap.put("num_tries", getMessageBytes(mpacket, 160, 168));
			msgMap.put("port_number", getMessageBytes(mpacket, 168, 184));
			msgMap.put("reserved", getMessageBytes(mpacket, 184, 192));

			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE_OK)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_caller", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ipv4_callee", getMessageBytes(mpacket, 64, 96));
			msgMap.put("pseudo_identity_caller", getMessageBytes(mpacket, 96, 128));
			msgMap.put("pseudo_identity_callee", getMessageBytes(mpacket, 128, 160));
			msgMap.put("port_number", getMessageBytes(mpacket, 160, 176));
			msgMap.put("reserved", getMessageBytes(mpacket, 176, 192));

			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_BUSY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_caller", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ipv4_callee", getMessageBytes(mpacket, 64, 96));
			msgMap.put("pseudo_identity_caller", getMessageBytes(mpacket, 96, 128));
			msgMap.put("pseudo_identity_callee", getMessageBytes(mpacket, 128, 160));
			msgMap.put("port_number", getMessageBytes(mpacket, 160, 176));
			msgMap.put("reserved", getMessageBytes(mpacket, 176, 192));

			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_WAITING)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_caller", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ipv4_callee", getMessageBytes(mpacket, 64, 96));
			msgMap.put("pseudo_identity_caller", getMessageBytes(mpacket, 96, 128));
			msgMap.put("pseudo_identity_callee", getMessageBytes(mpacket, 128, 160));
			msgMap.put("port_number", getMessageBytes(mpacket, 160, 176));
			msgMap.put("reserved", getMessageBytes(mpacket, 176, 192));

			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_DATA)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();

			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_caller", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ipv4_callee", getMessageBytes(mpacket, 64, 96));
			msgMap.put("pseudo_identity_caller", getMessageBytes(mpacket, 96, 128));
			msgMap.put("pseudo_identity_callee", getMessageBytes(mpacket, 128, 160));
			msgMap.put("audio_data", getMessageBytes(mpacket, 160, 1184));
			msgMap.put("hashMD5", getMessageBytes(mpacket, 1184, 1200));
			msgMap.put("port_number", getMessageBytes(mpacket, 1200, 1216));
			msgMap.put("reserved", getMessageBytes(mpacket, 1216, 1232));

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
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 32, 64));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT_REPLY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("pseudo_identity", getMessageBytes(mpacket, 32, 64));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_START)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("ipv4_caller", getMessageBytes(mpacket, 32, 64));
			msgMap.put("ipv4_callee", getMessageBytes(mpacket, 64, 96));
			msgMap.put("pseudo_identity_caller", getMessageBytes(mpacket, 96, 128));
			msgMap.put("pseudo_identity_callee", getMessageBytes(mpacket, 128, 160));
			msgMap.put("port_number", getMessageBytes(mpacket, 160, 176));
			msgMap.put("reserved", getMessageBytes(mpacket, 176, 192));

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
			msgMap.put("dht_get_reply_content", getMessageBytes(mpacket, 64, PACKET_SIZE));
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
			if (mpacket.length > 160 & mpacket.length <= 256) {
				// (N-1)th hop
				msgMap.put("peer_id_n2", getMessageBytes(mpacket, 160, 192));
				msgMap.put("kx_port_n2", getMessageBytes(mpacket, 192, 208));
				msgMap.put("reserved_n2", getMessageBytes(mpacket, 208, 224));
				msgMap.put("ipv4_address_n2", getMessageBytes(mpacket, 224, 240));
				msgMap.put("ipv6_address_n2", getMessageBytes(mpacket, 240, 256));
			}
			if (mpacket.length > 256) {
				// (N-2) th hop
				msgMap.put("peer_id_n3", getMessageBytes(mpacket, 256, 288));
				msgMap.put("kx_port_n3", getMessageBytes(mpacket, 288, 304));
				msgMap.put("reserved_n3", getMessageBytes(mpacket, 304, 320));
				msgMap.put("ipv4_address_n3", getMessageBytes(mpacket, 320, 336));
				msgMap.put("ipv6_address_n3", getMessageBytes(mpacket, 336, 352));
				return msgMap;
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
		} else if (msgtype.equals(MessageType.MSG_VOIP_ERROR)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("request_type", getMessageBytes(mpacket, 32, 48));
			msgMap.put("unused", getMessageBytes(mpacket, 48, 64));
			msgMap.put("pseudo_identity_request", getMessageBytes(mpacket, 64, 96));
			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_PUBLICKEY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("dhspecg", getMessageBytes(mpacket, 32, 544));
			msgMap.put("dhspecp", getMessageBytes(mpacket, 544, 1056));
			msgMap.put("dhspecl", getMessageBytes(mpacket, 1056, 1568));
			msgMap.put("dh_public_key_caller", getMessageBytes(mpacket, 1568, 2568));

			return msgMap;
		} else if (msgtype.equals(MessageType.MSG_VOIP_PUBLICKEY_REPLY)) {
			HashMap<String, byte[]> msgMap = new HashMap<String, byte[]>();
			msgMap.put("size", getMessageBytes(mpacket, 0, 16));
			msgMap.put("messageType", getMessageBytes(mpacket, 16, 32));
			msgMap.put("dh_public_key_calle", getMessageBytes(mpacket, 32, 1132));
			return msgMap;
		}

		return null;
	}

	/**
	 * De-crypts the message packet using the shared secret key as provided
	 * during the Diffie Hellman Key exchange.
	 * 
	 * @param mpacket
	 * @param dhSecretKey2
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private byte[] decryptMessagePacket(byte[] mpacket, SecretKey dhsecretkey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// TODO Auto-generated method stub
		byte[] header = getMessageBytes(mpacket, 0, 32);
		byte[] mpacket_content = getMessageBytes(mpacket, 32, mpacket.length);
		Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, dhsecretkey);
		byte[] decryptedMessageBytesTmp = c.doFinal(mpacket_content);
		byte[] decryptedMessageBytes = concatenateTwoByteArrays(header, decryptedMessageBytesTmp);
		return decryptedMessageBytes;
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
		// System.out.println("Difference: " + diff);
		System.arraycopy(src, 0, dest, 0, src.length);
		return dest;
	}

	public byte[] getPaddedArray(byte[] src, int size) {
		int len = src.length;
		byte[] dest = new byte[size];
		// System.out.println("Difference: " + diff);
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
	 * Concatenates Two byte arrays and returns a resultant byte array
	 * 
	 * @param a:
	 *            First byte[] array
	 * @param b:
	 *            Second byte[] array
	 * @return: Concatenated byte array of first and second byte array
	 */

	public byte[] concatenateTwoByteArrays(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
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
