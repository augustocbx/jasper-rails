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
package net.sf.jasperreports.crosstabs.design;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRConstants;


/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRCrosstabOrigin.java 1865 2007-09-19 13:27:44Z lucianc $
 */
public class JRCrosstabOrigin implements Serializable
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public static final byte TYPE_HEADER_CELL = 1;
	public static final byte TYPE_WHEN_NO_DATA_CELL = 2;
	public static final byte TYPE_ROW_GROUP_HEADER = 3;
	public static final byte TYPE_ROW_GROUP_TOTAL_HEADER = 4;
	public static final byte TYPE_COLUMN_GROUP_HEADER = 5;
	public static final byte TYPE_COLUMN_GROUP_TOTAL_HEADER = 6;
	public static final byte TYPE_DATA_CELL = 7;

	private final JRDesignCrosstab crosstab;
	private final byte type;
	private final String rowGroupName;
	private final String columnGroupName;
	
	public JRCrosstabOrigin(JRDesignCrosstab crosstab, byte type)
	{
		this(crosstab, type, null, null);
	}
	
	public JRCrosstabOrigin(JRDesignCrosstab crosstab, byte type, String rowGroupName, String columnGroupName)
	{
		this.crosstab = crosstab;
		this.type = type;
		this.rowGroupName = rowGroupName;
		this.columnGroupName = columnGroupName;
	}
	
	public byte getType()
	{
		return type;
	}
	
	public String getRowGroupName()
	{
		return rowGroupName;
	}
	
	public String getColumnGroupName()
	{
		return columnGroupName;
	}
	
	public JRDesignCrosstab getCrosstab()
	{
		return crosstab;
	}
	
}
