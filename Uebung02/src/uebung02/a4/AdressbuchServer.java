package uebung02.a4;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;
import uebung02.CorbaManager;

public class AdressbuchServer
{
    /**
     * Installs an {@link uebung02.a4.AdressbuchService} at localhost.
     *
     * @param args
     * @throws NotFound
     * @throws InvalidName
     * @throws WrongPolicy
     * @throws AdapterInactive
     * @throws ServantNotActive
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     * @throws CannotProceed
     */
    public static void main(String[] args) throws NotFound, InvalidName, WrongPolicy, AdapterInactive, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed
    {
        CorbaManager.installRemoteObjectAtLocalhost(new AdressbuchService(), "Adressbuch");
    }

}