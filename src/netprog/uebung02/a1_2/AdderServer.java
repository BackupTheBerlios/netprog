package netprog.uebung02.a1_2;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 2</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class AdderServer {

    /**
     * main - method
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // ORB initialisieren
        ORB orb = ORB.init(args, null);

        // Root Objekt ermitteln und aktivieren
        org.omg.CORBA.Object rootRef =
                orb.resolve_initial_references("RootPOA");
        POA root = POAHelper.narrow(rootRef); // Cast (POA) rootRef
        root.the_POAManager().activate();

        // Namensdienst ermitteln
        org.omg.CORBA.Object namingRef =
                orb.resolve_initial_references("NameService");
        NamingContextExt naming =
                NamingContextExtHelper.narrow(namingRef); // (NamingContextExt)

        // Neuen Counter erzeugen
        AdderServiceImpl theAdder = new AdderServiceImpl();

        // Referenz auf Counter Objekt unter "OurFloatAdd" registrieren
        org.omg.CORBA.Object serviceRef =
                root.servant_to_reference(theAdder);
        NameComponent[] path = naming.to_name("OurFloatAdd");
        naming.rebind(path, serviceRef);

        // Auf Aufrufe warten
        orb.run();
    }
}