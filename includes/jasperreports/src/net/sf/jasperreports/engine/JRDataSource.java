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
package net.sf.jasperreports.engine;


/**
 * This interface represents the abstract representation of a JasperReports data source. All data source types must
 * implement this interface.
 * <p>
 * JasperReports provides default implementations of result set, bean collections and bean arrays data sources.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDataSource.java 1229 2006-04-19 10:27:35Z teodord $
 */
public interface JRDataSource
{


	/**
	 * Tries to position the cursor on the next element in the data source.
	 * @return true if there is a next record, false otherwise
	 * @throws JRException if any error occurs while trying to move to the next element
	 */
	public boolean next() throws JRException;

	/**
	 * Gets the field value for the current position.
	 * @return an object containing the field value. The object type must be the field object type.
	 */
	public Object getFieldValue(JRField jrField) throws JRException;


}
