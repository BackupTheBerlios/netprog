package uebung03.a2;

import java.io.IOException;

public class StartServer
{

    public static void main(String[] args)
    {
        //    --------|=|-----------|=| Read Port |=|-----------|=|--------    \\

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

        //    --------|=|-----------|=| Start Server |=|-----------|=|--------    \\

        try
        {
            AdderServer adderServer = new AdderServer(port);
            adderServer.start();
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Initialisieren des Adder-Servers");
            e.printStackTrace();
            return;
        }

    }
}
