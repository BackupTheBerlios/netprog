package netprog.uebung01.a4;

import java.rmi.*;

import netprog.RegistryManager;

/**
 * Created by IntelliJ IDEA.
 * User: Sebastian Koske
 * Date: 07.11.2003
 * Time: 16:54:39
 * To change this template use Options | File Templates.
 */
public class MainC_RegisterTeilvektor
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Integer[] field = {new Integer(4), new Integer(5), new Integer(6)};

        RegistryManager.getLocalRegistry().bind("TeilVektor2", new TeilVektorImpl(field));

    }
}
