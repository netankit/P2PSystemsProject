package crypto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.PublicKey;

/**
 * Generate the peer ID from the host key given on the disk. Also has the
 * functions to generate a 4096 bit encrypted hostekey and store it on disk.
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 */
public class IDGenerator {
	/**
	 * 
	 * @param hostkey_file
	 *            : Path to the hostkey.. read from the config.ini file should
	 *            be .pem format
	 * @param representation:
	 *            "binary" or "hexa"
	 * @return byte array with the 256 bit key for binary representation and the
	 *         requisite length for the hexadecimal representation.
	 * @throws Exception
	 */
	public byte[] getPeerID(String hostkey_file, String representation) throws Exception {
		if (!hostkey_file.endsWith(".pem") || representation == "") {
			return null;
		}
		// Generates a public key part of the public/private key and stores the
		// same in a binary format
		String command2 = "openssl rsa -in " + hostkey_file + " -pubout -inform PEM -outform DER -out pub.der";
		String output2 = executeCommand(command2);
		byte[] peerid = null;
		if (representation == "binary") {
			// Use pub.der and extract the PEER_ID (256 bit BINARY)
			PublicKeyReader pubkeyrdr = new PublicKeyReader();
			PublicKey pub = pubkeyrdr.get("pub.der");
			byte[] tmp = pub.toString().getBytes();
			MD5Hash md5 = new MD5Hash();
			peerid = md5.getSHA256HashOfData(tmp);
			// System.out.println("PEER ID (Binary): " + peerid.toString());
			// return peerid;
		} else if (representation == "hexa") {
			// Use pub.der and extract the PEER_ID (Hexa-Decimal) -- Only for
			// Debugging Purposes.
			PublicKeyReader pubkeyrdr = new PublicKeyReader();
			PublicKey pub = pubkeyrdr.get("pub.der");
			byte[] tmp = pub.toString().getBytes();
			MD5Hash md5 = new MD5Hash();
			peerid = md5.ConvertByteArrayToHexString(md5.getSHA256HashOfData(tmp)).getBytes();
			// System.out.println("PEER ID (Hexadecimal): " +
			// peerid.toString());
			// return peerid;
		}
		return peerid;
	}

	public String executeCommand(String command) {
		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();

	}

	/**
	 * Returns a padded byte array, the size is the size of the resulting padded
	 * array.
	 * 
	 * @param txt
	 * @param size
	 * @return
	 */
	public byte[] getPaddedByteArray(String txt, int size) {
		byte[] src = txt.getBytes();
		int len = src.length;
		int diff = size - len;
		byte[] dest = new byte[size];
		System.out.println("Difference: " + diff);
		System.arraycopy(src, 0, dest, 0, src.length);
		return dest;
	}

}
