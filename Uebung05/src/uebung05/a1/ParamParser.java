package uebung05.a1;

public class ParamParser
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Constants                     |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public static final int GET = 0;
	public static final int POST = 1;
	public static final int COOKIE = 2;

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	/**
	 * @param parameter the parameter's name
	 * @param request   the string where to search in
	 * @param method    the search-type depending on request-type
	 * @return the value for the given parameter in the given request.
	 */
	public static String parseParameter(String parameter, String request, int method)
	{
		// Preconditions:
		assert parameter != null : "PRE 1: parameter != null returned false @ ParamParser.parseParameter()";
		assert request != null : "PRE 2: request != null returned false @ ParamParser.parseParameter()";

		// Implementation:
		switch (method)
		{
			case GET:
				return parseParameter_GET(parameter, request);
			case POST:
				return parseParameter_POST(parameter, request);
			case COOKIE:
				return parseParameter_COOKIE(parameter, request);
			default:
				throw new IllegalArgumentException("Unsupoported Request Type");
		}
	}

	//    --------|=|-----------|=||=|-----------|=|--------    \\

	private static String parseParameter_COOKIE(String parameter, String request)
	{
		try
		{
			request = request.substring(request.indexOf("Cookie:"));
			request = request.substring(request.indexOf(":") + 1, request.indexOf("\n")).trim();
			return getParam(request, parameter);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static String parseParameter_POST(String parameter, String request)
	{
		try
		{
			String result = request.trim();

			return getParam(result.substring(result.indexOf(parameter)), parameter);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static String parseParameter_GET(String parameter, String request)
	{
		try
		{
			return getParam(request.substring(request.indexOf('?') + 1, request.indexOf("HTTP")).trim(), parameter);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static String getParam(String parameters, String parameterName)
	{
		String[] params = parameters.split("&");

		// iterate parameters
		for (int i = 0; i < params.length; i++)
		{
			String s = params[i];

			// split into name and value:
			String[] tokens = s.split("=");

			// maybe we are not yet where we want to be
			if (tokens.length != 2) continue;

			// parameter found - return value:
			if (tokens[0].equals(parameterName)) return tokens[1];
		}

		return null;
	}
}
