package netprog.uebung01.a4;

import java.rmi.*;

import netprog.uebung01.RMIManager;

public class MainA_RegisterGesamtVektor
{
    /**
     * Registers a {@link GesamtVektorImpl} with the name "GesamtVektor".
     * @param args isn't used
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        RMIManager.getLocalRegistry().bind("GesamtVektor", new GesamtVektorImpl());

    }
}
