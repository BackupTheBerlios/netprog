package netprog.uebung01.a3;

import java.rmi.RemoteException;

public class ReplicatedStringImpl
extends java.rmi.server.UnicastRemoteObject
implements ReplicatedString
{
    //------------------------------------------------
    //  Instanzvariablen:
    //------------------------------------------------

    private String s;
    private ReplicatedString replicate;

    //------------------------------------------------
    //  Konstruktoren:
    //------------------------------------------------

    public ReplicatedStringImpl()
    throws RemoteException
    {
    }

    //------------------------------------------------
    //  sondierende Methoden:
    //------------------------------------------------

    /**
     * @return the stored String previously set by {@link set(String)}.
     */
    public String get()
    {
        return s;
    }

    //------------------------------------------------
    //  Prozeduren:
    //------------------------------------------------

    /**
     * Stores the given String and replicates it if another ReplicateString has been set
     by using {@link netprog.uebung01.a3.ReplicatedStringImpl#replicateAt(ReplicatedString)
     replicateAt(ReplicatedString s)}.
     * The given String may be null.
     * Note that calling this method may cause a StackOverflow when a cycle has been created.
     *
     * @param s the string to be stored and replicated.
     * @throws RemoteException
     */
    public void set(String s)
    throws RemoteException
    {
        this.s = s;
        if (replicate != null) replicate.set(s);
    }

    /**
     * Sets the {@link ReplicatedString} where any String given as parameter for {@link set(String)} should be replicated.
     * The Object may be null causing no replication.
     *
     * @param rs the Object where the String should be replicated.
     */
    public void replicateAt(ReplicatedString rs)
    {
        replicate = rs;
    }
}
