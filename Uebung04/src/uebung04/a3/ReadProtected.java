package uebung04.a3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * Ablauf:
 * - Normales GET
 * - Antwort 401 und WWW-Authenticate: Header, der Nachweis in unterschiedlichen
 *   Schemata anfordert
 * - Weiteres GET mit Authorization: Header, der je nach Schema Parameter trägt
 * - Antwort 200
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Sebastian S.
 * @version 1.0
 */
public class ReadProtected {

    // todo: eventuell aus Text-Datei(en) ziehen
    private static final String[] usernames = {"Alle", "Jeder", "Keiner"};
    private static final String[] passwords = {"gelb", "blau", "rot"};


    // Fehlermeldungen
    // todo: Fehlermeldungen
    private static final String ERR_IO =
            "Fehler bei der Kommunikation mit dem Server";
    private static final String ERR_HTTP = "keine Http Verbindung";


    /**
     * constructor
     *
     * @param page
     */
    public ReadProtected(URL page) {

        // ------------------------------------
        //  normal verbinden und Response-Code
        //              erfragen
        // ------------------------------------

        HttpURLConnection connection = null;
        int response = HttpURLConnection.HTTP_OK;

        try {
            connection = (HttpURLConnection) page.openConnection();
            connection.connect();
            response = connection.getResponseCode();
        } catch (ClassCastException ex) {
            System.err.println(ERR_HTTP);
            return;
        } catch (IOException ex) {
            System.err.println(ERR_IO);
            return;
        }

        // ------------------------------------
        //     Auswerten des Response-Code
        // ------------------------------------

        switch (response) {

        // keine Authentifizierung erforderlich
        case HttpURLConnection.HTTP_OK: {
            System.out.println("keine Authentifizierung erforderlich");
            // Inhalt ausgeben
            try {
                out(connection.getInputStream());
            } catch (IOException e) {
                System.err.println(ERR_IO);
            }
            return;
        }

        // Authentifizierung erforderlich
        case HttpURLConnection.HTTP_UNAUTHORIZED: {
            // Benutzername und Password finden
            for (int i = 0; i < usernames.length; i++)
                for (int j = 0; j < passwords.length; j++) {
                    try {
                        InputStream is = openAuthorizedStream(page, usernames[i],
                                passwords[j]);
                        if (is != null) {
                            System.out.println("USERNAME: " + usernames[i] +
                                               "PASSWORD: " + passwords[j]);
                            out(is);
                        }
                    } catch (IOException ex) {
                        System.out.println(ERR_IO);
                        return;
                    }
                }
            System.out.println("KEINE ÜBEREINSTIMMUNG GEFUNDEN");
            return;
        }

        // Seite nicht gefunden
        case HttpURLConnection.HTTP_NOT_FOUND: {
            System.out.println("angeforderte Recource nicht gefunden");
            return;
        }

        // usw.
        // todo: noch mehr ?
        default: {
            System.out.println("Fortsetzung nicht möglich." +
                               " Http Result: " + response);
        }
        }
    }


    /**
     * openAuthorizedStream
     *
     * @param page
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    private InputStream openAuthorizedStream(URL page, String username,
                                             String password) throws
            IOException {

        // todo: realm notwendig ??

        // anforden mit Authorization Header
        HttpURLConnection connection = (HttpURLConnection) page.openConnection();
        connection.setRequestProperty("Authorization", "Basic " +
                                      encodeBase64(username, password));
        connection.connect();

        // Response-Code erfragen
        int response = connection.getResponseCode();

        // richtige User/PWD-Kombination gefunden
        if (response == HttpURLConnection.HTTP_OK)
            return connection.getInputStream();

        // falsche User/PWD-Kombination
        return null;
    }


    /**
     * out
     * gibt den Inhalt der angeforderten Recource aus.
     *
     * @param stream
     */
    private void out(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        System.out.println("---");
        while (true) {
            String l = null;
            l = br.readLine();
            if (l == null)
                break;
            else
                System.out.println(l);
        }
    }


    /**
     * encodeBase64
     *
     * @param username
     * @param password
     * @return
     */
    private String encodeBase64(String username, String password) {
        return new sun.misc.BASE64Encoder().encode((username + ":" + password).
                getBytes());
    }


    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {

        URL page = null;
        try {
            page = new URL(args[0]);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return;
        }
        new ReadProtected(page);
    }
}
