package uebung05.a1.post;

import uebung05.a1.*;


public class HttpAddRequestHandler_POST
extends HttpAddRequestHandler
{
	public String getMethodName()
	{
		return "POST";
	}

	protected String getAdditionalHeaders(HttpAddRequest httpAddRequest_post)
	{
		assert httpAddRequest_post instanceof HttpAddRequest_POST;

		return "";
	}

	protected String getAdditionalForms(HttpAddRequest request)
	{
		return "<input name=\"sessionID\" value=\"" + request.getSessionID() + "\" type=\"hidden\" />";
	}
}
