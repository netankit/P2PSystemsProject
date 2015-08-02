package messages;

/**
 * DHTMessageFactory: Responsible for all DHT relevant Messages.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class DHTMessage implements Message {

	public Message createGenericMessage(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message readGenericMessage(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * Calls the specific version of the createDTHMessage based on the type of
	 * dhtMessageType parameter passed as a string.
	 * 
	 * @param dhtMessageType
	 * @return Message<DHT>
	 */
	public Message createMessage(String dhtMessageType) {
		// TODO Auto-generated method stub
		if (dhtMessageType == null) {
			return null;
		} else if (dhtMessageType.equalsIgnoreCase("DHT_TRACE")) {
			return DHTMessage.createDHTMessage(MessageType.DHT_TRACE);
		} else if (dhtMessageType.equalsIgnoreCase("DHT_GET")) {
			return DHTMessage.createDHTMessage(MessageType.DHT_GET);
		} else if (dhtMessageType.equalsIgnoreCase("DHT_PUT")) {
			return DHTMessage.createDHTMessage(MessageType.DHT_PUT);
		}

		return null;
	}

	/**
	 * Creates DHT specific Messages.
	 * 
	 * @param messageType
	 * @return Message<DHT>
	 */
	private static Message createDHTMessage(MessageType messageType) {

		if (messageType == null) {
			return null;
		} else if (messageType.equals(MessageType.DHT_TRACE)) {
			System.out.println("DHT_TRACE Message Successfully Called!");
			// MessagePacket dhttrace = new MessagePacket("DHT_TRACE");

			return null;
		} else if (messageType.equals(MessageType.DHT_GET)) {
			System.out.println("DHT_GET Message Successfully Called!");
			return null;
		} else if (messageType.equals(MessageType.DHT_PUT)) {
			System.out.println("DHT_TRACE Message Successfully Called!");
			return null;
		}
		// TODO Auto-generated method stub
		return null;
	}

	public Message readMessage(String dhtMessageType) {
		// TODO Auto-generated method stub
		return null;

	}

	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return null;

	}

}
