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

import net.sf.jasperreports.engine.design.JRDesignTextElement;

import org.xml.sax.Attributes;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRTextElementFactory.java 1581 2007-02-12 14:19:02Z shertage $
 */
public class JRTextElementFactory extends JRBaseFactory
{

	/**
	 *
	 */
	public Object createObject(Attributes atts)
	{
		JRDesignTextElement textElement = (JRDesignTextElement)digester.peek();

		Byte horizontalAlignment = (Byte)JRXmlConstants.getHorizontalAlignMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_textAlignment));
		if (horizontalAlignment != null)
		{
			textElement.setHorizontalAlignment(horizontalAlignment);
		}

		Byte verticalAlignment = (Byte)JRXmlConstants.getVerticalAlignMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_verticalAlignment));
		if (verticalAlignment != null)
		{
			textElement.setVerticalAlignment(verticalAlignment);
		}

		Byte rotation = (Byte)JRXmlConstants.getRotationMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_rotation));
		if (rotation != null)
		{
			textElement.setRotation(rotation);
		}

		Byte lineSpacing = (Byte)JRXmlConstants.getLineSpacingMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_lineSpacing));
		if (lineSpacing != null)
		{
			textElement.setLineSpacing(lineSpacing);
		}

		String isStyledText = atts.getValue(JRXmlConstants.ATTRIBUTE_isStyledText);
		if (isStyledText != null && isStyledText.length() > 0)
		{
			textElement.setStyledText(Boolean.valueOf(isStyledText));
		}

		return textElement;
	}
	

}
