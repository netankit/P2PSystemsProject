package messages;


/**
 * This class is the generic message class for VOIP application responsible to
 * create a message.
 * 
 * It also takes care of the message lengths.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class MessageFactory implements Message {

	public Message createGenericMessage(String msgType) {
		// TODO Auto-generated method stub

		if (msgType == null) {
			return null;
		}

		if (msgType.equalsIgnoreCase("DHT_MESSAGE")) {
			return new DHTMessage();
		} else if (msgType.equalsIgnoreCase("KX_MESSAGE")) {
			return new KXMessage();
		} else if (msgType.equalsIgnoreCase("VOIP_MESSAGE")) {
			return new VOIPMessage();
		}
		return null;

	}

	public Message readGenericMessage(String msg) {
		return null;
		// TODO Auto-generated method stub

	}

	public Message createMessage(String messageType) {
		return null;
	};

	public Message readMessage(String messageType) {
		return null;
	}

	public MessageType getMessageType() {
		return null;
		// TODO Auto-generated method stub

	}

}
