package uebung05.a1;

import java.io.IOException;

public class StartServer
{
	/**
	 * Starts the HttpServer which listens for client connections.
	 *
	 * @param args start at given port - optional (default: 80)
	 * @throws IOException
	 */
	public static void main(String[] args)
	{
		int port = 80;

		if (args.length == 1)
		{
			try
			{
				port = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e)
			{
				System.err.println(e.toString());
			}
		}

		try
		{
			new HttpAddServer(port).start();
		}
		catch (IOException e)
		{
			System.err.println("Server konnte nicht gestartet werden: ");
			System.err.println(e.toString());
		}
	}
}
