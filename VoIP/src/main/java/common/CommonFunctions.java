package common;

import java.math.BigInteger;

public class CommonFunctions {
	
	// this function check if the input IP address is correct
	public static boolean checkIPAddr(String IPAddr)
	{
		return IPAddr.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
	}
	
	public static String ByteArrayToString(byte[] src)
	{
		return new String(new BigInteger(new BigInteger(src).toString(2), 2).toByteArray()).trim();	
	}
}
