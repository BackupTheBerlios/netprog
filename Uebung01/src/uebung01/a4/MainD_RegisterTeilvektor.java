package uebung01.a4;

import java.rmi.*;

import uebung01.RMIManager;

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

        RMIManager.getLocalRegistry().bind("TeilVektor3", new TeilVektorImpl(field));

    }
}
