package voip;

import java.io.*;
import java.net.*;
import org.apache.commons.logging.Log;
import ui.ClientUI;
import logger.LogSetup;

/**
 * Manages the call status
 * Opens a TCP socket to receive calls from other Peer
 * @author Zeeshan
 *
 */
public class ConnectionStatusManager extends Thread {

	LogSetup lg = new LogSetup();
	Log logger = lg.getLog(ClientUI.class.getName());

	// TCP port receiving calls
	private int TimeOut = 1000;
	private int status; // current status of peer
	private String TunnelIPAddress; // tunnel IP Address
	private int PeerStatusPort = 11999;
	
	public static final int PEER_STATUS_IDLE = 1;
	public static final int PEER_STATUS_SESSION = 2;
	public static final int PEER_STATUS_ERROR = 3;
	public static final int PEER_STATUS_WAITING = 4;

	private CallManager callManager;
	
	public ConnectionStatusManager(CallManager callManager, int PeerStatusPort)
	{
		super(); // init the super class Thread
		this.callManager = callManager;
		status = PEER_STATUS_IDLE; // set idle
		if (PeerStatusPort != 0)
			this.PeerStatusPort = PeerStatusPort;
	}

	public int CheckStatus(String IPAddr) throws IOException
	{
		int result = -1;
		Socket s = new Socket();
		s.connect(new InetSocketAddress(IPAddr, PeerStatusPort), TimeOut); // connect to the peer
		
		// set up TCP stream reader and writer
		BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		
		writer.println("MSG_VOIP_CALL_INITIATE"); // write initiate message
		writer.flush();
		String statusMsg = reader.readLine(); // wait and read the reply
		// check the replied message status
		if(statusMsg.equals("MSG_VOIP_CALL_INITIATE_OK"))
		{
			// populate the result variable with Message.MSG_VOIP_CALL_INITIATE_REPLY
			// ready to call
		}
		else if(statusMsg.equals("MSG_VOIP_CALL_BUSY"))
		{
			// busy in another call
		}
		else if (statusMsg.equals("MSG_VOIP_CALL_WAITING"))
		{
			// wait for some time mentioned in the reply and then try again
		} 
		else
		{
			// there is some error
		}
		
		// close streams
		reader.close();
		writer.close();
		// close socket
		s.close();
		return result;
	}
	
	public int ValidatePeer(String IPAddr) throws IOException
	{
		int result = 0;
		Socket s = new Socket();
		s.connect(new InetSocketAddress(IPAddr, PeerStatusPort), TimeOut); // connect to the peer
		
		// set up TCP stream reader and writer
		BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		
		writer.println("MSG_VOIP_HEART_BEAT"); // write initiate message
		writer.flush();
		String statusMsg = reader.readLine(); // wait and read the reply
		// check the replied message status
		if(statusMsg.equals("MSG_VOIP_HEART_BEAT_REPLY"))
		{
			result = 1;
		}
		
		// close streams
		reader.close();
		writer.close();
		// close socket
		s.close();
		
		return result;
	}
	
	public void quitSession(String IPAddress)
	{
		try
		{
			Socket s = new Socket(); // create a client socket
			s.connect(new InetSocketAddress(IPAddress, PeerStatusPort)); // connect to the guy
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
			writer.println("MSG_VOIP_CALL_TERMINATE");
			writer.flush();
			writer.close();
			s.close();
		}
		catch(Exception e)
		{
			logger.error("ConnectionStatusManager: Errors occur when sending quitting the session with " + IPAddress);
			logger.error("ConnectionStatusManager: " + e.getMessage());
			System.exit(1);
		}
	}

	public void setStatus(int newStatus)
	{
		status = newStatus;
	}

	public int getStatus()
	{
		return status;
	}

	public void setTunnelIPAddress(String IPAddress)
	{
		TunnelIPAddress = IPAddress;
	}
	
	public void setPeerStatusPort(int PeerStatusPort)
	{
		this.PeerStatusPort = PeerStatusPort;
	}

	public void run()
	{		
		try
		{
			// open a server TCP socket receiving the incoming call requests
			ServerSocket ss = new ServerSocket(PeerStatusPort);
			for(;;)
			{
				Socket client = ss.accept(); // accept a connect from a peer
				// set up read and write streams
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
				String handshakeMsg = in.readLine(); // read incoming message
				if(handshakeMsg.equals("MSG_VOIP_CALL_INITIATE")) // if the message is a initiate
				{
					switch(status) // check my current status
					{
						case PEER_STATUS_IDLE:
							out.println("MSG_VOIP_CALL_INITIATE_OK");
							break;
						case PEER_STATUS_SESSION:
							out.println("MSG_VOIP_CALL_BUSY");
							break;
						case PEER_STATUS_WAITING:
							out.println("MSG_VOIP_CALL_WAITING");
							break;
						case PEER_STATUS_ERROR:
							out.println("MSG_VOIP_CALL_ERROR");
							break;					
						default: // unknown error
							out.println("MSG_VOIP_CALL_ERROR");
							logger.error("ConnectionStatusManager: Unknow Peer status");
							System.exit(1);
							break;
					}
					out.flush(); // flush out
				}
				else if (handshakeMsg.equals("MSG_VOIP_HEART_BEAT"))
				{
					out.println("MSG_VOIP_HEART_BEAT_REPLY");
				}
				else if(handshakeMsg.equals("MSG_VOIP_CALL_START")) // if the message is an incoming call request
				{
					this.status = PEER_STATUS_SESSION;
					callManager.startCall(TunnelIPAddress); // start call
				}
				else if(handshakeMsg.equals("MSG_VOIP_CALL_TERMINATE"))
				{
					callManager.stopCall();
				}
				in.close();
				out.close();
			}
		}
		catch(Exception e)
		{
			logger.error("ConnectionStatusManager: " + e.getMessage());
			System.exit(1);
		}
	}
}