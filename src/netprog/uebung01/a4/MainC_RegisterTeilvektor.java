package netprog.uebung01.a4;

import java.rmi.*;

import netprog.uebung01.RMIManager;

public class MainC_RegisterTeilvektor
{
    /**
     * Registers a {@link TeilVektorImpl} with the name "TeilVektor2".
     * @param args isn't used
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Integer[] field = {new Integer(4), new Integer(5), new Integer(6)};

        RMIManager.getLocalRegistry().bind("TeilVektor2", new TeilVektorImpl(field));

    }
}
