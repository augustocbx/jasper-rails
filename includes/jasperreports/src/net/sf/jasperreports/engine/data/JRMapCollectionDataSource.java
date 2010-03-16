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
package net.sf.jasperreports.engine.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRMapCollectionDataSource.java 1229 2006-04-19 10:27:35Z teodord $
 */
public class JRMapCollectionDataSource implements JRRewindableDataSource
{

	/**
	 *
	 */
	private Collection records = null;
	private Iterator iterator = null;
	private Map currentRecord = null;
	

	/**
	 *
	 */
	public JRMapCollectionDataSource(Collection col)
	{
		records = col;

		if (records != null)
		{
			iterator = records.iterator();
		}
	}
	

	/**
	 *
	 */
	public boolean next()
	{
		boolean hasNext = false;
		
		if (iterator != null)
		{
			hasNext = iterator.hasNext();
			
			if (hasNext)
			{
				currentRecord = (Map)iterator.next();
			}
		}
		
		return hasNext;
	}
	
	
	/**
	 *
	 */
	public Object getFieldValue(JRField field)
	{
		Object value = null;
		
		if (currentRecord != null)
		{
			value = currentRecord.get(field.getName());
		}

		return value;
	}

	
	/**
	 *
	 */
	public void moveFirst()
	{
		if (records != null)
		{
			iterator = records.iterator();
		}
	}


}
