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
package net.sf.jasperreports.engine.xml;

import net.sf.jasperreports.engine.JRPropertiesHolder;

import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;


/**
 * Digester rule that handles an object property.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRPropertyDigesterRule.java 1669 2007-03-26 14:50:07Z lucianc $
 */
public class JRPropertyDigesterRule extends Rule
{

	public void begin(String namespace, String name, Attributes attributes)
	{
		JRPropertiesHolder propertiesHolder = (JRPropertiesHolder) digester.peek();
		String key = attributes.getValue(JRXmlConstants.ATTRIBUTE_name);
		String value = attributes.getValue(JRXmlConstants.ATTRIBUTE_value);
		propertiesHolder.getPropertiesMap().setProperty(key, value);
	}

}
