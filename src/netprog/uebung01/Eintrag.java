package netprog.uebung01;

import java.rmi.*;

public class Eintrag
{
    /************************************************
     |  Konstanten:
     *************************************************/

    private static final boolean LOCAL_RMI = true;

    /************************************************
     |  Klassenprozeduren:
     *************************************************/

    public static void main(String[] args)
    {
        new Eintrag();
    }

    /************************************************
     |  Konstruktoren:
     *************************************************/

    public Eintrag()
    {
        try
        {
            String hostname = LOCAL_RMI ? "localhost" : "nawab.inf.fu-berlin.de";

            Gruppenliste grouplist = (Gruppenliste)java.rmi.Naming.lookup("rmi://" + hostname + "/liste");
            grouplist.loesche(6);

            grouplist.trageEin(6, "Sebastian Koske", "3500729", "koske@inf.fu-berlin.de");
            grouplist.trageEin(6, "Rafael Grote", "3500729", "grote@inf.fu-berlin.de");
            grouplist.trageEin(6, "Sebastian Schaepe", "3500729", "schaepe@inf.fu-berlin.de");
            grouplist.trageEin(6, "Mike Rohland", "3500729", "rohland@inf.fu-berlin.de");

            String[] content = grouplist.toString(6);

            for (int i = 0; i < content.length; i++)
                System.out.println(content[i].toString());

            grouplist.loesche(6);

            /*  Ich bekomme bei der uni-rmi immer folgende Exception:

                java.rmi.UnmarshalException: error unmarshalling return; nested exception is:
                java.lang.ClassNotFoundException: GruppenlisteServer_Stub (no security manager: RMI class loader disabled)

                Ich denke es an der Serverkonfiguration an der Uni liegt, denn lokal gibt es keine Probleme.
                Hat jemand vielleicht noch eine Idee?
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
}