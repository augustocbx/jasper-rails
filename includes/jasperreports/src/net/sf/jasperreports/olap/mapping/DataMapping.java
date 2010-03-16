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

import java.util.Iterator;
import java.util.List;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: DataMapping.java 1247 2006-05-05 16:13:59Z lucianc $
 */
public class DataMapping implements Mapping
{
	private final boolean formatted;
	private final List filter;
	private final List positions;
	
	public DataMapping(boolean formatted, List filter, List positions)
	{
		this.formatted = formatted;
		this.filter = filter;
		this.positions = positions;
	}
	
	
	public List getFilter()
	{
		return filter;
	}
	
	public boolean isFormatted()
	{
		return formatted;
	}
	
	public List getPositions()
	{
		return positions;
	}

	public Iterator memberMappings()
	{
		return filter == null ? new EmptyIt() : filter.iterator();
	}
	
	protected static class EmptyIt implements Iterator
	{
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		public boolean hasNext()
		{
			return false;
		}

		public Object next()
		{
			return null;
		}
	}
}
