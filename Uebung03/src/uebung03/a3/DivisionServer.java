package uebung03.a3;

import java.net.*;
import java.io.*;
import java.util.*;
/***
 * 
 * @author Gruppe 6
 *
 * Dieser Server stellt einen Dienst zur Division von zwei Ganzzahlen bereit.<br>
 * Die PortNummer, die vom Server benutzt wird, wird über die Kommandozeile übergeben.<br>
 * Der Dienst wird mit folgendem Protokoll ermöglicht:<br>
 * 1) Der Client übergibt die Ganzzahlen in zwei verschiedenen UDP-Packeten.<br>
 * 2) Ein Packet hat das Format ZahlNr:Zahl<br>
 * 3) Die ZahlNr ist die Reihenfolgenr der gesendete Zahl.<br>
 * 4) Eine Division findet statt, wenn vom Server zwei Pakete empfangen werden und
 * die Zahlen aufeinanderfolgende ReihenfolgeNr haben und die kleinere ReihenfolgeNr
 * eine ungerade Zahl ist.<br>
 * 5) Wenn die zweite Zahl eine Null ist, wird 'ZahlNr:Division by 0' zurückgegeben.<br>
 * 6) Die Antwort des Servers hat das Format: ZahlNr:Ergebnis <br>
 * 7) Die ZahlNr in der Antwort ist die ReihenfolgeNr der zweiten Zahl.<br>
 */
public class DivisionServer {

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.out.println("Bitte geben einen Port an!");
			return ;
		}
		int port=0;
		try{
			port = Integer.parseInt(args[0]);
		}catch(NumberFormatException e){
			System.out.println("Das Format der Portnummer ist ungültig !");
			return ;
		}
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(port);
		}catch (IOException e) {
			System.out.println("Fehler beim Initialisieren eines Server-Sockets!");
			e.printStackTrace();
			return;
		}
		
		//ein HashMap wird benötigt, um die empfangenen Zahlen von verschiedenen Clients zu verwalten
		HashMap objHashMap = new HashMap();
		
		final int LENGTH = 100;
		byte[] inData = new byte[LENGTH];
		byte[] outData = null;
		DatagramPacket inPacket = null;
		DatagramPacket outPacket = null;
		String strMessage = null;
		int intZahlNr = 0;
		int intZahl = 0;
		Zahl objZahl = null;
		int intNr1 = 0;
		int intZahl1 = 0;
		int intNr2 = 0;
		int intZahl2 = 0;
		
		while(true){
			//Der Server wartet auf Datagrampakete
			inPacket = new DatagramPacket(inData, inData.length);
			try {
				socket.receive(inPacket);
			} catch(IOException e){
				System.err.println("Fehler beim Empfangen eines Pakets.");
				e.printStackTrace();
				continue;
			}
			
			//die Nachricht aus dem Paket holen
			strMessage = new String(inPacket.getData(), inPacket.getOffset(), inPacket.getLength());
			System.out.println("Nachricht empfangen: " + strMessage);
			try {
				//ReihenfolgeNr und Zahl der Nachricht entnehmen
				intZahlNr = Integer.parseInt(strMessage.substring(0, strMessage.indexOf(":")));
				intZahl = Integer.parseInt(strMessage.substring(strMessage.indexOf(":") + 1));
			} catch(Exception e){
				//wenn das Paket nicht das gewünschte Format hat, weiter mit dem nächsten Paket
				continue;
			}
			
			//prüfe, ob andere Nachrichten vom gleichen Sender bei mir vorliegen
			objZahl = (Zahl)objHashMap.get(inPacket.getAddress().getHostAddress() + ":" + inPacket.getPort());
			if (objZahl == null){	//keine Nachrichten vom gleichen Sender
				//speichere diese Nachricht bei mir und warte auf die nächste
				objHashMap.put(inPacket.getAddress().getHostAddress() + ":" + inPacket.getPort(), new Zahl(intZahl, intZahlNr));
			} else {
				//ordne die Zahlen nach ReihenfolgeNr
				if (objZahl.intZahlNr < intZahlNr){
					intNr1 = objZahl.intZahlNr;
					intZahl1 = objZahl.intZahl;
					intNr2 = intZahlNr;
					intZahl2 = intZahl;
				} else {
					intNr1 = intZahlNr;
					intZahl1 = intZahl;
					intNr2 = objZahl.intZahlNr;
					intZahl2 = objZahl.intZahl;
				}
				if (intNr1 == intNr2){ //die Zahlen haben eine und dieselbe ReihenfolgeNr
					//speichere die gerade empfangene Zahl anstelle der früheren,
					//obwohl es keine Garantie gibt, dass die neue tatsächlich später abgeschickt wurde
					objHashMap.put(inPacket.getAddress().getHostAddress() + ":" + inPacket.getPort(), new Zahl(intZahl, intZahlNr));
				} else {
					//die ReihenfolgNr beider Zahlen sind aufeinanderfolgend und die erste ReihenfolgeNr ist ungerade
					if (intNr1 == intNr2 - 1 && intNr1 % 2 == 1){
						objHashMap.remove(inPacket.getAddress().getHostAddress() + ":" + inPacket.getPort()); //lösche bei mir die frühere Nachricht vom Sender
						if (intZahl2 == 0){	//Division durch 0 ist unzulässig, und Absturz des Servers auch
							outData = ("" + intNr2 + ":Division by 0").getBytes();
						} else {
							//Antwort vorbereiten
							outData = ("" + intNr2 + ":" + (intZahl1 / intZahl2)).getBytes(); 
						}
						//Paket auf die Sendung vorbereiten
						outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
						try {
							//Paket schicken
							socket.send(outPacket);
						} catch(IOException e){
							System.err.println("Fehler beim Senden eines Pakets an " 
								+ inPacket.getAddress().getHostAddress() + ":" + inPacket.getPort());
							e.printStackTrace();
							continue;	
						}
					} else { //die Zahlen gehören nicht zusammen
						//die Zahl mit der größeren ReihenfolgeNr speichern und auf die nächste warten
						objHashMap.put(inPacket.getAddress().getHostAddress() + ":" + inPacket.getPort(), new Zahl(intZahl2, intNr2));
					}
				}
			}
		}
	}
}

class Zahl{
	int intZahl;
	int intZahlNr;

	public Zahl(int intZahl, int intZahlNr){
		this.intZahl = intZahl;
		this.intZahlNr = intZahlNr;
	}
}