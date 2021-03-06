package uebung03.a2;

import java.io.*;
import java.net.*;

/**
 * Handles the actual adding work
 */
public class AdderHandler
{
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                   Constructors                    |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    public AdderHandler(Socket clientSocket)
    throws IOException
    {
        if (clientSocket == null) return;

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

        while (true)
        {
            // read numbers and write result:
            try
            {
                String readLine = in.readLine();
                if (readLine.equals("CLOSE")) break;
                int a1 = Integer.parseInt(readLine);

                readLine = in.readLine();
                if (readLine.equals("CLOSE")) break;
                int a2 = Integer.parseInt(readLine);

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
            }
        }
    }
}



