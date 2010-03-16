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
package net.sf.jasperreports.olap.mondrian;

import java.util.Iterator;

import mondrian.olap.Member;
import mondrian.olap.Position;
import net.sf.jasperreports.olap.result.JROlapMember;
import net.sf.jasperreports.olap.result.JROlapMemberTuple;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRMondrianTuple.java 1775 2007-07-02 16:21:50Z lucianc $
 */
public class JRMondrianTuple implements JROlapMemberTuple
{

	private final JRMondrianMember[] members;
	
	public JRMondrianTuple(Position position, JRMondrianFactory factory)
	{
		members = new JRMondrianMember[position.size()];
		int idx = 0;
		for (Iterator it = position.iterator(); it.hasNext(); ++idx)
		{
			Member member = (Member) it.next();
			members[idx] = factory.createMember(member);
		}
	}

	public JROlapMember[] getMembers()
	{
		return members;
	}

}
