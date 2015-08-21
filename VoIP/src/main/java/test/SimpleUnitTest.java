package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.junit.Test;

import config.ConfigReader;
import crypto.IDGenerator;
import crypto.MD5Hash;
import crypto.PublicKeyReader;
import logger.LogSetup;
import messages.Message;
import messages.Message.MessageType;
import messages.MessageFactory;
import messages.MessagePacket;
import ui.ClientUI;

/**
 * SimpleUnitTest Class has some sample tests.
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 */
public class SimpleUnitTest {
	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	@Test
	public void dhtMessageTest() {
		String msgType = "DHT_MESSAGE";

		MessageFactory msgfac = new MessageFactory();

		Message msgtemp = msgfac.createGenericMessage(msgType);

		byte[] msg = msgtemp.createMessage("MSG_DHT_TRACE");
	}

	@Test
	public void packetTest() {

		// pseudocode for creating a data packet from sender's side
		// MSG_VOIP_CALL_INITIATE
		String size = "1234"; // 16bits
		String messageType = ""; // 16 bits
		String pseudo_identity_calle = "";
		String num_tries = ""; // 8 bits
		String reserved = ""; // 24bits

		// need to get the data in the format with 64KB packets sent across the
		// network.
		// function - convert string in bit format.
		// function - padding the bits if not done.
		// function - regulate packet size //64KB //512000 bits

		// returns a stream of byte buffer which can be read by the server on
		// the other side.

		// pseudocode for the reciever to unpack the same data-pack.
		// First extract the type of the message to initiate the appropriate
		// function call which is message specific.
		// Extract bit by bit. Say we save the first 16 bits of the byte stream
		// and then save them in temporary
		// variables based on the type of message.
		// Load the variables in the respective variables (sort of global) and
		// send the final audio packets to
		// the module which listen for the same.
	}

	@Test
	public void configTest() throws IOException {
		String filename = "./config.ini";
		ConfigReader conf = new ConfigReader(filename);
		System.out.println(conf.getHostkey());
	}

	@Test
	public void byteHandlingExampleTest() {

		// String ip = "192.168.10.1";
		// char[] ip_bytes = ip.toCharArray();
		// System.out.println(ip_bytes.length);
		//
		// final long startTime = System.currentTimeMillis();
		//
		// String text = "MSG_KX_TN_READY";
		// System.out.println("Text: " + text);
		//
		// String binary = new BigInteger(text.getBytes()).toString(2);
		// System.out.println("As binary: " + binary);
		// System.out.println(binary.length());
		//
		// byte[] pack = new byte[512000];
		// byte[] b2 = text.getBytes();
		//
		// String text_1 = "MSG_KX_TN_OPEN";
		// System.out.println(text_1.getBytes().length);
		//
		// byte[] b1 = text_1.getBytes();
		// ByteBuffer target = ByteBuffer.wrap(pack);
		// target.put(b1);
		// target.put(b2);
		//
		// byte[] c = target.array();
		//
		// String binary_2 = new BigInteger(c).toString(2);
		// String text3 = new String(new BigInteger(binary_2, 2).toByteArray());
		// System.out.println("As text concat: " + text3);
		// final long endTime = System.currentTimeMillis();
		// System.out.println("Total execution time: " + (endTime - startTime) /
		// 1000 + " seconds");
		//
		// target.clear();
		// String binary_1 = new BigInteger(text_1.getBytes()).toString(2);
		//
		// String text2 = new String(new BigInteger(binary, 2).toByteArray());
		// System.out.println("As text: " + text2);

		final long startTime = System.currentTimeMillis();
		ArrayList<byte[]> abc = new ArrayList();

		String msg1 = "MSG_KX_TN_READY";
		String msg2 = "MSG_KX_TN_OPEN";

		byte[] msg1b = getPaddedByteArray(msg1, 16);
		byte[] msg2b = getPaddedByteArray(msg2, 16);

		abc.add("MSG_KX_TN_READY".getBytes());
		abc.add("MSG_KX_TN_OPEN".getBytes());

		byte[] zen = concatenateByteArrays(abc);
		String binary = new BigInteger(zen).toString(2);
		String out = new String(new BigInteger(binary, 2).toByteArray());
		System.out.println(out);
		final long endTime = System.currentTimeMillis();

		System.out.println("Total execution time: " + (endTime - startTime) / 1000 + " seconds");

		byte[] dest = new byte[10];

		byte[] src = "ankit".getBytes();

		System.arraycopy(src, 0, dest, 0, src.length);
		System.out.println(printByteArrayContents(dest));
		System.out.println(dest.length);
		logger.info("Initialized");

	}

	@Test
	public void checkArrayCopyRange() {
		int[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] arr1 = Arrays.copyOfRange(arr, 0, 5);
		System.out.println("First Part");
		for (int i = 0; i < arr1.length; i++) {
			System.out.print(arr1[i] + " ");
		}
		int[] arr2 = Arrays.copyOfRange(arr, 5, 10);
		System.out.println("\nSecond Part");
		for (int i = 0; i < arr2.length; i++) {
			System.out.print(arr2[i] + " ");
		}
	}

