package uebung05.a1;


public class RequestHandler
{
	private int sum = 0;

	public RequestHandler()
	{
	}

	public String createResponse(ClientRequest request)
	{
		String response;

		if (!(request.isGet() || request.isPost()))
		{
			response = "HTTP /1.0 400 BAD REQUEST";
		}
		else
		{
			if (request.isPost())
			{
				sum += request.getSummand();
			}

			response = "HTTP /1.0 200 OK\n" +
			           "Content-type: text/html\n" +
			           "\n" +
			           "<HTML>\n" +
			           "<BODY>\n" +
			           "<FORM method=\"post\">\n" +
			           "<TABLE border=\"1\">\n" +
			           "<TR>\n" +
			           "<TD>Bisherige Summe:</TD>\n" +
			           "<TD>" + sum + "</TD>\n" +
			           "</TR>\n" +
			           "<TR>\n" +
			           "<TD>Neuer Summand:</TD>\n" +
			           "<TD><input name=\"summand\" value=\"0\" type=\"text\" />" +
			           "<input name=\"sessionID\" value=\"" + request.getSessionID() + "\" type=\"hidden\" /></TD>\n" +
			           "</TR>\n" +
			           "<TR>\n" +
			           "<TD colspan=\"2\" align=\"center\">\n" +
			           "<input type=\"submit\" value=\"Neu Berechnen\" />\n" +
			           "</TD>\n" +
			           "</TR>\n" +
			           "</TBLE>\n" +
			           "</FORM>\n" +
			           "</BODY>\n" +
			           "</HTML>\n";
		}

		//System.out.println("\n-------------- NEXT REQUEST --------------\n");

		return response;

	}


}
