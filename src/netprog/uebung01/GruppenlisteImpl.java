package netprog.uebung01;

import java.rmi.*;

public class GruppenlisteImpl
extends java.rmi.server.UnicastRemoteObject
implements Gruppenliste
{
    /************************************************
     |  Klassenprozeduren:
     *************************************************/

    public static void main(String[] args)
    throws RemoteException, RemoteException, RemoteException
    {
        System.setSecurityManager(new RMISecurityManager());

        GruppenlisteImpl liste = new GruppenlisteImpl();
        java.rmi.registry.LocateRegistry.createRegistry(1099).rebind("liste", liste);
    }

    /************************************************
     |  Konstruktoren:
     *************************************************/

    public GruppenlisteImpl()
    throws RemoteException
    {
    }

    /************************************************
     |    Prozeduren:
     *************************************************/

    public void trageEin(int gruppenNummer, String name, String matrikel, String email)
    throws RemoteException
    {   //todo: implement this method;

        // Dummy - Implementation:
        System.out.println(name);
    }

    public void loesche(int nr)
    {   //todo: implement this method;
    }

    public String[] toString(int gruppenNummer)
    throws RemoteException
    {   //todo: implement this method;

        // Dummy - Implementation:
        return new String[0];
    }
}
