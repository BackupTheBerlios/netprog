package uebung05.a1.get;

import uebung05.a1.*;


public class HttpAddRequest_GET
extends HttpAddRequest
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HttpAddRequest_GET(String request)
	{
		super(request);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	public String extractSessionID(String request)
	{
		String result = ParamParser.parseParameter("sessionID", request, ParamParser.GET);
		return result == null ? "-1" : result;
	}

	public int extractSummand(String request)
	{
		try
		{
			return Integer.parseInt(ParamParser.parseParameter("summand", request, ParamParser.GET));
		}
		catch (Exception e)
		{

		}

		return 0;
	}
}
