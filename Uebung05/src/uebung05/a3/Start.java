package uebung05.a3;

import java.net.*;
import java.io.IOException;
import java.math.BigDecimal;

public class Start
{
	public static void main(String[] args)
	{
		try
		{
			URL url = new URL("file:E:\\Studium\\Netzprogrammierung\\Übungen\\Übung05\\libs\\a3\\test.html");

			long size = new URLSizeChecker(url).checkSize();

			System.out.println("Ermittelte Größe:\t"+size+ " Bytes");
			System.out.println("\t\t\t\t\t"+round(size / 1024d)+ " kB");
			System.out.println("\t\t\t\t\t"+round(size / 1024d / 1024d)+ " MB");
		}
		catch (MalformedURLException e)
		{
			System.err.println(e.toString());
		}
		catch (IOException e)
		{
			System.err.println(e.toString());
		}
	}

	private static double round(double val)
	{
		return new BigDecimal(val).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
