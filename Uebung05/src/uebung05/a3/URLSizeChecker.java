package uebung05.a3;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;
import java.net.*;
import java.util.*;

public class URLSizeChecker
extends HTMLEditorKit.ParserCallback
{
	private final URL url;
	private long size = 0;
	private final HashMap resources;

	public URLSizeChecker(URL url)
	{
		this(url, new HashMap());

	}

	private URLSizeChecker(URL url, HashMap recources)
	{
		this.url = url;
		this.resources = recources;


	}

	public void handleEndTag(HTML.Tag t, int pos)
	{

	}

	public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributeSet, int pos)
	{
		try
		{
			if (tag.equals(HTML.Tag.IMG))
			{
				checkResource((String) attributeSet.getAttribute(HTML.Attribute.SRC));
			}

			else if (tag.equals(HTML.Tag.APPLET))
			{
				if (attributeSet.getAttribute(HTML.Attribute.ARCHIVE) != null)
				{
					checkResource((String) attributeSet.getAttribute(HTML.Attribute.ARCHIVE));
				}
				else
				{
					checkResource((String) attributeSet.getAttribute(HTML.Attribute.CODE));
				}
			}

			else if (tag.equals(HTML.Tag.BODY))
			{
				checkResource((String) attributeSet.getAttribute(HTML.Attribute.BACKGROUND));
			}

			else if (tag.equals(HTML.Tag.LINK))
			{
				checkResource((String) attributeSet.getAttribute(HTML.Attribute.HREF));
			}

			else if (tag.equals(HTML.Tag.FRAME))
			{
				URL frameURL = getURL((String) attributeSet.getAttribute(HTML.Attribute.SRC));
				URLSizeChecker n = new URLSizeChecker(frameURL, resources);
				size += n.checkSize();
			}

			// todo implement object -> classid, falls java
			// todo implement applet -> codebase
			// todo implement oject -> data (allgemeine plugins)
			// todo implement oject -> param -> value (wav, mpeg, midi, etc.)
			// todo implement oject -> param -> value (flash)
		}
		catch (URISyntaxException e)
		{
			System.err.println(e.toString());
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

	private void checkResource(String attribute)
	throws URISyntaxException, IOException
	{
		if (attribute == null) return;

		URL url = getURL(attribute);

		if (resources.containsKey(url.toExternalForm())) return;

		resources.put(url.toExternalForm(), url);
		int contentLength = url.openConnection().getContentLength();
		if (contentLength > 0) size += contentLength;
	}

	private URL getURL(String attribute)
	throws URISyntaxException, MalformedURLException
	{
		URI uri = new URI(attribute);
		if (!uri.isAbsolute())
		{
			uri = new URI(url.toExternalForm().substring(0, url.toExternalForm().lastIndexOf('/') + 1) + uri.toString());
		}

		String[] s = uri.normalize().toURL().toExternalForm().split("/");
		Vector elems = new Vector();
		for (int i = 0; i < s.length; i++)
		{
			String s1 = s[i];
			if (s1.equals(".."))
			{
				elems.removeElement(elems.lastElement());
			}
			else
			{
				elems.add(s1);
			}

		}

		String r = "";
		for (int i = 0; i < elems.size(); i++)
		{
			r += elems.elementAt(i) + (i == elems.size() - 1 ? "" : "/");
		}

		uri = new URI(r);

		return uri.normalize().toURL();
	}




	public long checkSize()
	throws IOException, URISyntaxException
	{   checkResource(url.toExternalForm());

		ParserDelegator parserDelegator = new ParserDelegator();

		parserDelegator.parse(new BufferedReader(new InputStreamReader(url.openConnection().getInputStream())), this,
		                            true);

		return size;
	}
}
