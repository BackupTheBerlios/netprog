package uebung01.a2;

/**
 * Schnittstelle der Klasse auf dem Server, die die Gruppen registriert.
 */
public interface Gruppenliste extends java.rmi.Remote
{
    public void loesche(int gruppenNummer) throws java.rmi.RemoteException;

    public void trageEin(int gruppenNummer,
                         String name, String matrikel,
                         String email) throws java.rmi.RemoteException;

    public String[] toString(int gruppenNummer) throws java.rmi.RemoteException;
}
