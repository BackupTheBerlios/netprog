package uebung04.a3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * <p> NETZPROGRAMMIERUNG ÜBUNG 4 - AUFGABE 3 </p>
 * <p/>
 * <p> Ablauf:
 * 0. URL auf korrektes Schema überprüfen
 * 1. Normales GET
 * - Antwort 200: keine Autorisierung notwendig - der Inhalt wird ausgegeben
 * - Antwort 401: Suche nach Benutzer/Passwort-Kombination
 * - andere Antworten: (not found, forbidden usw.) Abbruch
 * 2. Suchen nach Benutzer/Passwort-Kombination
 * durch weiteres GET mit Autorisierung
 * - Antwort 200: Ausgabe der B/P-Kombination und des Inhalts
 * <p/>
 * </p>
 *
 * @author Gruppe 6
 */
public class ReadProtected
{

	// Benutzernamen und Kennwörter
	private static final String[] userNames = {"Alle", "Keiner", "Jeder"};
	private static final String[] passwords = {"gelb", "blau", "rot"};

	// Fehlermeldungen
	private static final String ERR_IO = "Fehler bei der Kommunikation mit dem Server";
	private static final String ERR_HTTP = "keine Http Verbindung";

	private HttpURLConnection connection;
	private int response;


	/**
	 * constructor
	 *
	 * @param page
	 */
	public ReadProtected(URL page)
	{

		// ------------------------------------
		//  normal verbinden und Response-Code
		//              erfragen
		// ------------------------------------

		response = HttpURLConnection.HTTP_OK;
		String responseMessage;
		try
		{
			connection = (HttpURLConnection) page.openConnection();
			response = connection.getResponseCode();
			responseMessage = connection.getResponseMessage();
		}
		catch (ClassCastException ex)
		{
			System.err.println(ERR_HTTP);
			return;
		}
		catch (IOException ex)
		{
			System.err.println(ERR_IO);
			return;
		}

		// ------------------------------------
		//     Auswerten des Response-Code
		// ------------------------------------

		switch (response)
		{

			// keine Autorisierung erforderlich
			case HttpURLConnection.HTTP_OK:
				{
					System.out.println("KEINE AUTORISIERUNG ERFORDERLICH");
					// Inhalt ausgeben
					try
					{
						print(connection.getInputStream());
						connection.disconnect(); // Kann nicht schaden, oder?
					}
					catch (IOException e)
					{
						System.err.println(ERR_IO);
					}
					return;
				}

				// Authentifizierung erforderlich
			case HttpURLConnection.HTTP_UNAUTHORIZED:
				{
					System.out.println("SUCHE NACH USER/PWD-KOMBINATION");
					try
					{
						System.out.println("Herausforderung: " +
						connection.getHeaderField("WWW-Authenticate").substring(12));
						doAuthorize(page);
					}
					catch (IOException ex)
					{
						System.err.println(ERR_IO);
					}
					return;
				}

				// nicht gefunden usw.
			default:
				{
					System.err.println("FORTSETZUNG NICHT MÖGLICH: " +
					responseMessage);
					connection.disconnect();
				}
		}
	}


	/**
	 * doAuthorize
	 *
	 * @param page URL
	 */
	private void doAuthorize(URL page)
	throws IOException
	{

		// Benutzername und Password finden
		for (int i = 0; i < userNames.length; i++)
			for (int j = 0; j < passwords.length; j++)
			{

				// neu anforden mit Authorization Header
				connection = (HttpURLConnection) page.openConnection();
				connection.setRequestProperty("Authorization", "Basic " +
				encodeBase64(userNames[i], passwords[j]));
				// Response-Code erfragen
				response = connection.getResponseCode();

				// Response-Code auswerten
				// Die eigentliche Autorisierung ist nicht nur bei Antwort 200
				// erfolgreich. So gibt der Server z.B. erst nach der
				// Autorisierung zurück, ob die angeforderte Ressource überhaupt
				// existiert.
				// Auch wenn der Server z.B. einen internen Fehler meldet,
				// müsste die Anfrage nochmals mit der gerade gesendeten
				// Benutzer/Pwd - Kennung erfolgen

				if (response == HttpURLConnection.HTTP_OK)
				{
					System.out.println("BENUTZERNAME:\t " + userNames[i] +
					"\nPASSWORT:\t " + passwords[j]);
					print(connection.getInputStream());
					connection.disconnect();
					return;
				}
			}

		System.out.println("KEINE ÜBEREINSTIMMUNG GEFUNDEN");
		connection.disconnect();
	}


	/**
	 * print
	 * gibt zeilenweise den Inhalt der angeforderten Ressource aus.
	 *
	 * @param stream
	 */
	private void print(InputStream stream)
	throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		System.out.println("---");
		while (true)
		{
			String l;
			l = br.readLine();
			if (l == null)
				break;
			else
				System.out.println(l);
		}
		br.close();
	}


	/**
	 * encodeBase64
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	private String encodeBase64(String username, String password)
	{
		return new sun.misc.BASE64Encoder().encode((username + ":" + password).getBytes());
	}


	/**
	 * main
	 * URL auf korrektes Schema überprüfen
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		URL page;

		try
		{
			page = new URL(args[0]);
		}
		catch (MalformedURLException ex)
		{
			System.out.println(ex.getLocalizedMessage());
			return;
		}

		new ReadProtected(page);
	}
}
