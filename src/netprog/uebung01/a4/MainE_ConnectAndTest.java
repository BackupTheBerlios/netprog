package netprog.uebung01.a4;

import java.net.MalformedURLException;
import java.rmi.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sebastian Koske
 * Date: 07.11.2003
 * Time: 16:55:08
 * To change this template use Options | File Templates.
 */
public class MainE_ConnectAndTest
{
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
