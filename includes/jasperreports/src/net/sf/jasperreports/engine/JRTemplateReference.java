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

import net.sf.jasperreports.engine.xml.JRXmlTemplateLoader;


/**
 * A static template reference, consisting of a location from which the template
 * can be loaded.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRTemplateReference.java 1759 2007-06-20 16:47:34Z lucianc $
 * @see JRTemplate#getIncludedTemplates()
 * @see JRXmlTemplateLoader#load(String)
 */
public class JRTemplateReference
{

	private String location;

	/**
	 * Creates an empty reference.
	 */
	public JRTemplateReference()
	{
	}

	/**
	 * Creates a reference for a specific location.
	 * 
	 * @param location the template location
	 */
	public JRTemplateReference(String location)
	{
		this.location = location;
	}

	/**
	 * Returns the template location.
	 * 
	 * @return the template location
	 */
	public String getLocation()
	{
		return location;
	}
	
	/**
	 * Sets the template location.
	 * 
	 * @param location the location of the template
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}
	
}
