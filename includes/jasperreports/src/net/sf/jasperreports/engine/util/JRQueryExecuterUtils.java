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
package net.sf.jasperreports.engine.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;

/**
 * Query executer utility class.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRQueryExecuterUtils.java 1828 2007-08-24 13:58:43Z teodord $
 */
public class JRQueryExecuterUtils
{
	private static final JRSingletonCache cache = new JRSingletonCache(JRQueryExecuterFactory.class);
	
	/**
	 * Returns a query executer factory for a query language.
	 * 
	 * @param language the query language
	 * @return a query executer factory
	 * @throws JRException
	 * @see JRProperties#QUERY_EXECUTER_FACTORY_PREFIX
	 */
	public static JRQueryExecuterFactory getQueryExecuterFactory(String language) throws JRException
	{
		String factoryClassName = JRProperties.getProperty(JRQueryExecuterFactory.QUERY_EXECUTER_FACTORY_PREFIX + language);
		if (factoryClassName == null)
		{
			throw new JRException("No query executer factory class registered for " + language + " queries.  " +
					"Create a propery named " + JRQueryExecuterFactory.QUERY_EXECUTER_FACTORY_PREFIX + language + ".");
		}
		
		return (JRQueryExecuterFactory) cache.getCachedInstance(factoryClassName);
	}
}
