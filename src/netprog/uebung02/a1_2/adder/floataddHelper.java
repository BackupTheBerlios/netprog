package netprog.uebung02.a1_2.adder;


/**
* netprog/uebung02/a1_2/adder/floataddHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from adder.idl
* Dienstag, 18. November 2003 23.32 Uhr CET
*/

abstract public class floataddHelper
{
  private static String  _id = "IDL:adder/floatadd:1.0";

  public static void insert (org.omg.CORBA.Any a, netprog.uebung02.a1_2.adder.floatadd that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static netprog.uebung02.a1_2.adder.floatadd extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (netprog.uebung02.a1_2.adder.floataddHelper.id (), "floatadd");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static netprog.uebung02.a1_2.adder.floatadd read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_floataddStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, netprog.uebung02.a1_2.adder.floatadd value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static netprog.uebung02.a1_2.adder.floatadd narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof netprog.uebung02.a1_2.adder.floatadd)
      return (netprog.uebung02.a1_2.adder.floatadd)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      netprog.uebung02.a1_2.adder._floataddStub stub = new netprog.uebung02.a1_2.adder._floataddStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}