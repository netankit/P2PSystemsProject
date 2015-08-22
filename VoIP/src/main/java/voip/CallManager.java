package voip;

import java.util.Timer;

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
	private Timer timer;
	
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
		timer = new Timer();
		// send heart beat messages after 30 Sec
		timer.schedule(new PeerAvailabilityChecker(voip.getPeerStatus()), 0, 30000);
	}
	
	public void stopCall()
	{
		timer.cancel();
		// quit the session first
		voip.getPeerStatus().quitSession(voip.getPeerStatus().getIPAddressOfOtherPeer());
		voip.getPeerStatus().setStatus(ConnectionStatusManager.PEER_STATUS_IDLE);
		audioVoiceSession.stopSession();
	}
	
	public void ForceStopCall()
	{
		timer.cancel();
		// quit the session first
		voip.getPeerStatus().setStatus(ConnectionStatusManager.PEER_STATUS_IDLE);
		audioVoiceSession.stopSession();
	}
}