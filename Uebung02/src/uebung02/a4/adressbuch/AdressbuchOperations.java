package uebung02.a4.adressbuch;


/**
* uebung02/a4/adressbuch/AdressbuchOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../libs/a4/adressbuch.idl
* Mittwoch, 10. Dezember 2003 18.19 Uhr CET
*/

public interface AdressbuchOperations 
{
  void store (uebung02.a4.adressbuch.Adresse adr);
  uebung02.a4.adressbuch.Adresse search (uebung02.a4.adressbuch.SearchParam param, String searchstr);
} // interface AdressbuchOperations
