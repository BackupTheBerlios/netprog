package uebung05.a1;

public abstract class HttpAddRequestHandler
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Fields                       |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	int sum = 0;

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

		if (request == null)
		{
			return "HTTP /1.0 400 BAD REQUEST";
		}


		sum += request.getSummand();


		return "HTTP /1.0 200 OK\n" +
		       getAdditionalHeaders(request) +
		       "Content-type: text/html\n\n" +

		       "<HTML>\n" +
		       "<BODY><CENTER>\n" +
		       "<FORM method=\"" + getMethodName() + "\">\n" +
		       "<TABLE border=\"1\">\n" +
		       "<TR>\n" +
		       "<TD>Bisherige Summe:</TD>\n" +
		       "<TD>" + sum + "</TD>\n" +
		       "</TR>\n" +
		       "<TR>\n" +
		       "<TD>Neuer Summand:</TD>\n" +
		       "<TD><input name=\"summand\" value=\"0\" type=\"text\" />" +
		       getAdditionalForms(request) + "</TD>\n" +
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
	}

	protected abstract String getAdditionalHeaders(HttpAddRequest request);

	protected abstract String getMethodName();

	protected abstract String getAdditionalForms(HttpAddRequest request);
}
