package netprog.uebung01;

import java.rmi.*;

public class Eintrag
{
    public Eintrag()
    {   try
        {   Gruppenliste c = (Gruppenliste)java.rmi.Naming.lookup("rmi://nawab.inf.fu-berlin.de/liste");
            c.loesche(6);
            c.trageEin(6,"Sebastian Koske","3500729","koske@inf.fu-berlin.de");
            System.out.println(c.toString(6));
            c.loesche(6);

            /*  Ich bekommen immer folgende Exception:

                java.rmi.UnmarshalException: error unmarshalling return; nested exception is:
	            java.lang.ClassNotFoundException: GruppenlisteServer_Stub (no security manager: RMI class loader disabled)

                keine Ahnung, ob das mein Fehler ist, oder ob es am Server liegt, hat jemand ne Idee?
            */
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