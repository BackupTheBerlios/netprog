package uebung03.a3;

import java.util.Iterator;
import java.util.Vector;

public class DivHandler
{
    private Integer arg1;
    private Integer arg2;


    public void setValue(String in)
    {   if (in.charAt(0) != ':') arg1 = new Integer(in);
        else arg2 = new Integer(in.substring(1));
    }

    public boolean canGetResult()
    {
        return arg1 != null && arg2 != null;  //Todo implement canGetResult()
    }

    public int getResult()
    {   assert canGetResult() : "Precondition violotated";

        return arg1.intValue() / arg2.intValue();  //Todo implement getResult()
    }

    public void reset()
    {
        arg1 = null;
        arg2 = null;


    }
}
