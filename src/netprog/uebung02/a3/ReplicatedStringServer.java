package netprog.uebung02.a3;

import netprog.uebung02.CorbaManager;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;

public class ReplicatedStringServer
{
    public static void main(String[] args)
    throws InvalidName, AdapterInactive, WrongPolicy, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName,
    NotFound, CannotProceed
    {
        // Neuen Counter erzeugen
        ReplicatedStringImpl repString = new ReplicatedStringImpl();

        CorbaManager.installRemoteObjectAtLocalhost(repString, "ReplStringService1");
    }
}
