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
    private boolean isReplicating = false;

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
     * Stores the given String and replicates it if another {@link ReplicatedString} has been set
     by using {@link replicateAt(ReplicatedString)}.
     * The given String may be null.
     * If there is a ring (e.g. this object is it's own replicant's replicant) recurvive replicating
     * stops once all replicants have been updated avoiding endless recursion.
     *
     * @param s the string to be stored and replicated.
     * @throws RemoteException
     */
    public void set(String s)
    throws RemoteException
    {
        if (isReplicating) return;

        isReplicating = true;

        this.s = s;
        if (replicate != null) replicate.set(s);

        isReplicating = false;
    }

    /**
     * Sets the {@link ReplicatedString} where any String given as parameter for {@link set(String)} should be replicated.
     * The Object may be null causing no replication.
     *
     * @param rs the {@link ReplicatedString} where the String should be replicated at.
     */
    public void replicateAt(ReplicatedString rs)
    {
        replicate = rs;
    }
}
