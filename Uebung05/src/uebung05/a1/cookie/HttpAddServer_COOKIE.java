package uebung05.a1.cookie;

import uebung05.a1.*;

import java.io.IOException;

public class HttpAddServer_COOKIE
extends HttpAddServer
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HttpAddServer_COOKIE(int port)
	throws IOException
	{
		super(port);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	protected HttpAddRequestHandler createNewRequestHandler()
	{
		return new HttpAddRequestHandler_COOKIE();
	}

	protected HttpAddRequest createRequest(String req)
	{
		return new HttpAddRequest_COOKIE(req);
	}
}
