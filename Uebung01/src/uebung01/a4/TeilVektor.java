package uebung01.a4;

import java.rmi.*;

public interface TeilVektor
extends Remote
{
    public int size() throws RemoteException;

    public int sum() throws RemoteException;
}
