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

package com.fujitsu.vdmj.ast.definitions;

import com.fujitsu.vdmj.ast.definitions.visitors.ASTDefinitionVisitor;
import com.fujitsu.vdmj.ast.lex.LexNameToken;
import com.fujitsu.vdmj.lex.LexLocation;

/**
 * A class to hold a definition of, as yet, an unknown type. See POValueDefinition
 * and TCExportedValue for the purpose of this class.
 */
public class ASTUntypedDefinition extends ASTDefinition
{
	private static final long serialVersionUID = 1L;

	public ASTUntypedDefinition(LexLocation location, LexNameToken name)
	{
		super(location, name);
	}

	@Override
	public String toString()
	{
		return "Unknown type definition " + name;
	}

	@Override
	public String kind()
	{
		return "untyped";
	}

	@Override
	public <R, S> R apply(ASTDefinitionVisitor<R, S> visitor, S arg)
	{
		return visitor.caseUntypedDefinition(this, arg);
	}
}
