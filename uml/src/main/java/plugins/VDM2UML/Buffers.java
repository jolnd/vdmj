package plugins.VDM2UML;

import com.fujitsu.vdmj.tc.definitions.TCClassList;
import com.fujitsu.vdmj.typechecker.PublicClassEnvironment;

public class Buffers 
{
    public StringBuilder defs;
    public StringBuilder asocs;
    public static PublicClassEnvironment env;


    public Buffers(TCClassList classes)
    {
        defs = new StringBuilder();
        asocs = new StringBuilder();
        env = new PublicClassEnvironment(classes);
    }

   /*  public StringBuilder getDef()
    {
        return defs;
    }
    
    public StringBuilder getAsoc()
    {
        return asocs;
    } */
}