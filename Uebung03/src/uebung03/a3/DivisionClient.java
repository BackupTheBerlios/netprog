package uebung03.a3;

import java.io.*;
import java.net.*;

/***
 * 
 * @author Gruppe 6
 *
 * Diese Klasse implementiert einen Client, der die Divisionsdienst eines Servers benutzt.<br>
 * Die IP, die Portnummer und die beide Ganzzahlen werden über die Kommandozeile
 * übergeben.<br>
 * Der Client versucht maximal dreimal die Division durchzuführen.<br>
 * Der Client wartet maximal 3 Sekunden auf Antwort.
 */
class DivisionClient {

	public static void main (String args[]) throws java.io.IOException {
		if (args.length < 4) {
			System.out.println("Bitte geben Sie eine IP- und eine Port-Nummer und zwei Ganzzahlen" +
				" im folgenden Format an: IP port a1 a2");
			return ;
		}
		int port = 0;
		InetAddress addr = null;
		
		try {
			addr = InetAddress.getByName(args[0]);
		}catch(UnknownHostException e){
			System.out.println("Das Format der IP-Nummer ist ungültig !");
			return ;
		}
		try{
			port = Integer.parseInt(args[1]);
		}catch(NumberFormatException e){
			System.out.println("Das Format der Portnummer ist ungültig !");
			return ;
		}
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(3000);	//maximal 3 Sekunden auf Antwort warten
		}catch (IOException e) {
			System.out.println("Fehler beim Initialisieren eines Sockets!");
			e.printStackTrace();
			return;
		}
		
		final int LENGTH = 100;
		byte[] inData = new byte[LENGTH];
		byte[] outData = null;
		DatagramPacket inPacket = null;
		DatagramPacket outPacket = null;
		String strMessage = null;
		int intZahlNr = 0;
		String strErgebnis;
		int intErgebnis = 0;
		int intErgebnisNr;
		
		loop1:
		while (intZahlNr < 6){	//maximal dreimal versuchen (2 Pakete pro Versuch)
			intZahlNr++;
			outData = ("" + intZahlNr + ":" + args[2]).getBytes();
			outPacket = new DatagramPacket(outData, outData.length, addr, port);
			try {
				socket.send(outPacket);	//die erste Zahl schicken
			} catch(IOException e){
				System.out.println("Fehler beim Senden eines Pakets an den Server.");
				e.printStackTrace();
				intZahlNr++;	//unbedingt erhöhen, damit der nächste Versuch mit einer ungerade Zahl anfängt
				continue;
			}
			
			intZahlNr++;
			outData = ("" + intZahlNr + ":" + args[3]).getBytes();
			outPacket = new DatagramPacket(outData, outData.length, addr, port);
			try {
				socket.send(outPacket);	//die zweite Zahl schicken
			} catch(IOException e){
				System.out.println("Fehler beim Senden eines Pakets an den Server.");
				e.printStackTrace();
				continue;
			}
			
			//Pakete empfangen, bis der Timeout schlägt
			while (true) {
				inPacket = new DatagramPacket(inData, inData.length);
				try {
					socket.receive(inPacket);
				} catch(SocketTimeoutException e){
					System.out.println("Der Server hat innerhalb 3 sec. nicht geantwortet.");
					continue loop1;
				} catch(IOException e){
					System.out.println("Fehler beim Empfangen eines Pakets.");
					e.printStackTrace();
					continue loop1;
				}
				
				//die Nachricht aus dem Paket holen
				strMessage = new String(inPacket.getData(), inPacket.getOffset(), inPacket.getLength());
				try {
					//ErgebnisNr und Ergebnis der Nachricht entnehmen
					intErgebnisNr = Integer.parseInt(strMessage.substring(0, strMessage.indexOf(":")));
					strErgebnis = strMessage.substring(strMessage.indexOf(":") + 1);
				} catch(Exception e){
					//wenn das Paket nicht das gewünschte Format hat
					System.out.println("Der Server hat einen Paket in einem ungültigen Format zurückgeschickt.");
					continue;
				}
				
				if (intErgebnisNr == intZahlNr){ //genau diese Nachricht interessiert mich
					try {
						intErgebnis = Integer.parseInt(strErgebnis);
						System.out.println("Das Ergebnis ist: " + intErgebnis);
					} catch (NumberFormatException e){
						System.out.println("Das Ergebnis ist: " + strErgebnis);
					}
					//zum Schluss kommen
					break loop1;
				} else { //ein Paket, der sich auf früheren Anfragen bezieht
					//auf weitere Pakete warten
					continue;
				}
			}
		}
		
		socket.close();
	}
}
