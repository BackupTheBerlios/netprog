package netprog.uebung02.a3.repstring;


/**
 * netprog/uebung02/a3/repstring/replicatedstringPOA.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.1"
 * from repstring.idl
 * Donnerstag, 20. November 2003 16.26 Uhr CET
 */

public abstract class replicatedstringPOA extends org.omg.PortableServer.Servant
implements netprog.uebung02.a3.repstring.replicatedstringOperations, org.omg.CORBA.portable.InvokeHandler
{

    // Constructors

    private static java.util.Hashtable _methods = new java.util.Hashtable();

    static
    {
        _methods.put("set", new java.lang.Integer(0));
        _methods.put("get", new java.lang.Integer(1));
        _methods.put("replicateAt", new java.lang.Integer(2));
    }

    public org.omg.CORBA.portable.OutputStream _invoke(String $method,
                                                       org.omg.CORBA.portable.InputStream in,
                                                       org.omg.CORBA.portable.ResponseHandler $rh)
    {
        org.omg.CORBA.portable.OutputStream out = null;
        java.lang.Integer __method = (java.lang.Integer)_methods.get($method);
        if (__method == null)
            throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

        switch (__method.intValue())
        {
            case 0:  // repstring/replicatedstring/set
                {
                    String s = in.read_string();
                    this.set(s);
                    out = $rh.createReply();
                    break;
                }

            case 1:  // repstring/replicatedstring/get
                {
                    String $result = null;
                    $result = this.get();
                    out = $rh.createReply();
                    out.write_string($result);
                    break;
                }

            case 2:  // repstring/replicatedstring/replicateAt
                {
                    netprog.uebung02.a3.repstring.replicatedstring rs = netprog.uebung02.a3.repstring.replicatedstringHelper.read(in);
                    this.replicateAt(rs);
                    out = $rh.createReply();
                    break;
                }

            default:
                throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
        }

        return out;
    } // _invoke

    // Type-specific CORBA::Object operations
    private static String[] __ids = {
        "IDL:repstring/replicatedstring:1.0"};

    public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectId)
    {
        return (String[])__ids.clone();
    }

    public replicatedstring _this()
    {
        return replicatedstringHelper.narrow(
        super._this_object());
    }

    public replicatedstring _this(org.omg.CORBA.ORB orb)
    {
        return replicatedstringHelper.narrow(
        super._this_object(orb));
    }


} // class replicatedstringPOA