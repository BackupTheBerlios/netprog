package netprog;

import java.rmi.*;
import java.rmi.registry.*;

public class RegistryManager
{
    private static Registry localRegistry = null;

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
