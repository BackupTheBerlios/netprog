package netprog.uebung02.a4.adressbuch;

import java.util.Properties;
import java.util.Vector;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/**
 *	Der Server, der den Adressbuchdienst zur Verfügung stellt
 */
public class AdressbuchServer extends AdressbuchPOA {
	private Vector objAdressen = null;
	
	public AdressbuchServer() {
		objAdressen = new Vector();
	}
	
	public static void main(String[] args) {
		//Default-Properties
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialPort", "2500");
		props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		
		// ORB initialisieren
		ORB orb = ORB.init(args, props);
		
		// Root Objekt ermitteln und aktivieren
		org.omg.CORBA.Object rootRef = null;
		try {
			rootRef = orb.resolve_initial_references("RootPOA");
		} catch (org.omg.CORBA.ORBPackage.InvalidName e){
			e.printStackTrace();
			return;
		}
		
		POA root = POAHelper.narrow(rootRef); // Cast (POA) rootRef
		try {
			root.the_POAManager().activate();
		} catch (AdapterInactive e) {
			e.printStackTrace();
			return;
		}
		
		// Namensdienst ermitteln
		org.omg.CORBA.Object namingRef = null;
		try {
			namingRef = orb.resolve_initial_references("NameService");
		} catch (org.omg.CORBA.ORBPackage.InvalidName e) {
			e.printStackTrace();
			return;
		}
		
		NamingContextExt naming = NamingContextExtHelper.narrow(namingRef); // (NamingContextExt)
		
		// AdressbuchServer erzeugen
		AdressbuchServer adrbuch = new AdressbuchServer();
		// Referenz auf AdressbuchServer registrieren
		org.omg.CORBA.Object serviceRef;
		try {
			serviceRef = root.servant_to_reference(adrbuch);
		} catch (ServantNotActive e1) {
			e1.printStackTrace();
			return;
		} catch (WrongPolicy e1) {
			e1.printStackTrace();
			return;
		}
		
		NameComponent[] path;
		try {
			path = naming.to_name("Adressbuch");
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e2) {
			e2.printStackTrace();
			return;
		}
		
		try {
			naming.rebind(path,serviceRef);
		} catch (NotFound e3) {
			e3.printStackTrace();
			return;
		} catch (CannotProceed e3) {
			e3.printStackTrace();
			return;
		} catch (InvalidName e3) {
			e3.printStackTrace();
			return;
		}
		
		// Auf Aufrufe warten
		orb.run();
	}

	/** 
	 * Speichert die übergebene Adresse.
	 * @param adr die zu speichernde Adresse
	 */
	public void store(Adresse adr) {
		if (adr!=null){
			objAdressen.add(adr);
		}			
	}

	/** 
	 * Sucht nach dem ersten Element, das das Kriterium erfüllt
	 * @param param der Typ des Suchkriteriums
	 * @param searchstr die Suchzeichenkette
	 * @return der erste gefundene Eintrag im Adressbuch	
	 */
	public Adresse search(SearchParam param, String searchstr) {
		int i;
		for(i=0; i<objAdressen.size(); i++){
			switch (param.value()) {
				case SearchParam._NAME:
					if (((Adresse)objAdressen.get(i)).Name.equals(searchstr)) {
						return (Adresse)objAdressen.get(i);
					}
					break;
				case SearchParam._STADT:
					if (((Adresse)objAdressen.get(i)).Stadt.equals(searchstr)) {
						return (Adresse)objAdressen.get(i);
					}
					break;
				case SearchParam._STRASSE:
					if (((Adresse)objAdressen.get(i)).Strasse.equals(searchstr)) {
						return (Adresse)objAdressen.get(i);
					}
				 	break;
			}
		}
		return new Adresse(new String(""), new String(""), (short)0, 0, new String(""));
	}
}
