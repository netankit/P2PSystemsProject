package ui;

/**
 * 
 * ClientUIInterface: Interface to the ClientUI Class
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public interface ClientUIInterface {
	/**
	 * Performs Call Initiation.
	 */
	public void initiateCall();

	/**
	 * Terminates the current call.
	 */
	public void terminateCall();

	/**
	 * Signals that the current peer is busy
	 */
	public void busyCall();

	/**
	 * Performs call waiting for the busy peer for a fixed duration of time (15
	 * seconds).
	 */
	public void waitingCall();

}
