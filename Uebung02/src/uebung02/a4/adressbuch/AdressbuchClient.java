package uebung02.a4.adressbuch;

import java.util.Properties;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

/**
 *	Das ist ein einfacher Client, der den Adressbuchdienst testet.
 */
public class AdressbuchClient {

	public static void main(String[] args) {
		//Default-Properties
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialPort", "2500");
		props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		
		// ORB initialisieren
		ORB orb = ORB.init(args, props);
		
		//Namensdienst auffinden
		org.omg.CORBA.Object namingRef = null;
		try {
	 		namingRef = orb.resolve_initial_references("NameService");
		} catch (org.omg.CORBA.ORBPackage.InvalidName e) {
			e.getMessage();
			e.printStackTrace();
			return;	
		}
		
	 	NamingContextExt naming = NamingContextExtHelper.narrow(namingRef); // (NamingContextExt)
		//Referenz auffinden
		org.omg.CORBA.Object adressbuchRef = null;
		try {
			adressbuchRef = naming.resolve_str("Adressbuch");
		} catch (Exception e){
			e.getMessage();
			e.printStackTrace();
			return;
		}
	 	
	 	Adressbuch adrbuch = AdressbuchHelper.narrow(adressbuchRef);
	 	
	 	System.out.println("Fuege den Eintrag ein: Boris, StrasseA, 1, 14055, Berlin");
	 	adrbuch.store(new Adresse(new String("Boris"), new String("StrasseA"), (short)1, 14055, new String("Berlin")));
		
		System.out.println("Fuege den Eintrag ein: Ivan, Potsdamerpl., 1, 10000, Berlin");
		adrbuch.store(new Adresse(new String("Ivan"), new String("Potsdamerpl."), (short)1, 10000, new String("Berlin")));
	 	
	 	Adresse adr1 = null;
	 	System.out.println("Suche nach einem Eintrag mit Namen 'Boris': ");
		adr1 = adrbuch.search(SearchParam.NAME, new String("Boris"));
		
		if (!adr1.Name.equals("")){
			System.out.println("Ergebniss der Suche: " + adr1.Name + ", " + adr1.Stadt + ", " + adr1.PLZ + ", " + adr1.Strasse + ", " + adr1.HausNr);
		} else {
			System.out.println("Kein passenden Eintrag wurde gefunden.");
		}
	 	
		System.out.println("Suche nach einem Eintrag mit Strasse 'Potsdamerpl.': ");
		adr1 = adrbuch.search(SearchParam.STRASSE, new String("Potsdamerpl."));
		if (!adr1.Name.equals("")){
			System.out.println("Ergebniss der Suche: " + adr1.Name + ", " + adr1.Stadt + ", " + adr1.PLZ + ", " + adr1.Strasse + ", " + adr1.HausNr);
		} else {
			System.out.println("Kein passenden Eintrag wurde gefunden.");
		}
		
		System.out.println("Suche nach einem Eintrag mit Stadt 'Potsdam': ");
	 	adr1 = adrbuch.search(SearchParam.STADT, new String("Potsdam"));
	 	if (!adr1.Name.equals("")){
			System.out.println("Ergebniss der Suche: " + adr1.Name + ", " + adr1.Stadt + ", " + adr1.PLZ + ", " + adr1.Strasse + ", " + adr1.HausNr);
	 	} else {
	 		System.out.println("Kein passender Eintrag wurde gefunden.");
	 	}
	}
}
