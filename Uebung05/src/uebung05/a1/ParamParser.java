package uebung05.a1;

public class ParamParser
{
	public static final int GET = 0;
	public static final int POST = 1;
	public static final int COOKIE = 2;

	public static String parseParameter(String parameter, String request, int method)
	{
		switch (method)
		{
			case GET:
				return parseParameter_GET(parameter, request);
			case POST:
				return parseParameter_POST(parameter, request);
			case COOKIE:
				return parseParameter_COOKIE(parameter, request);
		}

		return null;
	}

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

			// maybe there's something wrong - like parameter is called POST
			// and there's no '=' at this point.
			if (tokens.length != 2) continue;

			// parameter found - return value:
			if (tokens[0].equals(parameterName)) return tokens[1];
		}

		return null;
	}
}
