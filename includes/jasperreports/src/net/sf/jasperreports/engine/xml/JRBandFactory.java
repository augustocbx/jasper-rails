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

import net.sf.jasperreports.engine.design.JRDesignBand;

import org.xml.sax.Attributes;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBandFactory.java 1581 2007-02-12 14:19:02Z shertage $
 */
public class JRBandFactory extends JRBaseFactory
{
	
	/**
	 *
	 */
	public Object createObject(Attributes atts)
	{
		JRDesignBand band = new JRDesignBand();
		
		String height = atts.getValue(JRXmlConstants.ATTRIBUTE_height);
		if (height != null && height.length() > 0)
		{
			band.setHeight(Integer.parseInt(height));
		}

		String isSplitAllowed = atts.getValue(JRXmlConstants.ATTRIBUTE_isSplitAllowed);
		if (isSplitAllowed != null && isSplitAllowed.length() > 0)
		{
			band.setSplitAllowed(Boolean.valueOf(isSplitAllowed).booleanValue());
		}

		return band;
	}
	

}
