package messages;

import org.apache.commons.logging.Log;

import logger.LogSetup;

/**
 * 
 * KXMessageFactory: Responsible for all KX related messages.
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class KXMessage implements Message {
	static LogSetup lg = new LogSetup();
	static Log logger = lg.getLog(KXMessage.class.getName());
	private MessageFields fields;

	public void SetMessageFields(MessageFields fields) {
		this.fields = fields;
	}

	public byte[] createMessage(String kxMessage) {
		// TODO Auto-generated method stub
		if (kxMessage == null) {
			return null;
		} else if (kxMessage == "MSG_KX_TN_READY") {
			return createKXMessage(MessageType.MSG_KX_TN_READY);
		} else if (kxMessage == "MSG_KX_TN_BUILD_IN") {
			return createKXMessage(MessageType.MSG_KX_TN_BUILD_IN);
		} else if (kxMessage == "MSG_KX_TN_BUILD_OUT") {
			return createKXMessage(MessageType.MSG_KX_TN_BUILD_OUT);
		} else if (kxMessage == "MSG_KX_TN_DESTROY") {
			return createKXMessage(MessageType.MSG_KX_TN_DESTROY);
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

	public byte[] createKXMessage(MessageType messageType) {
		MessagePacket mpacket = new MessagePacket();
		// TODO Auto-generated method stub
		if (messageType == null) {
			return null;
		} else if (messageType.equals(MessageType.MSG_KX_TN_READY)) {
			logger.info("MSG_KX_TN_READY successfully CALLED.");
			return mpacket.createMessagePacket(MessageType.MSG_KX_TN_READY);

		} else if (messageType.equals(MessageType.MSG_KX_ERROR)) {
			logger.info("MSG_KX_ERROR successfully CALLED.");
			return mpacket.createMessagePacket(MessageType.MSG_KX_ERROR);

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
