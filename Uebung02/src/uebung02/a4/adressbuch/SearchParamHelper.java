package uebung02.a4.adressbuch;


/**
* uebung02/a4/adressbuch/SearchParamHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../libs/a4/adressbuch.idl
* Mittwoch, 10. Dezember 2003 18.19 Uhr CET
*/

abstract public class SearchParamHelper
{
  private static String  _id = "IDL:adressbuch/SearchParam:1.0";

  public static void insert (org.omg.CORBA.Any a, uebung02.a4.adressbuch.SearchParam that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static uebung02.a4.adressbuch.SearchParam extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (uebung02.a4.adressbuch.SearchParamHelper.id (), "SearchParam", new String[] { "NAME", "STRASSE", "STADT"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static uebung02.a4.adressbuch.SearchParam read (org.omg.CORBA.portable.InputStream istream)
  {
    return uebung02.a4.adressbuch.SearchParam.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, uebung02.a4.adressbuch.SearchParam value)
  {
    ostream.write_long (value.value ());
  }

}
