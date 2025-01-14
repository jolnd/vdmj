/*******************************************************************************
 *
 *	Copyright (c) 2020 Nick Battle.
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
 *	SPDX-License-Identifier: GPL-3.0-or-later
 *
 ******************************************************************************/

package com.fujitsu.vdmj.ast.patterns.visitors;

import java.util.Collection;

import com.fujitsu.vdmj.ast.ASTVisitorSet;
import com.fujitsu.vdmj.ast.patterns.ASTMultipleBind;
import com.fujitsu.vdmj.ast.patterns.ASTMultipleSeqBind;
import com.fujitsu.vdmj.ast.patterns.ASTMultipleSetBind;
import com.fujitsu.vdmj.ast.patterns.ASTMultipleTypeBind;

/**
 * This ASTMultipleBind visitor visits all of the leaves of a bind tree and calls
 * the basic processing methods for the simple cases.
 */
public abstract class ASTLeafMultipleBindVisitor<E, C extends Collection<E>, S> extends ASTMultipleBindVisitor<C, S>
{
	protected ASTVisitorSet<E, C, S> visitorSet = new ASTVisitorSet<E, C, S>()
	{
		@Override
		protected void setVisitors()
		{
			multiBindVisitor = ASTLeafMultipleBindVisitor.this;
		}

		@Override
		protected C newCollection()
		{
			return ASTLeafMultipleBindVisitor.this.newCollection();
		}
	};

 	@Override
	abstract public C caseMultipleBind(ASTMultipleBind node, S arg);

 	@Override
	public C caseMultipleSeqBind(ASTMultipleSeqBind node, S arg)
	{
		return caseMultipleBind(node, arg);
	}

 	@Override
	public C caseMultipleSetBind(ASTMultipleSetBind node, S arg)
	{
		return caseMultipleBind(node, arg);
	}

 	@Override
	public C caseMultipleTypeBind(ASTMultipleTypeBind node, S arg)
	{
		return caseMultipleBind(node, arg);
	}

 	abstract protected C newCollection();
}
