package uebung01.a3;

import java.rmi.*;

public interface ReplicatedString
extends Remote
{
    public void set(String s) throws RemoteException;

    public String get() throws RemoteException;

    public void replicateAt(ReplicatedString rs) throws RemoteException;
}
