package netprog.uebung01.a5;

import java.rmi.RemoteException;
import java.rmi.registry.*;

public class TestRegistry
{
    public static void main(String[] args)
    {
        String host = args[0];

        Registry registry = null;
        try
        {
            registry = LocateRegistry.getRegistry(host);
            System.out.println("Found registry with " + registry.list().length + " registered objects at " + host);
        }
        catch (RemoteException e)
        {
            System.out.println("No registry found at " + host);
        }

    }

}