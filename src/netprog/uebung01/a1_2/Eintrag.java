package netprog.uebung01.a1_2;

import java.rmi.*;

public class Eintrag
{
    //------------------------------------------------
    //  Klassenkonstanten:
    //------------------------------------------------

    private static final boolean LOCAL_RMI = false;

    //------------------------------------------------
    //  Klassenprozeduren:
    //------------------------------------------------

    /**
     * Connects to an instance of {@link Gruppenliste} running in a different JVM and adds this group.
     Prints group infomation afterwards.
     * @param args isn't used.
     */
    public static void main(String[] args)
    {
        try
        {
            String hostname = LOCAL_RMI ? "localhost" : "nawab.inf.fu-berlin.de";

            Gruppenliste grouplist = (Gruppenliste)Naming.lookup("rmi://" + hostname + "/liste");
            grouplist.loesche(6);

            grouplist.trageEin(6, "Sebastian Koske", "3500729", "koske@inf.fu-berlin.de");
            grouplist.trageEin(6, "Boris Tsarev", "3668329", "tsarev@inf.fu-berlin.de");
            grouplist.trageEin(6, "Sebastian Schaepe", "3483427", "schaepe@inf.fu-berlin.de");
            grouplist.trageEin(6, "Rafael Grote", "3519895", "grote@inf.fu-berlin.de");
            grouplist.trageEin(6, "Mike Rohland", "3514979", "rohland@inf.fu-berlin.de");

            String[] content = grouplist.toString(6);

            for (int i = 0; i < content.length; i++)
                System.out.println(content[i].toString());
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