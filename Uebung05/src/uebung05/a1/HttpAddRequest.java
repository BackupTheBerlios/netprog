package uebung05.a1;

public class HttpAddRequest
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Constants                     |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public static final String NO_SESSIONID = "NONE";

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Fields                       |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	private String sessionID;

	private final int summand;
	private final boolean isPost;
	private final boolean isGet;

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	/**
	 * extracts and represents the important data of an HTTP request.
	 *
	 * @param request the request where to read data from
	 */
	public HttpAddRequest(String request)
	{
		isPost = request.substring(0, request.indexOf(' ')).equalsIgnoreCase("POST");
		isGet = request.substring(0, request.indexOf(' ')).equalsIgnoreCase("GET");

		summand = extractSummand(request);
		sessionID = extractSessionID(request);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                Modifying Methods                  |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	/**
	 * simple setter
	 *
	 * @param sessionID
	 */
	public void setSessionID(String sessionID)
	{
		this.sessionID = sessionID;
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                  Probing Methods                  |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	/**
	 * simple getter
	 *
	 * @return {@link #isGet}
	 */
	public boolean isGet()
	{
		return isGet;
	}

	/**
	 * simple getter
	 *
	 * @return {@link #isPost}
	 */
	public boolean isPost()
	{
		return isPost;
	}

	/**
	 * simple getter
	 *
	 * @return {@link #sessionID}
	 */
	public String getSessionID()
	{
		return sessionID;
	}

	/**
	 * simple getter
	 *
	 * @return {@link #summand}
	 */
	public int getSummand()
	{
		return summand;
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	/**
	 * @param request where to search
	 * @return the value for the sessionID-parameter if exists, "-1" otherwise.
	 */
	private String extractSessionID(String request)
	{
		try
		{
			String result = extractParameter("sessionID", request);

			return result == null ? "-1" : result;
		}
		catch (Exception e)
		{
			return "-1";
		}
	}

	/**
	 * @param request where to search
	 * @return the value for the summand-parameter if exists, 0 otherwise.
	 */
	private int extractSummand(String request)
	{
		try
		{
			return Integer.parseInt(extractParameter("summand", request));
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * returns the value of the given parameter if exists, null otherwise.
	 *
	 * @param parameter the parameter's name
	 * @param request   the String where to search for the given parameter
	 * @return the parameter'S value if exists, null otherwise.
	 */
	private String extractParameter(String parameter, String request)
	{
		// cosmetics:
		String result = request.trim();

		// skip everything before first appearance of <parameter>
		int index = result.indexOf(parameter);
		if (index < 0) return null;
		result = result.substring(index);

		// split the rest at all appearances of '&' so that we get an
		// array of "parameter=value" expressions
		String[] params = result.split("&");

		// iterate parameters
		for (int i = 0; i < params.length; i++)
		{
			String s = params[i];

			// split into name and value:
			String[] tokens = s.split("=");

			// maybe there's something wrong - like parameter is called POST
			// and there's no '=' at this point.
			if (tokens.length != 2) continue;

			// parameter found - return value:
			if (tokens[0].equals(parameter)) return tokens[1];
		}

		return null;
	}
}
