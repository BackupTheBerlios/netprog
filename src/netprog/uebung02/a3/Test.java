package netprog.uebung02.a3;

import netprog.uebung02.CorbaManager;
import netprog.uebung02.a3.repstring.*;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 3</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class Test
{
    public static void main(String[] args) throws Exception
    {
        ReplicatedStringImpl repString2 = new ReplicatedStringImpl();
        CorbaManager.installRemoteObjectAtLocalhost(repString2, "ReplStringService2");

        replicatedstring rs = replicatedstringHelper.narrow(CorbaManager.getRemoteObject("localhost", "ReplStringService1"));
        replicatedstring rsi = replicatedstringHelper.narrow(CorbaManager.getRemoteObject("localhost", "ReplStringService2"));

        // nu mal testen:
        rs.set("Hallo Welt");
        System.out.println("GET: " + rs.get());

        rs.replicateAt(rsi);

        rs.set("TEST TEST");

        System.out.println("REMOTE SAGT: " + rs.get());
        System.out.println("LOCAL SAGT: " + rsi.get());

    }
}