package uebung02;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;

public class CorbaManager
{
    /**
     * Look for a remote object with the given name at port 3000 at the given host and returns it.
     *
     * @param host The host where the object can be found
     * @param name
     * @return
     * @throws InvalidName
     * @throws CannotProceed
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     * @throws NotFound
     */
    public static org.omg.CORBA.Object getRemoteObject(String host, String name)
    throws InvalidName, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound
    {
        String[] args = {"-ORBInitialPort", "3000", "-ORBInitialHost", host};

        // ORB initialisieren
        ORB orb = ORB.init(args, null);

        // Namensdienst auffinden und Object finden:
        return NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService")).resolve_str(name);
    }

    /**
     * Installs the given Servant at port 3000 at localhost with the given name.
     * The run() method of the ORB object is started in a single Thread in order to enable the method caller to
     * proceed (otherwise calling this method would result in an infinite loop).
     *
     * @param servant
     * @param name
     * @throws InvalidName
     * @throws AdapterInactive
     * @throws WrongPolicy
     * @throws ServantNotActive
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     * @throws NotFound
     * @throws CannotProceed
     */
    public static void installRemoteObjectAtLocalhost(Servant servant, String name)
    throws InvalidName, AdapterInactive, WrongPolicy, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed
    {
        String[] args = {"-ORBInitialPort", "3000"};

        // ORB initialisieren
        final ORB orb = ORB.init(args, null);

        // Root Objekt ermitteln und aktivieren
        POA root = POAHelper.narrow(orb.resolve_initial_references("RootPOA")); // Cast (POA) rootRef
        root.the_POAManager().activate();

        // Namensdienst ermitteln
        NamingContextExt naming = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService")); // (NamingContextExt)

        // Referenz auf Counter Objekt unter "OurFloatAdd" registrieren
        org.omg.CORBA.Object serviceRef = root.servant_to_reference(servant);
        NameComponent[] path = naming.to_name(name);
        naming.rebind(path, serviceRef);

        // Auf Aufrufe warten:
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                orb.run();
            }
        });

        t.start();
    }
}
