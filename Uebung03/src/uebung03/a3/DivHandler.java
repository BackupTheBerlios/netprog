package uebung03.a3;

import java.util.Iterator;
import java.util.Vector;

public class DivHandler
{
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Fields                       |   \\
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

    private Integer arg1;
    private Integer arg2;

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                  Probing Methods                  |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    public boolean canGetResult()
    {
        return arg1 != null && arg2 != null;
    }

    public int getResult()
    {   // Preconditions:
        assert canGetResult() : "PRE 1: canGetResult() returned false @ DivHandler.getResult()";

        // Implementation:
        return arg1.intValue() / arg2.intValue();
    }

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                Modifying Methods                  |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    public void setValue(String in)
    {   // Preconditions:
        if (in == null) return;

        if (in.charAt(0) != ':')
            arg1 = new Integer(in);
        else
            arg2 = new Integer(in.substring(1));
    }

    public void reset()
    {
        arg1 = null;
        arg2 = null;
    }
}
