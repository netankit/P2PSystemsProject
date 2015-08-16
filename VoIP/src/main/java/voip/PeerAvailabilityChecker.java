package voip;

import java.io.IOException;
import java.util.TimerTask;
import org.apache.commons.logging.Log;
import ui.ClientUI;
import logger.LogSetup;

public class PeerAvailabilityChecker extends TimerTask {
	
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());
	private VOIP voip;
	private int ToleranceLevel = 3;
	
	public PeerAvailabilityChecker(VOIP voip)
	{
		this.voip = voip;	
	}
	
	public void run()
	{
		if(voip.getPeerStatus().getStatus() == ConnectionStatusManager.PEER_STATUS_SESSION)
		{
			try
			{
				Boolean isActive = false;
				ConnectionStatusManager status = voip.getPeerStatus();
				for (int i = 0; i < ToleranceLevel; i++)
				{
					int result = status.ValidatePeer(voip.getIPAddress());
					if (result == 1)
					{
						isActive = true;
						break;
					}	
				}
				
				if (!isActive)
				{
					logger.error("PeerAvailabilityChecker: Peer is not available at " + voip.getIPAddress());
					logger.error("PeerAvailabilityChecker: So dropping the call");
					voip.StopCall();
				}
			}
			catch (IOException ex)
			{
				logger.error("PeerAvailabilityChecker: " + ex.getMessage());
			}
			catch (Exception e)
			{
				logger.error("PeerAvailabilityChecker: " + e.getMessage());
			}
		}
	}
}

//Timer timer = new Timer();
//timer.schedule(new PeerAvailabilityChecker(voip), 1000);
