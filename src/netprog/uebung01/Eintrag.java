package netprog.uebung01;

import java.rmi.*;

public class Eintrag
{
    public Eintrag()
    {
        try
        {
            Gruppenliste c = (Gruppenliste)java.rmi.Naming.lookup("rmi://nawab.inf.fu-berlin.de/liste");
            c.loesche(6);
            c.trageEin(6,"Sebastian Koske","3500729","koske@inf.fu-berlin.de");
            System.out.println(c.toString(6));
            c.loesche(6);
        }
        catch (NotBoundException e)
        {
            System.err.println(e.toString());
        }
        catch (java.net.MalformedURLException e)
        {
            System.err.println(e.toString());
        }
        catch (RemoteException e)
        {
            System.err.println(e.toString());
        }
    }

    public static void main(String[] args)
    {
        new Eintrag();
	
    }

}