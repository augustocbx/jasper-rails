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
package net.sf.jasperreports.renderers;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import net.sf.jasperreports.engine.JRAbstractSvgRenderer;
import net.sf.jasperreports.engine.JRConstants;

import org.jfree.chart.JFreeChart;


/**
 * Renderer used by JasperReports charts.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JFreeChartRenderer.java 1364 2006-08-31 15:13:20Z lucianc $
 */
public class JFreeChartRenderer extends JRAbstractSvgRenderer
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private JFreeChart chart;


	/**
	 * Create a chart renderer.
	 * 
	 * @param chart the chart
	 */
	public JFreeChartRenderer(JFreeChart chart) 
	{
		this.chart = chart;
	}
	
	
	public void render(Graphics2D grx, Rectangle2D rectangle) 
	{
		if (chart != null)
		{
			chart.draw(grx, rectangle);
		}
	}
	
	
	/**
	 * Return the chart this renderer uses.
	 * 
	 * @return the chart this renderer uses.
	 */
	public JFreeChart getChart()
	{
		return chart;
	}
	
}
