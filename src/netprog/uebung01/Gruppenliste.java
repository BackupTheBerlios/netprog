package netprog.uebung01;

import java.rmi.Remote;

public interface Gruppenliste
extends Remote
{
    public void loesche(int gruppenNummer)
    throws java.rmi.RemoteException;

    public void trageEin(int gruppenNummer, String name, String matrikel, String email)
    throws java.rmi.RemoteException;

    public String[] toString(int gruppenNummer)
    throws java.rmi.RemoteException;
}
