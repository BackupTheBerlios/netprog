package uebung02.a1_2;

import uebung02.CorbaManager;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POAPackage.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 2</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class AdderServer
{

    /**
     * Installs an {@link AdderService} at localhost
     *
     * @param args not used
     * @throws NotFound
     * @throws InvalidName
     * @throws WrongPolicy
     * @throws AdapterInactive
     * @throws ServantNotActive
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     * @throws CannotProceed
     */
    public static void main(String[] args)
    throws NotFound, InvalidName, WrongPolicy, AdapterInactive, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed
    {   // Referenz auf AdderService Objekt unter "OurFloatAdd" registrieren:
        CorbaManager.installRemoteObjectAtLocalhost(new AdderService(), "OurFloatAdd");
    }
}