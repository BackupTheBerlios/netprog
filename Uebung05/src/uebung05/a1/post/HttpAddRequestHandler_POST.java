package uebung05.a1.post;

import uebung05.a1.*;


public class HttpAddRequestHandler_POST
extends HttpAddRequestHandler
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                  Probing Methods                  |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	protected String getMethodName()
	{
		return "POST";
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	protected String createAdditionalInputTags(HttpAddRequest request)
	{
		return "<input name=\"sessionID\" value=\"" + request.getSessionID() + "\" type=\"hidden\" />";
	}
}
