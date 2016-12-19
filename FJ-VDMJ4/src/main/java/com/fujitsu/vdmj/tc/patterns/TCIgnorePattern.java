/*******************************************************************************
 *
 *	Copyright (c) 2016 Fujitsu Services Ltd.
 *
 *	Author: Nick Battle
 *
 *	This file is part of VDMJ.
 *
 *	VDMJ is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	VDMJ is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with VDMJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package com.fujitsu.vdmj.tc.patterns;

import com.fujitsu.vdmj.lex.LexLocation;
import com.fujitsu.vdmj.tc.definitions.TCDefinitionList;
import com.fujitsu.vdmj.tc.types.TCType;
import com.fujitsu.vdmj.tc.types.TCUnknownType;
import com.fujitsu.vdmj.typechecker.NameScope;

public class TCIgnorePattern extends TCPattern
{
	private static final long serialVersionUID = 1L;

	public TCIgnorePattern(LexLocation location)
	{
		super(location);
	}

	@Override
	public String toString()
	{
		return "-";
	}

	@Override
	public TCDefinitionList getAllDefinitions(TCType type, NameScope scope)
	{
		return new TCDefinitionList();
	}

	@Override
	public int getLength()
	{
		return ANY;	// Special value meaning "any length"
	}

	@Override
	public TCType getPossibleType()
	{
		return new TCUnknownType(location);	// As we don't care
	}
}