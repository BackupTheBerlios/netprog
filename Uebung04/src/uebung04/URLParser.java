package uebung04;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class URLParser
{
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Singleton                     |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	private static URLParser sharedInstance_ = new URLParser();

	public static URLParser sharedInstance()
	{
		return sharedInstance_;
	}

	private URLParser()
	{
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Services                      |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	public URL parseURL(String urlString)
	throws MalformedURLException
	{   if (urlString == null) throw new MalformedURLException("Der URL-String ist null");

		// URL erzeugen, löst MalformedURLException bei unbekanntem Protokoll aus
		URL url = new URL(urlString);

		// Hostname darf nicht leer sein
		if (url.getHost().length() == 0)
			throw new MalformedURLException("Kein Hostname angegeben.");

		// Hostname darf nur Buchstaben, Zahlen, sowie die Zeichen '-' und '.' enthalten
		if (!url.getHost().matches("[a-zA-Z0-9-.]*"))
			throw new MalformedURLException("Hostname enthält ungültige Zeichen.");

		// Hostname darf nicht mit einem Punkt beginnen oder enden,
		// bzw. zwei aufeinander folgende Punkte enthalten
		if (url.getHost().matches("[.].*|.*[.][.].*|.*[.]"))
			throw new MalformedURLException("Fehler im Hostnamen: '.' falsch gesetzt");

		// Hostname darf nicht mit '-' beginnen/enden oder ".-" bzw. "-." enthalten
		if (url.getHost().matches("[-].*|.*[.][-].*|.*[-][.].*|.*[-]"))
			throw new MalformedURLException("Fehler im Hostnamen: '-' falsch gesetzt");

		return url;
	}

	public boolean checkResponseOK(HttpURLConnection httpURLConnection)
	throws IOException
	{
		int responseCode = httpURLConnection.getResponseCode();

		switch (responseCode)
		{
			case HttpURLConnection.HTTP_OK:
				return true;
			case HttpURLConnection.HTTP_NOT_FOUND:
				System.err.println(httpURLConnection + "  - wurde nicht gefunden");
				return false;
			case HttpURLConnection.HTTP_FORBIDDEN:
				System.err.println((httpURLConnection + " - Zugriff verweigert"));
				return false;
			case HttpURLConnection.HTTP_UNAUTHORIZED:
				System.err.println((httpURLConnection + " - Authorisierung erforderlich"));
				return false;
			default :
				System.err.println((httpURLConnection + " - unbekannter Fehler"));
				return false;
		}
	}


}