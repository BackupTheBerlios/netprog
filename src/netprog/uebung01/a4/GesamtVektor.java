package netprog.uebung01.a4;

import java.rmi.*;

public interface GesamtVektor extends Remote
{
    public void append(TeilVektor t) throws RemoteException;

    public void remove(TeilVektor t) throws RemoteException;

    public int size() throws RemoteException;
 
    public int sum() throws RemoteException;
}
