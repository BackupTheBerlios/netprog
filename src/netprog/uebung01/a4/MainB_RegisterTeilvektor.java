package netprog.uebung01.a4;

import java.rmi.*;

import netprog.RegistryManager;

public class MainB_RegisterTeilvektor
{
    /**
     * Registers a {@link TeilVektorImpl} with the name "TeilVektor1".
     * @param args isn't used
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Integer[] field = {new Integer(1), new Integer(2), new Integer(3)};

        RegistryManager.getLocalRegistry().bind("TeilVektor1", new TeilVektorImpl(field));

    }
}
