package uebung05.a1;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class HttpAddServer
implements Runnable
{

	private ServerSocket socket;
	private HashMap handlers = new HashMap();

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

	public void run()
	{
		while (true)
		{
			try
			{
				Socket client = socket.accept();

				String requestString = readRequest(client);
				ClientRequest request = new ClientRequest(requestString);

				if (request.getSessionID().equals("-1") || !handlers.containsKey(request.getSessionID()))
				{
					RequestHandler handler = new RequestHandler();
					request.setSessionID(handler.toString());
					handlers.put(handler.toString(), handler);
				}

				String response = ((RequestHandler) handlers.get(request.getSessionID())).createResponse(request);

				BufferedWriter ou = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				ou.write(response);
				ou.flush();

				client.close();
			}
			catch (IOException e)
			{
				System.err.println(e.toString());
			}
		}
	}

	private String readRequest(Socket client)
	throws IOException
	{
		client.setSoTimeout(100);

		BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

		String request = "";

		while (true)
		{
			try
			{
				request += input.readLine() + "\n";
			}
			catch (SocketTimeoutException e)
			{
				break;
			}
		}

		return request;
	}

	public void start()
	{
		new Thread(this).start();
	}
}