package voip;

import java.io.IOException;

import org.apache.commons.logging.Log;

import ui.ClientUI;
import logger.LogSetup;
import messages.Message;

public class VOIP {
	
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	private CallManager callManager;
	private ConnectionStatusManager peerStatus;
	private int calleePortNumber;
	private int dataPacketSize;
	private String IPAddress;
	private String psuedoIdentity;
	
	public VOIP(int portNumber, int dataPacketSize, String IPAddress, String psuedoIdentity)
	{	
		this.dataPacketSize = 512;
		if (dataPacketSize != 0)
			this.dataPacketSize = dataPacketSize;
		calleePortNumber = 0;
		if (portNumber != 0)
			calleePortNumber = portNumber;
		this.IPAddress = IPAddress;
		this.psuedoIdentity = psuedoIdentity;
		callManager = new CallManager(this);
		peerStatus = new ConnectionStatusManager(callManager, calleePortNumber, this.psuedoIdentity, this.IPAddress);
		peerStatus.start();
	}
	
	public String getIPAddress()
	{
		return IPAddress;
	}
	
	public int getDataPacketSize()
	{
		return dataPacketSize;
	}
	
	public int getCalleePortNumber()
	{
		return calleePortNumber;
	}
	
	public ConnectionStatusManager getPeerStatus()
	{
		return peerStatus;
	}

	public int InitiateCall(String IPAddressOfCallee, String psuedoIdentityOfCallee)
	{
		int remoteStatus = Message.MessageType.MSG_VOIP_ERROR.getValue();
		try
		{
			peerStatus.setIPAddressOfOtherPeer(IPAddressOfCallee);
			peerStatus.SetPseudoIdentityOfOtherPeer(psuedoIdentityOfCallee);
			int validateResult = peerStatus.ValidatePeer(IPAddressOfCallee);
			if (validateResult == Message.MessageType.MSG_VOIP_ERROR.getValue())
			{
				logger.info("Unable to validate the peer at " + IPAddressOfCallee);
				return remoteStatus;
			}
			
			remoteStatus = peerStatus.CheckStatus(IPAddressOfCallee);
		}
		catch(IOException ex)
		{
			logger.info("Fail to make connection to " + IPAddressOfCallee);
			return remoteStatus;
		}
		
		if (remoteStatus == Message.MessageType.MSG_VOIP_CALL_INITIATE_OK.getValue())
		{
			// set my Peer status to in-session
			peerStatus.setStatus(ConnectionStatusManager.PEER_STATUS_SESSION);
			// start the call
			try
			{
				peerStatus.InformCalleeToInitiate(IPAddressOfCallee);
			}
			catch(IOException ex)
			{
				logger.info("Fail to initiate call to " + IPAddressOfCallee);
				return Message.MessageType.MSG_VOIP_ERROR.getValue();
			}
			
			callManager.startCall(IPAddressOfCallee);
		}
		else if (remoteStatus == Message.MessageType.MSG_VOIP_CALL_BUSY.getValue())
		{
			logger.info(IPAddressOfCallee + " is already in call");
		}
		else if (remoteStatus == Message.MessageType.MSG_VOIP_CALL_WAITING.getValue())
		{
			logger.info(IPAddressOfCallee + " is in waiting state. Try bit later");
		}
		else if (remoteStatus == Message.MessageType.MSG_VOIP_ERROR.getValue())
		{
			logger.info(IPAddressOfCallee + " has some error");
		}
		else
		{
			logger.info(IPAddressOfCallee + " has unknown network error");
		}
		
		return remoteStatus;
	}

	public void StopCall() {
		callManager.stopCall();
	}
}