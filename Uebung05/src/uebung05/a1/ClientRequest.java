package uebung05.a1;


public class ClientRequest
{
	public void setSessionID(String sessionID)
	{
		this.sessionID = sessionID;
	}

	private String sessionID;
	private final int summand;
	private final boolean post;
	private final boolean get;


	public ClientRequest(String request)
	{
		post = request.substring(0, request.indexOf(' ')).equalsIgnoreCase("POST");
		get = request.substring(0, request.indexOf(' ')).equalsIgnoreCase("GET");

		summand = extractSummand(request);
		sessionID = extractSessionID(request);

		System.out.println("SUMMAND: " + summand);
		System.out.println("SESSIONID: " + sessionID);
	}


	public boolean isGet()
	{
		return get;
	}

	public boolean isPost()
	{
		return post;
	}

	public String getSessionID()
	{
		return sessionID;
	}

	public int getSummand()
	{
		return summand;
	}

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

	private String extractParameter(String parameter, String request)
	{
		String result = request.trim();
		int index = result.indexOf(parameter);

		if (index < 0) return null;


		result = result.substring(index);
		System.out.println("EXTRACTING " + parameter + " (STEP 1): " + result);

		String[] params = result.split("&");

		for (int i = 0; i < params.length; i++)
		{
			String s = params[i];

			String[] tokens = s.split("=");
			if (tokens.length != 2) continue;

			if (tokens[0].equals(parameter)) return tokens[1];
		}

		return null;
	}


}
