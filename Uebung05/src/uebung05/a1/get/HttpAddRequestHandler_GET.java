package uebung05.a1.get;

import uebung05.a1.*;


public class HttpAddRequestHandler_GET
extends HttpAddRequestHandler
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                  Probing Methods                  |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	protected String getMethodName()
	{
		return "GET";
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	protected String createAdditionalInputTags(HttpAddRequest request)
	{
		return "<input name=\"sessionID\" value=\"" + request.getSessionID() + "\" type=\"hidden\" />";
	}
}
