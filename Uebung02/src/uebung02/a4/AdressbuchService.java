package uebung02.a4;

import java.util.Vector;

import uebung02.a4.adressbuch.*;

/**
 *	Der Server, der den Adressbuchdienst zur Verfügung stellt
 */
public class AdressbuchService extends AdressbuchPOA
{
    private Vector objAdressen = null;

    public AdressbuchService()
    {
        objAdressen = new Vector();
    }

    /**
     * Speichert die übergebene Adresse.
     * @param adr die zu speichernde Adresse
     */
    public void store(Adresse adr)
    {
        if (adr != null)
        {
            objAdressen.add(adr);
        }
    }

    /**
     * Sucht nach dem ersten Element, das das Kriterium erfüllt
     * @param param der Typ des Suchkriteriums
     * @param searchstr die Suchzeichenkette
     * @return der erste gefundene Eintrag im Adressbuch
     */
    public Adresse search(SearchParam param, String searchstr)
    {
        int i;
        for (i = 0; i < objAdressen.size(); i++)
        {
            switch (param.value())
            {
                case SearchParam._NAME:
                    if (((Adresse)objAdressen.get(i)).Name.equals(searchstr))
                    {
                        return (Adresse)objAdressen.get(i);
                    }
                    break;
                case SearchParam._STADT:
                    if (((Adresse)objAdressen.get(i)).Stadt.equals(searchstr))
                    {
                        return (Adresse)objAdressen.get(i);
                    }
                    break;
                case SearchParam._STRASSE:
                    if (((Adresse)objAdressen.get(i)).Strasse.equals(searchstr))
                    {
                        return (Adresse)objAdressen.get(i);
                    }
                    break;
            }
        }
        return new Adresse(new String(""), new String(""), (short)0, 0, new String(""));
    }
}
