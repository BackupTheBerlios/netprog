package uebung05.a2;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;
import java.util.HashMap;


/**
 * NETZPROGRAMMIERUNG ÜBUNG 5 - AUFGABE 2
 * GRUPPE 6
 *
 * Auschnitt aus airport.htm:
 * ...
 * <B><TT>AAL</TT></B> - <B>Aalborg, Denmark</B> - Aalborg <BR>
 * <B><TT>AAR</TT></B> - <B>Aarhus, Denmark</B> - Tirstrup <BR>
 * ...
 * <B><TT>EAP</TT></B> - <B>, Switzerland</B><BR>
 * ...
 * <B><TT>LKL</TT></B> - <BR>
 * ...
 *
 * - Der Inhalt des TT - Tag bezeichnet das Kürzel.
 * - Der folgende Text bis zum BR - Tag den Namen des Flughafens
 */
public class AirportParser extends HTMLEditorKit.ParserCallback {

    private boolean readCode;
    private String code;
    private String location;
    private HashMap airports;


    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (t == HTML.Tag.TT)
            readCode = true;
    }

    public void handleEndTag(HTML.Tag t, int pos) {
        if (t == HTML.Tag.TT)
            readCode = false;
    }

    public void handleText(char[] text, int position) {
        if (readCode)
            code = new String(text);
        else
            location += new String(text);
    }

    public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (t == HTML.Tag.BR) {
            // Bindestrich entfernen
            // Ist die Länge des eingelesenen Flughafennamens kleiner als 4,
            // wurde der Name nicht gesetzt.
            if (location.length() <= 3)
                location = "NAME NICHT GESETZT";
            else
                location = location.substring(3);

            airports.put(code, location);

            // zur?cksetzen
            location = "";
            code = null;
            readCode = false;
        }
    }


    public HashMap parse(InputStream is) throws IOException {

        readCode = false;
        airports = new HashMap();

        // Ströme
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        // Parser
        new ParserDelegator().parse(br, this, true);

        return airports;
    }

}
