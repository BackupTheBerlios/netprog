package uebung03.a2;


/**
 * <p>Title: Netzprogrammierung - �bung 3</p>
 * <p>Description:
 * (3 Punkte) Bitte bauen Sie einen kleinen TCP-Basierten Addier-Dienst.
 * Der Server soll auf einem Port auf Verbindungen warten.
 * Er erh�lt dann zwei Ganzzahlen in Textnotation in jeweils einer Zeile
 * �bermittelt. Er sendet die Summe dieser Beiden Zahlen in Textnotation in einer
 * Zeile zur�ck und baut dann die Verbindung ab. Schreiben Sie ein entsprechendes
 * Server Programm und einen Klienten zum Testen.
 * </p>
 * @author Sebastian S.
 * @version 1.0
 */

import java.io.*;
import java.net.*;

public class AdderService {

    /**
     *
     * @param port int Portnummer
     * @throws IOException
     */
    public AdderService(int port) throws IOException {

        // Socket reservieren:
        ServerSocket listenSocket = new ServerSocket(port);

        while (true) {
            // auf Verbindungswunsch warten:
            Socket commSocket = listenSocket.accept();

            // Str�me vorbereiten:
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    commSocket.getInputStream()));
            PrintStream out = new PrintStream(commSocket.getOutputStream());

            // Zeilen auswerten:
            try {
                int i = Integer.parseInt(in.readLine());
                int j = Integer.parseInt(in.readLine());

                // Ergebnis �bermitteln:
                out.println(i + " + " + j + " = " + (i + j));

            } catch (NumberFormatException ex) {
                out.println("fehlerhafte Eingabe");
            }

            // Alles schliessen und Schluss
            out.close();
            in.close();
            commSocket.close();
        }
    }

    public static void main(String[] args) {
        try {
            new AdderService(10000);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
