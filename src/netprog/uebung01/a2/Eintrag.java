package netprog.uebung01.a2;

import java.rmi.*;

/**
 * Diese Klasse registriert die Gruppe 6 und deren Mitglieder.
 */
public class Eintrag {
	public static void main(String[] args) {
		try {
			//Gruppenliste objServer = (Gruppenliste)Naming.lookup("rmi://nawab.inf.fu-berlin.de/liste");
			Gruppenliste objServer = (Gruppenliste)Naming.lookup("rmi://localhost/Gruppenliste");
			objServer.loesche(6);
			objServer.trageEin(6, "Boris Tsarev", "3668329", "tsarev@inf.fu-berlin.de");
			objServer.trageEin(6, "Rafael Grote", "3519895", "grote@inf.fu-berlin.de");
			objServer.trageEin(6, "Sebastian Koske", "3500729", "koske@inf.fu-berlin.de");
			objServer.trageEin(6, "Ivan Boev", "3845191", "boev@inf.fu-berlin.de");
			objServer.trageEin(6, "Sebastian Schaepe", "3483427", "schaepe@inf.fu-berlin.de");
			objServer.trageEin(6, "Mike Rohland", "3514979", "rohland@inf.fu-berlin.de");
			String arrListe[]  = objServer.toString(6);
			for(int i=0; i<arrListe.length; i++){
				System.out.println(arrListe[i]);
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
}
