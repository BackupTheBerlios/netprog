package netprog.uebung01.a2;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.io.*;

/**
 * Diese Klasse ist ein Server, auf dem sich Gruppen eintragen bzw. herauslöschen können
 */
public class GruppenlisteServer extends UnicastRemoteObject implements Gruppenliste
{

    public GruppenlisteServer() throws RemoteException
    {
    }

    public static void main(String[] args)
    {
        System.setSecurityManager(new RMISecurityManager());
        try
        {
            GruppenlisteServer objServer = new GruppenlisteServer();
            Naming.rebind("rmi://localhost/Gruppenliste", objServer);
            System.out.println("Server ist unter rmi://localhost/Gruppenliste gestartet.");
        }
        catch (IOException e)
        {
            System.err.println("Fehler bei der Registrierung des Servers in der lokalen RMI-Registry.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mit dieser Methode löscht man eine Gruppe
     * @param gruppenNummer 	die Nummer der zu löschenden Gruppe
     */
    public void loesche(int gruppenNummer) throws java.rmi.RemoteException
    {
        System.out.println("loesche() wurde aufgerufen ..");
        //?? gibt false zurück new File("Gruppe" + Integer.toString(gruppenNummer) + ".txt")).delete();
        try
        {
            new FileWriter("Gruppe" + Integer.toString(gruppenNummer) + ".txt", false);
        }
        catch (IOException e)
        {
            System.err.println("Fehler beim Löschen einer Gruppe.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mit dieser Methode kann man Mitglieder einer Gruppe eintragen.
     * <p>
     * Achtung: Es wird nicht geprüft, ob der angegebene Mitglied bereits
     * in der Gruppe eingetragen ist.
     * @param gruppenNummer 	die Nummer der Gruppe
     * @param name 				der Name des Mitglieds
     * @param matrikel			die Matrikelnummer des Mitglieds
     * @param email				die Emailadresse des Mitglieds
     */
    public void trageEin(int gruppenNummer,
                         String name, String matrikel,
                         String email) throws java.rmi.RemoteException
    {
        try
        {
            BufferedWriter objFile = new BufferedWriter(new FileWriter("Gruppe" + Integer.toString(gruppenNummer) + ".txt", true));
            objFile.write(name + "," + matrikel + "," + email + '\n');
            objFile.close();
        }
        catch (IOException e)
        {
            System.err.println("Fehler bei der Eintragung von einem Mitglieder in einer Gruppe.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mit dieser Methode können die Liste der Mitglieder einer Gruppe abgerufen werden.
     * @param gruppenNummer		die Nummer der Gruppe
     */
    public String[] toString(int gruppenNummer) throws java.rmi.RemoteException
    {
        Vector objVector = new Vector();
        String strLine;

        try
        {
            BufferedReader objBufferedReader = new BufferedReader(
            new FileReader("Gruppe" + Integer.toString(gruppenNummer) + ".txt"));

            while ((strLine = objBufferedReader.readLine()) != null)
            {
                objVector.add(strLine);
            }
        }
        catch (FileNotFoundException e)
        {
            //keine Mitglieder in dieser Gruppenliste
        }
        catch (IOException e)
        {
            System.err.println("Fehler bei Abruf der Liste der Mitglieder einer Gruppe.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        String arrListe[] = new String[objVector.size()];
        for (int i = 0; i < arrListe.length; i++)
        {
            arrListe[i] = (String)objVector.get(i);
        }
        return arrListe;
    }
}
