/*
 * Created on 05.01.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package uebung03.a4;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Rafael & Mike
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Proxy {
    
    private static final String ERROR_MSG = "Usage: java uebung03.a4.Proxy <portnumber>"; 
    private ServerSocket listenSocket; 
    private static long trafficCounter = 0;
    
    protected static synchronized long getTraffic(){
    
        return trafficCounter;
    }
    
    protected static synchronized void addTraffic(long traffic){
        
        trafficCounter += traffic;
    }
    
    private Proxy (int port) {

        try{    
            
            // Socket reservieren:
            listenSocket = new ServerSocket(port);
            System.out.println("Proxy started on port " + port);
            while (true) {
        
                // auf Verbindungswunsch warten:
                new Thread(new ProxyThread(listenSocket.accept())).start();

            }
        }catch(IOException e){
            
            System.err.println("ERROR: Unexpected IOException: " + e.getMessage());
            e.printStackTrace();
            try{
                
                listenSocket.close();
            }catch(IOException e2){
                
                System.err.println("\n\nERROR: Could not close Socket: " + e2.getMessage());
            }
        }
        
    }

    /**
     * start des servers und fehlerbehandlung
     * 
     * @param args
     */
	public static void main (String[] args) {
        
        if (!(args.length == 1)){
            
            System.out.println(ERROR_MSG);
            return;
        }
        int port;
        try{
            
            port = Integer.parseInt(args[0]);
        }catch(NumberFormatException e) {
            
            System.out.println(ERROR_MSG);
            return;
        }
        
        new Proxy(port);

        return;
	}
}
