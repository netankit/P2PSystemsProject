package crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;

import logger.LogSetup;

public class MD5Hash {

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(MD5Hash.class.getName());

	public byte[] getMD5HashOfData(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			return md.digest();
		} catch (NoSuchAlgorithmException ex) {
			logger.error("MD5Hash: " + ex.getMessage());
		} catch (Exception e) {
			logger.error("MD5Hash: " + e.getMessage());
		}

		return null;
	}

	public byte[] getSHA256HashOfData(byte[] data) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(data);
			return sha.digest();
		} catch (NoSuchAlgorithmException ex) {
			logger.error("MD5Hash: " + ex.getMessage());
		} catch (Exception e) {
			logger.error("MD5Hash: " + e.getMessage());
		}

		return null;
	}

	public String ConvertByteArrayToHexString(byte[] arrayBytes) {

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}

	public Boolean CheckMD5HashOfData(byte[] data, byte[] MD5Hash) {
		String dataInHex = ConvertByteArrayToHexString(data);
		String MD5HashInHex = ConvertByteArrayToHexString(MD5Hash);

		return dataInHex.compareToIgnoreCase(MD5HashInHex) == 0;
	}
}
