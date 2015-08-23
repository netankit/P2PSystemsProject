package voip;

import java.util.Timer;

import org.apache.commons.logging.Log;

import datacontrol.AudioSession;
import logger.LogSetup;

public class CallManager {

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(CallManager.class.getName());

	private AudioSession audioVoiceSession;
	private String IPAddress;
	private VOIP voip;
	private Timer timer;

	public CallManager(VOIP voip) {
		this.voip = voip;
		// audioVoiceSession = new AudioSession(this.voip.getDataPacketSize(),
		// this.voip.getCalleePortNumber());
		audioVoiceSession = new AudioSession(voip);
	}

	public VOIP getVOIP() {
		return this.voip;
	}

	public void StopCall() {
		stopCall();
	}

	public void startCall(String IPAddress) {
		this.IPAddress = IPAddress;
		audioVoiceSession.initSession(IPAddress);
		audioVoiceSession.startSession();
		timer = new Timer();
		// send heart beat messages after 10 Sec
		timer.schedule(new PeerAvailabilityChecker(voip.getPeerStatus()), 0, 10000);
	}

	public void stopCall() {
		timer.cancel();
		// quit the session first
		voip.getPeerStatus().quitSession(voip.getPeerStatus().getIPAddressOfOtherPeer());
		voip.getPeerStatus().setStatus(ConnectionStatusManager.PEER_STATUS_IDLE);
		audioVoiceSession.stopSession();
	}

	public void ForceStopCall() {
		timer.cancel();
		// quit the session first
		voip.getPeerStatus().setStatus(ConnectionStatusManager.PEER_STATUS_IDLE);
		audioVoiceSession.stopSession();
	}
}