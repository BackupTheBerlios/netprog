/*
 * Created on 05.01.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package uebung03.a4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * @author Rafael
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ProxyThread implements Runnable
{
    private Socket client, server;
    private BufferedReader clientIn;
    private BufferedInputStream serverIn;
    private PrintStream serverOut;
    private BufferedOutputStream clientOut;

    public ProxyThread(Socket client){
        
        this.client = client;
    }
    
	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
        String host = "";
        int port = 0;
        String request = "";
//        String response = "";
        String callType = "";
        

System.out.println("request from " + client.getInetAddress());        
        // Ströme vorbereiten:
        try{

            clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            clientOut = new BufferedOutputStream(client.getOutputStream());
System.out.println("Clientstreams established.");
        }catch(IOException e) {
            
            error("Unexpected error while creating client streams", e);
            return;
        }

        // server und aufruftyp extrahieren 
        try{
            request = clientIn.readLine();
System.out.println("request: " + request);
            StringTokenizer t = new StringTokenizer(request, " ");
            callType = t.nextToken();
            String s = t.nextToken();
            if(!s.startsWith("http://") && !(callType.equalsIgnoreCase("GET") || callType.equalsIgnoreCase("HEAD"))){
                
                error("Illegal request", new IOException("Illegal request"));
            }
            // servername und pfad extrahieren
            s = s.substring(7); // http:// entfernen or as raffi would say: rauskicken! ;)
            int colon = s.indexOf(':');
            int slash = s.indexOf('/');
            if(slash < 0) slash = s.length(); 
            if((colon > 0) && (colon < slash)) {
            
                host = s.substring(0, colon);
                port = Integer.parseInt(s.substring(colon + 1, slash));
            } else {
                
                host = s.substring(0, slash);
                port = 80;
            }
            // host und port aus dem request eliminieren
            s = "http://" + s.substring(0,slash);
            int i = request.indexOf(s);
            request = request.substring(0,i) + request.substring(s.length() + i);
System.out.println("host: " + host + " | port: " + port);
        }catch(IOException e) {
            
            error("Unexpected error while extracting destinationserver and calltype", e);
            return;
        }catch(NumberFormatException e){
            
            error("Could not exctract port", e);
            return;
            
        }
        
        // socket zum server und streams öffnen keepAlive auf false setzen
        try {
            
			server = new Socket(host, port);
            serverIn = new BufferedInputStream(server.getInputStream());
            serverOut = new PrintStream(server.getOutputStream());

System.out.println("connection to server and streams established serveraddress: " + server.getInetAddress());
        }catch (IOException e) {
            
            error("Unexpected error while connecting to server", e);
            return;
        }

        // anfrage weiterleiten
        try{
            do{

System.out.println("forwarding to server: " + request);
                serverOut.println(request); 
                request = clientIn.readLine();
            }while(clientIn.ready());
            
        }catch (IOException e) {
            
            error("Unexpected error while forwarding request", e);
            return;
        }
        //  
        serverOut.println("Connection: Close");
        serverOut.println("Content-Transfer-Encoding: Identity");
        serverOut.print("\r\n"); // request fertig gesendet

        // response empfangen und X-traffic-counted einfügen
        
        try {
			long traffic = 0; 
            
            byte[] buf = new byte[1024];
            int counter, position = 0;
            boolean foundHttpHeader = !callType.equalsIgnoreCase("GET"); // nur beachten wenn es eine get anfrage ist!
            
            while(!((counter = serverIn.read(buf, 0, 1024)) < 0)){
                
                traffic += counter;
// erstes vorkommen von /r/n suchen (das bytearray kann noch keinen "restmüll" enthalten)
                if((position = (new String(buf)).indexOf("\r\n")) > 0 && !foundHttpHeader){ // einfügen des x-traffic-counted

                    foundHttpHeader = true;
System.out.println("forwarding to client: " + new String(buf, 0, position + 2));  
                    clientOut.write(buf,0,position + 2);
System.out.println("X-Traffic-Counted: " + String.valueOf(Proxy.getTraffic()) + "\n");  
                    clientOut.write(("X-Traffic-Counted: " + String.valueOf(Proxy.getTraffic()) + "\n").getBytes());
System.out.println("forwarding to client: " + new String(buf, position + 2, counter - (position + 2)));  
                    clientOut.write(buf, position + 2, counter - (position + 2));
                }else{

System.out.println("forwarding to client: " + new String(buf));
                    clientOut.write(buf, 0, counter);
                }
                
            }
            Proxy.addTraffic(traffic);
                
/*            while(!server.isClosed() && ((response = serverIn.readLine()) != null)){              
            
System.out.println("forwarding to client: " + response);
                
                // TODO: X-traffic-counted einfügen
                clientOut.println(response);
            }
*/            clientOut.flush();
		}catch (IOException e) {
            error("Unexpected error while receiving response", e);
            return;
		}
        
        
        // Alles schliessen und Schluss
        serverOut.close();

        try{
            clientIn.close();
            serverIn.close();
            clientOut.close();
            client.close();
            server.close();
        }catch(IOException e){
            
            error("Could not close all streams", e);
            return;
        }
System.out.println("thread closed");
	}
    
    private void error(String str, Exception e){
        
        System.err.println("ERROR: " + str + ": \n" + e.getMessage());
        e.printStackTrace();
        // Alles schliessen und Schluss
        serverOut.close();
        try{
            clientIn.close();
            serverIn.close();
            clientOut.close();
            client.close();
            server.close();
        }catch(IOException ex){
            
            error("Could not close all streams", ex);
            return;
        }
    }

}
