package uebung02.a4.adressbuch;

/**
* uebung02/a4/adressbuch/AdressbuchHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../libs/a4/adressbuch.idl
* Mittwoch, 10. Dezember 2003 18.19 Uhr CET
*/

public final class AdressbuchHolder implements org.omg.CORBA.portable.Streamable
{
  public uebung02.a4.adressbuch.Adressbuch value = null;

  public AdressbuchHolder ()
  {
  }

  public AdressbuchHolder (uebung02.a4.adressbuch.Adressbuch initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = uebung02.a4.adressbuch.AdressbuchHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    uebung02.a4.adressbuch.AdressbuchHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return uebung02.a4.adressbuch.AdressbuchHelper.type ();
  }

}
