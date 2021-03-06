package uebung01.a4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TeilVektorImpl
extends UnicastRemoteObject
implements TeilVektor
{
    //------------------------------------------------
    //  Instanzvariablen:
    //------------------------------------------------

    private Integer[] field;
    private int sum = 0;

    //------------------------------------------------
    //  Konstruktoren:
    //------------------------------------------------

    /**
     * Creates an internal field with the given Integers.
     * Sum information is updated.
     * @param field the Integers to be stored
     * @throws RemoteException
     */
    public TeilVektorImpl(Integer[] field)
    throws RemoteException
    {
        this.field = new Integer[field.length];

        System.arraycopy(field, 0, this.field, 0, field.length);

        for (int i = 0; i < field.length; i++)
            sum += field[i].intValue();

    }

    //------------------------------------------------
    //  sondierende Methoden:
    //------------------------------------------------

    /**
     * @return the field's size
     */
    public int size()
    {
        return field.length;
    }

    /**
     * @return the field's integers' size
     */
    public int sum()
    {
        return sum;
    }
}
