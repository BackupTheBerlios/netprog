package netprog.uebung01.a3;

import java.rmi.*;

import netprog.RegistryManager;

public class MainC_ConnectAndTest
{
    /**
     * Connects ttwo registered {@link ReplicatedString} objects and runs tests.
     * The objects must be registered first by starting {@link MainA_RegisterRepString} and
     * {@link MainB_RegisterRepString}
     * @param args isn't used
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException, NotBoundException
    {
        ReplicatedString r1 = (ReplicatedString)RegistryManager.getLocalRegistry().lookup("RepString1");
        ReplicatedString r2 = (ReplicatedString)RegistryManager.getLocalRegistry().lookup("RepString2");

        r1.replicateAt(r2);

        r1.set("Hallo");

        System.out.println("Object 1: \"" + r1.get() + "\" - Object 2: \"" + r2.get() + "\"");

    }
}
