package uebung03.a2;

import java.net.*;
import java.io.*;
/***
 * 
 * @author Gruppe 6
 *
 * Dieser Server stellt einen Dienst zur Addition von zwei Ganzzahlen bereit.
 * Die PortNummer, die vom Server benutzt wird, wird über die Kommandozeile übergeben.
 * Der Dienst wird mit folgendem Protokoll ermöglicht:
 * 1) Die zu addierenden Ganzzahlen müssen jeweils auf eine Zeile übermittelt werden.
 * 2) Wenn der Inhalt einer der beiden Zeilen keine Ganzzahl ist, 
 * gibt der Server 'err' zurück, sonst gibt er die Summe der Ganzzahlen zurück.
 */
public class AddierServer {

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
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		}catch (IOException e) {
			System.out.println("Fehler beim Initialisieren eines Server-Sockets!");
			e.printStackTrace();
			return;
		}
		
		int a1=0;
		int a2=0;
		Socket clientSocket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		String line = null;
		
		while(true){
			try{
				//Der Server wartet auf Anfragen
				clientSocket = serverSocket.accept();
				//Ströme vorbereiten
				in = new BufferedReader(new InputStreamReader
								(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream());
				
				//Die erste Zahl holen
				line = in.readLine();
				if (line == null) {	//prüft, ob der Client den Strom geschlossen hat
					out.println("err");
					out.flush();
					in.close();
					out.close(); 
					clientSocket.close();
					continue;
				}
				
				try{
					a1=Integer.parseInt(line);
				}catch(NumberFormatException e){
					out.println("err");
					out.flush();
					in.close();
					out.close(); 
					clientSocket.close();
					continue;
				}	
				
				//Die zweite Zahl holen
				line = in.readLine();
				try{
					a2=Integer.parseInt(line);
				}catch(NumberFormatException e){
					out.println("err");
					out.flush();
					in.close();
					out.close(); 
					clientSocket.close();
					continue;
				}	
				
				out.println(""+(a1+a2));
				out.flush();	//sendet den Inhalt des Puffers des Ausgabenstroms explizit
				in.close();
				out.close(); 
				clientSocket.close();
			}catch(IOException e){
				System.err.println("Fehler beim Aufbauen einer Verbindung mit einem Clienten!");
				e.printStackTrace();
			}
		
		}
	}
}
