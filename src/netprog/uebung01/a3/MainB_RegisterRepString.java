package netprog.uebung01.a3;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import netprog.uebung01.RegistryManager;

public class MainB_RegisterRepString
{
    /**
     * Registers an instance of {@link ReplicatedStringImpl} with the name "RepString2".
     * @param args isn't used
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException
    {
        Registry registry = RegistryManager.getLocalRegistry();

        ReplicatedString r = new ReplicatedStringImpl();
        registry.rebind("RepString2", r);
    }
}
