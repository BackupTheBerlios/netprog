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
	 * creates a new HTML response with the current sum.
	 *
	 * @param request the original HTTP-Request-String that is to be checked.
	 * @return the answer to the request in HTML
	 */
	public String createResponse(HttpAddRequest request)
	{

		if (request == null)
		{
			return "HTTP /1.0 400 BAD REQUEST";
		}


		sum += request.getSummand();


		return "HTTP /1.0 200 OK\n" +
		       createAdditionalHeaders(request) +
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
		       createAdditionalInputTags(request) + "</TD>\n" +
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

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	protected String createAdditionalHeaders(HttpAddRequest request)
	{
		return "";
	}

	protected String createAdditionalInputTags(HttpAddRequest request)
	{
		return "";
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |             Abstract Probing Methods              |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\
    	        
	protected abstract String getMethodName();
}
