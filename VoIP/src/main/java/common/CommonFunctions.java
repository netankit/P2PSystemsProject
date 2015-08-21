package common;

import java.math.BigInteger;

public class CommonFunctions {
	
	// this function check if the input IP address is correct
	public static boolean checkIPAddr(String IPAddr)
	{
		return IPAddr.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
	}
	
	public static int ByteArrayToInt(byte[] src)
	{
		int number = 0;
		try
		{
			String msg = new String(new BigInteger(new BigInteger(src).toString(2), 2).toByteArray()).trim();
			number = Integer.parseInt(msg);
		}
		catch(Exception ex)
		{
			number = 0;
		}
		
		return number;	
	}
	
	public static String ByteArrayToString(byte[] src)
	{
		return new String(new BigInteger(new BigInteger(src).toString(2), 2).toByteArray()).trim();	
	}
}
