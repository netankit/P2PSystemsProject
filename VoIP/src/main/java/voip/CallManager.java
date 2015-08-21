package voip;

import org.apache.commons.logging.Log;
import ui.ClientUI;
import logger.LogSetup;
import datacontrol.*;

public class CallManager {
	
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	private AudioSession audioVoiceSession;
	private String IPAddress;
	private VOIP voip;
	
	public CallManager(VOIP voip)
	{
		this.voip = voip;
		audioVoiceSession = new AudioSession(this.voip.getDataPacketSize(), this.voip.getCalleePortNumber());
	}	
	
	public void StopCall()
	{
		stopCall();
	}
	
	public void startCall(String IPAddress)
	{
		this.IPAddress = IPAddress;
		audioVoiceSession.initSession(IPAddress);
		audioVoiceSession.startSession();
	}
	
	public void stopCall()
	{
		// quit the session first
		voip.getPeerStatus().quitSession(IPAddress);
		voip.getPeerStatus().setStatus(ConnectionStatusManager.PEER_STATUS_IDLE);
		audioVoiceSession.stopSession();
	}
}