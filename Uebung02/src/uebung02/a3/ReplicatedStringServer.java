package uebung02.a3;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;
import uebung02.CorbaManager;

public class ReplicatedStringServer
{
    /**
     * Installs a {@link uebung02.a3.ReplicatedStringService} at localhost
     *
     * @param args not used
     * @throws InvalidName
     * @throws AdapterInactive
     * @throws WrongPolicy
     * @throws ServantNotActive
     * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     * @throws NotFound
     * @throws CannotProceed
     */
    public static void main(String[] args)
    throws InvalidName, AdapterInactive, WrongPolicy, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName,
    NotFound, CannotProceed
    {
        CorbaManager.installRemoteObjectAtLocalhost(new ReplicatedStringService(), "ReplStringService1");
    }
}
