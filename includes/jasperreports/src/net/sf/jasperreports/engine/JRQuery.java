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
 * Represents a query used for generation of report data.
 * <p/>
 * Based on the query language, query executer implementations are used to retrieve the data and
 * create a {@link net.sf.jasperreports.engine.JRDataSource JRDataSource} to be used by the filling process.
 * <p/>
 * When using the default SQL connection data source,
 * an SQL query must also be provided for JasperReports to automatically retrieve the data.
 * For SQL queries, a very important aspect is that column names in the result set obtained from the query must match the field names
 * defined in the report template.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRQuery.java 1954 2007-11-13 12:55:16Z teodord $
 * @see net.sf.jasperreports.engine.query.JRQueryExecuterFactory
 */
public interface JRQuery extends JRCloneable
{
	
	
	/**
	 *
	 */
	public JRQueryChunk[] getChunks();

	/**
	 * Returns the query string.
	 */
	public String getText();
	

	/**
	 * Returns the query language.
	 * <p/>
	 * Based on the query language, a corresponding
	 * {@link net.sf.jasperreports.engine.query.JRQueryExecuterFactory JRQueryExecuterFactory} is used to create
	 * a {@link net.sf.jasperreports.engine.query.JRQueryExecuter JRQueryExecuter} instance.  The query executer
	 * is responsible for executing the query and creating a {@link JRDataSource JRDataSource} from the result.
	 * 
	 * @return the query language
	 */
	public String getLanguage();
}
