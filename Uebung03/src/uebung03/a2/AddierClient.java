package uebung03.a2;

import java.io.*;
import java.net.*;

/***
 * 
 * @author Gruppe 6
 *
 *Diese Klasse implementiert einen Client, der die Additionsdienst
 *eines Servers benutzt.
 *Die IP, die Portnummer und die beide Ganzzahlen werden über die Kommandozeile
 *übergeben.
 */
class AddierClient {

	public static void main (String args[]) throws java.io.IOException {
		if (args.length < 4) {
			System.out.println("Bitte geben Sie eine IP- und eine Port-Nummer und zwei Ganzzahlen" +
				" im folgenden Format an: IP port a1 a2");
			return ;
		}
		int port = 0;
		int a1 = 0;
		int a2 = 0;
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
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try{
			//Verbindung aufbauen
			socket = new Socket(addr.getHostAddress(),port);
		}catch(IOException e){
			System.err.println("Fehler beim Aufbauen einer Verbindung mit dem Server!");
			e.printStackTrace();
			return;
		}
		try{
			//Ströme vorbereiten
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			//Befehl schicken
			out.println(args[2]);
			out.println(args[3]);
			out.flush();//sendet den Inhalt des Puffers des Ausgabenstroms explizit
			System.out.println(in.readLine());	//Antwort ausgeben
		}catch(IOException e){
			System.err.println("Fehler bei der Kommunikation mit dem Server!");
			e.printStackTrace();
		}
		try{
			out.close();//falls out gleich null ist
		}catch(Exception e){}
		try{
			in.close();
		}catch(IOException e){}
		try{
			socket.close();
		}catch(IOException e){}	
	}
}
