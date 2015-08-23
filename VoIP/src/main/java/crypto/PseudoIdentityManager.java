/**
 * 
 */
package crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 */
public class PseudoIdentityManager {

	MD5Hash shahash = new MD5Hash();
	// cachedMap: Its the universal map which stores <PseudoIdentity,
	// PublicKey>
	// of the Caller(own) and other calle (other) peers.
	HashMap<byte[], byte[]> cachedMapOthers = new HashMap<byte[], byte[]>();
	HashMap<byte[], byte[]> cachedMapOwn = new HashMap<byte[], byte[]>();

	/**
	 * @param cachedMapOthers
	 * @param cachedMapOwn
	 */
	public PseudoIdentityManager() {
		super();
		if (this.cachedMapOwn.isEmpty()) {
			KeyPairGenerator kpg;
			try {
				kpg = KeyPairGenerator.getInstance("DH");
				kpg.initialize(2048);
				KeyPair kp = kpg.generateKeyPair();

				byte[] publicKey = kp.getPublic().toString().getBytes();
				byte[] pseudoIdentity = shahash.getSHA256HashOfData(publicKey);
				this.cachedMapOwn.put(pseudoIdentity, publicKey);
				// System.out.println(pseudoIdentity);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void main(String args[]) {
		PseudoIdentityManager tmp = new PseudoIdentityManager();
		if (tmp.cachedMapOwn.isEmpty()) {
			System.out.println("Empty");
		} else {
			for (byte[] key : tmp.cachedMapOwn.keySet()) {
				System.out.println(key + " " + tmp.cachedMapOwn.get(key));
			}

		}
	}

}
