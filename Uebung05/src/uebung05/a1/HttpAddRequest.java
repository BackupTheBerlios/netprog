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
		summand = extractSummand(request);
		sessionID = extractSessionID(request);
	}

	/**
	 * simple setter
	 *
	 * @param sessionID
	 */
	public void setSessionID(String sessionID)
	{
		this.sessionID = sessionID;
	}

	/**
	 * simple getter
	 *
	 * @return {@link #sessionID}
	 */
	public String getSessionID()
	{
		return sessionID;
	}

	/**
	 * simple getter
	 *
	 * @return {@link #summand}
	 */
	public int getSummand()
	{
		return summand;
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                 Abstract Services                 |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	public abstract String extractSessionID(String request);

	public abstract int extractSummand(String request);
}
