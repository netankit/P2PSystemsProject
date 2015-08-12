package test;

import java.io.IOException;

import messages.Message;
import messages.MessageFactory;

import org.junit.Test;

import config.ConfigReader;

/**
 * SimpleUnitTest Class has some sample tests.
 * 
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 */
public class SimpleUnitTest {
	@Test
	public void dhtMessageTest() {
		String msgType = "DHT_MESSAGE";

		MessageFactory msgfac = new MessageFactory();

		Message msgtemp = msgfac.createGenericMessage(msgType);

		Message msg = msgtemp.createMessage("DHT_TRACE");
	}

	// System.out.println(msg.toString());

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
		// function - regulate packet size

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
		String filename = "C:\\Users\\Ankit\\Documents\\Eclipse_Workspace\\group09\\VoIP\\config.ini";
		ConfigReader conf = new ConfigReader(filename);
		System.out.println(conf.getHostkey());
	}
}
