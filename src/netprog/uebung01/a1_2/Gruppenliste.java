package netprog.uebung01.a1_2;

import java.rmi.*;

public interface Gruppenliste
extends Remote
{
    public void loesche(int gruppenNummer)
    throws RemoteException;

    public void trageEin(int gruppenNummer, String name, String matrikel, String email)
    throws RemoteException;

    public String[] toString(int gruppenNummer)
    throws RemoteException;
}
