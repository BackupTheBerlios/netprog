package uebung05.a1.get;

import uebung05.a1.*;

import java.io.IOException;

public class HttpAddServer_GET
extends HttpAddServer
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HttpAddServer_GET(int port)
	throws IOException
	{
		super(port);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	public HttpAddRequestHandler createNewRequestHandler()
	{
		return new HttpAddRequestHandler_GET();
	}

	public HttpAddRequest createRequest(String req)
	{
		return new HttpAddRequest_GET(req);
	}
}
