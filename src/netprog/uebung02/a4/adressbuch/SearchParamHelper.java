package adressbuch;


/**
* adressbuch/SearchParamHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from adressbuch.idl
* Samstag, 22. November 2003 16.13 Uhr CET
*/

abstract public class SearchParamHelper
{
  private static String  _id = "IDL:adressbuch/SearchParam:1.0";

  public static void insert (org.omg.CORBA.Any a, adressbuch.SearchParam that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static adressbuch.SearchParam extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (adressbuch.SearchParamHelper.id (), "SearchParam", new String[] { "NAME", "STRASSE", "STADT"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static adressbuch.SearchParam read (org.omg.CORBA.portable.InputStream istream)
  {
    return adressbuch.SearchParam.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, adressbuch.SearchParam value)
  {
    ostream.write_long (value.value ());
  }

}
