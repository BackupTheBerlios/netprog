package uebung04.a1;

import uebung04.URLParser;

import java.io.*;
import java.net.*;

public class RessourceComparer
{
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                    Main Method                    |   \\
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

    /**
     * compares to recources specified by their urls.
     *
     * @param args the urls to be compared
     * @throws IOException
     */
    public static void main(String[] args)
    {
        // alle Argumente vorhanden?
        if (args.length != 2)
        {
            System.err.println("Usage: java uebung04.a1.RessourceComparer <URL> <URL>");
            System.exit(-1);
        }

        // urls checken und vergleichen
        try
        {
            URL url1 = URLParser.sharedInstance().parseURL(args[0]);
            URL url2 = URLParser.sharedInstance().parseURL(args[1]);
            System.out.println("Die Ressourcen\n\n\t" + url1 + "\nund\t" + url2 + "\n\nsind: " + (new RessourceComparer().areRecourcesEqual(url1, url2) ? "" : "NICHT ") + "IDENTISCH\n");
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

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Methods                      |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    /**
     * compares to content of two urls
     *
     * @param url1 first url
     * @param url2 second url
     * @return whether the recources are equal or not
     * @throws IOException
     */
    public boolean areRecourcesEqual(URL url1, URL url2)
    throws IOException
    {
        URLConnection connection1 = url1.openConnection();

        // get detailed error - information on HttpURLConnections:
        if (connection1 instanceof HttpURLConnection && !URLParser.sharedInstance().checkResponseOK(((HttpURLConnection) connection1)))
        {
            System.exit(-1);
        }

        URLConnection connection2 = url2.openConnection();

        // get detailed error - information on HttpURLConnections:
        if (connection2 instanceof HttpURLConnection && !URLParser.sharedInstance().checkResponseOK(((HttpURLConnection) connection2)))
        {
            System.exit(-1);
        }

        // same size ?
        if (connection1.getContentLength() != -1 && connection2.getContentLength() != -1)
            if (connection1.getContentLength() != connection2.getContentLength()) return false;

        // same type ?
        if (!connection1.getContentType().equals(connection2.getContentType()))
            return false;

        BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
        BufferedReader in2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));

        int line1, line2;

        // same content?
        while (true)
        {
            line1 = in1.read();
            line2 = in2.read();

            if (line1 == -1 || line2 == -1)
            {
                return line1 == -1 && line2 == -1;
            }

            if (line1 != (line2))
            {
                return false;
            }
        }
    }
}
