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

import net.sf.jasperreports.engine.JRCommonGraphicElement;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRStyle;

import org.xml.sax.Attributes;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBoxFactory.java 1581 2007-02-12 14:19:02Z shertage $
 */
public class JRPenFactory extends JRBaseFactory
{

	/**
	 *
	 */
	public Object createObject(Attributes atts)
	{
		JRCommonGraphicElement graphicElement = (JRCommonGraphicElement) digester.peek();
		setPenAttributes(atts, graphicElement.getLinePen());
		return graphicElement;
	}


	protected static void setPenAttributes(Attributes atts, JRPen pen)
	{
		String lineWidth = atts.getValue(JRXmlConstants.ATTRIBUTE_lineWidth);
		if (lineWidth != null && lineWidth.length() > 0)
		{
			pen.setLineWidth(Float.parseFloat(lineWidth));
		}

		Byte lineStyle = (Byte)JRXmlConstants.getLineStyleMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_lineStyle));
		if (lineStyle != null)
		{
			pen.setLineStyle(lineStyle);
		}

		String lineColor = atts.getValue(JRXmlConstants.ATTRIBUTE_lineColor);
		if (lineColor != null && lineColor.length() > 0)
		{
			pen.setLineColor(JRXmlConstants.getColor(lineColor, null));
		}
	}
	

	/**
	 * 
	 */
	public static class Style extends JRPenFactory
	{
		public Object createObject(Attributes atts)
		{
			JRStyle style = (JRStyle) digester.peek();
			setPenAttributes(atts, style.getLinePen());
			return style;
		}
	}
	
	/**
	 * 
	 */
	public static class Box extends JRPenFactory
	{
		public Object createObject(Attributes atts)
		{
			JRLineBox box = (JRLineBox) digester.peek();
			setPenAttributes(atts, box.getPen());
			return box;
		}
	}
	
	/**
	 * 
	 */
	public static class Top extends JRPenFactory
	{
		public Object createObject(Attributes atts)
		{
			JRLineBox box = (JRLineBox) digester.peek();
			setPenAttributes(atts, box.getTopPen());
			return box;
		}
	}
	
	/**
	 * 
	 */
	public static class Left extends JRPenFactory
	{
		public Object createObject(Attributes atts)
		{
			JRLineBox box = (JRLineBox) digester.peek();
			setPenAttributes(atts, box.getLeftPen());
			return box;
		}
	}
	
	/**
	 * 
	 */
	public static class Bottom extends JRPenFactory
	{
		public Object createObject(Attributes atts)
		{
			JRLineBox box = (JRLineBox) digester.peek();
			setPenAttributes(atts, box.getBottomPen());
			return box;
		}
	}
	
	/**
	 * 
	 */
	public static class Right extends JRPenFactory
	{
		public Object createObject(Attributes atts)
		{
			JRLineBox box = (JRLineBox) digester.peek();
			setPenAttributes(atts, box.getRightPen());
			return box;
		}
	}
}
