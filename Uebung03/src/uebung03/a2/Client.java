package uebung03.a2;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * <p>Title: Netzprogrammierung - Übung 3</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class Client {

    /**
     * main - Method
     * @param args String[]
     */
    public static void main(String[] args) {
        try {
            String message = args[0] + "\n" + args[1];

            // zu diesem Rechner verbinden:
            Socket socket = new Socket("localhost", 10000);

            // Ströme vorbereiten
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());

            // Abschicken:
            out.println(message);

            // Antwort lesen und ausgeben
            System.out.println(in.readLine());

            // alles schliessen und Schluss.
            out.close();
            in.close();
            socket.close();

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
