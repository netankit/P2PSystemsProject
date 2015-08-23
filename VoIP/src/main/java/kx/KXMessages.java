package kx;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

import logger.LogSetup;
import messages.Message;
import messages.MessageFactory;
import messages.MessageFields;
import messages.MessagePacket;

import org.apache.commons.logging.Log;

import common.CommonFunctions;

import dht.ExchangePointInfo;
import ui.ClientUI;

public class KXMessages {

	private int portNumber;
	private String hostName;
	private int TimeOut = 1000;
	
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	public KXMessages(int portNumber, String hostName)
	{
		this.portNumber = portNumber;
		this.hostName = hostName;
	}
	
	public boolean CallKXToBuildIncomingTunnel(String psuedoIdentity, ExchangePointInfo exchange)
	{
		boolean retVal = false;
		try
		{
			Socket s = new Socket();
			s.connect(new InetSocketAddress(hostName, portNumber), TimeOut); // connect to the peer
			
			// set up TCP stream reader and writer
			InputStream inputStream = s.getInputStream();
			OutputStream outputStream = s.getOutputStream();
			
			MessageFields fields = new MessageFields();
			MessageFactory msgfac = new MessageFactory();
			Message message = msgfac.createGenericMessage("KX_MESSAGE");
			fields.setNumberOfHops("3");
			fields.setPseudo_identity(psuedoIdentity);
			fields.setPort_number(exchange.getKxPotNumber());
			fields.setPeerIdentity(exchange.getPeerId());
			fields.setIpv4_address(exchange.getKxIPv4Address());
			fields.setIpv6_address(exchange.getKxIPv6Address());
			message.SetMessageFields(fields);
	
			byte[] msg = message.createMessage("MSG_KX_TN_BUILD_IN");
			outputStream.write(msg);
			outputStream.flush();
			
			byte[] recievedBytes = new byte[MessagePacket.PACKET_SIZE]; // 64kb as we are communicating with DHT
			int recievedBytesCount = inputStream.read(recievedBytes); // wait and read the reply
			
			// parse message packets
			MessagePacket packet = new MessagePacket();
			HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedBytes);
			
			if (hmap == null || recievedBytesCount == 0)
			{
				inputStream.close();
				outputStream.close();
				s.close();
				return retVal;
			}
			// check the replied message status
			int statusMsg = CommonFunctions.ByteArrayToInt(hmap.get("messageType"));
			// check the replied message status
			if(statusMsg ==  Message.MessageType.MSG_KX_TN_READY.getValue())
			{
				String pseudo_identity = CommonFunctions.ByteArrayToString(hmap.get("pseudo_identity"));
				retVal = true;
			}
			else if (statusMsg == Message.MessageType.MSG_DHT_ERROR.getValue())
			{
				logger.error("KXMessages: unable to recieve the MSG_KX_TN_READY message from the KX. ");
			}
			
			// close streams
			inputStream.close();
			outputStream.close();
			// close socket
			s.close();
		}
		catch(Exception ex)
		{
			logger.error("KXMessages: unable to build the incoming tunnel. " + ex.getMessage());
		}
		
		return retVal;
	}
	
	public String CallKXToBuildOutgoingTunnel(String psuedoIdentity, ExchangePointInfo exchange)
	{
		String ipAddress = "";
		try
		{
			Socket s = new Socket();
			s.connect(new InetSocketAddress(hostName, portNumber), TimeOut); // connect to the peer
			
			// set up TCP stream reader and writer
			InputStream inputStream = s.getInputStream();
			OutputStream outputStream = s.getOutputStream();
			
			MessageFields fields = new MessageFields();
			MessageFactory msgfac = new MessageFactory();
			Message message = msgfac.createGenericMessage("KX_MESSAGE");
			fields.setNumberOfHops("3");
			fields.setPseudo_identity(psuedoIdentity);
			fields.setPort_number(exchange.getKxPotNumber());
			fields.setPeerIdentity(exchange.getPeerId());
			fields.setIpv4_address(exchange.getKxIPv4Address());
			fields.setIpv6_address(exchange.getKxIPv6Address());
			message.SetMessageFields(fields);
	
			byte[] msg = message.createMessage("MSG_KX_TN_BUILD_OUT");
			outputStream.write(msg);
			outputStream.flush();
			
			byte[] recievedBytes = new byte[MessagePacket.PACKET_SIZE]; // 64kb as we are communicating with DHT
			int recievedBytesCount = inputStream.read(recievedBytes); // wait and read the reply
			
			// parse message packets
			MessagePacket packet = new MessagePacket();
			HashMap<String, byte[]> hmap = packet.readMessagePacket(recievedBytes);
			
			if (hmap == null || recievedBytesCount == 0)
			{
				inputStream.close();
				outputStream.close();
				s.close();
				return ipAddress;
			}
			// check the replied message status
			int statusMsg = CommonFunctions.ByteArrayToInt(hmap.get("messageType"));
			// check the replied message status
			if(statusMsg ==  Message.MessageType.MSG_KX_TN_READY.getValue())
			{
				String pseudo_identity = CommonFunctions.ByteArrayToString(hmap.get("pseudo_identity"));
				ipAddress = CommonFunctions.ByteArrayToString(hmap.get("ipv4_address"));
			}
			else if (statusMsg == Message.MessageType.MSG_DHT_ERROR.getValue())
			{
				logger.error("KXMessages: unable to recieve the MSG_KX_TN_READY message from the KX.");
			}
			
			// close streams
			inputStream.close();
			outputStream.close();
			// close socket
			s.close();
		}
		catch(Exception ex)
		{
			logger.error("KXMessages: unable to build the outgoing tunnel. " + ex.getMessage());
		}
		
		return ipAddress;
	}
	
	public boolean CallKXTunnelDestroy(String psuedoIdentity)
	{
		boolean retVal = false;
		try
		{
			Socket s = new Socket();
			s.connect(new InetSocketAddress(hostName, portNumber), TimeOut); // connect to the peer
			
			// set up TCP stream reader and writer
			InputStream inputStream = s.getInputStream();
			OutputStream outputStream = s.getOutputStream();
			
			MessageFields fields = new MessageFields();
			MessageFactory msgfac = new MessageFactory();
			Message message = msgfac.createGenericMessage("KX_MESSAGE");
			fields.setPseudo_identity(psuedoIdentity);
			message.SetMessageFields(fields);
	
			byte[] msg = message.createMessage("MSG_KX_TN_DESTROY");
			outputStream.write(msg);
			outputStream.flush();
			
			// close streams
			inputStream.close();
			outputStream.close();
			// close socket
			s.close();
			retVal = true;
		}
		catch(Exception ex)
		{
			logger.error("KXMessages: unable to destroy the tunnel. " + ex.getMessage());
		}
		
		return retVal;
	}
}
