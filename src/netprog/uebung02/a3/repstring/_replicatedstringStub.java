package netprog.uebung02.a3.repstring;


/**
 * netprog/uebung02/a3/repstring/_replicatedstringStub.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.1"
 * from repstring.idl
 * Donnerstag, 20. November 2003 16.26 Uhr CET
 */

public class _replicatedstringStub extends org.omg.CORBA.portable.ObjectImpl implements netprog.uebung02.a3.repstring.replicatedstring
{

    public void set(String s)
    {
        org.omg.CORBA.portable.InputStream $in = null;
        try
        {
            org.omg.CORBA.portable.OutputStream $out = _request("set", true);
            $out.write_string(s);
            $in = _invoke($out);
            return;
        }
        catch (org.omg.CORBA.portable.ApplicationException $ex)
        {
            $in = $ex.getInputStream();
            String _id = $ex.getId();
            throw new org.omg.CORBA.MARSHAL(_id);
        }
        catch (org.omg.CORBA.portable.RemarshalException $rm)
        {
            set(s);
        }
        finally
        {
            _releaseReply($in);
        }
    } // set

    public String get()
    {
        org.omg.CORBA.portable.InputStream $in = null;
        try
        {
            org.omg.CORBA.portable.OutputStream $out = _request("get", true);
            $in = _invoke($out);
            String $result = $in.read_string();
            return $result;
        }
        catch (org.omg.CORBA.portable.ApplicationException $ex)
        {
            $in = $ex.getInputStream();
            String _id = $ex.getId();
            throw new org.omg.CORBA.MARSHAL(_id);
        }
        catch (org.omg.CORBA.portable.RemarshalException $rm)
        {
            return get();
        }
        finally
        {
            _releaseReply($in);
        }
    } // get

    public void replicateAt(netprog.uebung02.a3.repstring.replicatedstring rs)
    {
        org.omg.CORBA.portable.InputStream $in = null;
        try
        {
            org.omg.CORBA.portable.OutputStream $out = _request("replicateAt", true);
            netprog.uebung02.a3.repstring.replicatedstringHelper.write($out, rs);
            $in = _invoke($out);
            return;
        }
        catch (org.omg.CORBA.portable.ApplicationException $ex)
        {
            $in = $ex.getInputStream();
            String _id = $ex.getId();
            throw new org.omg.CORBA.MARSHAL(_id);
        }
        catch (org.omg.CORBA.portable.RemarshalException $rm)
        {
            replicateAt(rs);
        }
        finally
        {
            _releaseReply($in);
        }
    } // replicateAt

    // Type-specific CORBA::Object operations
    private static String[] __ids = {
        "IDL:repstring/replicatedstring:1.0"};

    public String[] _ids()
    {
        return (String[])__ids.clone();
    }

    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException
    {
        String str = s.readUTF();
        String[] args = null;
        java.util.Properties props = null;
        org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init(args, props).string_to_object(str);
        org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate();
        _set_delegate(delegate);
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException
    {
        String[] args = null;
        java.util.Properties props = null;
        String str = org.omg.CORBA.ORB.init(args, props).object_to_string(this);
        s.writeUTF(str);
    }
} // class _replicatedstringStub
