package netprog.uebung01.a3;

import java.rmi.*;

import netprog.RegistryManager;

public class MainC_ConnectAndTest
{
    public static void main(String[] args) throws RemoteException, NotBoundException
    {
        ReplicatedString r1 = (ReplicatedString)RegistryManager.getLocalRegistry().lookup("RepString1");
        ReplicatedString r2 = (ReplicatedString)RegistryManager.getLocalRegistry().lookup("RepString2");

        r1.replicateAt(r2);

        r1.set("Hallo");

        System.out.println("Object 1: \"" + r1.get() + "\" - Object 2: \"" + r2.get() + "\"");

    }
}
