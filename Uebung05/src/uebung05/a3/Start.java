package uebung05.a3;

import java.net.*;
import java.io.IOException;

public class Start
{
	public static void main(String[] args)
	{
		try
		{
			URL url = new URL(args[0]);
			System.out.println("Ermittelte Größe: "+new URLSizeChecker(url).checkSize());
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
}
