package config;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.Wini;

/**
 * Reads the default configuration Parameters and sets them globally to be used
 * by all the other classes. This class uses ini4j library to parse windows INI
 * files.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class ConfigReader implements Config {

	/**
	 * @param ini
	 * @param hostkey
	 * @param port
	 * @param hostname
	 * @param overlay_hostname
	 * @param hostlist
	 * @param filename
	 */
	public ConfigReader(String filename) throws IOException {
		super();
		this.setiniFile(filename);
		this.setHostkey();
		this.setHostlist();
		this.setHostname();
		this.setPort();
		this.setOverlay_hostname();
		this.setTUNIP();
		this.setVoiceDataPacketSize();
		this.setVOIPPortNumber();
		this.setKXOutReachPortNumber();
		this.setKXOutReachHostName();
	}

	private Wini ini;
	private String hostkey;
	private String[] port;
	private String[] hostname;
	private String[] overlay_hostname;
	private String hostlist;
	private String TUN_IP;
	private String voipPortNumber;
	private String voiceDataPacketSize;
	String filename = "";
	private String kxOutreachPortNumber;
	private String kxOutreachHostName;

	public void setiniFile(String filename) throws IOException {
		this.ini = new Wini(new File(filename));
	}

	public Wini getiniFile() throws IOException {
		return this.ini;
	}

	/**
	 * @return the hostkey
	 */
	public String getHostkey() {
		return hostkey;
	}

	/**
	 * @param hostkey
	 *            the hostkey to set
	 */
	public void setHostkey() {
		Ini.Section defaultSection = ini.get("DEFAULT");
		String hostkey = defaultSection.get("HOSTKEY", 0);
		this.hostkey = hostkey;
	}

	/**
	 * @return the port
	 */
	public String[] getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort() {
		Ini.Section dht = ini.get("DHT");
		String[] port = dht.getAll("PORT", String[].class);
		this.port = port;
	}

	/**
	 * @return the hostname
	 */
	public String[] getHostname() {
		return hostname;
	}

	/**
	 * @param hostname
	 *            the hostname to set
	 */
	public void setHostname() {
		Ini.Section dht = ini.get("DHT");
		String[] hostname = dht.getAll("HOSTNAME", String[].class);
		this.hostname = hostname;
	}

	/**
	 * @return the overlay_hostname
	 */
	public String[] getOverlay_hostname() {
		return overlay_hostname;
	}

	/**
	 * @param overlay_hostname
	 *            the overlay_hostname to set
	 */
	public void setOverlay_hostname() {
		Ini.Section dht = ini.get("DHT");
		String[] overlay_hostname = dht.getAll("OVERLAY_HOSTNAME",
				String[].class);
		this.overlay_hostname = overlay_hostname;
	}

	/**
	 * @return the hostlist
	 */
	public String getHostlist() {
		return hostlist;
	}

	/**
	 * @param hostlist
	 *            the hostlist to set
	 */
	public void setHostlist() {
		Ini.Section dht = ini.get("DHT");
		String hostlist = dht.get("HOSTLIST", 0);
		this.hostlist = hostlist;
	}
	
	public String getTUNIP()
	{
		return TUN_IP;
	}
	
	public void setTUNIP()
	{
		Ini.Section defaultSection = ini.get("KX");
		String tunIP = defaultSection.get("TUN_IP", 0);
		this.TUN_IP = tunIP;	
	}

	public int getVOIPPortNumber()
	{
		return Integer.parseInt(voipPortNumber);
	}
	
	public void setVOIPPortNumber()
	{
		Ini.Section defaultSection = ini.get("VOIP");
		String voipPortNumber = defaultSection.get("VOIP_Port", 0);
		this.voipPortNumber = voipPortNumber;
	}
	
	public int getVoiceDataPacketSize()
	{
		return Integer.parseInt(voiceDataPacketSize);
	}
	
	public void setVoiceDataPacketSize()
	{
		Ini.Section defaultSection = ini.get("VOIP");
		String voiceDataPacketSize = defaultSection.get("VoiceDataPacketSize", 0);
		this.voiceDataPacketSize = voiceDataPacketSize;
	}
	
	public int getKXOutReachPortNumber()
	{
		return Integer.parseInt(kxOutreachPortNumber);
	}
	
	public void setKXOutReachPortNumber()
	{
		Ini.Section defaultSection = ini.get("KX");
		String kxOutreachPortNumber = defaultSection.get("OUTREACH_PORT", 0);
		this.kxOutreachPortNumber = kxOutreachPortNumber;
	}
	
	public String getKXOutReachHostName()
	{
		return kxOutreachHostName;
	}
	public void setKXOutReachHostName()
	{
		Ini.Section defaultSection = ini.get("KX");
		String kxOutreachHostName = defaultSection.get("OUTREACH_HOSTNAME", 0);
		this.kxOutreachHostName = kxOutreachHostName;
	}
}
