package plugins.VDM2UML;

import java.util.Collection;
import java.util.Vector;
import java.util.List;

import com.fujitsu.vdmj.tc.types.TCInMapType;
import com.fujitsu.vdmj.tc.types.TCMapType;
import com.fujitsu.vdmj.tc.types.TCOptionalType;
import com.fujitsu.vdmj.tc.types.TCProductType;
import com.fujitsu.vdmj.tc.types.TCSeqType;
import com.fujitsu.vdmj.tc.types.TCSet1Type;
import com.fujitsu.vdmj.tc.types.TCSeq1Type;
import com.fujitsu.vdmj.tc.types.TCSetType;
import com.fujitsu.vdmj.tc.types.TCType;
import com.fujitsu.vdmj.tc.types.TCUnionType;
import com.fujitsu.vdmj.tc.types.TCType;
import com.fujitsu.vdmj.tc.types.visitors.TCLeafTypeVisitor;

public class UMLTypeVisitor extends TCLeafTypeVisitor<Object, List<Object>, UMLType>
{
	@Override
	public List<Object> caseType(TCType node, UMLType arg)
	{
		arg.depth++;
		if (node.isClass(arg.env))
			arg.endClass = node.toString();
		else
			if (arg.inClassType == "")
				arg.inClassType = node.toString();
		return null;
	}

	@Override
	public List<Object> caseSet1Type(TCSet1Type node, UMLType arg)
	{
        /** Cardinality must be 1..* */
		arg.multiplicity += "1..";
		return caseSetType(node, arg);
	}

    @Override
	public List<Object> caseSetType(TCSetType node, UMLType arg)
	{
		arg.depth++;
		arg.multiplicity += "*";
		arg.inClassType += "set of ";
        node.setof.apply(new UMLTypeVisitor(), arg);

		if (arg.depth < 3)
			node.setof.apply(new UMLTypeVisitor(), arg);

		return null;
	}

    @Override
	public List<Object> caseSeq1Type(TCSeq1Type node, UMLType arg)
	{
        /** Cardinality must be (1..*) */
		arg.multiplicity += "(1..";
		return caseSeqType(node, arg);
	}

    @Override
	public List<Object> caseSeqType(TCSeqType node, UMLType arg)
	{
		arg.depth++;
		if (arg.multiplicity.contains("("))
			arg.multiplicity += ")";
		else
			arg.multiplicity = "(*)";
		
		if (arg.depth < 3)
			node.seqof.apply(new UMLTypeVisitor(), arg);
        
		return null;
	}

	@Override
	public List<Object> caseInMapType(TCInMapType node, UMLType arg)
	{
        /** Parentheses around qualifier */
		arg.qualifier += "(";
		return caseMapType(node, arg);
	}

    @Override
	public List<Object> caseMapType(TCMapType node, UMLType arg)
	{
		arg.depth++;
		if (arg.depth > 2)
			return null;
		arg.inClassType += "map ";
		if (!node.from.isClass(arg.env))
			arg.qualifier = node.from.toString();
/* 		System.out.println("- caseMapType with " + node.toString() + "\n"); */
		node.to.apply(new UMLTypeVisitor(), arg);

		return null;
	}

    @Override
	public List<Object> caseProductType(TCProductType node, UMLType arg)
	{
		arg.depth++;
		if (arg.depth < 2)
			arg.inClassType = "*";
		return null;
	}

    @Override
	public List<Object> caseUnionType(TCUnionType node, UMLType arg)
	{
		arg.depth++;
		if (arg.depth < 2)
			arg.inClassType = "|";
		return null;
	}

    @Override
	public List<Object> caseOptionalType(TCOptionalType node, UMLType arg)
	{
        /** Add [*] to variable */
		arg.depth++;
		if (arg.depth < 2)
			arg.inClassType = "[]";
		return null;
	}

	@Override
	protected List<Object> newCollection()
	{
		return new Vector<Object>();
	}
}

