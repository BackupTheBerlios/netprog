package uebung05.a1;

import uebung05.a1.post.HttpAddRequest_POST;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public abstract class HttpAddServer
implements Runnable
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Fields                       |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	private final ServerSocket socket;
	private final HashMap handlers = new HashMap();

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

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

	/**
	 * Waits for a client connection, reads it's content, delegates content to
	 * corresponding handler and prints result to client's OutputStream.
	 */
	public void run()
	{
		while (true)
		{
			try
			{
				// accept client:
				Socket client = socket.accept();

				// Read HTTP Request and create Response:
				String response = getResponse(readRequest(client));

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

	private String getResponse(String req)
	{
		HttpAddRequest request = createRequest(req);

		// if no corresponding handler exists, create a new one:
		String sessionID = request.getSessionID();

		if (sessionID.equals(HttpAddRequest_POST.NO_SESSIONID) || !handlers.containsKey(sessionID))
		{
			HttpAddRequestHandler handler = createNewRequestHandler();
			request.setSessionID(handler.toString());
			handlers.put(handler.toString(), handler);
		}

		// get handler and let it create the appropriate answer (in HTML)
		HttpAddRequestHandler handler = (HttpAddRequestHandler) handlers.get(request.getSessionID());
		return handler.createResponse(request);
	}

	/**
	 * Reads an HTTP Request from a client socket
	 *
	 * @param client the socket where to read from
	 * @return all read lines in one String
	 * @throws java.io.IOException
	 */
	private String readRequest(Socket client)
	throws IOException
	{

		client.setSoTimeout(50);

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


		String result = new String(buffer);
		return result;
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                 Abstract Services                 |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	protected abstract HttpAddRequest createRequest(String req);

	protected abstract HttpAddRequestHandler createNewRequestHandler();
}
