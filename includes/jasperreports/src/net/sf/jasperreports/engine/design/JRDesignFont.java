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
package net.sf.jasperreports.engine.design;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultFontProvider;
import net.sf.jasperreports.engine.JRReportFont;
import net.sf.jasperreports.engine.base.JRBaseFont;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignFont.java 1915 2007-10-22 16:11:10Z lucianc $
 */
public class JRDesignFont extends JRBaseFont
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_DEFAULT_FONT_PROVIDER = "defaultFontProvider";


	/**
	 *
	 */
	public JRDesignFont()
	{
	}
		

	/**
	 *
	 */
	public JRDesignFont(JRDefaultFontProvider defaultFontProvider)
	{
		super(defaultFontProvider);
	}
		

	/**
	 *
	 */
	public void setDefaultFontProvider(JRDefaultFontProvider defaultFontProvider)
	{
		Object old = this.defaultFontProvider;
		this.defaultFontProvider = defaultFontProvider;
		getEventSupport().firePropertyChange(PROPERTY_DEFAULT_FONT_PROVIDER, old, this.defaultFontProvider);
	}


	/**
	 *
	 */
	public void setReportFont(JRReportFont reportFont)//FIXME remove?
	{
		Object old = this.reportFont;
		this.reportFont = reportFont;
		getEventSupport().firePropertyChange(PROPERTY_REPORT_FONT, old, this.reportFont);
	}


}
