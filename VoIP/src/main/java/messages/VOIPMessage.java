package messages;

/**
 * VOIPMessageFactory : Responsible for all VOIP relevant Messages.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class VOIPMessage implements Message {

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
	public Message createMessage(String messageType) {
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
		}

		return null;
	}

	public MessageType getMessageType() {
		return null;
		// TODO Auto-generated method stub

	}

}
