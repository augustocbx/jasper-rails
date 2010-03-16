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
package net.sf.jasperreports.charts;

import java.awt.Color;

import net.sf.jasperreports.engine.JRFont;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net) 
 * @version $Id: JRCategoryAxisFormat.java 1662 2007-03-26 08:52:40Z teodord $
 */
public interface JRCategoryAxisFormat
{

	/**
	 * 
	 */
	public JRFont getCategoryAxisLabelFont();
	
	/**
	 * 
	 */
	public Color getCategoryAxisLabelColor();
		
	/**
	 * 
	 */
	public Color getOwnCategoryAxisLabelColor();
		
	/**
	 * 
	 */
	public JRFont getCategoryAxisTickLabelFont();
	
	/**
	 * 
	 */
	public Color getCategoryAxisTickLabelColor();
	
	/**
	 * 
	 */
	public Color getOwnCategoryAxisTickLabelColor();
	
	/**
	 * 
	 */
	public String getCategoryAxisTickLabelMask();
	
	/**
	 * 
	 */
	public Color getCategoryAxisLineColor();
	
	/**
	 * 
	 */
	public Color getOwnCategoryAxisLineColor();
	
}
