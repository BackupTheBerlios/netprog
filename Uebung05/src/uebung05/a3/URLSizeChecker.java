package uebung05.a3;

import javax.swing.text.html.*;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.MutableAttributeSet;
import java.net.URL;
import java.io.*;

public class URLSizeChecker
extends HTMLEditorKit.ParserCallback
{
	private final URL url;
	private long size;

	public URLSizeChecker(URL url)
	{
		this.url = url;
	}

	public void handleEndTag(HTML.Tag t, int pos)
	{
		System.out.println("END\t\t"+t.toString());
	}

	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos)
	{

	}

	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos)
	{
		System.out.println("START\t\t"+t.toString());
	}

	public void handleText(char[] data, int pos)
	{

	}

	public long checkSize() throws IOException
	{
		new ParserDelegator().parse(new BufferedReader(new InputStreamReader(url.openConnection().getInputStream())),this, false);

		return size;
	}
}
