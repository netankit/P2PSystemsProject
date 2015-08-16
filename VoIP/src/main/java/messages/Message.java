package messages;

/**
 * This class is the generic message interface for VOIP application responsible
 * to create a message.
 * 
 * It also takes care of the message lengths.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public interface Message {

	public Message createGenericMessage(String msg);

	public Message readGenericMessage(String msg);

	public byte[] createMessage(String messageType);

	public Message readMessage(String messageType);

	public enum MessageType {

		DHT_MESSAGE, KX_MESSAGE, VOIP_MESSAGE, DHT_GET, DHT_PUT, DHT_TRACE, MSG_KX_TN_READY, MSG_VOIP_CALL_INITIATE, MSG_VOIP_CALL_OK, MSG_VOIP_CALL_BUSY, MSG_VOIP_CALL_WAITING, MSG_VOIP_CALL_INITIATE_OK, MSG_VOIP_CALL_DATA, MSG_VOIP_CALL_CALL_END, MSG_VOIP_HEART_BEAT, MSG_VOIP_HEART_BEAT_REPLY

	}

	public MessageType getMessageType();
}
