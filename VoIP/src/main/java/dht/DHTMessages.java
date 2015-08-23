package dht;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;

import common.CommonFunctions;
import logger.LogSetup;
import messages.Message;
import messages.MessageFactory;
import messages.MessageFields;
import messages.MessagePacket;

public class DHTMessages {

	private int portNumber;
	private String hostName;
	private int TimeOut = 1000;

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(DHTMessages.class.getName());

	public DHTMessages(int portNumber, String hostName) {
		this.portNumber = portNumber;
		this.hostName = hostName;
	}

	public ExchangePointInfo CallDHTTrace(String key) {
		ExchangePointInfo exchangeInfo = null;
		try {
			Socket s = new Socket();
			s.connect(new InetSocketAddress(hostName, portNumber), TimeOut); // connect
																				// to
																				// the
																				// peer

			// set up TCP stream reader and writer
			InputStream inputStream = s.getInputStream();
			OutputStream outputStream = s.getOutputStream();

			MessageFields fields = new MessageFields();
			MessageFactory msgfac = new MessageFactory();
			Message message = msgfac.createGenericMessage("DHT_MESSAGE");
			fields.setKey(key);
			message.SetMessageFields(fields);

			byte[] msg = message.createMessage("MSG_DHT_TRACE");
			outputStream.write(msg);
			outputStream.flush();

			byte[] recievedBytes = new byte[MessagePacket.PACKET_SIZE]; // 64kb
																		// as we
																		// are
																		// communicating
																		// with
																		// DHT
			int recievedBytesCount = inputStream.read(recievedBytes); // wait
																		// and
																		// read
																		// the
																		// reply

			// parse message packets
			MessagePacket packet = new MessagePacket();
			HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedBytes);

			if (hmap == null || recievedBytesCount == 0) {
				inputStream.close();
				outputStream.close();
				s.close();
				return exchangeInfo;
			}
			// check the replied message status
			int statusMsg = CommonFunctions.ByteArrayToInt(hmap.get("messageType"));
			// check the replied message status
			if (statusMsg == Message.MessageType.MSG_DHT_TRACE_REPLY.getValue()) {
				exchangeInfo = new ExchangePointInfo();

				if (hmap.containsKey("peer_id_n1")) {
					exchangeInfo.setPeerId(CommonFunctions.ByteArrayToString(hmap.get("peer_id_n1")));
					exchangeInfo.setKxIPv4Address(CommonFunctions.ByteArrayToString(hmap.get("ipv4_address_n1")));
					exchangeInfo.setKxIPv6Address(CommonFunctions.ByteArrayToString(hmap.get("ipv6_address_n1")));
					exchangeInfo.setKxPotNumber(CommonFunctions.ByteArrayToString(hmap.get("kx_port_n1")));
				}

				/*
				 * if (hmap.containsKey("peer_id_n3")) {
				 * exchangeInfo.setPeerId(CommonFunctions.ByteArrayToString(hmap
				 * .get("peer_id_n3")));
				 * exchangeInfo.setKxIPv4Address(CommonFunctions.
				 * ByteArrayToString(hmap.get("ipv4_address_n3")));
				 * exchangeInfo.setKxIPv6Address(CommonFunctions.
				 * ByteArrayToString(hmap.get("ipv6_address_n3")));
				 * exchangeInfo.setKxPotNumber(CommonFunctions.ByteArrayToString
				 * (hmap.get("kx_port_n3")));
				 * 
				 * } if (hmap.containsKey("peer_id_n2")) {
				 * exchangeInfo.setPeerId(CommonFunctions.ByteArrayToString(hmap
				 * .get("peer_id_n2")));
				 * exchangeInfo.setKxIPv4Address(CommonFunctions.
				 * ByteArrayToString(hmap.get("ipv4_address_n2")));
				 * exchangeInfo.setKxIPv6Address(CommonFunctions.
				 * ByteArrayToString(hmap.get("ipv6_address_n2")));
				 * exchangeInfo.setKxPotNumber(CommonFunctions.ByteArrayToString
				 * (hmap.get("kx_port_n2"))); }
				 */

			} else if (statusMsg == Message.MessageType.MSG_DHT_ERROR.getValue()) {
				logger.error("DHTMessages: unable to send the DHT Trace Reply message from the DHT. ");
			}

			// close streams
			inputStream.close();
			outputStream.close();
			// close socket
			s.close();
		} catch (Exception ex) {
			logger.error("DHTMessages: unable to send/read the DHT Trace message from the DHT. " + ex.getMessage());
			exchangeInfo = null;
		}

