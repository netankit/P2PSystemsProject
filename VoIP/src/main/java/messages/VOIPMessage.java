package messages;

import org.apache.commons.logging.Log;

import logger.LogSetup;
import ui.ClientUI;

/**
 * VOIPMessageFactory : Responsible for all VOIP relevant Messages.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class VOIPMessage implements Message {
	static LogSetup lg = new LogSetup();
	static Log logger = lg.getLog(ClientUI.class.getName());

	public Message createGenericMessage(String msg) {
		// TODO Auto-generated method stub
		return null;

	}

	public Message readGenericMessage(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	// VOIP Messages
	// MSG_VOIP_CALL_INITIATE, MSG_VOIP_CALL_OK, MSG_VOIP_CALL_BUSY,
	// MSG_VOIP_CALL_WAITING,
	// MSG_VOIP_CALL_INITIATE_OK, MSG_VOIP_CALL_DATA, MSG_VOIP_CALL_CALL_END,
	// MSG_VOIP_HEART_BEAT,
	// MSG_VOIP_HEART_BEAT_REPLY
<<<<<<< HEAD
	public Message createMessage(String messageType) {
=======
	public byte[] createMessage(String messageType) {
>>>>>>> upstream/master
		// TODO Auto-generated method stub
		if (messageType == null) {
			return null;
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_CALL_INITIATE")) {
			return createVOIPMessage(MessageType.MSG_VOIP_CALL_INITIATE);
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_CALL_OK")) {
			return createVOIPMessage(MessageType.MSG_VOIP_CALL_OK);
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_CALL_BUSY")) {
			return createVOIPMessage(MessageType.MSG_VOIP_CALL_BUSY);
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_CALL_WAITING")) {
			return createVOIPMessage(MessageType.MSG_VOIP_CALL_WAITING);
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_CALL_INITIATE_OK")) {
			return createVOIPMessage(MessageType.MSG_VOIP_CALL_INITIATE_OK);
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_CALL_DATA")) {
			return createVOIPMessage(MessageType.MSG_VOIP_CALL_DATA);
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_CALL_CALL_END")) {
			return createVOIPMessage(MessageType.MSG_VOIP_CALL_CALL_END);
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_HEART_BEAT")) {
			return createVOIPMessage(MessageType.MSG_VOIP_HEART_BEAT);
		} else if (messageType.equalsIgnoreCase("MSG_VOIP_HEART_BEAT_REPLY")) {
			return createVOIPMessage(MessageType.MSG_VOIP_HEART_BEAT_REPLY);
		}
		return null;
	}

	public Message readMessage(String messageType) {
		// TODO Auto-generated method stub
		return null;
	}

<<<<<<< HEAD
	public static Message createVOIPMessage(MessageType msgtype) {
		if (msgtype == null) {
			return null;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE)) {
			System.out.println("MSG_VOIP_CALL_INITIATE called");
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_OK)) {
			System.out.println("MSG_VOIP_CALL_OK called");
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_BUSY)) {
			System.out.println("MSG_VOIP_CALL_BUSY called");
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_WAITING)) {
			System.out.println("MSG_VOIP_CALL_WAITING called");
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE_OK)) {
			System.out.println("MSG_VOIP_CALL_INITIATE_OK called");
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_DATA)) {
			System.out.println("MSG_VOIP_CALL_DATA called");
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_CALL_END)) {
			System.out.println("MSG_VOIP_CALL_CALL_END called");
		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT)) {
			System.out.println("MSG_VOIP_HEART_BEAT called");
		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT_REPLY)) {
			System.out.println("MSG_VOIP_HEART_BEAT_REPLY called");
=======
	public static byte[] createVOIPMessage(MessageType msgtype) {
		MessagePacket mpacket = new MessagePacket();

		if (msgtype == null) {
			return null;
		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE)) {
			logger.info("MSG_VOIP_CALL_INITIATE called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_CALL_INITIATE);

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_OK)) {
			logger.info("MSG_VOIP_CALL_OK called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_CALL_OK);

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_BUSY)) {
			logger.info("MSG_VOIP_CALL_BUSY called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_CALL_BUSY);

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_WAITING)) {
			logger.info("MSG_VOIP_CALL_WAITING called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_CALL_WAITING);

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_INITIATE_OK)) {
			logger.info("MSG_VOIP_CALL_INITIATE_OK called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_CALL_INITIATE_OK);

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_DATA)) {
			logger.info("MSG_VOIP_CALL_DATA called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_CALL_DATA);

		} else if (msgtype.equals(MessageType.MSG_VOIP_CALL_CALL_END)) {
			logger.info("MSG_VOIP_CALL_CALL_END called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_CALL_CALL_END);

		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT)) {
			logger.info("MSG_VOIP_HEART_BEAT called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_HEART_BEAT);

		} else if (msgtype.equals(MessageType.MSG_VOIP_HEART_BEAT_REPLY)) {
			logger.info("MSG_VOIP_HEART_BEAT_REPLY called");
			return mpacket.createMessagePacket(MessageType.MSG_VOIP_HEART_BEAT_REPLY);

>>>>>>> upstream/master
		}

		return null;
	}

	public MessageType getMessageType() {
		return null;
		// TODO Auto-generated method stub

	}

}
