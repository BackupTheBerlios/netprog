package uebung05.a1;

public class HttpAddRequestHandler
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Fields                       |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	private int sum = 0;

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Methods                      |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	/**
	 * creates a new HTML response with the current sum. If the given request is POST
	 * add the tramsitted value first.
	 *
	 * @param request the source of all necessary information (POST/GET, summand, sessionID)
	 * @return HTML Response
	 */
	public String createResponse(HttpAddRequest request)
	{
		String response;

		if (request == null || !(request.isGet() || request.isPost()))
		{
			return "HTTP /1.0 400 BAD REQUEST";
		}

		if (request.isPost())
		{
			sum += request.getSummand();
		}

		response = "HTTP /1.0 200 OK\n" +
		           "Content-type: text/html\n\n" +

		           "<HTML>\n" +
		           "<BODY><CENTER>\n" +
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
		           "<input type=\"submit\" value=\"Zahlen Addieren\" />\n" +
		           "</TD>\n" +
		           "</TR>\n" +
		           "</TBLE>\n" +
		           "</FORM>\n" +
		           "</CENTER></BODY>\n" +
		           "</HTML>\n";

		return response;
	}
}
