package uebung03.a2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AdderServer
extends ServerSocket
{

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                   Constructors                    |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    public AdderServer(int port)
    throws IOException
    {
        super(port);
    }

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                Modifying Methods                  |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    /**
     * Listens to the given port.
     * Any connecting client is passed to an AdderHandler in a single Thread -
     * allowing multiple connections.
     */
    public void start()
    {
        while (true)
        {
            try
            {
                // detect client
                final Socket client = accept();

                // start a new Thread which performs the actual work (multithreaded server)
                new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            new AdderHandler(client);
                        }
                        catch (IOException e)
                        {
                            System.err.println(e.toString());
                        }
                    }

                }).start();
            }
            catch (IOException e)
            {
                System.err.println(e.toString());
            }

            // and continue listening for new Clients
        }


    }
}
