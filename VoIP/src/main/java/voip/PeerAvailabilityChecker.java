package voip;

import java.io.IOException;
import java.util.TimerTask;

import org.apache.commons.logging.Log;

import logger.LogSetup;
import messages.Message;

public class PeerAvailabilityChecker extends TimerTask {

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(PeerAvailabilityChecker.class.getName());
	private ConnectionStatusManager status;
	private int ToleranceLevel = 3;

	public PeerAvailabilityChecker(ConnectionStatusManager status) {
		this.status = status;
	}

	public void run() {
		if (status.getStatus() == ConnectionStatusManager.PEER_STATUS_SESSION) {
			try {
				Boolean isActive = false;
				for (int i = 0; i < ToleranceLevel; i++) {
					int result = status.ValidatePeer(status.getIPAddressOfOtherPeer());
					if (result != Message.MessageType.MSG_VOIP_ERROR.getValue()) {
						isActive = true;
						break;
					}
				}

				if (!isActive) {
					System.out.println(
							"PeerAvailabilityChecker: Peer is not available at " + status.getIPAddressOfOtherPeer());
					System.out.println("PeerAvailabilityChecker: So dropping the call. Check log file for details!");
					logger.error(
							"PeerAvailabilityChecker: Peer is not available at " + status.getIPAddressOfOtherPeer());
					logger.error("PeerAvailabilityChecker: So dropping the call");
					status.getCallManager().ForceStopCall();
				}
			} catch (IOException ex) {
				System.out.println(
						"PeerAvailabilityChecker: Peer is not available at " + status.getIPAddressOfOtherPeer());
				System.out.println("PeerAvailabilityChecker: So dropping the call. Check log file for details!");
				logger.error("PeerAvailabilityChecker: " + ex.getMessage());
				logger.error("PeerAvailabilityChecker: Peer is not available at " + status.getIPAddressOfOtherPeer());
				logger.error("PeerAvailabilityChecker: So dropping the call");
				status.getCallManager().ForceStopCall();
			} catch (Exception e) {
				System.out.println(
						"PeerAvailabilityChecker: Peer is not available at " + status.getIPAddressOfOtherPeer());
				System.out.println("PeerAvailabilityChecker: So dropping the call. Check log file for details!");
				logger.error("PeerAvailabilityChecker: " + e.getMessage());
				logger.error("PeerAvailabilityChecker: Peer is not available at " + status.getIPAddressOfOtherPeer());
				logger.error("PeerAvailabilityChecker: So dropping the call");
				status.getCallManager().ForceStopCall();
			}
		}
	}
}