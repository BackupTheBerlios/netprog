package uebung02.a1_2;

import uebung02.CorbaManager;
import uebung02.a1_2.adder.*;
import org.omg.CORBA.DoubleHolder;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 1 / 2</p>
 * <p>Description:
 * --------------------------------- AUFGABE 1 ---------------------------------
 * Client starten:
 * > java uebung02.a1_2.Test -ORBInitialPort 3000 -ORBInitialHost nawab.inf.fu-berlin.de
 *
 * --------------------------------- AUFGABE 2 ---------------------------------
 * 1. ORB Server starten:
 *     > orbd -ORBInitialPort 3000
 * 2. Addier-Server starten:
 *     > java AdderServer -ORBInitialPort 3000
 * 3. Client starten:
 *     > java uebung02.a1_2.Test -ORBInitialPort 3000
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class Test
{

    public static void main(String[] args) throws Exception
    {
        String host, name;

        if (args.length < 4)
        {
            host = "localhost";
            name = "OurFloatAdd";
        }
        else
        {
            host = args[3];
            name = "FloatAdd";
        }

        // Referenz auffinden
        floatadd adder = floataddHelper.narrow(CorbaManager.getRemoteObject(host, name));

        // nu mal testen:
        DoubleHolder dH = new DoubleHolder();
        dH.value = 5;
        System.out.println("addiere " + dH.value + " mit 2");

        adder.sum(dH, 2);
        System.out.println("Ergebnis: " + dH.value);
    }
}