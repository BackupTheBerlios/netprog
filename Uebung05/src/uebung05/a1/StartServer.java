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
	throws IOException
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

		new HttpAddServer(port).start();
	}
}
