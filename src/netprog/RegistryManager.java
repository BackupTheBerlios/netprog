package netprog;

import java.rmi.*;
import java.rmi.registry.*;

public class RegistryManager
{
    //------------------------------------------------
    //  Klassenvariablen:
    //------------------------------------------------

    private static Registry localRegistry = null;

    //------------------------------------------------
    //  sondierende Klassenmethoden:
    //------------------------------------------------

    /**
     * Initializes the {@link java.rmi.RMISecurityManager}, checks for a running {@link java.rmi.registry.Registry}
     and returns it. If no {@link java.rmi.registry.Registry} is currently running, creates one and return it.
     * @return a {@link java.rmi.registry.Registry} running on localhost at port 1099.
     * @throws RemoteException
     */
    public static Registry getLocalRegistry()
    throws RemoteException
    {
        if (localRegistry == null)
        {
            System.setSecurityManager(new RMISecurityManager());

            try
            {
                localRegistry = LocateRegistry.createRegistry(1099);
            }
            catch (Exception e)
            {
                localRegistry = LocateRegistry.getRegistry(1099);
            }
        }

        return localRegistry;
    }
}
