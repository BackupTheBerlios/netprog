package uebung03.a3;


import java.io.*;
import java.net.*;


/**
 * <p>Title: Netzprogrammierung - �bung 3</p>
 * <p>Description:
 * 1. Client sendet das erste Argument an den Server
 * 2. Server sendet eine Empfangsbest�tigung an den Client
 * 3. Client sendet das zweite Argument an den Server
 * 4. Server berechnet Ergebnis und sendet es an den Client
 * </p>
 *
 * @author Sebastian S.
 * @version 1.0
 */


public class DivisionService {

    private DatagramSocket socket;
    private DatagramPacket in;

    static final int RETRIES = 3;
    static final int TIMEOUT = 300;

    public DivisionService(int port) {

        // Socket binden
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException ex1) {
            ex1.printStackTrace();
            return;
        }

        // Platz f�r Pakete vorbereiten
        in = new DatagramPacket(new byte[1024], 1024);

        division();
    }


    /**
     * sendet Daten zum Client
     *
     * @param outData byte[]
     * @param senderIP InetAddress
     * @param senderPort int
     * @return boolean
     */
    private boolean send(byte[] outData, InetAddress senderIP, int senderPort) {
        boolean done = false;
        int i = 0;
        DatagramPacket out =
            new DatagramPacket(outData, outData.length, senderIP, senderPort);
        do {
            try {
                socket.send(out);
                done = true;
            } catch (IOException ex) {
                i++;
            }
        } while (done || i >= RETRIES);
        return done;
    }


    private void division() {
        while (true) {
            String numerator = "", denominator = "";

            // Erstes Argument empfangen
            // falls es schief geht, zur�ck zum Ausgangszustand
            try {
                socket.setSoTimeout(0);
                socket.receive(in);
                numerator = new String(in.getData(), 0, in.getLength());
            } catch (IOException ex) {
                continue;
            }

            // Erstes Argument �berpr�fen
            // das erste Argument ist mit "NUM:" gekennzeichnet
            if (!numerator.startsWith("NUM:")) continue;

            // Infos ermitteln
            InetAddress senderIP = in.getAddress();
            int senderPort = in.getPort();

            // Best�tigung senden
            // als Best�tigung wird das gerade erhaltene Paket zur�ckgeschickt  !?!
            // falls es schief geht, zur�ck zum Ausgangszustand
            if (!send(in.getData(), senderIP, senderPort)) continue;

            // Zweites Argument empfangen
            // falls der Client nicht antwortet
            try {
                socket.setSoTimeout(TIMEOUT);
                socket.receive(in);
                denominator = new String(in.getData(), 0, in.getLength());
            } catch (IOException ex) {
                ex.printStackTrace();
                continue;
            }

            // Zweites Argument �berpr�fen
            // das zweite Argument ist vom Client mit "DEN:" gekennzeichnet
            if (!denominator.startsWith("DEN:")) continue;

            // Ergebnis berechnen und Antwort erzeugen
            byte[] outData = calculate(numerator, denominator);

            // Ergebnis senden
            send(outData, senderIP, senderPort);
        }
    }


    /**
     * �berpr�ft Argumente auf korrektes Format,
     * berechnet Ergebnis oder gibt Fehlermeldung zur�ck
     *
     * @param s String Z�hler
     * @param t String Nenner
     * @return byte[] Ergebnis
     */
    private byte[] calculate(String s, String t) {
        double arg1, arg2;
        try {
            arg1 = Double.parseDouble(s.substring(3));
        } catch (NumberFormatException ex) {
            return "error: <arg1> is not a number".getBytes();
        }
        try {
            arg2 = Double.parseDouble(t.substring(3));
            if (arg2 == 0) return "error: division by zero".getBytes();
        } catch (NumberFormatException ex) {
            return "error: <arg2> is not a number".getBytes();
        }
        return (arg1 + " / " + arg2 + " = " + (arg1 / arg2)).getBytes();
    }


    /**
     * main method
     * �berpr�ft, ob port korrekt eingegeben worden ist
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            System.out.println("Usage: java uebung03.a3.DivisionService <port>");
            System.exit(1);
        }

        if (port >= 0 && port <= 65535) {
            new DivisionService(port);
        } else {
            System.out.println("<port> out of range");
        }
    }

}
