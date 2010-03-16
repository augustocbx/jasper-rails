/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.olap.mapping;

import net.sf.jasperreports.olap.result.JROlapMember;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: Member.java 1446 2006-10-25 09:00:05Z lucianc $
 */
public class Member
{
	private final TuplePosition pos;
	private final MemberDepth depth;
	
	public Member(TuplePosition pos, MemberDepth depth)
	{
		this.pos = pos;
		this.depth = depth;
	}

	public Axis getAxis()
	{
		return pos.getAxis();
	}

	public MemberDepth getDepth()
	{
		return depth;
	}

	public TuplePosition getPosition()
	{
		return pos;
	}
	
	public boolean matches(JROlapMember member)
	{
		boolean match;
		int memberDepth = member.getDepth();
		
		if (depth == null)
		{
			match = true;
		}
		else
		{
			match = memberDepth == depth.getDepth();
		}
		return match;
	}

	public JROlapMember ancestorMatch(JROlapMember member)
	{
		JROlapMember ancestor;
		int memberDepth = member.getDepth();
		
		if (depth == null)
		{
			ancestor = member;
		}
		else if (depth.getDepth() <= memberDepth)
		{
			ancestor = member;
			for (int i = depth.getDepth(); i < memberDepth; ++i)
			{
				ancestor = ancestor.getParentMember();
			}
		}
		else
		{
			ancestor = null;
		}
		
		return ancestor;
	}
}
