package uebung03.a4;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Nebenläufige Bearbeitung einer Anfrage. 
 * @author Gruppe 6
 */
public class ProxyThread implements Runnable
{
    // Sockets anfragender Client, Webserver
    private Socket client, server;
    // Zeichenorientierte Übertragung vom Client zum Server
    private BufferedReader clientIn;
    private PrintStream serverOut;
    // Byteweises Übertragen vom Server zum Client (ermöglicht das Senden von Bildern etc.)
    private BufferedInputStream serverIn;
    private BufferedOutputStream clientOut;

    /**
     * Konstruktor
     * @param client Kommunikationssocket zum Client
     */
    public ProxyThread(Socket client) {
        
        this.client = client;
        
    }
    
	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
        // angefragter Webserver
        String host = "";
        // angefragter Port
        int port = 0;
        // Buffer für je eine Zeile der Anfrage
        String request = "";
        // Anfragetyp
        String callType = "";
        int id = this.hashCode();
        

        System.out.println(id + " | Source:       " + client.getInetAddress());
                
        // Ströme vorbereiten:
        try{

            clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            clientOut = new BufferedOutputStream(client.getOutputStream());
        } catch(IOException e) {
            
            error("Unexpected error while creating client streams", e);
            return;
        }

        // server und aufruftyp extrahieren 
        try{

            request = clientIn.readLine();
            StringTokenizer t = new StringTokenizer(request, " ");
            callType = t.nextToken();
            String s = t.nextToken();
            // Nur HTTP Anfragen "GET" und "HEAD" werden bearbeitet
            if(!s.toLowerCase().startsWith("http://") || !(callType.equalsIgnoreCase("GET") || callType.equalsIgnoreCase("HEAD"))){
                
                error("Illegal request", new IOException("Illegal request"));
                return;
            }
            
            // servername und pfad extrahieren
            s = s.substring(7); // "http://" entfernen
            int colon = s.indexOf(':');
            int slash = s.indexOf('/');
            // wenn kein Vorkommen von '/' dann wird der ganze String s als host+port interpretiert
            if(slash < 0) slash = s.length(); 
            // wenn ':' vorkommt und vor dem ersten '/' steht ist ein Port angegeben
            if((colon > 0) && (colon < slash)) {
            
                host = s.substring(0, colon);
                port = Integer.parseInt(s.substring(colon + 1, slash));
            // sonst geht der hostname bis zum '/' und wir verwenden Port 80
            } else {
                
                host = s.substring(0, slash);
                port = 80;
            }
            
            // host und port aus dem request eliminieren
            s = "http://" + s.substring(0,slash);
            int i = request.indexOf(s);
            request = request.substring(0,i) + request.substring(s.length() + i);

            System.out.println(id + " | Destination:  " + host + ":" + port);
            System.out.println(id + " | Request:      " + request);

        }catch(IOException e) {
            
            error("Unexpected error while extracting host and calltype", e);
            return;
        }catch(NumberFormatException e){
            
            error("Could not exctract port", e);
            return;
        }
        
        // Socket zum Server und Streams Öffnen
        try {
            
			server = new Socket(host, port);
            serverIn = new BufferedInputStream(server.getInputStream());
            serverOut = new PrintStream(server.getOutputStream());

        } catch (IOException e) {
            
            error("Unexpected error while connecting to server", e);
            return;
        }

        // Anfrage Weiterleiten
        try{
            do{

                // Die Header "Connection" und "Content-Transfer-Encoding" werden entfernt
                if (!request.startsWith("Connection:") && !request.startsWith("Content-Transfer-Encoding:"))
                    serverOut.println(request); 
                // nächste Zeile lesen
                request = clientIn.readLine();
            } while(clientIn.ready());
            
        } catch (IOException e) {
            
            error("Unexpected error while forwarding request", e);
            return;
        }
        
        // Zusätzliche Anweisungen in den HTTP-Header stecken  
        serverOut.println("Connection: Close");
        serverOut.println("Content-Transfer-Encoding: Identity");
        serverOut.print("\r\n"); // request fertig gesendet

        // Antwort Weiterleiten und X-Traffic-Counted einfügen
        try {
			// Traffic-Zähler für aktuelle Antwort
            long traffic = 0; 
            
            byte[] buf = new byte[1024];
            int counter, position = 0;
            // Neuer Header "X-Traf..." bereits engefügt?
            boolean trafficInserted = false;
            
            // Byteweises weiterleiten
            while (!((counter = serverIn.read(buf, 0, 1024)) < 0)){
                
                // Traffic mitzählen
                traffic += counter;

                // erstes vorkommen von "/r/n" suchen (das bytearray kann noch keinen "restmüll" enthalten)
                if ((position = (new String(buf)).indexOf("\r\n")) > 0 && !trafficInserted) { 
                    
                    // einfügen des x-traffic-counted hinter "/r/n"
                    trafficInserted = true;
                    clientOut.write(buf,0,position + 2);
                    clientOut.write(("X-Traffic-Counted: " + String.valueOf(Proxy.getTraffic()) + "\n").getBytes());
                    clientOut.write(buf, position + 2, counter - (position + 2));
                } else {

                    // normales Durchleiten
                    clientOut.write(buf, 0, counter);
                }
                
            }
            
            // Traffic aufaddieren (nur bei Get)
            if (callType.equalsIgnoreCase("GET")) Proxy.addTraffic(traffic);
                
            clientOut.flush();
            
            System.out.println(id + " | Bytes sent:   " + traffic);
		} catch (IOException e) {
            
            error("Unexpected error while receiving response", e);
            return;
		}
        
        // Alles Schliessen
        serverOut.close();
        try{
            clientIn.close();
            serverIn.close();
            clientOut.close();
            client.close();
            server.close();           
            
            System.out.println(id + " | Connections closed.");
        }catch(IOException e){
            
            error("Error on closing streams, trying again.", e);
            return;
        }

	}
    
    /**
     * Methode zur Fehlerbehandlung
     * Gibt Informationen zum afgetretenen Fehler aus.
     * Versucht alle offenen Sockets bzw. Streams zu schließen.
     * @param str Fehlermeldung
     * @param e aufgetretene Ausnahme
     */
    private void error(String str, Exception e){
        
        int id = this.hashCode();
        System.err.println(id + " | ERROR: " + str + ": \n" + e.getMessage());
        e.printStackTrace();
        // Alles Schliessen
        if (serverOut != null) serverOut.close();
        try{
            if (clientIn != null) clientIn.close();
            if (serverIn != null) serverIn.close();
            if (clientOut != null) clientOut.close();
            if (client != null) client.close();
            if (server != null) server.close();
        }catch(IOException ex){
            
            System.err.println(id + " | Could not close all streams");
            return;
        }
    }

}
