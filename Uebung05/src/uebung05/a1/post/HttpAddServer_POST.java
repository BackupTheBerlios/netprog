package uebung05.a1.post;

import uebung05.a1.*;

import java.io.IOException;

public class HttpAddServer_POST
extends HttpAddServer
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HttpAddServer_POST(int port)
	throws IOException
	{
		super(port);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	public HttpAddRequestHandler createNewRequestHandler()
	{
		return new HttpAddRequestHandler_POST();
	}

	public HttpAddRequest createRequest(String req)
	{
		return new HttpAddRequest_POST(req);
	}
}