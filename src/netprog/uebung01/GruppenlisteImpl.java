package netprog.uebung01;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;

public class GruppenlisteImpl
extends java.rmi.server.UnicastRemoteObject
implements Gruppenliste
{   public void loesche(int nr)
{

}

    public GruppenlisteImpl()
    throws RemoteException
    {

    }

    public void trageEin(int gruppenNummer, String name, String matrikel, String email)
    throws RemoteException
    {	System.out.println(name);
    }

    public String[] toString(int gruppenNummer)
    throws RemoteException
    {
        return new String[0];
    }

    public static void main(String[] args)
    throws RemoteException, AlreadyBoundException
        , RemoteException, AlreadyBoundException
        , MalformedURLException, RemoteException
    {   System.setSecurityManager(new RMISecurityManager());

        Registry r = java.rmi.registry.LocateRegistry.getRegistry();
        GruppenlisteImpl i = new GruppenlisteImpl();
		
        r.bind("rmi://localhost/liste",i);
    }
}
