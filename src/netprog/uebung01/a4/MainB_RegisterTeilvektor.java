package netprog.uebung01.a4;

import java.rmi.*;

import netprog.RegistryManager;

/**
 * Created by IntelliJ IDEA.
 * User: Sebastian Koske
 * Date: 07.11.2003
 * Time: 16:54:24
 * To change this template use Options | File Templates.
 */
public class MainB_RegisterTeilvektor
{
    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Integer[] field = {new Integer(1), new Integer(2), new Integer(3)};

        RegistryManager.getLocalRegistry().bind("TeilVektor1", new TeilVektorImpl(field));

    }
}
