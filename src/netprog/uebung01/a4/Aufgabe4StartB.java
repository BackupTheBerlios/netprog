package netprog.uebung01.a4;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import netprog.RegistryManager;

public class Aufgabe4StartB
{
    public static void main(String[] args) throws RemoteException
    {
        Registry registry = RegistryManager.getLocalRegistry();

        ReplicatedString r = new ReplicatedStringImpl();
        registry.rebind("RepString2", r);
    }
}
