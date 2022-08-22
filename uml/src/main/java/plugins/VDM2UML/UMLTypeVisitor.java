package plugins.VDM2UML;

import java.util.Collection;
import java.util.Vector;
import java.util.List;

import com.fujitsu.vdmj.tc.types.TCInMapType;
import com.fujitsu.vdmj.tc.types.TCMapType;
import com.fujitsu.vdmj.tc.types.TCOptionalType;
import com.fujitsu.vdmj.tc.types.TCProductType;
import com.fujitsu.vdmj.tc.types.TCSeqType;
import com.fujitsu.vdmj.tc.types.TCSeq1Type;
import com.fujitsu.vdmj.tc.types.TCSetType;
import com.fujitsu.vdmj.tc.types.TCType;
import com.fujitsu.vdmj.tc.types.TCUnionType;
import com.fujitsu.vdmj.tc.types.TCType;
import com.fujitsu.vdmj.tc.types.visitors.TCLeafTypeVisitor;

public class UMLTypeVisitor extends TCLeafTypeVisitor<Object, List<Object>, Buffers>
{
    @Override
	public List<Object> caseSetType(TCSetType node, Buffers arg)
	{
        

		return newCollection();
	}

    @Override
	public List<Object> caseSeq1Type(TCSeq1Type node, Buffers arg)
	{
        
		return caseSeqType(node, arg);
	}

    @Override
	public List<Object> caseSeqType(TCSeqType node, Buffers arg)
	{
        

		return newCollection();
	}

    @Override
	public List<Object> caseMapType(TCMapType node, Buffers arg)
	{
        

		return newCollection();
	}

    @Override
	public List<Object> caseInMapType(TCInMapType node, Buffers arg)
	{
        
		return caseMapType(node, arg);
	}

    @Override
	public List<Object> caseProductType(TCProductType node, Buffers arg)
	{
        
		return newCollection();
	}

    @Override
	public List<Object> caseUnionType(TCUnionType node, Buffers arg)
	{
        
		return newCollection();
	}

    @Override
	public List<Object> caseOptionalType(TCOptionalType node, Buffers arg)
	{
        
		return newCollection();
	}

    @Override
	public List<Object> caseType(TCType node, Buffers arg)
	{
		return newCollection();
	}

	@Override
	protected List<Object> newCollection()
	{
		return new Vector<Object>();
	}
}

