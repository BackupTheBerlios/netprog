package netprog.uebung01.a4;

import java.rmi.*;

import netprog.RegistryManager;

public class MainA_RegisterGesamtVektor
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        RegistryManager.getLocalRegistry().bind("GesamtVektor", new GesamtVektorImpl());

    }
}
