package uebung05.a1;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class HttpServer
implements Runnable
{

	private ServerSocket socket;
	private HashMap clients = new HashMap();

	public HttpServer()
	throws IOException
	{
		this(80);
	}

	public HttpServer(int port)
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

				new Thread(new ClientCommunication(client)).start();
			}
			catch (IOException e)
			{
				System.err.println(e.toString());
			}
		}
	}
}