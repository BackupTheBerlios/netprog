package netprog.uebung02;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sebastian Koske
 * Date: 21.11.2003
 * Time: 01:21:24
 * To change this template use Options | File Templates.
 */
public class CorbaManager
{
    public static org.omg.CORBA.Object getRemoteObject(String host, String name)
    throws InvalidName, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound
    {
        String[] args = {"-ORBInitialPort", "3000", "-ORBInitialHost", host};

        // ORB initialisieren
        ORB orb = ORB.init(args, null);

        // Namensdienst auffinden
        NamingContextExt naming = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
        org.omg.CORBA.Object result = naming.resolve_str(name);

        return result;
    }

    public static void installRemoteObjectAtLocalhost(Servant servant, String name)
    throws InvalidName, AdapterInactive, WrongPolicy, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed
    {
        String[] args = {"-ORBInitialPort", "3000"};

        // ORB initialisieren
        ORB orb = ORB.init(args, null);

        // Root Objekt ermitteln und aktivieren
        POA root = POAHelper.narrow(orb.resolve_initial_references("RootPOA")); // Cast (POA) rootRef
        root.the_POAManager().activate();

        // Namensdienst ermitteln
        NamingContextExt naming = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService")); // (NamingContextExt)

        // Referenz auf Counter Objekt unter "OurFloatAdd" registrieren
        org.omg.CORBA.Object serviceRef = root.servant_to_reference(servant);
        NameComponent[] path = naming.to_name(name);
        naming.rebind(path, serviceRef);

        // Auf Aufrufe warten
        orb.run();
    }
}
