package uebung02.a4.adressbuch;


/**
* adressbuch/AdresseHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from adressbuch.idl
* Samstag, 22. November 2003 16.13 Uhr CET
*/

abstract public class AdresseHelper
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
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [5];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "Name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "Strasse",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
          _members0[2] = new org.omg.CORBA.StructMember (
            "HausNr",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ulong);
          _members0[3] = new org.omg.CORBA.StructMember (
            "PLZ",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[4] = new org.omg.CORBA.StructMember (
            "Stadt",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (AdresseHelper.id (), "Adresse", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Adresse read (org.omg.CORBA.portable.InputStream istream)
  {
    Adresse value = new Adresse ();
    value.Name = istream.read_wstring ();
    value.Strasse = istream.read_wstring ();
    value.HausNr = istream.read_ushort ();
    value.PLZ = istream.read_ulong ();
    value.Stadt = istream.read_wstring ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Adresse value)
  {
    ostream.write_wstring (value.Name);
    ostream.write_wstring (value.Strasse);
    ostream.write_ushort (value.HausNr);
    ostream.write_ulong (value.PLZ);
    ostream.write_wstring (value.Stadt);
  }

}
