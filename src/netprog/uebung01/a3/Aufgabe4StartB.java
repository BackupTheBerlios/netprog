package netprog.uebung01.a3;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import netprog.RegistryManager;
import netprog.uebung01.a4.*;

public class Aufgabe4StartB
{
    public static void main(String[] args) throws RemoteException
    {
        Registry registry = RegistryManager.getLocalRegistry();

        ReplicatedString r = new ReplicatedStringImpl();
        registry.rebind("RepString2", r);
    }
}
