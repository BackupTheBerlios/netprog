package uebung05.a4;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.HTML.Tag;

/**
 * @author Rafael
 *
 */
public class Crawler extends HTMLEditorKit.ParserCallback
{
    // Basis-URL (nur auf dem hier enthaltenen Webserver wird gecrawlt)
    private final URL base;
    
    // Nur Seiten mit diesem Pfad werden gecrawlt
    private final String searchPath;
    
    // URL der Seite, die gerade gecrawlt wird
    private URL context;
    
    // Zähler für interne Links
    private int internal = 0;
    // Zähler für externe Links
    private int external = 0;
    // Zähler für fehlerhafte Links
    private int illegal = 0;
    
    // Bereits ausgewertete Ressourcen
    private final Vector crawled = new Vector();
    
    // Noch auszuwertende Ressourcen
    private final Vector todo = new Vector();

    public Crawler(URL base, String searchPath) throws Ressource.NotAccessibleException
    {
        this.base = base;
        this.searchPath = searchPath;
        
        todo.add(new Ressource(base,""));
        
        // Starten
        while (!todo.isEmpty()) crawl((Ressource)todo.remove(0));
        
        // Ergebnis
        System.out.println("\n");
        System.out.println("Externe Links: "+external);
        System.out.println("Interne Links: "+internal);
        System.out.println("Falsche Links: "+illegal);
        if (external!=0 && internal!=0)
            System.out.println("Verhältnis:    Extern/Intern = "+((float)external/internal));        
    }
    
    private void crawl(Ressource ressource) throws Ressource.NotAccessibleException
    {
        // Wurde vielleicht schon gecrawlt?
        if (crawled.contains(ressource)) return;

        System.out.println("\nUntersuche " + ressource);
        
        // vermerken das diese Ressource gecrawlt wird/wurde
        crawled.add(ressource);
        
        // URL für relative Pfade setzen
        context = ressource.getURL(); 
        
        // crawlen
        try
        {
            ressource.crawl(this);
        }
        catch (IOException e)
        {
            throw new Ressource.NotAccessibleException(ressource.toString(),e,"Crawlen fehlgeschlagen.");
        } 
    }
	
    /**
     * Handler für <frame src=...>
     * Frames werden nicht als Link gezählt.
     * In den Frames wird aber ggf. weiter gecrawlt.
     */
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int pos)
	{
        // nur Frames werden behandelt
        if (!tag.equals(HTML.Tag.FRAME)) return;
        
        // Das src Attribut muss existieren
        String src = (String) attributes.getAttribute(HTML.Attribute.SRC);
        if (src == null) return;
        
        try
        {
            Ressource res = new Ressource(context,src);
            if (res.isOnServer(base) && res.isCrawlable(searchPath)) todo.add(res);
        }
        catch (Ressource.NotAccessibleException e)
        {
            System.out.println(e);
        }
	}

    /**
     * Handler für <a href=...>
     * Zählen von Links, ggf. weiter crawlen.
     */
	public void handleStartTag(Tag tag, MutableAttributeSet attributes, int pos)
	{
        // nur Links werden behandelt
        if (!tag.equals(HTML.Tag.A)) return;
        
        // Das href Attribut muss existieren
        String href = (String) attributes.getAttribute(HTML.Attribute.HREF);
        if (href == null) return;
        
        try        
        {
            Ressource res = new Ressource(context,href);
            if (res.isOnServer(base))
            {
                ++internal;
                if (res.isCrawlable(searchPath)) todo.add(res);
            } 
            else
            {
                ++external;
            }
        }
        catch (Ressource.NotAccessibleException e)
		{
            System.out.println(e);
            ++illegal;
		}
	}

}
