package uebung03.a3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class DivServer
extends DatagramSocket
{
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Fields                       |   \\
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    
    private HashMap handlers = new HashMap();

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                   Constructors                    |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\
    
    public DivServer(int port)
    throws SocketException
    {
        super(port);
    }

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Methods                      |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\
    
    public void start() throws IOException
    {
        while (true)
        {
            byte[] inData = new byte[1024];
            byte[] outData = new byte[1024];

            //    --------|=|-----------|=| Read Incoming Package |=|-----------|=|--------    \\

            DatagramPacket in = new DatagramPacket(inData, inData.length);
            receive(in);

            //    --------|=|-----------|=| Identify Sender And Delegate To Corressponding Handler |=|-----------|=|--------    \\

            String clientID = in.getSocketAddress().toString();

            if (!handlers.containsKey(clientID))
                handlers.put(clientID, new DivHandler());

            DivHandler handler = (DivHandler) handlers.get(clientID);

            try
            {
                handler.setValue(new String(in.getData(), in.getOffset(), in.getLength()));
            }
            catch (NumberFormatException e)
            {
                System.err.println(e.toString());
            }

            //    --------|=|-----------|=| Return Possible Results |=|-----------|=|--------    \\

            // if there is a result
            if (handler.canGetResult())
            {
                try
                {
                    int result = handler.getResult();
                    outData = new String("" + result).getBytes();
                }
                catch (Exception e)
                {   // Division by Zero:
                    outData = "NAN".getBytes();
                }

                // Reset for next usage:
                handler.reset();

                // Send result:
                DatagramPacket out = new DatagramPacket(outData, outData.length, in.getSocketAddress());
                send(out);
            }
        }
    }
}
