package netprog.uebung01.a4;

import java.rmi.*;

import netprog.RegistryManager;

public class MainD_RegisterTeilvektor
{
    /**
     * Registers a {@link TeilVektorImpl} with the name "TeilVektor3".
     * @param args isn't used
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Integer[] field = {new Integer(7), new Integer(8), new Integer(9)};

        RegistryManager.getLocalRegistry().bind("TeilVektor3", new TeilVektorImpl(field));

    }
}
