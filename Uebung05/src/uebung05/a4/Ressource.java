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
    private final boolean internal;
    
    /**
     * Konstruktor
     * Zugreifbarkeit und Typ der Ressource checken
     * @throws IOException wenn der Link (im Kontext) nicht gültig ist
     */    
    public Ressource(URL context, String link) throws NotAccessibleException
    {
        try
        {
            this.url = new URL(context,link);
            
            internal = url.getProtocol().equals(context.getProtocol())
                       && url.getAuthority().equalsIgnoreCase(context.getAuthority());

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
                connection.connect();
                try
                {
                    connection.getContent(); //TODO
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
     * Ist dies ein interner link? 
     */
    public boolean isInternal()
    {
        return internal;
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
            return "Ressource "+link+"ist nicht erreichbar. ("+getMessage()+causeMessage+")";        }
    }
}
