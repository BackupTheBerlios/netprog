package netprog.uebung02.a3;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;

public class ReplicatedStringServer
{
    public static void main(String[] args)
    throws InvalidName, AdapterInactive, WrongPolicy, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName,
    NotFound, CannotProceed
    {
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
        ReplicatedStringServiceImpl stringService = new ReplicatedStringServiceImpl();

        // Referenz auf Counter Objekt unter "OurFloatAdd" registrieren
        org.omg.CORBA.Object serviceRef =
        root.servant_to_reference(stringService);
        NameComponent[] path = naming.to_name("ReplStringService");
        naming.rebind(path, serviceRef);

        // Auf Aufrufe warten
        orb.run();

    }
}
