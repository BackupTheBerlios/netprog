package netprog.uebung02.a3;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import netprog.uebung02.a3.repstring.*;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 3</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class ReplicatedStringClient {
    public static void main(String[] args) throws Exception {

        // ORB initialisieren
        ORB orb = ORB.init(args, null);

        // Namensdienst auffinden
        org.omg.CORBA.Object namingRef =
                orb.resolve_initial_references("NameService");
        NamingContextExt naming =
                NamingContextExtHelper.narrow(namingRef); // (NamingContextExt)

        // Referenz auffinden
        org.omg.CORBA.Object repstrREMOTE = naming.resolve_str("ReplStringService");
        replicatedstring rs = replicatedstringHelper.narrow(repstrREMOTE);

        args[3] = "localhost";

        // ORB initialisieren
        orb = ORB.init(args, null);

        // Namensdienst auffinden
        org.omg.CORBA.Object namingRefLocal =
                orb.resolve_initial_references("NameService");
        NamingContextExt namingLocal =
                NamingContextExtHelper.narrow(namingRefLocal); // (NamingContextExt)

        org.omg.CORBA.Object repstrLOCAL = namingLocal.resolve_str("ReplStringService");
        replicatedstring rsi = replicatedstringHelper.narrow(repstrLOCAL);

        // nu mal testen:
        rs.set("Hallo Welt");
        System.out.println("GET: " + rs.get());

        rs.replicateAt(rsi);

        rs.set("TEST TEST");

        System.out.println("REMOTE SAGT: " + rs.get());
        System.out.println("LOCAL SAGT: " + rsi.get());
    }
}