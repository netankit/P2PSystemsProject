package dht;

public class ExchangePointInfo {

	private String peerId;
	private String kxPotNumber;
	private String kxIPv4Address;
	private String kxIPv6Address;
	
	public ExchangePointInfo()
	{
		setKxPotNumber("3001");
		setKxIPv4Address("127.0.0.1");
	}

	public String getPeerId() {
		return peerId;
	}

	public void setPeerId(String peerId) {
		this.peerId = peerId;
	}

	public String getKxPotNumber() {
		return kxPotNumber;
	}

	public void setKxPotNumber(String kxPotNumber) {
		this.kxPotNumber = kxPotNumber;
	}

	public String getKxIPv4Address() {
		return kxIPv4Address;
	}

	public void setKxIPv4Address(String kxIPv4Address) {
		this.kxIPv4Address = kxIPv4Address;
	}

	public String getKxIPv6Address() {
		return kxIPv6Address;
	}

	public void setKxIPv6Address(String kxIPv6Address) {
		this.kxIPv6Address = kxIPv6Address;
	}
}
