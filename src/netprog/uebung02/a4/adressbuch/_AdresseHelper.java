package netprog.uebung02.a4.adressbuch;


/**
* adressbuch/_AdresseHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from adressbuch.idl
* Samstag, 22. November 2003 16.13 Uhr CET
*/

abstract public class _AdresseHelper
{
  private static String  _id = "IDL:adressbuch/Adresse:1.0";

  public static void insert (org.omg.CORBA.Any a, Adresse that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Adresse extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = AdresseHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (_AdresseHelper.id (), "Adresse", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Adresse read (org.omg.CORBA.portable.InputStream istream)
  {
    Adresse value = null;
    value = AdresseHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Adresse value)
  {
    AdresseHelper.write (ostream, value);
  }

}
