package uebung02.a1_2;

import uebung02.a1_2.adder.floataddPOA;
import org.omg.CORBA.DoubleHolder;

/**
 * <p>Title: NETZPROGRAMMIERUNG UEBUNG 2 - AUFGABE 2</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Hotzenklotz</p>
 * @author Sebastian S.
 * @version 1.0
 */

public class AdderImpl
extends floataddPOA
{
    /**
     * @param a
     * @param b
     */
    public void sum(DoubleHolder a, double b)
    {
        a.value += b;
    }
}