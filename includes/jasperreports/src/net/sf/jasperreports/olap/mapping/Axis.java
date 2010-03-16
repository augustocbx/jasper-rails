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

import net.sf.jasperreports.engine.JRRuntimeException;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: Axis.java 1247 2006-05-05 16:13:59Z lucianc $
 */
public class Axis
{
	public static final String AXIS0 = "Columns";
	public static final String AXIS1 = "Rows";
	public static final String AXIS2 = "Pages";
	public static final String AXIS3 = "Chapters";
	public static final String AXIS4 = "Sections";
	public static final String[] AXIS_NAMES = new String[]{AXIS0, AXIS1, AXIS2, AXIS3, AXIS4};
	
	public static final int getAxisIdx (String name)
	{
		for (int i = 0; i < AXIS_NAMES.length; i++)
		{
			if (AXIS_NAMES[i].equals(name))
			{
				return i;
			}
		}
		
		throw new JRRuntimeException("No such axis \"" + name + "\".");
	}
	
	private final int idx;
	
	public Axis (int idx)
	{
		this.idx = idx;
	}
	
	public Axis (String name)
	{
		this.idx = getAxisIdx(name);
	}
	
	public int getIdx ()
	{
		return idx;
	}

	public String toString ()
	{
		return "Axis(" + idx +")";
	}
}
