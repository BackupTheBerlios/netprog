package uebung03.a2;

import java.net.*;
import java.io.*;

public class StartClient
{
    public static void main(String[] args)
    {   if (args.length < 4)
        {
            System.out.println("Bitte geben Sie eine IP- und eine Port-Nummer und zwei Ganzzahlen" +
                    " im folgenden Format an: IP port a1 a2");
            return;
        }

        int port;
        InetAddress addr;
        Socket socket;

        BufferedReader in = null;
        PrintWriter out = null;

        try
        {
            addr = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
            socket = new Socket(addr.getHostAddress(), port);
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
        catch (IOException e)
        {
            System.err.println("Fehler beim Aufbauen einer Verbindung mit dem Server!");
            e.printStackTrace();
            return;
        }

        try
        {
            //Ströme vorbereiten
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            //Befehl schicken
            out.println(args[2]);
            out.println(args[3]);
            out.flush();//sendet den Inhalt des Puffers des Ausgabenstroms explizit

            System.out.println(in.readLine());	//Antwort ausgeben
        }
        catch (IOException e)
        {
            System.err.println("Fehler bei der Kommunikation mit dem Server!");
            e.printStackTrace();
            return;
        }
        finally
        {   out.close();

            try
            {
                in.close();
            }
            catch (IOException e)
            {
                System.err.println(e.toString());
            }

            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                System.err.println(e.toString());
            }

        }
    }
}
