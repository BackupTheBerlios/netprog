package uebung05.a1;

import java.io.IOException;

public class StartServer
{
	public static void main(String[] args) throws IOException
	{
		new Thread(new HttpServer()).start();
	}
}
