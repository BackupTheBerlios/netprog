package uebung05.a2;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.MutableAttributeSet;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


/**
 * Created by IntelliJ IDEA.
 * User: sebastian
 * Date: Jan 23, 2004
 * Time: 8:43:35 PM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * ...
 * <B><TT>AAL</TT></B> - <B>Aalborg, Denmark</B> - Aalborg <BR>
 * <B><TT>AAR</TT></B> - <B>Aarhus, Denmark</B> - Tirstrup <BR>
 * ...
 * <B><TT>EAP</TT></B> - <B>, Switzerland</B><BR>
 * ...
 * <B><TT>LKL</TT></B> - <BR>
 * ...
 *
 *
 *
 *
 *     readCode = true
 *         |
 *         |   readCode = false
 *         |       |
 *         |       |
 * ... <B><TT>AAR</TT></B> - <B>Aarhus, Denmark</B> - Tirstrup <BR> ...
 */
public class HTMLParser extends HTMLEditorKit.ParserCallback {

    private boolean readCode;
    private String code;
    private String location;
    private AirportCodes data;




    /**
     * @param t
     * @param a
     * @param pos
     */
    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (t == HTML.Tag.TT)
            readCode = true;
    }

    /**
     * @param t
     * @param pos
     */
    public void handleEndTag(HTML.Tag t, int pos) {
        if (t == HTML.Tag.TT)
            readCode = false;
    }


    /**
     * @param text
     * @param position
     */
    public void handleText(char[] text, int position) {
        if (readCode)
            code = new String(text);
        else
            location += new String(text);
    }


    /**
     * @param t
     * @param a
     * @param pos
     */
    public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (t == HTML.Tag.BR) {
            // " - " heraus filtern
            //
            if (location.length() <= 3)
                location = "NAME NICHT GESETZT";
            else
                location = location.substring(3);

            data.put(code, location);

            // zur?cksetzen
            location = "";
        }
    }


    /**
     * @param is
     * @return
     * @throws IOException
     */
    public AirportCodes parse(InputStream is) throws IOException {

        readCode = false;
        data = new AirportCodes();

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        // Parser
        ParserDelegator parser = new ParserDelegator();
        parser.parse(br, this, true); //todo: ignoreCharSet

        return data;
    }

}
