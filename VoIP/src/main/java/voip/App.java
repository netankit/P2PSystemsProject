package voip;

import java.util.Scanner;

import ui.ClientUI;

public class App extends ClientUI {

	private static Scanner in;

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("java -jar App.jar <pseudoidentity_peer>\n");
			System.exit(0);
		}

		printIntro();
		in = new Scanner(System.in);
		ClientUI client = new ClientUI();
		String userInput;

		String pseudoidentity = args[0];

		System.out.println("Pseudo-identity of Callee: " + pseudoidentity);

		do {
			System.out
					.print("\n\nVOIP CONSOLE (Type 'help' for list of commands)::>> ");
			userInput = in.nextLine();

			if (userInput.equalsIgnoreCase("start")) {
				System.out
						.println("Conntecting to peer with pseodo-identity ... "
								+ pseudoidentity);
				client.initiateCall();
			} else if (userInput.equalsIgnoreCase("end")) {
				System.out
						.println("Terminating the call to peer with pseodo-identity ... "
								+ pseudoidentity);
				client.terminateCall();
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
