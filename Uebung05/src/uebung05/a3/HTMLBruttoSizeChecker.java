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
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                     Constants                     |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	private static final boolean DEBUG = true;

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                      Fields                       |   \\
	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

	private final URL url;
	private long size = 0;
	private final HashMap resources;
	private final String message;
	private boolean checkFlash;
	private boolean checkActiveX;

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                   Constructors                    |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public HTMLBruttoSizeChecker(URL url)
	throws IOException
	{
		this(url, new HashMap(), "RECOURCE");
	}

	private HTMLBruttoSizeChecker(URL url, HashMap resources, String message)
	throws IOException
	{
		this.url = url;

		if (!url.openConnection().getContentType().startsWith("text/html"))
		{
			throw new MalformedURLException("URLSizeChecker can only be applied to html - sources");
		}

		this.resources = resources;
		this.message = message;
	}

	//  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
	//  |                Modifying Methods                  |   \\
	//  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

	public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributeSet, int pos)
	{
		if (tag == null) return;

		try
		{
			if (tag.equals(HTML.Tag.APPLET))
			{
				if (DEBUG) System.out.println("READING APPLET");

				String codebase = "";

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
				URL frameURL = new URL(url,(String) attributeSet.getAttribute(HTML.Attribute.SRC));

				// just let the new HTMLBruttoSizeChecker start with my HashMap to avoid
				// multiple countings - clever isn't it?
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

	//    --------|=|-----------|=||=|-----------|=|--------    \\

	/**
	 * Checks if the given resource has already been counted, adds it's content's length if not.
	 * Marks the resource as checked then.
	 *
	 * @param resource
	 * @throws IOException
	 */
	private void checkResource(String resource)
	throws IOException
	{
		if (resource == null)
		{
			if (DEBUG) System.out.println("\tMISSING Resource");
			return;
		}

		URL url = new URL(this.url,resource);

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
