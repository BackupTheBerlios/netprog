package uebung05.a4;

import java.net.*;

/**
 * @author Rafael
 *
 * Startet den Crawler.
 */
public class StartCrawler
{
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("Benutzung: java uebung05.a4.Crawler <StartURL> <CrawlingPath>");
            return;
        }
        
        try
        {
            new Crawler(new URL(Ressource.normalize(args[0])),args[1]);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Die Start-URL ist ungültig: "+e.getLocalizedMessage());
            return;
        } 
        catch (Ressource.NotAccessibleException e)
        {
            System.out.println("Fehler: " + e);
        }        
    }
}
