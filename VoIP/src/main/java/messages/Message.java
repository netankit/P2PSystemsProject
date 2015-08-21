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

		DHT_MESSAGE(0), KX_MESSAGE(1), VOIP_MESSAGE(2), MSG_DHT_PUT(500), MSG_DHT_GET(501), MSG_DHT_TRACE(502), MSG_DHT_GET_REPLY(503), MSG_DHT_TRACE_REPLY(504), MSG_DHT_ERROR(505), 
		MSG_KX_TN_BUILD_IN(600), MSG_KX_TN_BUILD_OUT(601),MSG_KX_TN_READY(602), MSG_KX_TN_DESTROY(603), MSG_KX_ERROR(604), 
		MSG_VOIP_CALL_INITIATE(700), MSG_VOIP_CALL_OK(701), MSG_VOIP_CALL_BUSY(702), MSG_VOIP_CALL_WAITING(703), MSG_VOIP_CALL_INITIATE_OK(704), 
		MSG_VOIP_CALL_DATA(705), MSG_VOIP_CALL_CALL_END(706), MSG_VOIP_HEART_BEAT(707), MSG_VOIP_HEART_BEAT_REPLY(708), MSG_VOIP_CALL_START(709), 
		MSG_VOIP_CALL_STARTED(710), MSG_VOIP_ERROR(711);

		private int value;
		 
	    private MessageType(int value) {
	        this.value = value;
	    }
	    
	    public int getValue() {
	        return value;
	    }
	}

	public MessageType getMessageType();
	
	public void SetMessageFields(MessageFields fields);
}
