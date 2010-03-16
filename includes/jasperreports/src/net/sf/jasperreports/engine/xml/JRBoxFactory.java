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

import java.awt.Color;

import net.sf.jasperreports.engine.JRBoxContainer;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.util.JRPenUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBoxFactory.java 2045 2007-12-14 15:01:11Z teodord $
 */
public class JRBoxFactory extends JRBaseFactory
{
	private static final Log log = LogFactory.getLog(JRBoxFactory.class);

	/**
	 *
	 */
	public Object createObject(Attributes atts)
	{
		JRBoxContainer boxContainer = (JRBoxContainer) digester.peek();
		JRLineBox box = boxContainer.getLineBox();
		setBoxAttributes(atts, box);
		return box;
	}


	public static void setBoxAttributes(Attributes atts, JRLineBox box)
	{
		Byte border = (Byte)JRXmlConstants.getPenMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_border));
		if (border != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'border' attribute is deprecated. Use the <pen> tag instead.");
				
			JRPenUtil.setLinePenFromPen(border, box.getPen());
		}

		Color borderColor = JRXmlConstants.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_borderColor), null);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'borderColor' attribute is deprecated. Use the <pen> tag instead.");
				
			box.getPen().setLineColor(borderColor);
		}

		String padding = atts.getValue(JRXmlConstants.ATTRIBUTE_padding);
		if (padding != null && padding.length() > 0)
		{
			box.setPadding(Integer.parseInt(padding));
		}

		border = (Byte)JRXmlConstants.getPenMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_topBorder));
		if (border != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'topBorder' attribute is deprecated. Use the <pen> tag instead.");
				
			JRPenUtil.setLinePenFromPen(border, box.getTopPen());
		}

		borderColor = JRXmlConstants.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_topBorderColor), Color.black);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'topBorderColor' attribute is deprecated. Use the <pen> tag instead.");
				
			box.getTopPen().setLineColor(borderColor);
		}

		padding = atts.getValue(JRXmlConstants.ATTRIBUTE_topPadding);
		if (padding != null && padding.length() > 0)
		{
			box.setTopPadding(Integer.parseInt(padding));
		}

		border = (Byte)JRXmlConstants.getPenMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_leftBorder));
		if (border != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'leftBorder' attribute is deprecated. Use the <pen> tag instead.");
				
			JRPenUtil.setLinePenFromPen(border, box.getLeftPen());
		}

		borderColor = JRXmlConstants.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_leftBorderColor), Color.black);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'leftBorderColor' attribute is deprecated. Use the <pen> tag instead.");
				
			box.getLeftPen().setLineColor(borderColor);
		}

		padding = atts.getValue(JRXmlConstants.ATTRIBUTE_leftPadding);
		if (padding != null && padding.length() > 0)
		{
			box.setLeftPadding(Integer.parseInt(padding));
		}

		border = (Byte)JRXmlConstants.getPenMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_bottomBorder));
		if (border != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'bottomBorder' attribute is deprecated. Use the <pen> tag instead.");
				
			JRPenUtil.setLinePenFromPen(border, box.getBottomPen());
		}

		borderColor = JRXmlConstants.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_bottomBorderColor), Color.black);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'bottomBorderColor' attribute is deprecated. Use the <pen> tag instead.");
				
			box.getBottomPen().setLineColor(borderColor);
		}

		padding = atts.getValue(JRXmlConstants.ATTRIBUTE_bottomPadding);
		if (padding != null && padding.length() > 0)
		{
			box.setBottomPadding(Integer.parseInt(padding));
		}

		border = (Byte)JRXmlConstants.getPenMap().get(atts.getValue(JRXmlConstants.ATTRIBUTE_rightBorder));
		if (border != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'rightBorder' attribute is deprecated. Use the <pen> tag instead.");
				
			JRPenUtil.setLinePenFromPen(border, box.getRightPen());
		}

		borderColor = JRXmlConstants.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_rightBorderColor), Color.black);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
				log.warn("The 'rightBorderColor' attribute is deprecated. Use the <pen> tag instead.");
				
			box.getRightPen().setLineColor(borderColor);
		}

		padding = atts.getValue(JRXmlConstants.ATTRIBUTE_rightPadding);
		if (padding != null && padding.length() > 0)
		{
			box.setRightPadding(Integer.parseInt(padding));
		}
	}
	

}
