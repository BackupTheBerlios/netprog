package uebung03.a4;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *  
 * Start des Proxys. Es wird ein ServerSocket an dem per Kommandozeilenparameter übergebenen Port bereitgestellt.
 * Einzelne Verbindungen werden parallel durch ein ProxyThread-Objekt bearbeitet.
 * @author Gruppe 6
 */
public class Proxy {
    
    // Fehlermeldung bei falschen Parametern
    private static final String ERROR_MSG = "Usage: java uebung03.a4.Proxy <portnumber>";
    // ServerSocket für den Proxy 
    private ServerSocket listenSocket; 
    // bisheriger Traffic
    private static long trafficCounter = 0;
    
    /**
     * @return bisheriger Traffic
     */
    protected static synchronized long getTraffic() {
    
        return trafficCounter;
    }

    /**
     * Traffic aufaddieren
     * @param traffic zusätlicher Traffic
     */    
    protected static synchronized void addTraffic(long traffic) {
        
        trafficCounter += traffic;
    }
    
    /**
     * Konstruktor
     * Stellt den ServerSocket bereit und delegiert eingehende Anfragen an neue Threads.
     * @param port Proxy läuft an diesem Port
     */
    private Proxy (int port) {

        try{    
            
            // Socket reservieren:
            listenSocket = new ServerSocket(port);
            System.out.println("Proxy started on port " + port);
            
            while (true) {
        
                // auf Verbindungswunsch warten:
                new Thread(new ProxyThread(listenSocket.accept())).start();
            }
        } catch(IOException e) {
            
            System.err.println("ERROR: Unexpected IOException: " + e.getMessage());
            e.printStackTrace();
            try {

                listenSocket.close();
            } catch(IOException e2) {
                
                System.err.println("\n\nERROR: Could not close Socket: " + e2.getMessage());
            }
        }
    }

    /**
     * Start des Proxys und Prüfen der Parameter
     */
	public static void main (String[] args) {
        
        if (!(args.length == 1)) {
            
            System.out.println(ERROR_MSG);
            return;
        }

        int port;
        try {
            
            port = Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            
            System.out.println(ERROR_MSG);
            return;
        }

        new Proxy(port);
	}
}