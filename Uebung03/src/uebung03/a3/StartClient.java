package uebung03.a3;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class StartClient
{

    /**
     * Starts a Div-Client wicht connects to the given host and port.
     * Then performs a division with the given arguments.
     * 
     * @param args 
     */
    public static void main(String args[])
    {
        if (args.length < 4)
        {
            System.out.println("Bitte geben Sie eine IP- und eine Port-Nummer und zwei Ganzzahlen" +
            " im folgenden Format an: IP port a1 a2");

            return;
        }

        int port = 0;
        InetAddress addr = null;

        try
        {
            addr = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        }
        catch (UnknownHostException e)
        {
            System.out.println("Das Format der IP-Nummer ist ungültig !");
            return;
        }
        catch (NumberFormatException e)
        {
            System.out.println("Das Format der Portnummer ist ungültig !");
            return;
        }

        int a;
        int b;

        try
        {
            a = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e)
        {
            System.err.println("Divident hat falsches Format");
            return;
        }

        try
        {
            b = Integer.parseInt(args[3]);
        }
        catch (NumberFormatException e)
        {
            System.err.println("Divisor hat falsches Format");
            return;
        }

        try
        {
            DivClient client = new DivClient(addr, port);
            client.setSoTimeout(10000);

            client.divide(a, b);

            // further tests:
            client.divide(10, 5);
            client.divide(20, 5);
            client.divide(30, 3);
            client.divide(40, 0);

            client.close();
        }
        catch (SocketException e)
        {
            System.err.println(e.toString());
        }
    }
}
