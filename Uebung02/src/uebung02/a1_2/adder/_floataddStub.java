package uebung02.a1_2.adder;


/**
* uebung02/a1_2/adder/_floataddStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../libs/a1_2/adder.idl
* Mittwoch, 26. November 2003 20.50 Uhr CET
*/

public class _floataddStub extends org.omg.CORBA.portable.ObjectImpl implements uebung02.a1_2.adder.floatadd
{

  public void sum (org.omg.CORBA.DoubleHolder a, double b)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sum", true);
                $out.write_double (a.value);
                $out.write_double (b);
                $in = _invoke ($out);
                a.value = $in.read_double ();
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                sum (a, b        );
            } finally {
                _releaseReply ($in);
            }
  } // sum

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:adder/floatadd:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _floataddStub
