package netprog.uebung01.a4;

import java.rmi.*;

import netprog.RegistryManager;

public class MainC_RegisterTeilvektor
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Integer[] field = {new Integer(4), new Integer(5), new Integer(6)};

        RegistryManager.getLocalRegistry().bind("TeilVektor2", new TeilVektorImpl(field));

    }
}
