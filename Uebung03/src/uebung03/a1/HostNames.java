package uebung03.a1;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * (1 Punkt) Schreiben Sie ein Programm, das für ein Subnetz aa.bb.cc testet,
 * welche Internet-Geräte darin einen Namen haben. aa.bb.cc soll die Kommandozeilenparameter sein;
 * Sie sollen aa.bb.cc.0 bis aa.bb.cc.255 testen und entsprechende Ausgaben erzeugen.
 */
public class HostNames
{
    public static void main(String[] args)
    {
        if (args.length <= 0)
        {
            System.out.println("Bitte geben Sie den vorderen Teil einer IP-Nummer an (aaa.bbb.ccc) !");
            return;
        }

        // extract ip-adress parts:
        short[] ipParts = new short[3];

        try
        {
            String[] parts = args[0].replace('.', ':').split(":");

            if (parts.length != 3)
            {
                System.out.println("Das Format der übergebenen IP ist ungültig !");
                return;
            }

            for (int i = 0; i < 3; i++)
                ipParts[i] = Short.parseShort(parts[i]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Das Format der übergebenen IP ist ungültig !");
            return;
        }

        // start looking:
        for (short i = 0; i < 256; i++)
        {
            String ip = ipParts[0] + "." + ipParts[1] + "." + ipParts[2] + "." + i;

            try
            {   InetAddress addr = InetAddress.getByName(ip);
                String name = addr.getCanonicalHostName();

                System.out.println("IP " + ip + " - " +  (!name.toString().equals(ip) ? "is called: " + name : "has no name"));
            }
            catch (UnknownHostException e)
            {
                System.out.println("IP " + ip + " - not found");
            }
        }
    }
}
