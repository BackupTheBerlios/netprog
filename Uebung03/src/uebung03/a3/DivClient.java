package uebung03.a3;

import java.net.*;
import java.io.IOException;

public class DivClient
extends DatagramSocket
{
    private final InetAddress addr;
    private final int port;

    public DivClient(InetAddress addr, int port)
    throws SocketException
    {
        this.addr = addr;
        this.port = port;
    }

    public void divide(int a, int b)
    {

        final int LENGTH = 100;
        byte[] inData = new byte[LENGTH];
        byte[] outData = null;
        DatagramPacket inPacket = null;
        DatagramPacket outPacket = null;
        String strMessage = null;

        String strErgebnis;

        //maximal dreimal versuchen (2 Pakete pro Versuch)
        outData = (a+"").getBytes();
        outPacket = new DatagramPacket(outData, outData.length, addr, port);
        try
        {
            send(outPacket);	//die erste Zahl schicken
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Senden eines Pakets an den Server.");
            e.printStackTrace();
        }

        outData = (":" + b).getBytes();
        outPacket = new DatagramPacket(outData, outData.length, addr, port);
        try
        {
            send(outPacket);	//die zweite Zahl schicken
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Senden eines Pakets an den Server.");
            e.printStackTrace();
        }

        inPacket = new DatagramPacket(inData, inData.length);
        try
        {
            receive(inPacket);
        }
        catch (SocketTimeoutException e)
        {
            System.out.println("Der Server hat innerhalb 3 sec. nicht geantwortet.");
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Empfangen eines Pakets.");
            e.printStackTrace();
        }

        //die Nachricht aus dem Paket holen
        strMessage = new String(inPacket.getData(), inPacket.getOffset(), inPacket.getLength());

        //ErgebnisNr und Ergebnis der Nachricht entnehmen
        //intErgebnisNr = Integer.parseInt(strMessage.substring(0, strMessage.indexOf(":")));
        strErgebnis = strMessage;//.substring(strMessage.indexOf(":") + 1);


        try
        {
            System.out.println("Ergebnis: "+a+" : "+b+" = "+Integer.parseInt(strErgebnis));
        }
        catch (NumberFormatException e)
        {
            System.out.println("Ergebnis: "+a+" : "+b+" = UNDEFINIERT");

        }
    }
}
