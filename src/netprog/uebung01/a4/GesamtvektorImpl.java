package netprog.uebung01.a4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class GesamtVektorImpl
extends UnicastRemoteObject
implements GesamtVektor
{
    private Vector vectors = new Vector();
    private int sum = 0;
    private int size = 0;

    public GesamtVektorImpl()
    throws RemoteException
    {
    }

    public void append(TeilVektor t) throws RemoteException
    {
        if (vectors.contains(t)) return;

        vectors.add(t);

        sum += t.sum();
        size += t.size();
    }

    public void remove(TeilVektor t) throws RemoteException
    {
        if (!vectors.contains(t)) return;

        vectors.remove(t);

        sum -= t.sum();
        size -= t.size();
    }

    public int size() throws RemoteException
    {
        return size;
    }

    public int sum() throws RemoteException
    {
        return sum;
    }
}
