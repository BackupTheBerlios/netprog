package uebung05.a2;

import java.io.InputStream;
import java.net.*;
import java.util.*;
import java.io.IOException;


/**
 * NETZPROGRAMMIERUNG ÜBUNG 5 - AUFGABE 2
 * GRUPPE 6
 *
 */
public class Start {

    public static void main(String[] args) {

        try {
            URL page = new URL("http://www.flughafen.co.at/airportcodes/airport.htm");

            URLConnection connection = page.openConnection();
            connection.connect();

            if (connection.getContentType().startsWith("text/html")) {

                InputStream is = connection.getInputStream();

                AirportParser hp = new AirportParser();
                HashMap airports = hp.parse(is);

                // Daten ausgeben
                Set keySet = airports.keySet();
                Iterator iter = keySet.iterator();

                System.out.println("CODE\tORT");
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    System.out.println(key + "\t" + airports.get(key));
                }

            } else {
                System.out.println("Kein HTML Inhalt");
            }
        } catch (IOException e) {
            System.err.println("Fehler bei der Verbindung");
        }
    }
}
