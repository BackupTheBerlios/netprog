package netprog.uebung01.a3;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import netprog.RegistryManager;

public class MainA_RegisterRepString
{
    public static void main(String[] args) throws RemoteException
    {
        Registry registry = RegistryManager.getLocalRegistry();

        ReplicatedStringImpl r = new ReplicatedStringImpl();

        registry.rebind("RepString1", r);

    }
}
