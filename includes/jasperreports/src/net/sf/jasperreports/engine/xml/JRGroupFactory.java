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

import net.sf.jasperreports.engine.design.JRDesignGroup;

import org.xml.sax.Attributes;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRGroupFactory.java 1581 2007-02-12 14:19:02Z shertage $
 */
public class JRGroupFactory extends JRBaseFactory
{

	/**
	 *
	 */
	public Object createObject(Attributes atts)
	{
		JRDesignGroup group = new JRDesignGroup();
		
		group.setName(atts.getValue(JRXmlConstants.ATTRIBUTE_name));
		
		String isStartNewColumn = atts.getValue(JRXmlConstants.ATTRIBUTE_isStartNewColumn);
		if (isStartNewColumn != null && isStartNewColumn.length() > 0)
		{
			group.setStartNewColumn(Boolean.valueOf(isStartNewColumn).booleanValue());
		}

		String isStartNewPage = atts.getValue(JRXmlConstants.ATTRIBUTE_isStartNewPage);
		if (isStartNewPage != null && isStartNewPage.length() > 0)
		{
			group.setStartNewPage(Boolean.valueOf(isStartNewPage).booleanValue());
		}

		String isResetPageNumber = atts.getValue(JRXmlConstants.ATTRIBUTE_isResetPageNumber);
		if (isResetPageNumber != null && isResetPageNumber.length() > 0)
		{
			group.setResetPageNumber(Boolean.valueOf(isResetPageNumber).booleanValue());
		}

		String isReprintHeaderOnEachPage = atts.getValue(JRXmlConstants.ATTRIBUTE_isReprintHeaderOnEachPage);
		if (isReprintHeaderOnEachPage != null && isReprintHeaderOnEachPage.length() > 0)
		{
			group.setReprintHeaderOnEachPage(Boolean.valueOf(isReprintHeaderOnEachPage).booleanValue());
		}

		String minHeightToStartNewPage = atts.getValue(JRXmlConstants.ATTRIBUTE_minHeightToStartNewPage);
		if (minHeightToStartNewPage != null && minHeightToStartNewPage.length() > 0)
		{
			group.setMinHeightToStartNewPage(Integer.parseInt(minHeightToStartNewPage));
		}

		return group;
	}


}
