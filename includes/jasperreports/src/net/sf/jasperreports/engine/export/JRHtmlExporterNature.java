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

/*
 * Contributors:
 * Greg Hilton 
 */

package net.sf.jasperreports.engine.export;

import net.sf.jasperreports.engine.JRPrintElement;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRHtmlExporterNature.java 1836 2007-08-31 15:02:00Z teodord $
 */
public class JRHtmlExporterNature implements ExporterNature
{
	
	private ExporterFilter filter = null;
	private final boolean deep;
	
	/**
	 * 
	 */
	public JRHtmlExporterNature(ExporterFilter filter, boolean deep)
	{
		this.filter = filter;
		this.deep = deep;
	}
	
	/**
	 * 
	 */
	public boolean isToExport(JRPrintElement element)
	{
		return filter == null || filter.isToExport(element);
	}
	
	/**
	 * 
	 */
	public boolean isDeep()
	{
		return deep;
	}
	
	/**
	 * 
	 */
	public boolean isSplitSharedRowSpan()
	{
		return false;
	}

	/**
	 * 
	 */
	public boolean isSpanCells()
	{
		return true;
	}
	
	/**
	 * 
	 */
	public boolean isIgnoreLastRow()
	{
		return false;
	}

	public boolean isHorizontallyMergeEmptyCells()
	{
		return true;
	}

}
