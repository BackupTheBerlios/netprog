package netprog.uebung01;

import java.rmi.registry.Registry;
import java.rmi.RemoteException;

public class TestRegistry
{
    public TestRegistry()
    {
    }

	public static void main(String[] args)
    {
        String host = args[0];

        Registry registry = null;
        try
        {
            registry = java.rmi.registry.LocateRegistry.getRegistry(host);
            System.out.println("Found registry with "+registry.list().length+" registered objects at "+host);
        }
        catch (RemoteException e)
        {
            System.out.println("No registry found at "+host);
        }

    }

}