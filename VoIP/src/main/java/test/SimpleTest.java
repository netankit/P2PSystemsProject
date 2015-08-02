package test;

import messages.Message;
import messages.MessageFactory;

import org.junit.Test;

public class SimpleTest {
	@Test
	public void test() {
		String msgType = "DHT_MESSAGE";

		MessageFactory msgfac = new MessageFactory();

		Message msgtemp = msgfac.createGenericMessage(msgType);

		Message msg = msgtemp.createMessage("DHT_TRACE");

		// System.out.println(msg.toString());

	}
}
