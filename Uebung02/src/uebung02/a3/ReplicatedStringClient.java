package uebung02.a3;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.*;
import uebung02.CorbaManager;
import uebung02.a3.repstring.*;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 3</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class ReplicatedStringClient
{
    /**
     * Installs a second {@link uebung02.a3.ReplicatedStringService} at localhost, connects it with
     * an already installed one and performs tests.
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
    public static void main(String[] args) throws NotFound, InvalidName, WrongPolicy, AdapterInactive, ServantNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed
    {
        ReplicatedStringService repString2 = new ReplicatedStringService();
        CorbaManager.installRemoteObjectAtLocalhost(repString2, "ReplStringService2");

        replicatedstring rs = replicatedstringHelper.narrow(CorbaManager.getRemoteObject("localhost", "ReplStringService1"));
        replicatedstring rsi = replicatedstringHelper.narrow(CorbaManager.getRemoteObject("localhost", "ReplStringService2"));

        // perform tests:
        rs.set("Hallo Welt");
        System.out.println("GET: " + rs.get());

        rs.replicateAt(rsi);

        rs.set("TEST TEST");

        System.out.println("REMOTE SAGT: " + rs.get());
        System.out.println("LOCAL SAGT: " + rsi.get());

    }
}