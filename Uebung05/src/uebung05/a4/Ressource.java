package uebung05.a4;

import java.io.*;
import java.net.*;

import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

/**
 * @author Rafael
 *
 */
public class Ressource
{
    private final URL url;
    private final boolean crawlable;

    /**
     * Konstruktor
     * Zugreifbarkeit und Typ der Ressource checken
     * @throws IOException wenn der Link (im Kontext) nicht gültig ist
     */    
    public Ressource(URL context, String link) throws NotAccessibleException
    {
        try
        {
			this.url = new URL(context,normalize(link));
            
            // "mailto" kann nicht geprüft werden
            if (url.getProtocol().equalsIgnoreCase("mailto"))
                throw new NotAccessibleException(link,"Dies ist eine email-Adresse."); 

            // Verbindung erstellen
            URLConnection connection = url.openConnection();

            // HTTP-Verbindungen
            if (connection instanceof HttpURLConnection)
            {
                crawlable = checkHTTP((HttpURLConnection)connection,link);
            }
            // sonstige Verbindungstypen
            else
            {
                crawlable = false;
                try
                {
                    connection.connect();
                    connection.getContent();
                }
                catch (IOException e)
                {
                    throw new NotAccessibleException(link,e,"Zugriff fehlgeschlagen");
                }
                
            }
        }
        catch (MalformedURLException e) // falsche URL
        {
            throw new NotAccessibleException(link,e,"Ungültige URL");
        }
        catch (SecurityException e) // keine Erlaubnis
        {
            throw new NotAccessibleException(link,e,"Verbindungsaufbau zum Host nicht erlaubt");
        }
        catch (UnknownHostException e) // kein DNS Eintrag
        {
            throw new NotAccessibleException(link,e,"Unbekannter Host");
        }
        catch (IOException e) // Fehlgeschlagener Verbindungsaufbau
        {
            throw new NotAccessibleException(link,e,"Keine Verbindung zum Server");
        }
    }

    /**
     * HTTP-Verbindung testen
     * @return true wenn es sich um eine HTML-Ressource handelt, false sonst.
     */
    private boolean checkHTTP(HttpURLConnection connection, String link) throws IOException, NotAccessibleException
    {
        connection.setRequestMethod("HEAD");
        connection.connect();
        int code = connection.getResponseCode();
                        
        switch (code)
        {
            case HttpURLConnection.HTTP_OK:
                return connection.getContentType().toLowerCase().indexOf("text/html") >= 0;            
                            
            case HttpURLConnection.HTTP_NOT_FOUND:
                throw new NotAccessibleException(link,"Nicht gefunden - " + code);

            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new NotAccessibleException(link,"Autorisation erforderlich - " + code);

            case HttpURLConnection.HTTP_FORBIDDEN:
                throw new NotAccessibleException(link,"Zugriff verboten - " + code);
                            
            default:
                throw new NotAccessibleException(link,"Zugriff fehlgeschlagen - HTTP-Code " + code);
        }
    }
    
    /**
     * Befindet sich diese Ressource auf dem Webserver in der URL 
     */
    public boolean isOnServer(URL url)
    {
        return this.url.getProtocol().equalsIgnoreCase(url.getProtocol())
            && this.url.getAuthority().equalsIgnoreCase(url.getAuthority());
    }
    
    /**
     * Soll hier weiter gecrawlt werden?
     */
    public boolean isCrawlable(String path)
    {
        return crawlable && url.getPath().startsWith(path);
    }
    
    /**
     * Diese Ressource crawlen.
     */
    public void crawl(HTMLEditorKit.ParserCallback callback) throws IOException
    {
        if (!crawlable) throw new IOException("Keine HTML-Ressource");
        new ParserDelegator().parse(new BufferedReader(new InputStreamReader(url.openStream())),callback,true);
    }
    
    /**
     * URL rausgeben
     */
    public URL getURL()
    {
        return url;
    }
    
    /**
     * Zwei Ressourcen vergleichen (URL Vergleich)
     */
    public boolean equals(Object object)
    {
        return object != null
            && object instanceof Ressource
            && ((Ressource) object).url.equals(this.url);
    }
    
    /**
     * als String 
     */
    public String toString()
    {
        return url.toExternalForm();
    }
    
    /**
     * Pfadangaben mit '/' abschliessen.
     * Falls nicht genau ein Punkt nachdem letzten '/' in path vorkommt, ist kein 
     * Dateiname angegeben. So eine Pfadangabe muss unbedingt durch ein '/'abgeschlossen 
     * werden, da sonst der Konstruktor URL(URL,String) nicht korrekt arbeitet.
     */    
    public static String normalize(String path)
    {
        // Links die '?' enthalten werden nicht verändert
        if (path.indexOf('?') >= 0) return path;

        // Position des letzten '/'
        int pos = path.lastIndexOf('/');
        
        // Kommt kein '/' vor, dann kompletten String betrachten
        if (pos < 0) pos = 0;
        
        // Ist path leer oder endet bereits mit '/',
        // dann wird keine Änderung vorgenommen.
        if (path.length()==0 || path.endsWith("/")) return path;
        
        // Ist im letzten teil genau ein '.', wird davon ausgegangen,
        // dass es sich um eine Datei handelt, und nichts geändert.
        if ((pos = path.indexOf('.',pos)) >= 0 && path.indexOf('.',pos+1) < 0)
            return path;
            
        // Es handelt sich offenbar um eine Pfadangabe.
        // Ein '/' wird angehängt.
        return path + "/";                
    }

    /**
     * Ausnahme wenn kein Zugriff auf Ressource
     */
    public static class NotAccessibleException extends Exception
    {
        private String link;

        public NotAccessibleException(String link, String message)
        {
            this(link,null,message);
        }
        
        public NotAccessibleException(String link, Throwable cause, String message)
        {
            super(message,cause);
            this.link = link;
            if (link.length() > 0) this.link += " ";
        }
        
        public String toString()
        {
            String causeMessage = (getCause()==null ? "" : " - "+getCause().getLocalizedMessage());
            return "Ressource " + link + "ist nicht erreichbar. " + getMessage() + causeMessage;        }
    }
}
