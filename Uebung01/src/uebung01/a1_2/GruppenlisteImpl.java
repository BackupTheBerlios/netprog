package uebung01.a1_2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import uebung01.RMIManager;

public class GruppenlisteImpl
extends UnicastRemoteObject
implements Gruppenliste
{
    //------------------------------------------------
    //  Klassenprozeduren:
    //------------------------------------------------

    /**
     * Installs an instance of {@link Gruppenliste} in the local {@link java.rmi.registry.Registry} with the name
     "liste".
     * @param args isn't used.
     * @throws RemoteException
     */
    public static void main(String[] args)
    throws RemoteException
    {
        Gruppenliste liste = new GruppenlisteImpl();

        RMIManager.getLocalRegistry().rebind("liste", liste);
    }

    //------------------------------------------------
    //  Instanzvariablen:
    //------------------------------------------------

    /**
     * Group Container
     */
    private HashMap groups = new HashMap();

    //------------------------------------------------
    //  Konstruktoren:
    //------------------------------------------------

    public GruppenlisteImpl()
    throws RemoteException
    {
    }

    //------------------------------------------------
    //  sondierende Methoden:
    //------------------------------------------------

    /**
     * @param gruppenNummer unique group id
     * @return a String representation of the given group's data
     * @throws RemoteException
     */
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

    //------------------------------------------------
    //  Prozeduren:
    //------------------------------------------------

    /**
     * Adds the student's given information to the group with the given id. If no such group exists, it is created.
     *
     * @param gruppenNummer unique group id
     * @param name name of the student
     * @param matrikel matrikelnummer of the student
     * @param email the student's emailadress
     * @throws RemoteException
     */
    public void trageEin(int gruppenNummer, String name, String matrikel, String email)
    throws RemoteException
    {
        GroupEntry entry = new GroupEntry(name, matrikel, email);

        if (!groups.containsKey(new Integer(gruppenNummer))) groups.put(new Integer(gruppenNummer), new Vector());

        Vector group = (Vector)groups.get(new Integer(gruppenNummer));

        group.add(entry);
    }

    /**
     * Removes all of the given group's data.
     *
     * @param nr unique group id
     */
    public void loesche(int nr)
    {
        groups.remove(new Integer(nr));
    }

    /************************************************
     |  Innere Klassen:
     *************************************************/

    /**
     * Representation of a single group entry.
     */
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

        /**
         * @return a String representation of this student's data.
         */
        public String toString()
        {
            return name + " (" + matrikel + ") - " + email;
        }
    }
}
