package uebung01.a3;

import java.rmi.*;

import uebung01.RMIManager;

public class MainC_ConnectAndTest
{
    /**
     * Connects two registered {@link ReplicatedString} objects and runs tests.
     * The objects must be registered first by starting {@link MainA_RegisterRepString} and
     * {@link MainB_RegisterRepString}
     * @param args isn't used
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException, NotBoundException
    {
        ReplicatedString r1 = (ReplicatedString)RMIManager.getLocalRegistry().lookup("RepString1");
        ReplicatedString r2 = (ReplicatedString)RMIManager.getLocalRegistry().lookup("RepString2");

        r1.replicateAt(r2);

        r1.set("Hallo");

        System.out.println("Object 1: \"" + r1.get() + "\" - Object 2: \"" + r2.get() + "\"");

        // Test ring behavior:
        r2.replicateAt(r1);

        r1.set("Welt");

        System.out.println("Object 1: \"" + r1.get() + "\" - Object 2: \"" + r2.get() + "\"");

    }
}
