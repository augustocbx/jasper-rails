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
package net.sf.jasperreports.charts.fill;

import java.awt.Color;

import net.sf.jasperreports.charts.JRValueDisplay;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

/**
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRFillValueDisplay.java 1994 2007-12-05 13:48:10Z teodord $
 */
public class JRFillValueDisplay implements JRValueDisplay
{

	protected JRValueDisplay parent = null;
	/**
	 *
	 */
	public JRFillValueDisplay(JRValueDisplay valueDisplay, JRFillObjectFactory factory)
	{
		factory.put(valueDisplay, this);

		parent = valueDisplay;
	}

	/**
	 *
	 */
	public Color getColor()
	{
		return parent.getColor();
	}

	/**
	 *
	 */
	public String getMask(){
		return parent.getMask();
	}

	/**
	 *
	 */
	public JRFont getFont()
	{
		return parent.getFont();
	}
	
	/**
	 *
	 */
	public Object clone() 
	{
		return null;
	}
}
