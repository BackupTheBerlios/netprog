package uebung05.a4;

import java.io.*;
import java.net.*;
import java.util.Vector;

import javax.swing.text.*;
import javax.swing.text.html.*;

/**
 * @author Rafael
 *
 */
public class Crawler extends HTMLEditorKit.ParserCallback
{
    private final URL base;
    private final String searchPath;
    
    // Link-Zähler für interne Links
    private int internal = 0;
    // Link-Zähler für externe Links
    private int external = 0;
    
    // Bereits ausgewertete Ressourcen
    private final Vector crawled = new Vector();

    public Crawler(URL base, String searchPath) throws Ressource.NotAccessibleException
    {
        this.base = base;
        this.searchPath = searchPath;
        
        // Starten
        crawl(new Ressource(base,""));
        
        // Ergebnis
        System.out.println("\nCrawlen beendet.");
        System.out.println("Externe Links: "+external);
        System.out.println("Interne Links: "+internal);
        System.out.println("Verhältnis:    "+(external/internal));        
    }
    
    private void crawl(Ressource ressource) throws Ressource.NotAccessibleException
    {
        System.out.println("\nCrawle "+ressource+"...");
        
        // Wurde vielleicht schon gecrawlt
        if (crawled.contains(ressource))
        {
            System.out.println("Wurde bereits gecrawlt.");
            return;
        }
        
        // vermerken das diese Ressource gecrawlt wurde
        crawled.add(ressource);
        
        // crawlen
        try
        {
            ressource.crawl(this);
        }
        catch (IOException e)
        {
            throw new Ressource.NotAccessibleException(base.toExternalForm(),e,"Crawlen fehlgeschlagen.");
        } 
    }
	
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int pos)
	{
        try
        {
    		// Frames werden nicht als link gezählt.
            // In den Frames wird aber weiter gecrawlt.
            if (tag.equals(HTML.Tag.FRAME))
            {
                Ressource res = new Ressource(base,(String)attributes.getAttribute(HTML.Attribute.SRC));
                if (res.isCrawlable(searchPath)) crawl(res);
            }
            // Link zählen / verfolgen
            else if (tag.equals(HTML.Tag.LINK))
            {
                Ressource res = new Ressource(base,(String)attributes.getAttribute(HTML.Attribute.HREF));
                if (res.isInternal()) ++internal;
                else ++external;                    
                if (res.isCrawlable(searchPath)) crawl(res);            
            }
            // alle anderen Tags werden ignoriert
        }
        catch (Ressource.NotAccessibleException e)
        {
            System.out.println(e);
        }
	}

}
