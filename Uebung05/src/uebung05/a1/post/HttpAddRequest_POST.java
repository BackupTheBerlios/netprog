package uebung05.a1.post;

import uebung05.a1.*;


public class HttpAddRequest_POST
extends HttpAddRequest
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HttpAddRequest_POST(String request)
	{
		super(request);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	public String extractSessionID(String request)
	{
		String result = ParamParser.parseParameter("sessionID", request, ParamParser.POST);
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
