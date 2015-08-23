package config;

/**
 * Native class which defines all the configuration parameters for the VOIP
 * application.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */

public interface Config {

	/**
	 * @return the hostkey
	 */
	public String getHostkey();

	/**
	 * @param hostkey
	 *            the hostkey to set
	 */
	public void setHostkey();

	/**
	 * @return the port
	 */
	public String[] getPort();

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort();

	/**
	 * @return the hostname
	 */
	public String[] getHostname();

	/**
	 * @param hostname
	 *            the hostname to set
	 */
	public void setHostname();

	/**
	 * @return the overlay_hostname
	 */
	public String[] getOverlay_hostname();

	/**
	 * @param overlay_hostname
	 *            the overlay_hostname to set
	 */
	public void setOverlay_hostname();

	/**
	 * @return the hostlist
	 */
	public String getHostlist();

	/**
	 * @param hostlist
	 *            the hostlist to set
	 */
	public void setHostlist();
	
	public String getTUNIP();
	public void setTUNIP();
	
	public int getVOIPPortNumber();
	public void setVOIPPortNumber();
	
	public int getVoiceDataPacketSize();
	public void setVoiceDataPacketSize();
	
	public int getKXOutReachPortNumber();
	public void setKXOutReachPortNumber();
	
	public String getKXOutReachHostName();
	public void setKXOutReachHostName();
}
