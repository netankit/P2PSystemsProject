package voip;

/**
 * 
 * VOIP - Main Application Console Control Panel
 *  
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu	
 *
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import messages.Message;
import config.ConfigReader;
import ui.ClientUI;

public class App extends ClientUI {

	private static Scanner in;

	public static void main(String[] args){

		if (args.length != 2) {
			System.err.println("java -jar App.jar <Configuration file path> <pseudoidentity_peer>\n");
			System.exit(0);
		}

		printIntro();
		in = new Scanner(System.in);
		String userInput;

		String configurationFilePath = args[0];
		String pseudoidentity = args[1];

		if (configurationFilePath.isEmpty() || pseudoidentity.isEmpty())
		{
			System.err.println("Configuration file path or pseudoidentity_peer is empty. Both are required to start application!\n");
			return;
		}
		
		System.out.println("Pseudo-identity of Callee: " + pseudoidentity);
		String IPAddress = "";
		int portNumber = 14999;
		int dataPacketSize = 512;
		int dhtPortNumber = 0;
		String dhtHostName = "";
		try
		{
			ConfigReader conf = new ConfigReader(configurationFilePath);
			IPAddress = conf.getTUNIP();
			portNumber = conf.getVOIPPortNumber();
			dataPacketSize = conf.getVoiceDataPacketSize();
			if (dataPacketSize > 1024)
			{
				System.out.println("Data packet size can not be greater than 1024 bytes. Please fix this issue in the config file");
				return;
			}
			
			// DHT configuration reader
			String[] dhtPortNumbers = conf.getPort();
			if (dhtPortNumbers.length <= 0)
			{
				System.out.println("No DHT port number is given in configuration file. Please fix this issue in the config file");
				return;
			}
			dhtPortNumber = Integer.parseInt(dhtPortNumbers[0]);
			String[] hostKeyNames = conf.getHostname();
			if (hostKeyNames.length <= 0)
			{
				System.out.println("No DHT host name is given in configuration file. Please fix this issue in the config file");
				return;
			}
			
			dhtHostName = hostKeyNames[0];
		}
		catch(Exception ex)
		{
			System.out.println("Error while reading the config file " + ex.getMessage());
			return;
		}

		VOIP voip = new VOIP(portNumber, dataPacketSize, IPAddress, "");
		
		
		do {
			System.out
					.print("\n\nVOIP CONSOLE (Type 'help' for list of commands)::>> ");
			userInput = in.nextLine();

			if (userInput.equalsIgnoreCase("start")) {
				System.out
						.println("Conntecting to peer with pseodo-identity ... "
								+ pseudoidentity);
				int result = voip.InitiateCall(pseudoidentity, "");
				if (result == Message.MessageType.MSG_VOIP_CALL_INITIATE_OK.getValue())
				{
					System.out.println("Call initiated with " + pseudoidentity);
				} 
				else if (result == Message.MessageType.MSG_VOIP_CALL_BUSY.getValue())
				{
					System.out.println("Calleee " + pseudoidentity + " is busy at the moment in another call");
				}
				else if (result == Message.MessageType.MSG_VOIP_CALL_WAITING.getValue())
				{
					System.out.println("Calleee " + pseudoidentity + " is in waiting state. Try bit later!");
				}
				else
				{
					System.out.println("There is some error during the call with " + pseudoidentity + " . Look at the log file for details.");
				}
				
			} else if (userInput.equalsIgnoreCase("end")) {
				System.out
						.println("Terminating the call to peer with pseodo-identity ... "
								+ pseudoidentity);
				voip.StopCall();
				System.out
				.println("Call terminated with pseodo-identity ... "
						+ pseudoidentity);
				;
			} else if (userInput.equalsIgnoreCase("help")) {
				printUsageHelp();
			} else if (userInput.equalsIgnoreCase("quit")) {
				// Do nothing here. Only here to confirm the input is valid one.
			} else {
				System.err.println("Please provide a valid input. Refer:");
				printUsageHelp();
			}
		} while (!userInput.equalsIgnoreCase("quit"));

		terminateApplication();
	}

	/**
	 * Terminates the application
	 */
	private static void terminateApplication() {
		System.out.println("Application Terminated ");
		System.exit(0);
	}

	/**
	 * Prints a general usage help instructions. Consists of a list of commands
	 * and their respective informations.
	 */
	private static void printUsageHelp() {
		System.out.println("\nHELP ");
		System.out.println("-----");
		System.out
				.println("start: Initiates a call to the peer with given psedo-identity.");
		System.out
				.println("end: End a call to the peer with given psedo-identity.");
		System.out.println("help : Print help with a list of commands.");
		System.out.println("quit: Terminates the application.");
	}

	/**
	 * Prints general information about the author and the project.
	 */
	private static void printIntro() {
		System.out.println("VOIP PROJECT");
		System.out.println("=============");
		System.out.println("Team Members: Ankit and Zeeshan");
		System.out.println("=================================");
	}
}