package uebung05.a1.cookie;

import uebung05.a1.*;


public class HttpAddRequestHandler_COOKIE
extends HttpAddRequestHandler
{
	public String getMethodName()
	{
		return "POST";
	}

	protected String getAdditionalHeaders(HttpAddRequest request)
	{
		assert request instanceof HttpAddRequest_COOKIE;

		return "Set-Cookie: HANDLEID=" + request.getSessionID() + "; path=/\n";
	}

	protected String getAdditionalForms(HttpAddRequest request)
	{
		return "";
	}

}
