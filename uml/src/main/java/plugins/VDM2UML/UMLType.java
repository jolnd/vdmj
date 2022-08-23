package plugins.VDM2UML;

import java.util.ArrayList;

import com.fujitsu.vdmj.tc.definitions.TCClassList;
import com.fujitsu.vdmj.typechecker.PublicClassEnvironment;

public class UMLType
{
    public Boolean isAssoc = false;
    public String qualifier = "";
    public String multiplicity = "";
    public String endClass = "";
    public String inClassType = "";
    public int depth = 0;
    public PublicClassEnvironment env;

    public UMLType(PublicClassEnvironment _env) {
        env = _env;
    }
}