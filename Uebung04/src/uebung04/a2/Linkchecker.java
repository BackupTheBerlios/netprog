// Gruppe 6
package uebung04.a2;

import java.io.*;
import java.net.*;

/**
 * Aufgabe 2 - Linkchecker
 * @author Rafael
 */
public class Linkchecker
{
    /**
     * Testen aller URLs in der übergebenen Datei
     * @param file Textdatei mit URLs
     */
    public Linkchecker(RandomAccessFile file)
    {
        String link;
        try
		{
			// Zeilenweises Lesen der Textdatei
            while ((link = file.readLine()) != null)
                // Testen der URL
                checkLink(link);
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Textdatei: "+e.getMessage());
            System.out.println("Programm beendet.");
            return;
		}
    }

    /**
     * Testen einer URL
     * @param link URL
     */
    private void checkLink(String link)
    {
        try
        {
            //Leerzeilen werden ignoriert
            if (link.trim().equals(""))
            {
                System.out.println("\nLeerzeile ignoriert.");
                return;
            } 
                    
            System.out.print("\nTeste "+link+"\n  -> ");
                    
            // String als URL parsen
            URL url = parseURL(link);
                    
            // Testen ob Host bekannt
            InetAddress.getByName(url.getHost());
            
            // Verbindung erstellen
            URLConnection connection = url.openConnection();
                    
            // Verbinden
            connection.connect();

            //System.out.print(url.getProtocol()+"-Verbindung zu "+url.getHost()+" hergestellt.\n  -> ");            
                    
            // HTTP-Verbindungen speziell behandeln
            if (connection instanceof HttpURLConnection)
                checkHttpConnection((HttpURLConnection)connection);
            // sonstige Verbindungstypen
            else
            {
                try
                {
                    connection.getContent();
                    System.out.println("URL ist zugreifbar!");
                }
                catch (IOException e)
				{
                    System.out.println("Zugriff auf Ressource "+url.getPath()+" fehlgeschlagen.");
				}
            }
        }
        catch (MalformedURLException e) // falsche URL
        {
            System.out.println("Ungültige URL. ("+e.getLocalizedMessage()+")");
        }
        catch (SecurityException e) // keine Erlaubnis
		{
			System.out.println("Verbindungsaufbau zum Host nicht erlaubt. ("+e.getLocalizedMessage()+")");
		}
        catch (UnknownHostException e) // kein DNS Eintrag
		{
			System.out.println("Unbekannter Host. ("+e.getLocalizedMessage()+")");
		}
        catch (IOException e) // Fehlgeschlagener Verbindungsaufbau
        {
            System.out.println("Keine Verbindung zum Server. ("+e.getLocalizedMessage()+")");
        }
        
    }
    
    /**
     * URL aus String erzeugen
     * @param spec Textrepräsentation einer URL
     * @return entsprechendes URL-Objekt
     * @throws MalformedURLException wenn spec ein unbekanntes Protokoll oder einen nicht erlaubten Hostnamen enthält
     */
    private URL parseURL(String spec) throws MalformedURLException
    {
        // URL erzeugen, löst MalformedURLException bei unbekanntem Protokoll aus
        URL url = new URL(spec);

        // Hostname darf nicht leer sein
        if (url.getHost().length()==0)
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
    
    /**
     * HTTP-Verbindung testen
     */
    private void checkHttpConnection(HttpURLConnection connection)
    {
        int code;
        String path = connection.getURL().getPath();
        if (path.equals("")) path = "/";        

        // Versuchen den HTTP Status-Code zu lesen
        // Sollte funktionieren, da bereits verbunden        
		try
		{
			code = connection.getResponseCode();
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Auslesen des HTTP Status-Codes: "+e.getLocalizedMessage());
            return;
		}
                        
        switch (code)
        {
            case HttpURLConnection.HTTP_OK:
                System.out.println("URL ist zugreifbar!");
            break;
                            
            case HttpURLConnection.HTTP_NOT_FOUND:
                System.out.println("Ressource "+path+" nicht gefunden. (" + code + ")");
            break;

            case HttpURLConnection.HTTP_UNAUTHORIZED:
                System.out.println("Autorisation für Ressource "+path+" erforderlich. (" + code + ")");
            break;

            case HttpURLConnection.HTTP_FORBIDDEN:
                System.out.println("Zugriff auf Ressource "+path+" verboten. (" + code + ")");
            break;
                            
            default:
                System.out.println("Zugriff auf Ressource "+path+" fehlgeschlagen. (HTTP-Code " + code + ")");
            break;
        }
    }
    
    /**
     * MAIN
     * Überprüft Parameter und versucht angegebene Datei zu öffnen
     * @param args Ein Parameter (Textdatei mit URLs)
     */
	public static void main(String[] args)
	{
        // Anzahl Parameter prüfen
        if (args.length != 1)
        {
            System.out.println("Benutzung: java uebung4.a2.Linkchecker <linkfile>");
        }
        
        // Textdatei versuchen zu öffnen und Linkchecker starten
        else
        {
            try
			{
				new Linkchecker(new RandomAccessFile(args[0],"r"));
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Datei " + args[0] + " nicht gefunden.");
			}            
        }
	}
}
