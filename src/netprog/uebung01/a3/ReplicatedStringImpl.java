package netprog.uebung01.a3;

import java.rmi.RemoteException;

public class ReplicatedStringImpl
extends java.rmi.server.UnicastRemoteObject
implements ReplicatedString
{
    /************************************************
     |  Instanzvariablen:
     *************************************************/

    private String s;
    private ReplicatedString replicate;

    /************************************************
     |  Konstruktoren:
     *************************************************/

    public ReplicatedStringImpl()
    throws RemoteException
    {
    }

    /************************************************
     |  sondierende Methoden:
     *************************************************/

    public String get()
    {
        return s;
    }

    /************************************************
     |  Prozeduren:
     *************************************************/

    public void set(String s)
    throws RemoteException
    {
        this.s = s;
        if (replicate != null) replicate.set(s);
    }

    public void replicateAt(ReplicatedString rs)
    {
        replicate = rs;
    }
}
