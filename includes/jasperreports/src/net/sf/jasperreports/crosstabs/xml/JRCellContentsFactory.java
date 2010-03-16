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
package net.sf.jasperreports.crosstabs.xml;

import java.util.Map;

import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRBaseFactory;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

import org.xml.sax.Attributes;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRCellContentsFactory.java 1759 2007-06-20 16:47:34Z lucianc $
 */
public class JRCellContentsFactory extends JRBaseFactory
{
	public static final String ELEMENT_cellContents = "cellContents";
	
	public static final String ATTRIBUTE_backcolor = "backcolor";
	public static final String ATTRIBUTE_mode = "mode";
	public static final String ATTRIBUTE_style = "style";

	public Object createObject(Attributes attributes)
	{
		JRDesignCellContents cell = new JRDesignCellContents();
		
		String backcolor = attributes.getValue(ATTRIBUTE_backcolor);
		if (backcolor != null && backcolor.length() > 0)
		{
			cell.setBackcolor(JRXmlConstants.getColor(backcolor, null));
		}
		
		String modeAtt = attributes.getValue(ATTRIBUTE_mode);
		if (modeAtt != null)
		{
			Byte mode = (Byte) JRXmlConstants.getModeMap().get(modeAtt);
			cell.setMode(mode);
		}
		
		String styleName = attributes.getValue(ATTRIBUTE_style);
		if (styleName != null)
		{
			JasperDesign jasperDesign = (JasperDesign)digester.peek(digester.getCount() - 2);
			Map stylesMap = jasperDesign.getStylesMap();

			if (stylesMap.containsKey(styleName))
			{
				JRStyle style = (JRStyle) stylesMap.get(styleName);
				cell.setStyle(style);
			}
			else
			{
				cell.setStyleNameReference(styleName);
			}
		}

		return cell;
	}
}
