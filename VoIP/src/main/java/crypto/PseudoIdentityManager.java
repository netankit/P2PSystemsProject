/**
 * 
 */
package crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the generation, storing / caching the pseudo-identity of the caller
 * and the callee after each DHT_GET_REPLY.
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 */
public class PseudoIdentityManager {

	MD5Hash shahash = new MD5Hash();
	// cachedMap: Its the universal map which stores <PseudoIdentity,
	// PublicKey>
	// of the Caller(own) and other calle (other) peers.
	public HashMap<byte[], byte[]> cachedMapOthers = new HashMap<byte[], byte[]>();
	public HashMap<byte[], KeyPair> cachedMapOwn = new HashMap<byte[], KeyPair>();
	public ArrayList<byte[]> psuedoIdentityArr = new ArrayList<byte[]>();
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
				kpg.initialize(1024);
				KeyPair kp = kpg.generateKeyPair();

				byte[] publicKey = kp.getPublic().getEncoded();
				byte[] pseudoIdentity = shahash.getSHA256HashOfData(publicKey);
				this.cachedMapOwn.put(pseudoIdentity, kp);
				this.psuedoIdentityArr.add(pseudoIdentity);
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
