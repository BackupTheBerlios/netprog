package uebung02.a1_2;

import org.omg.CORBA.DoubleHolder;
import uebung02.CorbaManager;
import uebung02.a1_2.adder.*;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 1 / 2</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class AdderClient
{

    /**
     * Connects to a {@link adder.floatadd} at the given host or localhost (default) and performs some tests.
     * @param args either  "-ORBInitialPort [port] -ORBInitialHost [hostname]" or empty
     * @throws Exception
     */
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

        // get romote object:
        floatadd adder = floataddHelper.narrow(CorbaManager.getRemoteObject(host, name));

        // perform tests:
        DoubleHolder dH = new DoubleHolder();
        dH.value = 5;
        System.out.println("addiere " + dH.value + " mit 2");

        adder.sum(dH, 2);
        System.out.println("Ergebnis: " + dH.value);
    }
}