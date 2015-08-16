package messages;

import org.apache.commons.logging.Log;

import logger.LogSetup;
import ui.ClientUI;

/**
 * DHTMessageFactory: Responsible for all DHT relevant Messages.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class DHTMessage implements Message {
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

	/**
	 * 
	 * Calls the specific version of the createDTHMessage based on the type of
	 * dhtMessageType parameter passed as a string.
	 * 
	 * @param dhtMessageType
	 * @return Message<DHT>
	 */
	public byte[] createMessage(String dhtMessageType) {
		// TODO Auto-generated method stub
		if (dhtMessageType == null) {
			return null;
		} else if (dhtMessageType.equalsIgnoreCase("MSG_DHT_TRACE")) {
			return DHTMessage.createDHTMessage(MessageType.MSG_DHT_TRACE);
		} else if (dhtMessageType.equalsIgnoreCase("MSG_DHT_GET")) {
			return DHTMessage.createDHTMessage(MessageType.MSG_DHT_GET);
		} else if (dhtMessageType.equalsIgnoreCase("MSG_DHT_PUT")) {
			return DHTMessage.createDHTMessage(MessageType.MSG_DHT_PUT);
		}

		return null;
	}

	/**
	 * Creates DHT specific Messages.
	 * 
	 * @param messageType
	 * @return Message<DHT>
	 */
	private static byte[] createDHTMessage(MessageType messageType) {
		MessagePacket mpacket = new MessagePacket();
		if (messageType == null) {
			return null;
		} else if (messageType.equals(MessageType.MSG_DHT_TRACE)) {
			logger.info("DHT_TRACE Message Successfully Called!");
			return mpacket.createMessagePacket(MessageType.MSG_DHT_TRACE);
		} else if (messageType.equals(MessageType.MSG_DHT_GET)) {
			logger.info("DHT_GET Message Successfully Called!");
			return mpacket.createMessagePacket(MessageType.MSG_DHT_GET);
		} else if (messageType.equals(MessageType.MSG_DHT_PUT)) {
			logger.info("DHT_TRACE Message Successfully Called!");
			return mpacket.createMessagePacket(MessageType.MSG_DHT_PUT);
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
