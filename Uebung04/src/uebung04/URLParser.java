package uebung04;

import java.net.URL;
import java.net.MalformedURLException;

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
}