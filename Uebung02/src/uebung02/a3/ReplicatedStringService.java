package uebung02.a3;

import uebung02.a3.repstring.*;

public class ReplicatedStringService
extends uebung02.a3.repstring.replicatedstringPOA
{

    //------------------------------------------------
    //  Instanzvariablen:
    //------------------------------------------------

    private String s;
    private replicatedstringOperations replicate;
    private boolean isReplicating = false;

    //------------------------------------------------
    //  Konstruktoren:
    //------------------------------------------------

    public ReplicatedStringService()
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
     * Stores the given String and replicates it if another {@link uebung01.a3.ReplicatedString} has been set
     by using {@link replicateAt(uebung01.a3.ReplicatedString)}.
     * The given String may be null.
     * If there is a ring (e.g. this object is it's own replicant's replicant) recursive replication
     * stops once all replicants have been updated avoiding endless recursion.
     *
     * @param s the string to be stored and replicated.
     * @throws java.rmi.RemoteException
     */
    public void set(String s)
    {
        if (isReplicating) return;

        isReplicating = true;

        this.s = s;
        if (replicate != null) replicate.set(s);

        isReplicating = false;

    }

    /**
     * Sets the {@link uebung01.a3.ReplicatedString} where any String given as parameter for {@link set(String)}
     should be replicated.
     * The Object may be null causing no replication.
     * Immediately replicated the currently stored String at the given {@link uebung01.a3.ReplicatedString}
     *
     * @param rs the {@link uebung01.a3.ReplicatedString} where the String should be replicated at.
     */
    public void replicateAt(replicatedstring rs)
    {
        replicate = rs;

        set(get());
    }
}
