package uebung03.a3;

import java.io.*;
import java.net.*;

/**
 * <p>Title: Netzprogrammierung - Übung 3</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Sebastian S.
 * @version 1.0
 */

public class Client {

    public static void main(String[] args) {

        int port = 10000;

        try {
            // Platz für Pakete vorbereiten
            byte[] inData = new byte[1024];
            byte[] param1 = new byte[1024];
            byte[] param2 = new byte[1024];

            // Socket erzeugen
            DatagramSocket socket = new DatagramSocket();

            // Paket bauen, adressieren und senden
            InetAddress serverIP = InetAddress.getByName("localhost");
            param1 = args[0].getBytes();
            param2 = args[1].getBytes();

            DatagramPacket out;
            out = new DatagramPacket(param1, param1.length, serverIP, port);
            socket.send(out);
            out = new DatagramPacket(param2, param2.length, serverIP, port);
            socket.send(out);

            // Antwort empfangen und ausgeben.
            DatagramPacket in = new DatagramPacket(inData, inData.length);
            socket.receive(in);

            String message = new String(in.getData(), 0, in.getLength());
            System.out.println(message);

            // Socket schliessen
            socket.close();

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
