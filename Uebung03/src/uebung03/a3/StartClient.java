package uebung03.a3;

import java.io.IOException;
import java.net.*;

/**
 * @author Gruppe 6
 *         <p/>
 *         Diese Klasse implementiert einen Client, der die Divisionsdienst eines Servers benutzt.<br>
 *         Die IP, die Portnummer und die beide Ganzzahlen werden über die Kommandozeile
 *         übergeben.<br>
 *         Der Client versucht maximal dreimal die Division durchzuführen.<br>
 *         Der Client wartet maximal 3 Sekunden auf Antwort.
 */
public class StartClient
{

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

        a = Integer.parseInt(args[2]);

        b = Integer.parseInt(args[3]);

        try
        {
            DivClient client = new DivClient(addr,port);
            client.setSoTimeout(10000);

            client.divide(a,b);

            // further tests:
            client.divide(10,5);
            client.divide(20,5);
            client.divide(30,3);
            client.divide(40,0);

            client.close();
        }
        catch (SocketException e)
        {
            System.err.println(e.toString());
        }
    }
}
