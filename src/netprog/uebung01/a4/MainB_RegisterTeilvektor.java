package netprog.uebung01.a4;

import java.rmi.*;

import netprog.RegistryManager;

public class MainB_RegisterTeilvektor
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Integer[] field = {new Integer(1), new Integer(2), new Integer(3)};

        RegistryManager.getLocalRegistry().bind("TeilVektor1", new TeilVektorImpl(field));

    }
}
