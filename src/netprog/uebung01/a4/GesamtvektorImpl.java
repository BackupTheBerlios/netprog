package netprog.uebung01.a4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class GesamtVektorImpl
extends UnicastRemoteObject
implements GesamtVektor
{
    //------------------------------------------------
    //  Instanzvariablen:
    //------------------------------------------------

    /**
     * stores instances of {@link TeilVektor}
     */
    private Vector vectors = new Vector();

    /**
     * caches the total sum information of all contained instances of {@link TeilVektor}
     */
    private int sum = 0;

    /**
     * caches the total size information of all contained instances of {@link TeilVektor}
     */
    private int size = 0;

    //------------------------------------------------
    //  Konstruktoren:
    //------------------------------------------------

    public GesamtVektorImpl()
    throws RemoteException
    {
    }

    //------------------------------------------------
    //  sondierende Methoden:
    //------------------------------------------------

    /**
     * @return the total size of all contained {@link TeilVektor}s.
     * @throws RemoteException
     */
    public int size() throws RemoteException
    {
        return size;
    }

    /**
     * @return the total sum of all contained {@link TeilVektor}s.
     * @throws RemoteException
     */
    public int sum() throws RemoteException
    {
        return sum;
    }

    //------------------------------------------------
    //  Prozeduren:
    //------------------------------------------------

    /**
     * Appends a {@link TeilVektor}. If it is already contained or null, nothing happens.
     Also size and sum informations are updated.
     * @param t the {@link TeilVektor} to be appended.
     * @throws RemoteException
     */
    public void append(TeilVektor t) throws RemoteException
    {
        if (t == null || vectors.contains(t)) return;

        vectors.add(t);

        sum += t.sum();
        size += t.size();
    }

    /**
     * Removes a {@link TeilVektor}. If it is already contained or null, nothing happens.
     Also size and sum informations are updated.
     * @param t the {@link TeilVektor} to be removed.
     * @throws RemoteException
     */
    public void remove(TeilVektor t) throws RemoteException
    {
        if (t == null || !vectors.contains(t)) return;

        vectors.remove(t);

        sum -= t.sum();
        size -= t.size();
    }
}
