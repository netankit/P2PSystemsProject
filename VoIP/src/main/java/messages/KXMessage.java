package messages;

/**
 * 
 * KXMessageFactory: Responsible for all KX related messages.
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class KXMessage implements Message {

	public Message createMessage(String kxMessage) {
		// TODO Auto-generated method stub
		if (kxMessage == null) {
			return null;
		}

		else if (kxMessage == "MSG_KX_TN_READY") {
			return KXMessage.createKXMessage(MessageType.MSG_KX_TN_READY);
		}
		return null;

	}

	public Message readGenericMessage(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message createGenericMessage(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Message createKXMessage(MessageType messageType) {
		// TODO Auto-generated method stub
		if (messageType == null) {
			return null;
		} else if (messageType.equals(MessageType.MSG_KX_TN_READY)) {
			System.out.println("MSG_KX_TN_READY successfully CALLED.");
		}

		return null;
	}

	public Message readMessage(String messageType) {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageType getMessageType() {
		return null;
		// TODO Auto-generated method stub

	}

}
