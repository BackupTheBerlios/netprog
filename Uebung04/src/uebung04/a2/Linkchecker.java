// Gruppe 6
package uebung04.a2;

import uebung04.URLParser;

import java.io.*;
import java.net.*;

/**
 * Aufgabe 2 - Linkchecker
 *
 * @author Rafael
 */
public class Linkchecker
{
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                    Main Method                    |   \\
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

    /**
     * Überprüft Parameter und versucht angegebene Datei zu öffnen
     *
     * @param args Ein Parameter (Textdatei mit URLs)
     */
    public static void main(String[] args)
    {
        // Anzahl Parameter prüfen
        if (args.length != 1)
        {
            System.out.println("Benutzung: java uebung04.a2.Linkchecker <linkfile>");
        }

        // Textdatei versuchen zu öffnen und Linkchecker starten
        else
        {
            try
            {
                new Linkchecker(new RandomAccessFile(args[0], "r"));
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Datei " + args[0] + " nicht gefunden.");
            }
        }
    }

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                   Constructors                    |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    /**
     * Testen aller URLs in der übergebenen Datei
     *
     * @param file Textdatei mit URLs
     */
    public Linkchecker(RandomAccessFile file)
    {
        String link;
        try
        {
            // Zeilenweises Lesen der Textdatei und überprüfen der URL
            while ((link = file.readLine()) != null)
                checkLink(link);
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Lesen der Textdatei: " + e.getMessage());
            System.exit(-1);
            return;
        }
    }

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Methods                      |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    /**
     * Testen einer URL
     *
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

            System.out.print("\nTeste " + link + "\n  ->\t");

            // String als URL parsen
            URL url = URLParser.sharedInstance().parseURL(link);

            // Testen ob Host bekannt
            InetAddress.getByName(url.getHost());

            // Verbindung erstellen
            URLConnection connection = url.openConnection();

            // HTTP-Verbindungen speziell behandeln
            if (connection instanceof HttpURLConnection)
            {
                if (URLParser.sharedInstance().checkResponseOK((HttpURLConnection) connection))
                    System.out.println("Zugriff möglich");
                else
                    System.out.println("\tZugriff nicht möglich");
            }

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
                    System.out.println("Zugriff auf Ressource " + url.getPath() + " fehlgeschlagen.");
                }
            }
        }
        catch (MalformedURLException e) // falsche URL
        {
            System.out.println("Ungültige URL. (" + e.getLocalizedMessage() + ")");
        }
        catch (SecurityException e) // keine Erlaubnis
        {
            System.out.println("Verbindungsaufbau zum Host nicht erlaubt. (" + e.getLocalizedMessage() + ")");
        }
        catch (UnknownHostException e) // kein DNS Eintrag
        {
            System.out.println("Unbekannter Host. (" + e.getLocalizedMessage() + ")");
        }
        catch (IOException e) // Fehlgeschlagener Verbindungsaufbau
        {
            System.out.println("Keine Verbindung zum Server. (" + e.getLocalizedMessage() + ")");
        }

    }
}
