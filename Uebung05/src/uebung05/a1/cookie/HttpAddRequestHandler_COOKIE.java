package uebung05.a1.cookie;

import uebung05.a1.*;


public class HttpAddRequestHandler_COOKIE
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

	protected String createAdditionalHeaders(HttpAddRequest request)
	{
		return "Set-Cookie: HANDLEID=" + request.getSessionID() + "; path=/\n";
	}
}
