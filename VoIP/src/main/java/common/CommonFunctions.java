package common;

public class CommonFunctions {
	
	// this function check if the input IP address is correct
	public static boolean checkIPAddr(String IPAddr)
	{
		return IPAddr.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
	}
}
