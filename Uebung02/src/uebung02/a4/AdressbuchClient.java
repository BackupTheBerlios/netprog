package uebung02.a4;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.*;
import uebung02.CorbaManager;
import uebung02.a4.adressbuch.*;

/**
 *	Das ist ein einfacher Client, der den Adressbuchdienst testet.
 */
public class AdressbuchClient
{

    /**
     * Performs tests on an installed {@link uebung02.a4.AdressbuchService}.
     *
     * @param args
     * @throws NotFound
     * @throws InvalidName
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     * @throws CannotProceed
     */
    public static void main(String[] args) throws NotFound, InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed
    {
        Adressbuch adrbuch = AdressbuchHelper.narrow(CorbaManager.getRemoteObject("localhost", "Adressbuch"));

        System.out.println("Fuege den Eintrag ein: Boris, StrasseA, 1, 14055, Berlin");
        adrbuch.store(new Adresse(new String("Boris"), new String("StrasseA"), (short)1, 14055, new String("Berlin")));

        System.out.println("Fuege den Eintrag ein: Ivan, Potsdamerpl., 1, 10000, Berlin");
        adrbuch.store(new Adresse(new String("Ivan"), new String("Potsdamerpl."), (short)1, 10000, new String("Berlin")));

        Adresse adr1 = null;
        System.out.println("Suche nach einem Eintrag mit Namen 'Boris': ");
        adr1 = adrbuch.search(SearchParam.NAME, new String("Boris"));

        if (!adr1.Name.equals(""))
        {
            System.out.println("Ergebniss der Suche: " + adr1.Name + ", " + adr1.Stadt + ", " + adr1.PLZ + ", " + adr1.Strasse + ", " + adr1.HausNr);
        }
        else
        {
            System.out.println("Kein passenden Eintrag wurde gefunden.");
        }

        System.out.println("Suche nach einem Eintrag mit Strasse 'Potsdamerpl.': ");
        adr1 = adrbuch.search(SearchParam.STRASSE, new String("Potsdamerpl."));
        if (!adr1.Name.equals(""))
        {
            System.out.println("Ergebniss der Suche: " + adr1.Name + ", " + adr1.Stadt + ", " + adr1.PLZ + ", " + adr1.Strasse + ", " + adr1.HausNr);
        }
        else
        {
            System.out.println("Kein passenden Eintrag wurde gefunden.");
        }

        System.out.println("Suche nach einem Eintrag mit Stadt 'Potsdam': ");
        adr1 = adrbuch.search(SearchParam.STADT, new String("Potsdam"));
        if (!adr1.Name.equals(""))
        {
            System.out.println("Ergebniss der Suche: " + adr1.Name + ", " + adr1.Stadt + ", " + adr1.PLZ + ", " + adr1.Strasse + ", " + adr1.HausNr);
        }
        else
        {
            System.out.println("Kein passender Eintrag wurde gefunden.");
        }
    }
}
