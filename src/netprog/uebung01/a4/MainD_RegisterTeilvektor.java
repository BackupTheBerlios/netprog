package netprog.uebung01.a4;

import java.rmi.*;

import netprog.RegistryManager;

/**
 * Created by IntelliJ IDEA.
 * User: Sebastian Koske
 * Date: 07.11.2003
 * Time: 16:54:48
 * To change this template use Options | File Templates.
 */
public class MainD_RegisterTeilvektor
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Integer[] field = {new Integer(7), new Integer(8), new Integer(9)};

        RegistryManager.getLocalRegistry().bind("TeilVektor3", new TeilVektorImpl(field));

    }
}
