package uebung03.a2;

import java.net.*;
import java.io.*;

public class StartClient
{
    public static void main(String[] args)
    {
        //    --------|=|-----------|=| Read IP And Port |=|-----------|=|--------    \\

        if (args.length < 4)
        {
            System.out.println("Bitte geben Sie eine IP- und eine Port-Nummer und zwei Ganzzahlen" +
                    " im folgenden Format an: IP port a1 a2");
            return;
        }

        int port;
        InetAddress addr;

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

        //    --------|=|-----------|=| Read Values |=|-----------|=|--------    \\

        int a = 0;
        int b = 0;

        try
        {
            a = Integer.parseInt(args[2]);
            b = Integer.parseInt(args[3]);
        }
        catch (NumberFormatException e)
        {
            System.err.println("Fehler beim Lesen der Summanden "+e.toString());
            return;
        }

        //    --------|=|-----------|=| Start Client |=|-----------|=|--------    \\

        try
        {
            AdderClient client = new AdderClient(addr.getHostAddress(), port);
            client.add(a,b);

            // further tests:
            System.out.println("\nWeitere Tests:\n");

            client.add(4,9);
            client.add(Integer.MAX_VALUE,1);
            client.close();
        }
        catch (IOException e)
        {
            System.err.println("Fehler bei der Kommunikation mit dem Server!");
        }
    }
}
