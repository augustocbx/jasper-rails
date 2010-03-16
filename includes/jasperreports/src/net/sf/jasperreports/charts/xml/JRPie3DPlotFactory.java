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
package net.sf.jasperreports.charts.xml;

import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.xml.JRBaseFactory;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

import org.xml.sax.Attributes;


/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id: JRPie3DPlotFactory.java 1787 2007-07-19 08:37:07Z teodord $
 */
public class JRPie3DPlotFactory extends JRBaseFactory
{
	/**
	 *
	 */
	private static final String ATTRIBUTE_depthFactor = "depthFactor";

	/**
	 *
	 */
	public Object createObject(Attributes atts)
	{
		JRChart chart = (JRChart) digester.peek();
		JRDesignPie3DPlot pie3DPlot = (JRDesignPie3DPlot)chart.getPlot();
		
		String depthFactor = atts.getValue(ATTRIBUTE_depthFactor);
		if (depthFactor != null && depthFactor.length() > 0)
		{
			pie3DPlot.setDepthFactor(Double.parseDouble(depthFactor));
		}
		
		String isCircular = atts.getValue(JRXmlConstants.ATTRIBUTE_isCircular);
		if (isCircular != null && isCircular.length() > 0) {
			pie3DPlot.setCircular(Boolean.valueOf(isCircular).booleanValue());
		}

		return pie3DPlot;
	}
}