		return exchangeInfo;
	}

	public boolean CallDHTPut(String key, String kxExchangeAddress, String kxPortNumber) {
		boolean retVal = true;
		try {
			Socket s = new Socket();
			s.connect(new InetSocketAddress(hostName, portNumber), TimeOut); // connect
																				// to
																				// the
																				// peer

			// set up TCP stream reader and writer
			InputStream inputStream = s.getInputStream();
			OutputStream outputStream = s.getOutputStream();

			MessageFields fields = new MessageFields();
			MessageFactory msgfac = new MessageFactory();
			Message message = msgfac.createGenericMessage("DHT_MESSAGE");
			fields.setKey(key);
			fields.setTtl("43200");
			fields.setReplication("10");
			// sign the content field here
			fields.setContent(kxExchangeAddress + ":" + kxPortNumber + ":" + (new Date().getTime()));

			message.SetMessageFields(fields);

			byte[] msg = message.createMessage("MSG_DHT_PUT");
			outputStream.write(msg);
			outputStream.flush();

			// close streams
			inputStream.close();
			outputStream.close();
			// close socket
			s.close();
		} catch (Exception ex) {
			logger.error("DHTMessages: unable to put the message into DHT. " + ex.getMessage());
			retVal = false;
		}

		return retVal;
	}

	public ArrayList<String> CallDHTGet(String key) {
		ArrayList<String> content = null;
		try {
			Socket s = new Socket();
			s.connect(new InetSocketAddress(hostName, portNumber), TimeOut); // connect
																				// to
																				// the
																				// peer

			// set up TCP stream reader and writer
			InputStream inputStream = s.getInputStream();
			OutputStream outputStream = s.getOutputStream();

			MessageFields fields = new MessageFields();
			MessageFactory msgfac = new MessageFactory();
			Message message = msgfac.createGenericMessage("DHT_MESSAGE");
			fields.setKey(key);
			message.SetMessageFields(fields);

			byte[] msg = message.createMessage("MSG_DHT_GET");
			outputStream.write(msg);
			outputStream.flush();

			try {
				content = new ArrayList<String>();

				while (true) {
					s.setSoTimeout(3000);
					byte[] recievedBytes = new byte[MessagePacket.PACKET_SIZE]; // 64kb
																				// as
																				// we
																				// are
																				// communicating
																				// with
																				// DHT
					int recievedBytesCount = inputStream.read(recievedBytes); // wait
																				// and
																				// read
																				// the
																				// reply

					// parse message packets
					MessagePacket packet = new MessagePacket();
					HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedBytes);

					if (hmap == null || recievedBytesCount == 0) {
						continue;
					}
					// check the replied message status
					int statusMsg = CommonFunctions.ByteArrayToInt(hmap.get("messageType"));
					// check the replied message status
					if (statusMsg == Message.MessageType.MSG_DHT_GET_REPLY.getValue()) {
						String dht_content = CommonFunctions.ByteArrayToString(hmap.get("dht_get_reply_content"));
						content.add(dht_content);
					} else if (statusMsg == Message.MessageType.MSG_DHT_ERROR.getValue()) {
						logger.error("DHTMessages: unable to get the DHT Get Reply message from the DHT. ");
					}
				}
			} catch (SocketTimeoutException timeOut) {
				logger.error("DHTMessages: All DHT Get reply messages are recieved. " + timeOut.getMessage());
			}

			// close streams
			inputStream.close();
			outputStream.close();
			// close socket
			s.close();
		} catch (Exception ex) {
			logger.error("DHTMessages: unable to get the reply of get messages from DHT. " + ex.getMessage());
			content = null;
		}

		return content;
	}
}