package netprog.uebung01.a3;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import netprog.uebung01.RMIManager;

public class MainA_RegisterRepString
{
    /**
     * Registers an instance of {@link ReplicatedStringImpl} with the name "RepString1".
     * @param args isn't used
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException
    {
        Registry registry = RMIManager.getLocalRegistry();

        ReplicatedStringImpl r = new ReplicatedStringImpl();

        registry.rebind("RepString1", r);

    }
}
