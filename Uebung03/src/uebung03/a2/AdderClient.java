package uebung03.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AdderClient
extends Socket
{
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Fields                       |   \\
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

    private final BufferedReader in = new BufferedReader(new InputStreamReader(getInputStream()));
    private final PrintWriter out = new PrintWriter(getOutputStream());

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                   Constructors                    |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    public AdderClient(String hostAddress, int port) throws IOException
    {
        super(hostAddress, port);

        setSoTimeout(5000);
    }

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Methods                      |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    public void add(int a, int b)
    throws IOException
    {
        //Befehl schicken
        out.println(a);
        out.println(b);

        //sendet den Inhalt des Puffers des Ausgabenstroms explizit:
        out.flush();

        try
        {
            int result = Integer.parseInt(in.readLine());
            System.out.println("Ergebnis: " + a + " + " + b + " = " + result);	//Antwort ausgeben
        }
        catch (NumberFormatException e)
        {
            System.out.println("Ergebnis: " + a + " + " + b + " = UNDEFINIERT (" + e.toString() + ")");	//Antwort ausgeben
        }

    }

    public synchronized void close()
    throws IOException
    {
        out.println("CLOSE");
        out.flush();

        super.close();
        
        in.close();
        out.close();
    }
}
