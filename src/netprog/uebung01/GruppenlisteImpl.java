package netprog.uebung01;

import java.rmi.*;
import java.util.*;

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

        Gruppenliste liste  = new GruppenlisteImpl();
        Gruppenliste liste2 = new GruppenlisteImpl();

        java.rmi.registry.LocateRegistry.createRegistry(1099).rebind("liste", liste);
        java.rmi.registry.LocateRegistry.getRegistry(1099).rebind("liste2", liste2);
    }

    /************************************************
     |  Instanzvariablen:
     *************************************************/

    private HashMap groups = new HashMap();

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
    {
        GroupEntry entry = new GroupEntry(name, matrikel, email);

        if (!groups.containsKey(new Integer(gruppenNummer))) groups.put(new Integer(gruppenNummer), new Vector());

        Vector group = (Vector)groups.get(new Integer(gruppenNummer));


        group.add(entry);
    }

    public void loesche(int nr)
    {
        groups.remove(new Integer(nr));
    }

    public String[] toString(int gruppenNummer)
    throws RemoteException
    {
        Vector group = (Vector)groups.get(new Integer(gruppenNummer));

        Iterator it = group.iterator();

        String[] result = new String[group.size()];

        int i = 0;

        while (it.hasNext())
            result[i++] = it.next().toString();

        return result;
    }

    /************************************************
     |  Innere Klassen:
     *************************************************/

    private class GroupEntry
    {
        private String name;
        private String matrikel;
        private String email;

        private GroupEntry(String name, String matrikel, String email)
        {
            this.name = name;
            this.matrikel = matrikel;
            this.email = email;
        }

        public String toString()
        {
            return name + " (" + matrikel + ") - " + email;
        }
    }
}
