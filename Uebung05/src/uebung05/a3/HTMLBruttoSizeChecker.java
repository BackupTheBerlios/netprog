package uebung05.a3;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;
import java.net.*;
import java.util.*;

public class HTMLBruttoSizeChecker
extends HTMLEditorKit.ParserCallback
{
	private static final boolean DEBUG = true;
	private URL url;
	private long size = 0;
	private final HashMap resources;
	private final String message;
	private boolean checkFlash;
	private boolean checkActiveX;

	public HTMLBruttoSizeChecker(URL url)
	throws IOException
	{
		this(url, new HashMap(), "RECOURCE");

	}

	private HTMLBruttoSizeChecker(URL url, HashMap resources, String s)
	throws IOException
	{
		this.url = url;

		String urlString = url.toExternalForm();

		if (!(urlString.toUpperCase().endsWith(".HTML") || urlString.toUpperCase().endsWith(".HTM")))
		{
			throw new MalformedURLException("URLSizeChecker can only be applied to html - sources");
		}

		this.resources = resources;
		this.message = s;
	}

	public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributeSet, int pos)
	{
		if (tag == null) return;

		try
		{
			if (tag.equals(HTML.Tag.APPLET))
			{
				String codebase = "";

				if (DEBUG) System.out.println("READING APPLET");

				if (attributeSet.getAttribute(HTML.Attribute.CODEBASE) != null)
				{
					codebase = (String) attributeSet.getAttribute(HTML.Attribute.CODEBASE);
				}

				if (attributeSet.getAttribute(HTML.Attribute.ARCHIVE) != null)
				{
					checkResource(codebase + "" + attributeSet.getAttribute(HTML.Attribute.ARCHIVE));
				}
				else
				{
					String code = (String) attributeSet.getAttribute(HTML.Attribute.CODE);
					code = code.substring(0, code.lastIndexOf('.'));
					code = code.replace('.', '/') + ".class";
					checkResource(codebase + "" + code);
				}
			}
			else if (tag.equals(HTML.Tag.BODY))
			{
				String background = (String) attributeSet.getAttribute(HTML.Attribute.BACKGROUND);

				if (background != null)
				{
					if (DEBUG) System.out.println("READING BACKROUND IMAGE");
					checkResource(background);
				}
			}
			else if (tag.equals(HTML.Tag.OBJECT))
			{
				if (attributeSet.getAttribute(HTML.Attribute.DATA) != null)
				{
					if (DEBUG) System.out.println("READING PLUGIN SOURCE");
					checkResource((String) attributeSet.getAttribute(HTML.Attribute.DATA));
				}
				else if (attributeSet.getAttribute(HTML.Attribute.CLASSID) != null)
				{
					if (attributeSet.getAttribute(HTML.Attribute.CLASSID).toString().endsWith(".class"))
					{
						checkResource((String) attributeSet.getAttribute(HTML.Attribute.CLASSID));
					}
					else if (attributeSet.getAttribute(HTML.Attribute.CLASSID).equals("CLSID:05589FA1-C356-11CE-BF01-00AA0055595A"))
					// Actixe-X (WAV, AU, MID, MP3, AVI, MPEG usw.)
					{
						if (DEBUG) System.out.println("READING ACTIVE-X CONTROL");
						checkActiveX = true;
					}
					else if (attributeSet.getAttribute(HTML.Attribute.CLASSID).equals("CLSID:D27CDB6E-AE6D-11cf-96B8-444553540000"))
					// Flash-Movie
					{
						if (DEBUG) System.out.println("READING FLASH MOVIE");
						checkFlash = true;
					}

				}
			}


		}
		catch (IOException e)
		{
			System.err.println(e.toString());
		}
	}

	public void handleEndTag(HTML.Tag tag, int pos)
	{
		if (tag == null) return;

		if (tag.equals(HTML.Tag.OBJECT))
		{
			checkActiveX = false;
			checkFlash = false;
		}
	}

	public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributeSet, int pos)
	{
		if (tag == null) return;

		try
		{
			if (tag.equals(HTML.Tag.IMG))
			{
				String src = (String) attributeSet.getAttribute(HTML.Attribute.SRC);

				if (DEBUG) System.out.println("READING IMAGE");
				checkResource(src);
			}

			else if (tag.equals(HTML.Tag.LINK))
			{
				if (DEBUG) System.out.println("READING STYLESHEET");
				checkResource((String) attributeSet.getAttribute(HTML.Attribute.HREF));
			}

			else if (tag.equals(HTML.Tag.FRAME))
			{
				URL frameURL = getURL((String) attributeSet.getAttribute(HTML.Attribute.SRC));
				HTMLBruttoSizeChecker n = new HTMLBruttoSizeChecker(frameURL, resources, "FRAMED HTML");
				size += n.checkSize();
			}
			else if (tag.equals(HTML.Tag.PARAM))
			{
				if (checkFlash)
				{
					if (attributeSet.getAttribute(HTML.Attribute.NAME) != null)
					{
						if (attributeSet.getAttribute(HTML.Attribute.NAME).toString().equalsIgnoreCase("movie"))
						{
							checkResource((String) attributeSet.getAttribute(HTML.Attribute.VALUE));
						}
					}
				}
				else if (checkActiveX)
				{
					if (attributeSet.getAttribute(HTML.Attribute.VALUE) != null)
					{
						checkResource((String) attributeSet.getAttribute(HTML.Attribute.VALUE));

					}
				}
			}


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
	throws IOException
	{
		if (attribute == null)
		{
			if (DEBUG) System.out.println("\tMISSING ATTRIBUTE");
			return;
		}

		URL url = getURL(attribute);

		if (DEBUG) System.out.print("\t" + url.toExternalForm());

		if (resources.containsKey(url.toExternalForm()))
		{
			if (DEBUG) System.out.println("\n\talready counted");
			return;
		}

		resources.put(url.toExternalForm(), url);
		int contentLength = url.openConnection().getContentLength();
		if (contentLength > 0)
		{
			size += contentLength;
		}

		if (DEBUG) System.out.println("\n\tadded " + (contentLength > 0 ? contentLength : 0) + " bytes");
	}

	private URL getURL(String attribute)
	throws MalformedURLException
	{

		if (attribute.startsWith("/"))
		{
			attribute = ".." + attribute;
		}

		try
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
		catch (URISyntaxException e)
		{
			throw new MalformedURLException(e.getMessage());
		}
	}

	public long checkSize()
	throws IOException
	{
		if (DEBUG) System.out.println("READING " + message);

		checkResource(url.toExternalForm());
		new ParserDelegator().parse(new BufferedReader(new InputStreamReader(url.openConnection().getInputStream())), this,
		                            true);

		return size;
	}
}
