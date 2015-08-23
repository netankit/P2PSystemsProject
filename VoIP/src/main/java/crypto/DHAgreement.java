/**
 * 
 */
package crypto;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DHParameterSpec;

/**
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 */
public class DHAgreement implements Runnable {
	byte bob[], alice[];
	boolean doneAlice = false;
	byte[] ciphertext;

	BigInteger aliceP, aliceG;
	int aliceL;

	public synchronized void run() {
		if (!doneAlice) {
			doneAlice = true;
			doAlice();
		} else
			doBob();
	}

	public synchronized void doAlice() {
		try {
			// Step 1: Alice generates a key pair / Used in DHT PUT Public Key
			// or
			// Pseudonym Key
			// HashMap<Pseudo Identity, Public Key>

			KeyPairGenerator kpg = KeyPairGenerator.getInstance("DH");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();

			// PeudoIdentity of Alice
			// Considering just the public key part of the key pair.
			// String - We can hash this
			System.out.println(kp.getPublic().toString().getBytes());

			// TODO: GET SHA HASH OF ABOVE ^/

			// Hash Key - Or We can hash this!
			System.out.println(kp.getPublic().hashCode());

			// Step 2: Alice sends the public key and the
			// Diffie-Hellman key parameters to Bob
			Class dhClass = Class.forName("javax.crypto.spec.DHParameterSpec");
			DHParameterSpec dhSpec = ((DHPublicKey) kp.getPublic()).getParams();
			aliceG = dhSpec.getG();
			aliceP = dhSpec.getP();
			aliceL = dhSpec.getL();
			alice = kp.getPublic().getEncoded();
			notify();

			// Step 4 part 1: Alice performs the first phase of the
			// protocol with her private key
			KeyAgreement ka = KeyAgreement.getInstance("DH");
			ka.init(kp.getPrivate());

			// Step 4 part 2: Alice performs the second phase of the
			// protocol with Bob's public key
			while (bob == null) {
				wait();
			}
			KeyFactory kf = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(bob);
			PublicKey pk = kf.generatePublic(x509Spec);
			ka.doPhase(pk, true);

			// Step 4 part 3: Alice can generate the secret key
			byte secret[] = ka.generateSecret();

			// Step 6: Alice generates a DES key
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			DESKeySpec desSpec = new DESKeySpec(secret);
			SecretKey key = skf.generateSecret(desSpec);

			// Padding and Encryption Test

			// byte[] test1 = "My name is ankit".getBytes();
			// byte[] abc = new byte[16];
			// byte[] test2 = Test2.concatenateTwoByteArrays(test1, abc);
			// byte[] test3 = " bahuguna".getBytes();
			// byte[] test4 = Test2.concatenateTwoByteArrays(test2, test3);
			// byte[] test5 = Test2.concatenateTwoByteArrays(test4, abc);

			// System.out.println(test.length);
			// System.out.println(c.length);

			// End of Padding and Encryption Test

			// Step 7: Alice encrypts data with the key and sends
			// the encrypted data to Bob
			Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, key);
			ciphertext = c.doFinal("Stand and unfold yourself".getBytes());
			// ciphertext = c.doFinal(test5);
			notify();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void doBob() {
		try {
			// Step 3: Bob uses the parameters supplied by Alice
			// to generate a key pair and sends the public key
			while (alice == null) {
				wait();
			}
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("DH");
			DHParameterSpec dhSpec = new DHParameterSpec(aliceP, aliceG, aliceL);
			kpg.initialize(dhSpec);
			KeyPair kp = kpg.generateKeyPair();
			bob = kp.getPublic().getEncoded();
			notify();

			// Step 5 part 1: Bob uses his private key to perform the
			// first phase of the protocol
			KeyAgreement ka = KeyAgreement.getInstance("DH");
			ka.init(kp.getPrivate());

			// Step 5 part 2: Bob uses Alice's public key to perform
			// the second phase of the protocol.
			KeyFactory kf = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(alice);
			PublicKey pk = kf.generatePublic(x509Spec);
			ka.doPhase(pk, true);

			// Step 5 part 3: Bob generates the secret key
			byte secret[] = ka.generateSecret();

			// Step 6: Bob generates a DES key
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			DESKeySpec desSpec = new DESKeySpec(secret);
			SecretKey key = skf.generateSecret(desSpec);

			// Step 8: Bob receives the encrypted text and decrypts it
			Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, key);
			while (ciphertext == null) {
				wait();
			}
			byte plaintext[] = c.doFinal(ciphertext);
			System.out.println("Bob got the string " + new String(plaintext));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		DHAgreement test = new DHAgreement();
		new Thread(test).start(); // Starts Alice
		new Thread(test).start(); // Starts Bob
	}
}
