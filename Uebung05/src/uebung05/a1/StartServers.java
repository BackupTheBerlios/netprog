package uebung05.a1;

import uebung05.a1.cookie.HttpAddServer_COOKIE;
import uebung05.a1.get.HttpAddServer_GET;
import uebung05.a1.post.HttpAddServer_POST;

import java.io.IOException;

public class StartServers
{
	/**
	 * Starts the HttpServers which listen for client connections.
	 *
	 * @param args not used
	 * @throws IOException
	 */
	public static void main(String[] args)
	{
		try
		{
			new HttpAddServer_POST(8080).start();
		}
		catch (IOException e)
		{
			System.err.println("Der Post-Server konnte nicht gestartet werden: ");
			System.err.println(e.toString());
		}

		try
		{
			new HttpAddServer_GET(8081).start();
		}
		catch (IOException e)
		{
			System.err.println("Der Get-Server konnte nicht gestartet werden: ");
			System.err.println(e.toString());
		}

		try
		{
			new HttpAddServer_COOKIE(8082).start();
		}
		catch (IOException e)
		{
			System.err.println("Der Cookie-Server konnte nicht gestartet werden: ");
			System.err.println(e.toString());
		}
	}
}
