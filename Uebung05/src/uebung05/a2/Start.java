package uebung05.a2;

import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.util.Set;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: sebastian
 * Date: Jan 23, 2004
 * Time: 9:47:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class Start {

    public static void main(String[] args) {

        try {
            URL page = new URL("http://www.flughafen.co.at/airportcodes/airport.htm");

            URLConnection connection = page.openConnection();
            connection.connect();
            if (connection.getContentType().startsWith("text/html")) {

            //File f = new File("e:/airport.htm");


                InputStream is = connection.getInputStream(); //new FileInputStream(f);

                HTMLParser hp = new HTMLParser();
                AirportCodes d = hp.parse(is);


                // damit spielen
                Set keySet = d.getCodes();
                Iterator iter = keySet.iterator();

                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    String name = (String) d.get(key);
                    System.out.println(key + " : " + name);
                }
            } else System.out.println(connection.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
