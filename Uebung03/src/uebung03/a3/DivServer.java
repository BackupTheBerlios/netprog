package uebung03.a3;

import java.net.*;
import java.io.*;
import java.util.*;

public class DivServer
extends DatagramSocket
{
    private HashMap handlers = new HashMap();

    public DivServer(int port)
    throws SocketException
    {   super(port);
    }

    public void start() throws IOException
    {
        byte[] inData = new byte[1024];
        byte[] outData = new byte[1024];

        while (true)
        {   DatagramPacket in = new DatagramPacket(inData,inData.length);

            receive(in);

            String clientID = in.getSocketAddress().toString();

            if (!handlers.containsKey(clientID))
                handlers.put(clientID,new DivHandler());

            DivHandler handler = (DivHandler) handlers.get(clientID);

            try
            {
                handler.setValue(new String(in.getData(), in.getOffset(), in.getLength()));
            }
            catch (NumberFormatException e)
            {
                System.err.println(e.toString());
            }

            if (handler.canGetResult())
            {
                try
                {
                    int result = handler.getResult();
                    outData = new String(""+result).getBytes();
                }
                catch (Exception e)
                {
                    outData = "NAN".getBytes();
                }
                
                handler.reset();

                DatagramPacket out = new DatagramPacket(outData,outData.length,in.getSocketAddress());
                send(out);
            }
        }
    }
}
