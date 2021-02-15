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
 *	SPDX-License-Identifier: GPL-3.0-or-later
 *
 ******************************************************************************/

package com.fujitsu.vdmj.in.patterns;

import com.fujitsu.vdmj.in.INMappedList;
import com.fujitsu.vdmj.tc.patterns.TCPatternList;
import com.fujitsu.vdmj.tc.patterns.TCPatternListList;

public class INPatternListList extends INMappedList<TCPatternList, INPatternList>
{
	private static final long serialVersionUID = 1L;
	
	public INPatternListList()
	{
		super();
	}
	
	public INPatternListList(TCPatternListList from) throws Exception
	{
		super(from);
	}
	
	@Override
	public INPatternListList subList(int from, int to)
	{
		INPatternListList result = new INPatternListList();
		
		for (int i=from; i<to; i++)
		{
			result.add(get(i));
		}
		
		return result;
	}
}
