package uebung05.a3;

import javax.swing.text.ChangedCharSetException;
import java.net.*;
import java.io.IOException;
import java.math.BigDecimal;

public class Start
{
	public static void main(String[] args)
	{
		try
		{
			URL //url = new URL("file:E:/Studium/Netzprogrammierung/Übungen//Übung05/libs/a3/index.html");
			//url = new URL("http://www.google.de");
			url = new URL("http://www.deviantart.com/");

			long size = new URLSizeChecker(url).checkSize();

			System.out.println("Ermittelte Größe:\t"+size+ " Bytes");
			System.out.println("\t\t\t\t\t"+round(size / 1024d)+ " kB");
			System.out.println("\t\t\t\t\t"+round(size / 1024d / 1024d)+ " MB");


		}
		catch(ChangedCharSetException e)
		{
			e.printStackTrace();
		}
		catch (MalformedURLException e)
		{
			System.err.println(e.toString());
		}
		catch (IOException e)
		{
			System.err.println(e.toString());
		}
		catch (URISyntaxException e)
		{
			System.err.println(e.toString());
		}
	}

	private static double round(double val)
	{
		return new BigDecimal(val).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
