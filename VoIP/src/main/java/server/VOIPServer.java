package server;

import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VOIPServer {
	private ServerSocket voipServerSocket;

	public void run() throws Exception {
		int port = 000;
		voipServerSocket = new ServerSocket(port);
		Socket voipsocket = voipServerSocket.accept();

		BufferedInputStream bis = new BufferedInputStream(
				voipsocket.getInputStream());

		// read number of bytes available
		int numByte = bis.available();

		// byte array declared
		byte[] buffer = new byte[numByte];
		int offset = 0;
		int length = 32;

		// Reading 32 bytes from the byte input stream from the offset zero.
		// This is customizable.
		bis.read(buffer, offset, length);

	}
}
