package uebung05.a1;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class HttpAddServer
implements Runnable
{

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Fields                       |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	private ServerSocket socket;
	private HashMap handlers = new HashMap();

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HttpAddServer()
	throws IOException
	{
		this(80);
	}

	public HttpAddServer(int port)
	throws IOException
	{
		socket = new ServerSocket(port);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Methods                      |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public void start()
	{
		new Thread(this).start();
	}

	//    --------|=|-----------|=||=|-----------|=|--------    \\

	/**
	 * Waits for a client connection, reads it's content, delegates content to
	 * corresponding handler and prints result to clients OutputStream.
	 */
	public void run()
	{
		while (true)
		{
			try
			{
				// accept client:
				Socket client = socket.accept();

				// Read HTTP Request and create representation:
				HttpAddRequest request = new HttpAddRequest(readRequest(client));

				// if no corresponding handler exists, create a new one:
				String sessionID = request.getSessionID();

				if (sessionID.equals(HttpAddRequest.NO_SESSIONID) || !handlers.containsKey(sessionID))
				{
					RequestHandler handler = new RequestHandler();
					request.setSessionID(handler.toString());
					handlers.put(handler.toString(), handler);
				}

				// get handler and let it create the appropriate answer (in HTML)
				RequestHandler handler = (RequestHandler) handlers.get(request.getSessionID());
				String response = handler.createResponse(request);

				// send answer to client:
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				out.write(response);
				out.flush();

				// close client socket:
				client.close();
			}
			catch (IOException e)
			{
				System.err.println(e.toString());
			}
		}
	}

	//    --------|=|-----------|=||=|-----------|=|--------    \\

	/**
	 * Reads an HTTP Request from a client socket
	 *
	 * @param client the socket where to read from
	 * @return all read lines in one String
	 * @throws IOException
	 */
	private String readRequest(Socket client)
	throws IOException
	{
		client.setSoTimeout(100);

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

		while (true)
		{
			try
			{
				buffer.append(reader.readLine());
				buffer.append('\n');
			}
			catch (SocketTimeoutException e)
			{
				break;
			}
		}

		client.setSoTimeout(0);

		return new String(buffer);
	}
}