package netprog.uebung01.a4;

import java.net.MalformedURLException;
import java.rmi.*;

public class MainE_ConnectAndTest
{
    /**
     * Loads previously registered instances of {@link TeilVektor} and {@link GesamtVektor} and runs tests.
     Objects must be installed by starting {@link MainA_RegisterGesamtVektor}, {@link MainB_RegisterTeilvektor},
     {@link MainC_RegisterTeilvektor} and {@link MainD_RegisterTeilvektor}
     * @param args isn't used
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException
    {
        GesamtVektor g = (GesamtVektor)Naming.lookup("GesamtVektor");
        TeilVektor t1 = (TeilVektor)Naming.lookup("TeilVektor1");
        TeilVektor t2 = (TeilVektor)Naming.lookup("TeilVektor2");
        TeilVektor t3 = (TeilVektor)Naming.lookup("TeilVektor3");

        System.out.println("Größe: " + g.size() + " - Summe: " + g.sum());

        g.append(t1);
        System.out.println("Größe: " + g.size() + " - Summe: " + g.sum());

        g.append(t2);
        System.out.println("Größe: " + g.size() + " - Summe: " + g.sum());

        g.append(t3);
        System.out.println("Größe: " + g.size() + " - Summe: " + g.sum());

        g.remove(t1);
        System.out.println("Größe: " + g.size() + " - Summe: " + g.sum());

        g.remove(t1);
        System.out.println("Größe: " + g.size() + " - Summe: " + g.sum());

        g.remove(t2);
        System.out.println("Größe: " + g.size() + " - Summe: " + g.sum());

    }
}