	@Test
	public void checkMessagePacketType() {
		MessagePacket mp = new MessagePacket();
		// byte[] temp = mp.createMessagePacket(MessageType.DHT_TRACE);
		String msgType = "DHT_MESSAGE";
		MessageFactory msgfac = new MessageFactory();
		Message msgtemp = msgfac.createGenericMessage(msgType);
		byte[] msg = msgtemp.createMessage("MSG_DHT_TRACE");
		MessageType result = mp.getMessagePacketType(msg);
		// System.out.println(result);
		assertEquals(MessageType.MSG_DHT_TRACE, result);

	}

	@Test
	public void checkMessagePacketReadAndCreate() {
		MessagePacket mp = new MessagePacket();
		// byte[] temp = mp.createMessagePacket(MessageType.DHT_TRACE);
		String msgType = "DHT_MESSAGE";
		MessageFactory msgfac = new MessageFactory();
		Message msgtemp = msgfac.createGenericMessage(msgType);
		byte[] msg = msgtemp.createMessage("MSG_DHT_TRACE");
		HashMap<String, byte[]> hmap = mp.readMessagePacket(msg);
		assertEquals("AnkitKey", printByteArrayContents(hmap.get("key")));

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

	/**
	 * Concatenate a list of byte arrays and returns the final array.
	 * 
	 * @param blocks
	 * @return byte array of resultant byte arrays.
	 */

	public byte[] concatenateByteArrays(ArrayList<byte[]> blocks) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		for (byte[] b : blocks) {
			os.write(b, 0, b.length);
		}
		byte[] result = os.toByteArray();
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Print the contents of the byte array
	 * 
	 * @param src
	 * @return
	 */
	public String printByteArrayContents(byte[] src) {
		return new String(new BigInteger(new BigInteger(src).toString(2), 2).toByteArray()).trim();
	}

	@Test
	public void checkPeerIDGenration() {
		// Tested on Mac-OSX
		// Generates a Public/Private Key pair 4096 bit SHA encrypted. HOSTKEY =
		// keypair.pem
		String command1 = "openssl genrsa -out keypair.pem 4096";
		String output1 = executeCommand(command1);
		System.out.println(output1);

		// Generates a public key part of the public/private key and stores the
		// same in a binary format
		String command2 = "openssl rsa -in keypair.pem -pubout -inform PEM -outform DER -out pub.der";
		String output2 = executeCommand(command2);
		System.out.println(output2);

		// Use pub.der and extract the PEER_ID (BINARY) using the following:
		String command3 = "openssl dgst -sha256 -binary pub.der";
		String output3 = executeCommand(command3);
		System.out.println("PEER ID (Binary): " + output3);

		// Use pub.der and extract the PEER_ID (HEX) using the following:
		String command4 = "openssl dgst -sha256 -hex pub.der";
		String output4 = executeCommand(command4);
		System.out.println("PEER ID (Hexadecimal): " + output4);

	}

	@Test
	public void checkIDGenerator() throws Exception {
		String filename = "./config.ini";
		ConfigReader conf = new ConfigReader(filename);
		String hostkey_file = conf.getHostkey();
		IDGenerator peeridgen = new IDGenerator();
		byte[] peerid_binary = peeridgen.getPeerID(hostkey_file, "binary");
		System.out.println(printByteArrayContents(peerid_binary));
		byte[] peerid_hexa = peeridgen.getPeerID(hostkey_file, "hexa");
		// assertEquals("SHA256(pub.der)=8ad1faced12b743da10d3b65b97e7706332287645903d3ca9c942d077390cd81",
		// printByteArrayContents(peerid_hexa));

	}

	@Test
	public void checkKeyReaders() throws Exception {

		String filename = "./config.ini";
		ConfigReader conf = new ConfigReader(filename);
		String hostkey_file = conf.getHostkey();

		// String command2 = "openssl rsa -in " + hostkey_file + " -pubout
		// -inform PEM -outform DER -out pub.der";
		// String output2 = executeCommand(command2);

		// PrivateKeyReader prvkeyrdr = new PrivateKeyReader();

		// PrivateKey priv = prvkeyrdr.get(hostkey_file);

		PublicKeyReader pubkeyrdr = new PublicKeyReader();
		PublicKey pub = pubkeyrdr.get("pub.der");
		byte[] tmp = pub.toString().getBytes();
		MD5Hash md5 = new MD5Hash();
		byte[] temp2 = md5.getSHA256HashOfData(tmp);
		System.out.println(temp2.length);

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

	@Test
	public void checkEnumMessageValue() {
		assertEquals("501", String.valueOf(MessageType.MSG_DHT_GET.getValue()));
	}

}
