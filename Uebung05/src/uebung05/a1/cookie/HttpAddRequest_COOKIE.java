package uebung05.a1.cookie;

import uebung05.a1.*;


public class HttpAddRequest_COOKIE
extends HttpAddRequest
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HttpAddRequest_COOKIE(String request)
	{
		super(request);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	public String extractSessionID(String request)
	{
		String result = ParamParser.parseParameter("HANDLEID", request, ParamParser.COOKIE);

		return result == null ? "-1" : result;
	}

	public int extractSummand(String request)
	{
		try
		{
			return Integer.parseInt(ParamParser.parseParameter("summand", request, ParamParser.POST));
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
