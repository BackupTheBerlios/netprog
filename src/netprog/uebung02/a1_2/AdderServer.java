package netprog.uebung02.a1_2;

import netprog.uebung02.CorbaManager;

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
     * main - method
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception
    {   // Neuen Counter erzeugen
        AdderImpl theAdder = new AdderImpl();

        // Referenz auf Counter Objekt unter "OurFloatAdd" registrieren
        CorbaManager.installRemoteObjectAtLocalhost(theAdder, "OurFloatAdd");
    }
}