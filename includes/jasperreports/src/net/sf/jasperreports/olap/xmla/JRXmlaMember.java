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
package net.sf.jasperreports.olap.xmla;

import mondrian.olap.Member;
import net.sf.jasperreports.olap.result.JROlapMember;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRXmlaMember.java 1446 2006-10-25 09:00:05Z lucianc $
 */
public class JRXmlaMember implements JROlapMember
{

	private final String name;
	private final String uniqueName;
	private final String dimensionName;
	private final String levelName;
	private final int depth;

	public JRXmlaMember(String name, String uniqueName, String dimensionName, String levelName, int depth)
	{
		this.name = name;
		this.uniqueName = uniqueName;
		this.dimensionName = dimensionName;
		this.levelName = levelName;
		this.depth = depth;
	}
	
	public int getDepth()
	{
		return depth;
	}

	public String getName()
	{
		return name;
	}

	public JROlapMember getParentMember()
	{
		// not implemented
		return null;
	}

	public Object getPropertyValue(String propertyName)
	{
		throw new UnsupportedOperationException("Member properties are not supported by the XML/A query executer");
	}

	public String getUniqueName()
	{
		return uniqueName;
	}
	
	public String getLevelName()
	{
		return levelName;
	}

	
	public String getDimensionName()
	{
		return dimensionName;
	}

	public Member getMondrianMember()
	{
		throw new UnsupportedOperationException("XML/A member cannot be converted to a mondrian.olap.Member");
	}

}
