package uebung05.a1.get;

import uebung05.a1.*;


public class HttpAddRequestHandler_GET
extends HttpAddRequestHandler
{
	protected String getAdditionalForms(HttpAddRequest request)
	{
		return "<input name=\"sessionID\" value=\"" + request.getSessionID() + "\" type=\"hidden\" />";
	}

	public String getMethodName()
	{
		return "GET";
	}

	protected String getAdditionalHeaders(HttpAddRequest httpAddRequest_get)
	{
		assert httpAddRequest_get instanceof HttpAddRequest_GET;

		return "";
	}
}
