package netprog.uebung02.a1_2;

import netprog.uebung02.a1_2.adder.floatadd;
import netprog.uebung02.a1_2.adder.floataddHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 1 / 2</p>
 * <p>Description:
 * --------------------------------- AUFGABE 1 ---------------------------------
 * Client starten:
 * > java netprog.uebung02.a1_2.Test -ORBInitialPort 3000 -ORBInitialHost nawab.inf.fu-berlin.de
 *
 * --------------------------------- AUFGABE 2 ---------------------------------
 * 1. ORB Server starten:
 *     > orbd -ORBInitialPort 3000
 * 2. Addier-Server starten:
 *     > java adder.AdderServer -ORBInitialPort 3000
 * 3. Client starten:
 *     > java netprog.uebung02.a1_2.Test -ORBInitialPort 3000
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class Test {

    public static final boolean LOCALCALL = true;

    public static void main(String[] args) throws Exception {

        // ORB initialisieren
        ORB orb = ORB.init(args, null);

        // Namensdienst auffinden
        org.omg.CORBA.Object namingRef =
                orb.resolve_initial_references("NameService");
        NamingContextExt naming =
                NamingContextExtHelper.narrow(namingRef); // (NamingContextExt)

        // Referenz auffinden
        String name = LOCALCALL ? "OurFloatAdd" : "FloatAdd";
        org.omg.CORBA.Object adderRef = naming.resolve_str(name);
        floatadd adder = floataddHelper.narrow(adderRef);

        // nu mal testen:
        org.omg.CORBA.DoubleHolder dH = new org.omg.CORBA.DoubleHolder();
        dH.value = 5;
        System.out.println("addiere " + dH.value + " mit 2");
        adder.sum(dH, 2);
        System.out.println("Ergebnis: " + dH.value);
    }
}