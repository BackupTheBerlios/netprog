package uebung03.a1;

import java.net.*;

public class HostNames {

	public static void main(String[] args){
		if (args.length <= 0) {
			System.out.println("Bitte geben Sie den vorderen Teil einer IP-Nummer an (aaa.bbb.ccc) !");
			return ;
		}
		byte[] ip = new byte[4];
		int i;
		InetAddress addr;
		i = args[0].indexOf(".");
		if (i<0) {
			System.out.println("Das Format der übergebenen IP ist ungültig !");
			return ;
		}
		try {
			ip[0] = Byte.parseByte(args[0].substring(0,i));
		}catch(NumberFormatException e){
			System.out.println("Das Format der übergebenen IP ist ungültig !");
			return ;
		}
		int i2;
		i2 = args[0].indexOf(".", i+1);
		if (i2<0) {
			System.out.println("Das Format der übergebenen IP ist ungültig !");
			return ;
		}
		try {
			ip[1] = Byte.parseByte(args[0].substring(i+1,i2));
		}catch(NumberFormatException e){
			System.out.println("Das Format der übergebenen IP ist ungültig !");
			return ;
		}
		
		try {
			ip[2] = Byte.parseByte(args[0].substring(i2+1));
		}catch(Exception e){
			System.out.println("Das Format der übergebenen IP ist ungültig !");
			return ;
		}
		
		for (i=0; i<256; i++){
			ip[3] = (byte) i;
			try {
				addr = InetAddress.getByAddress(ip);
				System.out.println("IP "+ip[0]+"."+ip[1]+"."+ip[2]+"."+ip[3]+"  - "+addr.getCanonicalHostName());
			}catch(UnknownHostException e){
				System.out.println("IP "+ip[0]+"."+ip[1]+"."+ip[2]+"."+ip[3]+" - ungültig!");
			}
		}
	}
}
