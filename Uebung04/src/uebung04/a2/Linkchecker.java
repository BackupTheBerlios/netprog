package uebung04.a2;

import java.io.*;
import java.net.*;

/**
 * @author Rafael
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Linkchecker
{
    RandomAccessFile file;
    
    public Linkchecker(RandomAccessFile file)
    {
        this.file = file;
        
        String link;
        
        try
		{
			while ((link = file.readLine()) != null)
			{
                try
                {
                    System.out.print("\nTeste " + link + "\n  -> ");
                    
                    URL url = new URL(link);
                    
                    URLConnection connection = url.openConnection();
                    
                    connection.connect();

                    System.out.print(url.getProtocol()+"-Verbindung: ");            
                    
                    if (url.getProtocol().equalsIgnoreCase("http"))
                    {
                        int code = ((HttpURLConnection)connection).getResponseCode();
                        
                        switch (code)
                        {
                            case HttpURLConnection.HTTP_OK:
                                System.out.println("Erfolgreicher Zugriff. (OK " + code + ")");
                            break;
                            
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                System.out.println("Nicht gefunden. (" + code + ")");
                            break;

                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                System.out.println("Nicht autorisiert. (" + code + ")");
                            break;

                            case HttpURLConnection.HTTP_FORBIDDEN:
                                System.out.println("Zugriff verweigert. (" + code + ")");
                            break;
                            
                            default:
                                System.out.println("Zugriff nicht erfolgreich. (HTTP-Code " + code + ")");
                            break;
                        }
                    }
                    else
                    {
                        if (connection.getContentLength() > 0)
                            System.out.println("Erfolgreicher Zugriff.");
                        else
                            System.out.println("Zugriff fehlgeschlagen.");                         
                    }
                }
                catch (MalformedURLException e)
                {
                    System.out.println("URL ungültig. (" + e.getLocalizedMessage() + ")");
                }
                catch (IOException e)
                {
                    System.out.println("Keine Verbindung zum Server. (" + e.getLocalizedMessage() + ")");
                }
			}
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Textdatei: " + e.getMessage());
            System.out.println("Programm beendet.");
            return;
		}
    }
    
    

	public static void main(String[] args)
	{
        if (args.length != 1)
        {
            System.out.println("Benutzung: java uebung4.a2.Linkchecker <linkfile>");
        }
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
