package ui;

/**
 *
 * ClientUI: This class implements the various basic UI functionalities of the
 * VOIP application such as initiate call, terminate call,busy call and waiting
 * call.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class ClientUI implements ClientUIInterface {

	// static LogSetup lg = new LogSetup();
	// static Log logger = lg.getLog(ClientUI.class.getName());

	public void initiateCall() {
		// TODO Auto-generated method stub
		// System.out.println("Call Initiated");
		// logger.info("Call Initiated");

	}

	public void terminateCall() {
		// TODO Auto-generated method stub
		// System.out.println("Call Terminated");
		// logger.info("Call Terminated");
	}

	public void busyCall() {
		// TODO Auto-generated method stub
		// logger.info("Call Busy");
	}

	public void waitingCall() {
		// TODO Auto-generated method stub
		// logger.info("Call Watiting");
	}

}
