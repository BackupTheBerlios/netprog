package netprog.uebung01.a3;

import java.rmi.RemoteException;

import netprog.uebung01.a3.ReplicatedString;

public class ReplicatedStringImpl
extends java.rmi.server.UnicastRemoteObject
implements ReplicatedString
{
    private String s;
    private ReplicatedString replicate;

    public ReplicatedStringImpl()
    throws RemoteException
    {
    }

    public void set(String s)
    throws RemoteException
    {
        this.s = s;
        if (replicate != null) replicate.set(s);
    }

    public String get()
    {
        return s;
    }

    public void replicateAt(ReplicatedString rs)
    {
        replicate = rs;
    }
}
