package netprog.uebung01.a5;

import java.rmi.RemoteException;
import java.rmi.registry.*;

public class TestRegistry
{
    /**
     * Checks the given host for a running registry.
     * Prints the number of registered objects if a registry is found.
     * Otherwise prints that there is no running registry.
     * @param args the host to be checked for a running registry.
     */
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