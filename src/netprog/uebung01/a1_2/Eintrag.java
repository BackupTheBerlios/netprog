package netprog.uebung01.a1_2;

import java.rmi.*;

public class Eintrag
{
    /************************************************
     |  Konstanten:
     *************************************************/

    private static final boolean LOCAL_RMI = false;

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

            Gruppenliste grouplist = (Gruppenliste)Naming.lookup("rmi://" + hostname + "/liste");
            grouplist.loesche(6);

            grouplist.trageEin(6, "Sebastian Koske", "3500729", "koske@inf.fu-berlin.de");
            grouplist.trageEin(6, "Boris Tsarev", "3668329", "tsarev@inf.fu-berlin.de");

            grouplist.trageEin(6, "Rafael Grote", "XXX", "grote@inf.fu-berlin.de");
            grouplist.trageEin(6, "Sebastian Schaepe", "XXX", "schaepe@inf.fu-berlin.de");
            grouplist.trageEin(6, "Mike Rohland", "XXX", "rohland@inf.fu-berlin.de");

            String[] content = grouplist.toString(6);

            for (int i = 0; i < content.length; i++)
                System.out.println(content[i].toString());

            grouplist.loesche(6);

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