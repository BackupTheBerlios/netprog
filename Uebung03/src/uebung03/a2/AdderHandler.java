package uebung03.a2;

import java.io.*;
import java.net.*;

/**
 * Handles the actual adding work
 */
public class AdderHandler
{
    public AdderHandler(Socket clientSocket)
    throws IOException
    {
        if (clientSocket == null) return;

        // initialize streams:
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        
        // read numbers and write result:
        try
        {
            int a1 = Integer.parseInt(in.readLine());
            int a2 = Integer.parseInt(in.readLine());

            out.println("" + (a1 + a2));
        }
        catch (NumberFormatException e)
        {
            out.println(e.getMessage());
        }
        catch (NullPointerException e)
        {
            out.println(e.getMessage());
        }
        catch (Exception e)
        {
            out.println(e.getMessage());
        }
        finally
        {
            out.flush();
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}



