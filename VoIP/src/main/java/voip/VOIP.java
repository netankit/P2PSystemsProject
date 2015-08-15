package voip;

import java.io.IOException;
import org.apache.commons.logging.Log;
import ui.ClientUI;
import logger.LogSetup;

public class VOIP {
	
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	private CallManager callManager;
	private ConnectionStatusManager peerStatus;
	private int calleePortNumber;
	private int dataPacketSize;
	private String IPAddress;
	
	public VOIP(int portNumber, int dataPacketSize)
	{	
		this.dataPacketSize = 512;
		if (dataPacketSize != 0)
			this.dataPacketSize = dataPacketSize;
		calleePortNumber = 0;
		if (portNumber != 0)
			calleePortNumber = portNumber;
		callManager = new CallManager(this);
		peerStatus = new ConnectionStatusManager(callManager, calleePortNumber);
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

	public int InitiateCall(String IPAddressOfCallee)
	{
		int result = -1;
		int remoteStatus = ConnectionStatusManager.PEER_STATUS_ERROR;
		try
		{
			int validateResult = peerStatus.ValidatePeer(IPAddressOfCallee);
			if (validateResult != 1)
			{
				logger.info("Unable to validate the peer at " + IPAddressOfCallee);
				return result;
			}
			
			remoteStatus = peerStatus.CheckStatus(IPAddressOfCallee);
		}
		catch(IOException ex)
		{
			logger.info("Fail to make connection to " + IPAddressOfCallee);
			return result;
		}
		switch(remoteStatus)
		{
			case ConnectionStatusManager.PEER_STATUS_IDLE:
				// set my Peer status to in-session
				peerStatus.setStatus(ConnectionStatusManager.PEER_STATUS_SESSION);
				// start the call
				callManager.startCall(IPAddressOfCallee);
				result = ConnectionStatusManager.PEER_STATUS_SESSION;
				break;
			case ConnectionStatusManager.PEER_STATUS_SESSION:
				logger.info(IPAddressOfCallee + " is already in call");
				result = ConnectionStatusManager.PEER_STATUS_SESSION;
				break;
			case ConnectionStatusManager.PEER_STATUS_ERROR:
				logger.info(IPAddressOfCallee + " has some error");
				result = ConnectionStatusManager.PEER_STATUS_ERROR;
				break;
			case ConnectionStatusManager.PEER_STATUS_WAITING:
				logger.info(IPAddressOfCallee + " is in waiting state. Try bit later");
				result = ConnectionStatusManager.PEER_STATUS_WAITING;
				break;
			default:
				logger.info(IPAddressOfCallee + " has unknown network error");
				break;
		}
		
		return result;
	}

	public void StopCall() {
		callManager.stopCall();
	}
}