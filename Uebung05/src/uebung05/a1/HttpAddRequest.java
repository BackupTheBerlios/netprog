package uebung05.a1;

public abstract class HttpAddRequest
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Constants                     |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public static final String NO_SESSIONID = "NONE";

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Fields                       |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	protected String sessionID;
	protected final int summand;

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HttpAddRequest(String request)
	{
		summand = parseSummand(request);
		sessionID = parseSessionID(request);
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                  Probing Methods                  |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public String getSessionID()
	{
		return sessionID;
	}

	public int getSummand()
	{
		return summand;
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                Modifying Methods                  |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public void setSessionID(String sessionID)
	{   // Preconditions:
		assert sessionID != null : "PRE 1: sessionID != null returned false @ HttpAddRequest.setSessionID()";

		// Implementation:
		this.sessionID = sessionID;
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                 Abstract Services                 |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
            
	public abstract String parseSessionID(String request);

	public abstract int parseSummand(String request);
}
