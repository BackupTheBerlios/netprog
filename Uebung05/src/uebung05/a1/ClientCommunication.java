package uebung05.a1;

import java.io.*;
import java.net.Socket;

public class ClientCommunication
implements Runnable
{
	private final Socket client;
	private int sum = 0;

	public ClientCommunication(Socket client)
	{
		this.client = client;
	}

	public void run()
	{
		try
		{
			BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

			String in = "-";
			while (!in.equals(""))
			{
				in = input.readLine();

				if (in.equals(""))
				{
					break;
				}

				System.out.println(in);
			}

			output.write("HTTP /1.0 200 OK\n");
			output.write("Content-type: text/html\n");
			output.write("\n");
			output.write("<HTML>" +
			             "<BODY>" +
			             "<FORM>" +
			             "<TABLE border=\"1\">" +
			             "<TR>" +
			             "<TD>Bisherige Summe:</TD>" +
			             "<TD>" + sum + "</TD>" +
			             "</TR>" +
			             "<TR>" +
			             "<TD>Neuer Summand:</TD>" +
			             "<TD>Hier muss das Feld hin</TD>" +
			             "</TR>" +
			             "<TR>" +
			             "<TD colspan=\"2\">Hier der button</TD>" +
			             "</TR>" +
			             "</TABLE>" +
			             "</FORM>" +
			             "</BODY>" +
			             "</HTML>");
			output.write("\n\n");
			output.flush();

			client.close();
		}
		catch (IOException e)
		{
			System.err.println(e.toString());
		}
	}
}
