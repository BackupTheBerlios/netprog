package uebung05.a3;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

public class Start
{
	public static void main(String[] args)
	{
		try
		{
			URL url = new URL(args[0]); //new File("libs/a3/index.html").toURL();

			long size = new HTMLBruttoSizeChecker(url).checkSize();

			System.out.println("\nErmittelte Größe von " + url.toExternalForm());
			System.out.println("\t" + size + " Bytes");
			System.out.println("\t" + round(size / 1024d) + " kB");
			System.out.println("\t" + round(size / 1024d / 1024d) + " MB");
		}
		catch (IOException e)
		{
			System.err.println(e.toString());
		}

	}

	private static double round(double val)
	{
		return new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
