package uebung03.a3;

import java.io.*;
import java.net.*;


/**
 * <p>Title: Netzprogrammierung - Übung 3</p>
 * <p>Description:
 * um sicherzustellen dass beide Argumente in der richtig übermittelt werden
 * 1. Client sendet das erste Argument an den Server
 * 2. Server sendet eine Empfangsbestätigung an den Client
 * 3. Client sendet das zweite Argument an den Server
 * 4. Server berechnet Ergebnis und sendet es an den Client
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Sebastian S.
 * @version 1.0
 */

public class Client {

    static final int RETRIES = 3;
    static final int TIMEOUT = 5000;

    private byte[] inData;
    private byte[] param1;
    private byte[] param2;

    private DatagramSocket socket;
    private InetAddress serverIP;
    private DatagramPacket out;
    private DatagramPacket in;

    private int port;

    /**
     *
     * @param host String
     * @param port int
     * @param arg1 String
     * @param arg2 String
     */
    public Client(String host, int port, String arg1, String arg2) {

        // Platz für Pakete vorbereiten
        inData = new byte[1024];
        param1 = new byte[1024];
        param2 = new byte[1024];

        // Socket erzeugen
        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        // Paket bauen, adressieren und senden
        try {
            serverIP = InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            System.out.println("unknown host: " + host);
            System.exit(1);
        }

        // Argumente markieren
        param1 = ("NUM:" + arg1).getBytes(); // numerator
        param2 = ("DEN:" + arg2).getBytes(); // denominator

        this.port = port;

        // Erstes Argument senden
        out = new DatagramPacket(param1, param1.length, serverIP, port);
        if (!send(out)) {
            System.out.println("Server not reachable");
            System.exit(1);
        }

        System.out.println("SEND ARG1");

        // auf Empfangsbestätigung vom Server warten
        DatagramPacket in = new DatagramPacket(inData, inData.length);
        try {
            socket.setSoTimeout(TIMEOUT);
            socket.receive(in);
        } catch (IOException ex) {
            System.out.println("...");
            System.exit(1);
        }

        String t = new String(in.getData(), 0, in.getLength());
        System.out.println("RECEIVED CONFIRMATION ARG1: '" + t + "'");

        // prüfen !?!
        if ((new String(param1, 0, param1.length).equals(t))) {
            System.out.println(" . . . ");
            return;
        }

        // Zweites Argument senden
        out = new DatagramPacket(param2, param2.length, serverIP, port);
        if (!send(out)) {
            return;
        }

        System.out.println("SEND ARG2");

        // Ergebnis empfangen und ausgeben.
        in = new DatagramPacket(inData, inData.length);
        try {
            socket.setSoTimeout(TIMEOUT);
            socket.receive(in);
        } catch (IOException ex) {
            System.out.println("...");
            System.exit(1);
        }

        String message = new String(in.getData(), 0, in.getLength());
        System.out.println(message);

        // Socket schliessen
        socket.close();
    }

    /**
     *
     * @param out DatagramPacket zu sendendes Paket
     * @return boolean
     */
    private boolean send(DatagramPacket out) {
        boolean done = false;
        int i = 0;

        do {
            try {
                socket.send(out);
                done = true;
            } catch (IOException ex) {
                i++;
            }
        } while (!done || i >= RETRIES);

        return done;
    }


    public static void main(String[] args) {

        int port = 0;

        if (args.length < 4) {
            System.out.println(
                "Usage: java uebung03.a3.Client <host> <port> <arg1> <arg2>"
            );
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            System.out.println(
                "Usage: java uebung03.a3.Client <host> <port> <arg1> <arg2>"
            );
            return;
        }

        if (port < 0 || port > 65535)
            System.out.println("<port> out of range");
        else
            new Client(args[0], port, args[2], args[3]);
    }
}
