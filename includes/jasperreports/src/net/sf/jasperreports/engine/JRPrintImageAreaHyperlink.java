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
 * A component of an image map.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRPrintImageAreaHyperlink.java 1364 2006-08-31 15:13:20Z lucianc $
 * @see JRImageMapRenderer
 */
public class JRPrintImageAreaHyperlink
{

	private JRPrintImageArea area;
	private JRPrintHyperlink hyperlink;

	
	/**
	 * Creates a blank image area.
	 */
	public JRPrintImageAreaHyperlink()
	{
	}

	
	/**
	 * Creates an image area by specifying its attributes.
	 * 
	 * @param area the area
	 * @param hyperlink the hyperlink information
	 */
	public JRPrintImageAreaHyperlink(JRPrintImageArea area, JRPrintHyperlink hyperlink)
	{
		this.area = area;
		this.hyperlink = hyperlink;
	}

	
	/**
	 * Returns the area of the image map component.
	 * 
	 * @return the area of the image map component
	 */
	public JRPrintImageArea getArea()
	{
		return area;
	}
	
	
	/**
	 * Sets the area of the image map component.
	 * 
	 * @param area the area
	 */
	public void setArea(JRPrintImageArea area)
	{
		this.area = area;
	}
	
	
	/**
	 * Returns the hyperlink information of the image map component.
	 * 
	 * @return the hyperlink information of the image map component
	 */
	public JRPrintHyperlink getHyperlink()
	{
		return hyperlink;
	}
	
	
	/**
	 * Sets the hyperlink information of the image map component.
	 * 
	 * @param hyperlink the hyperlink information
	 */
	public void setHyperlink(JRPrintHyperlink hyperlink)
	{
		this.hyperlink = hyperlink;
	}
	
}
