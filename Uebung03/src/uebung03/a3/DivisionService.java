package uebung03.a3;


import java.io.*;
import java.net.*;

/**
 * <p>Title: Netzprogrammierung - Übung 3</p>
 * <p>Description: </p>
 * <p>(3 Punkte) Bitte bauen Sie einen Divisions-Dienst auf UDP-Basis.
 * Die Parameter sollen dabei in getrennten UDP Paketen übermittelt werden.
 * Schreiben Sie entsprechendes Server-Programm und einen Klienten zum Testen.
 * </p>
 * <p>Company: </p>
 * @author Sebastian S.
 * @version 1.0
 */

public class DivisionService {

    public DivisionService(int port) throws SocketException, IOException {

        // Platz für Pakete vorbereiten
        byte[] inData = new byte[1024];
        byte[] outData = new byte[1024];

        // Socket binden
        DatagramSocket socket = new DatagramSocket(port);

        while (true) {

            // Pakete empfangen
            DatagramPacket in = new DatagramPacket(inData, inData.length);
            String s, t;

            socket.receive(in);
            s = new String(in.getData(), 0, in.getLength());
            socket.receive(in);
            t = new String(in.getData(), 0, in.getLength());

            // Berechnen
            double result = Double.parseDouble(s) / Double.parseDouble(t);

            // Infos ermitteln
            InetAddress senderIP = in.getAddress();
            int senderPort = in.getPort();

            // Antwort erzeugen
            outData = (s + " / " + t + " = " + result).getBytes();

            DatagramPacket out =
                    new DatagramPacket(outData, outData.length, senderIP,
                                       senderPort);

            // Antwort senden
            socket.send(out);
        }

    }

    public static void main(String[] args) {
        try {
            new DivisionService(10000);
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
