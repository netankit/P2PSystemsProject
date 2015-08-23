/**
 * 
 */
package messages;

import javax.crypto.SecretKey;

/**
 * Getters and Setters methods for various Message Fields for various Messages
 * used in the VOIP Application. Generally used by the createMessagePacket()
 * Method.
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 */
public class MessageFields {

	// default constructor
	public MessageFields() {

	}

	/**
	 * @param size
	 * @param messageType
	 * @param key
	 * @param ttl
	 * @param replication
	 * @param reserved
	 * @param reserveda
	 * @param reservedb
	 * @param content
	 * @param pseudo_identity
	 * @param ipv4_address
	 * @param ipv6_address
	 * @param pseudo_identity_callee
	 * @param num_tries
	 * @param port_number
	 * @param audio_data
	 * @param hashMD5
	 * @param ok_byte
	 * @param ipv4_address_bykx
	 * @param ipv4_address_ofcallee
	 * @param port_number_callee
	 * @param port_number_caller
	 * @param started_byte
	 * @param pseudo_identity_caller
	 */
	public MessageFields(String size, String messageType, String key, String ttl, String replication, String reserved,
			String reserveda, String reservedb, String content, String pseudo_identity, String ipv4_address,
			String ipv6_address, String pseudo_identity_callee, String num_tries, String port_number, byte[] audio_data,
			byte[] hashMD5, String ok_byte, String ipv4_address_bykx, String ipv4_address_ofcallee,
			String port_number_callee, String port_number_caller, String started_byte, String pseudo_identity_caller) {

		// Either initialize like this or the other given below using the
		// setters.
		super();
		this.size = size;
		this.messageType = messageType;
		this.key = key;
		this.ttl = ttl;
		this.replication = replication;
		this.reserved = reserved;
		this.reserveda = reserveda;
		this.reservedb = reservedb;
		this.content = content;
		this.pseudo_identity = pseudo_identity;
		this.ipv4_address = ipv4_address;
		this.ipv6_address = ipv6_address;
		this.pseudo_identity_callee = pseudo_identity_callee;
		this.num_tries = num_tries;
		this.port_number = port_number;
		this.audio_data = audio_data;
		this.hashMD5 = hashMD5;
		this.ok_byte = ok_byte;
		this.ipv4_address_bykx = ipv4_address_bykx;
		this.ipv4_address_ofcallee = ipv4_address_ofcallee;
		this.port_number_callee = port_number_callee;
		this.port_number_caller = port_number_caller;
		this.started_byte = started_byte;
		this.pseudo_identity_caller = pseudo_identity_caller;

		// Initialize the setter methods for all values with the values passed
		// as parameter in the constructor.
		// Pass the null values for byte[] and "" value for String, if non
		// applicable for a message field wrt a message.

		this.setSize(size);
		this.setMessageType(messageType);
		this.setKey(key);
		this.setTtl(ttl);
		this.setReplication(replication);
		this.setReserved(reserved);
		this.setReserveda(reserveda);
		this.setReservedb(reservedb);
		this.setContent(content);
		this.setPseudo_identity(pseudo_identity);
		this.setPseudo_identity_callee(pseudo_identity_callee);
		this.setIpv4_address(ipv4_address);
		this.setIpv6_address(ipv6_address);
		this.setNum_tries(num_tries);
		this.setPort_number(port_number);
		this.setAudio_data(audio_data);
		this.setHashMD5(hashMD5);
		this.setOk_byte(ok_byte);
		this.setIpv4_address_bykx(ipv4_address_bykx);
		this.setIpv4_address_ofcallee(ipv4_address_ofcallee);
		this.setPort_number_callee(port_number_callee);
		this.setPort_number_caller(port_number_caller);
		this.setStarted_byte(started_byte);
		this.setPseudo_identity_caller(pseudo_identity_caller);
	}

	private String size;
	private String messageType;
	private String key;
	private String ttl;
	private String replication;
	private String reserved;
	private String reserveda;
	private String reservedb;
	private String content;
	private String pseudo_identity;
	private String ipv4_address;
	private String ipv6_address;
	private String pseudo_identity_callee;
	private String num_tries;
	private String port_number;
	private byte[] audio_data;
	private byte[] hashMD5;
	private String ok_byte;
	private String ipv4_address_bykx;
	private String ipv4_address_ofcallee;
	private String port_number_callee;
	private String port_number_caller;
	private String started_byte;
	private String pseudo_identity_caller;
	private String numberOfHops;
	private String peerIdentity;
	private SecretKey dhSecretKey;

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType
	 *            the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the ttl
	 */
	public String getTtl() {
		return ttl;
	}

	/**
	 * @param ttl
	 *            the ttl to set
	 */
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	/**
	 * @return the replication
	 */
	public String getReplication() {
		return replication;
	}

	/**
	 * @param replication
	 *            the replication to set
	 */
	public void setReplication(String replication) {
		this.replication = replication;
	}

	/**
	 * @return the reserved
	 */
	public String getReserved() {
		return reserved;
	}

	/**
	 * @param reserved
	 *            the reserved to set
	 */
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	/**
	 * @return the reserveda
	 */
	public String getReserveda() {
		return reserveda;
	}

	/**
	 * @param reserveda
	 *            the reserveda to set
	 */
	public void setReserveda(String reserveda) {
		this.reserveda = reserveda;
	}

	/**
	 * @return the reservedb
	 */
	public String getReservedb() {
		return reservedb;
	}

