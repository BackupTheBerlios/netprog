package uebung03.a3;

import uebung03.a2.*;

import java.io.IOException;
import java.net.*;

public class StartServer
{

    /**
     * Starts a Div-Server at the given port.
     * @param args the port where the server should be runnig at
     */
    public static void main(String[] args)
    {
        if (args.length <= 0)
        {
            System.out.println("Bitte geben einen Port an!");
            return;
        }

        int port;

        try
        {
            port = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Das Format der Portnummer ist ungültig !");
            return;
        }

        try
        {
            DivServer divServer = new DivServer(port);
            divServer.start();
        }
        catch (SocketException e)
        {
            System.out.println("Fehler beim Initialisieren des Divisions-Servers");
            e.printStackTrace();
        } catch (IOException e)
        {
            System.err.println(e.toString());
        }

    }
}
