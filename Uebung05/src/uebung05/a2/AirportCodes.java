package uebung05.a2;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: </p>
 * @author not attributable
 * @version 1.0
 */
public class AirportCodes {

    //
    private HashMap data;


    /**
     *
     */
    public AirportCodes() {
        data = new HashMap();
    }


    /**
     *
     * @param code String
     * @return String
     */
    public String getLocation(String code) {
        return null;
    }


    /**
     *
     * @param code String
     * @param location String
     */
    public void put(String code, String location) {
        data.put(code, location);
    }


    /**
     *
     * @param code String
     * @return String
     */
    public String get(String code) {
        return (String) data.get(code);
    }


    /**
     *
     * @return Set
     */
    public Set getCodes() {
        return data.keySet();
    }
}
