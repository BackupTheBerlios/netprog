package uebung03.a3;

import java.io.IOException;
import java.net.*;

public class DivClient
extends DatagramSocket
{
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Fields                       |   \\
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    
    private final InetAddress addr;
    private final int port;
    
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                   Constructors                    |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\
    
    public DivClient(InetAddress addr, int port)
    throws SocketException
    {   // Preconditions:
        assert addr != null : "PRE 1: addr != null returned false @ DivClient.DivClient()";
        assert port >= 0 : "PRE 2: port >= 0 returned false @ DivClient.DivClient()";
        
        // Implementation:
        this.addr = addr;
        this.port = port;
    }

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Methods                      |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\
    
    public void divide(int a, int b)
    throws IOException
    {
        byte[] inData = new byte[1024];
        byte[] outData = new byte[1024];

        DatagramPacket inPacket;
        DatagramPacket outPacket;

        //    --------|=|-----------|=| Send Data |=|-----------|=|--------    \\

        try
        {
            outData = (a + "").getBytes();
            outPacket = new DatagramPacket(outData, outData.length, addr, port);

            send(outPacket);	//die erste Zahl schicken

            outData = (":" + b).getBytes();
            outPacket = new DatagramPacket(outData, outData.length, addr, port);

            send(outPacket);	//die zweite Zahl schicken
        }
        catch (IOException e)
        {
            System.err.println("Fehler beim Versenden der Zahlen: " + e.toString());
            throw e;
        }
        
        //    --------|=|-----------|=| Receive Result |=|-----------|=|--------    \\

        inPacket = new DatagramPacket(inData, inData.length);

        try
        {
            receive(inPacket);
            
            // Ergebnis lesen:
            String strErgebnis = new String(inPacket.getData(), inPacket.getOffset(), inPacket.getLength());
            
            // und ausgeben:
            System.out.println("Ergebnis: " + a + " : " + b + " = " + Integer.parseInt(strErgebnis));
        }
        catch (NumberFormatException e)
        {
            System.out.println("Ergebnis: " + a + " : " + b + " = UNDEFINIERT");
        }
        catch (SocketTimeoutException e)
        {
            System.out.println("Der Server hat innerhalb des TimeOut sec. nicht geantwortet.");
            throw e;
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Empfangen eines Pakets.");
            throw e;
        }
    }
}