	/**
	 * @param reservedb
	 *            the reservedb to set
	 */
	public void setReservedb(String reservedb) {
		this.reservedb = reservedb;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the pseudo_identity
	 */
	public String getPseudo_identity() {
		return pseudo_identity;
	}

	/**
	 * @param pseudo_identity
	 *            the pseudo_identity to set
	 */
	public void setPseudo_identity(String pseudo_identity) {
		this.pseudo_identity = pseudo_identity;
	}

	/**
	 * @return the ipv4_address
	 */
	public String getIpv4_address() {
		return ipv4_address;
	}

	/**
	 * @param ipv4_address
	 *            the ipv4_address to set
	 */
	public void setIpv4_address(String ipv4_address) {
		this.ipv4_address = ipv4_address;
	}

	/**
	 * @return the ipv6_address
	 */
	public String getIpv6_address() {
		return ipv6_address;
	}

	/**
	 * @param ipv6_address
	 *            the ipv6_address to set
	 */
	public void setIpv6_address(String ipv6_address) {
		this.ipv6_address = ipv6_address;
	}

	/**
	 * @return the pseudo_identity_callee
	 */
	public String getPseudo_identity_callee() {
		return pseudo_identity_callee;
	}

	/**
	 * @param pseudo_identity_callee
	 *            the pseudo_identity_callee to set
	 */
	public void setPseudo_identity_callee(String pseudo_identity_callee) {
		this.pseudo_identity_callee = pseudo_identity_callee;
	}

	/**
	 * @return the num_tries
	 */
	public String getNum_tries() {
		return num_tries;
	}

	/**
	 * @param num_tries
	 *            the num_tries to set
	 */
	public void setNum_tries(String num_tries) {
		this.num_tries = num_tries;
	}

	/**
	 * @return the port_number
	 */
	public String getPort_number() {
		return port_number;
	}

	/**
	 * @param port_number
	 *            the port_number to set
	 */
	public void setPort_number(String port_number) {
		this.port_number = port_number;
	}

	/**
	 * @return the audio_data
	 */
	public byte[] getAudio_data() {
		return audio_data;
	}

	/**
	 * @param audio_data
	 *            the audio_data to set
	 */
	public void setAudio_data(byte[] audio_data) {
		this.audio_data = audio_data;
	}

	/**
	 * @return the hashMD5
	 */
	public byte[] getHashMD5() {
		return hashMD5;
	}

	/**
	 * @param hashMD5
	 *            the hashMD5 to set
	 */
	public void setHashMD5(byte[] hashMD5) {
		this.hashMD5 = hashMD5;
	}

	/**
	 * @return the ok_byte
	 */
	public String getOk_byte() {
		return ok_byte;
	}

	/**
	 * @param ok_byte
	 *            the ok_byte to set
	 */
	public void setOk_byte(String ok_byte) {
		this.ok_byte = ok_byte;
	}

	/**
	 * @return the ipv4_address_bykx
	 */
	public String getIpv4_address_bykx() {
		return ipv4_address_bykx;
	}

	/**
	 * @param ipv4_address_bykx
	 *            the ipv4_address_bykx to set
	 */
	public void setIpv4_address_bykx(String ipv4_address_bykx) {
		this.ipv4_address_bykx = ipv4_address_bykx;
	}

	/**
	 * @return the ipv4_address_ofcallee
	 */
	public String getIpv4_address_ofcallee() {
		return ipv4_address_ofcallee;
	}

	/**
	 * @param ipv4_address_ofcallee
	 *            the ipv4_address_ofcallee to set
	 */
	public void setIpv4_address_ofcallee(String ipv4_address_ofcallee) {
		this.ipv4_address_ofcallee = ipv4_address_ofcallee;
	}

	/**
	 * @return the port_number_callee
	 */
	public String getPort_number_callee() {
		return port_number_callee;
	}

	/**
	 * @param port_number_callee
	 *            the port_number_callee to set
	 */
	public void setPort_number_callee(String port_number_callee) {
		this.port_number_callee = port_number_callee;
	}

	/**
	 * @return the port_number_caller
	 */
	public String getPort_number_caller() {
		return port_number_caller;
	}

	/**
	 * @param port_number_caller
	 *            the port_number_caller to set
	 */
	public void setPort_number_caller(String port_number_caller) {
		this.port_number_caller = port_number_caller;
	}

	/**
	 * @return the started_byte
	 */
	public String getStarted_byte() {
		return started_byte;
	}

	/**
	 * @param started_byte
	 *            the started_byte to set
	 */
	public void setStarted_byte(String started_byte) {
		this.started_byte = started_byte;
	}

	/**
	 * @return the pseudo_identity_caller
	 */
	public String getPseudo_identity_caller() {
		return pseudo_identity_caller;
	}

	/**
	 * @param pseudo_identity_caller
	 *            the pseudo_identity_caller to set
	 */
	public void setPseudo_identity_caller(String pseudo_identity_caller) {
		this.pseudo_identity_caller = pseudo_identity_caller;
	}

	public String getNumberOfHops() {
		return numberOfHops;
	}

	public void setNumberOfHops(String numberOfHops) {
		this.numberOfHops = numberOfHops;
	}

	public String getPeerIdentity() {
		return peerIdentity;
	}

	public void setPeerIdentity(String peerIdentity) {
		this.peerIdentity = peerIdentity;
	}

	/**
	 * @return the dhSecretKey
	 */
	public SecretKey getDhSecretKey() {
		return dhSecretKey;
	}

	/**
	 * @param dhSecretKey
	 *            the dhSecretKey to set
	 */
	public void setDhSecretKey(SecretKey dhSecretKey) {
		this.dhSecretKey = dhSecretKey;
	}

}
