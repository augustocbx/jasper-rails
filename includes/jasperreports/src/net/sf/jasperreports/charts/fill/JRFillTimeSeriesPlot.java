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

import net.sf.jasperreports.charts.JRTimeSeriesPlot;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.fill.JRFillChartPlot;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.util.JRStyleResolver;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRFillTimeSeriesPlot.java 1577 2007-02-09 11:25:48Z teodord $
 */

public class JRFillTimeSeriesPlot extends JRFillChartPlot implements JRTimeSeriesPlot {

	/**
	 *
	 */
	protected JRFont timeAxisLabelFont = null;
	protected Color timeAxisLabelColor = null;
	protected JRFont timeAxisTickLabelFont = null;
	protected Color timeAxisTickLabelColor = null;
	protected Color timeAxisLineColor = null;

	protected JRFont valueAxisLabelFont = null;
	protected Color valueAxisLabelColor = null;
	protected JRFont valueAxisTickLabelFont = null;
	protected Color valueAxisTickLabelColor = null;
	protected Color valueAxisLineColor = null;

	
	/**
	 *
	 */
	public JRFillTimeSeriesPlot( JRTimeSeriesPlot plot, JRFillObjectFactory factory ){
		super( plot, factory );

		timeAxisLabelFont = new JRBaseFont(null, null, plot.getChart(), plot.getTimeAxisLabelFont());
		timeAxisLabelColor = plot.getOwnTimeAxisLabelColor();
		timeAxisTickLabelFont = new JRBaseFont(null, null, plot.getChart(), plot.getTimeAxisTickLabelFont());
		timeAxisTickLabelColor = plot.getOwnTimeAxisTickLabelColor();
		timeAxisLineColor = plot.getTimeAxisLineColor();
		
		valueAxisLabelFont = new JRBaseFont(null, null, plot.getChart(), plot.getValueAxisLabelFont());
		valueAxisLabelColor = plot.getOwnValueAxisLabelColor();
		valueAxisTickLabelFont = new JRBaseFont(null, null, plot.getChart(), plot.getValueAxisTickLabelFont());
		valueAxisTickLabelColor = plot.getOwnValueAxisTickLabelColor();
		valueAxisLineColor = plot.getValueAxisTickLabelColor();
	}
	
	/**
	 *
	 */
	public JRExpression getTimeAxisLabelExpression()
	{
		return ((JRTimeSeriesPlot)parent).getTimeAxisLabelExpression();
	}

	/**
	 *
	 */
	public JRFont getTimeAxisLabelFont()
	{
		return timeAxisLabelFont;
	}

	/**
	 *
	 */
	public void setTimeAxisLabelFont(JRFont font)
	{
	}
	
	/**
	 *
	 */
	public Color getTimeAxisLabelColor()
	{
		return JRStyleResolver.getTimeAxisLabelColor(this, this);
	}

	/**
	 *
	 */
	public Color getOwnTimeAxisLabelColor()
	{
		return timeAxisLabelColor;
	}

	/**
	 *
	 */
	public void setTimeAxisLabelColor(Color color)
	{
	}

	/**
	 *
	 */
	public JRFont getTimeAxisTickLabelFont()
	{
		return timeAxisTickLabelFont;
	}

	/**
	 *
	 */
	public void setTimeAxisTickLabelFont(JRFont font)
	{
	}
	
	/**
	 *
	 */
	public Color getTimeAxisTickLabelColor()
	{
		return JRStyleResolver.getTimeAxisTickLabelColor(this, this);
	}

	/**
	 *
	 */
	public Color getOwnTimeAxisTickLabelColor()
	{
		return timeAxisTickLabelColor;
	}

	/**
	 *
	 */
	public void setTimeAxisTickLabelColor(Color color)
	{
	}

	/**
	 *
	 */
	public String getTimeAxisTickLabelMask()
	{
		return ((JRTimeSeriesPlot)parent).getTimeAxisTickLabelMask();
	}

	/**
	 *
	 */
	public void setTimeAxisTickLabelMask(String mask)
	{
	}

	/**
	 *
	 */
	public Color getTimeAxisLineColor()
	{
		return JRStyleResolver.getTimeAxisLineColor(this, this);
	}

	/**
	 *
	 */
	public Color getOwnTimeAxisLineColor()
	{
		return timeAxisLineColor;
	}

	/**
	 *
	 */
	public void setTimeAxisLineColor(Color color)
	{
	}

	/**
	 *
	 */
	public JRExpression getValueAxisLabelExpression()
	{
		return ((JRTimeSeriesPlot)parent).getValueAxisLabelExpression();
	}

	/**
	 *
	 */
	public JRFont getValueAxisLabelFont()
	{
		return valueAxisLabelFont;
	}

	/**
	 *
	 */
	public void setValueAxisLabelFont(JRFont font)
	{
	}
	
	/**
	 *
	 */
	public Color getValueAxisLabelColor()
	{
		return JRStyleResolver.getValueAxisLabelColor(this, this);
	}

	/**
	 *
	 */
	public Color getOwnValueAxisLabelColor()
	{
		return valueAxisLabelColor;
	}

	/**
	 *
	 */
	public void setValueAxisLabelColor(Color color)
	{
	}

	/**
	 *
	 */
	public JRFont getValueAxisTickLabelFont()
	{
		return valueAxisTickLabelFont;
	}

	/**
	 *
	 */
	public void setValueAxisTickLabelFont(JRFont font)
	{
	}
	
	/**
	 *
	 */
	public Color getValueAxisTickLabelColor()
	{
		return JRStyleResolver.getValueAxisTickLabelColor(this, this);
	}

	/**
	 *
	 */
	public Color getOwnValueAxisTickLabelColor()
	{
		return valueAxisTickLabelColor;
	}

	/**
	 *
	 */
	public void setValueAxisTickLabelColor(Color color)
	{
	}

	/**
	 *
	 */
	public String getValueAxisTickLabelMask()
	{
		return ((JRTimeSeriesPlot)parent).getValueAxisTickLabelMask();
	}

	/**
	 *
	 */
	public void setValueAxisTickLabelMask(String mask)
	{
	}

	/**
	 *
	 */
	public Color getValueAxisLineColor()
	{
		return JRStyleResolver.getValueAxisLineColor(this, this);
	}

	/**
	 *
	 */
	public Color getOwnValueAxisLineColor()
	{
		return valueAxisLineColor;
	}

	public boolean isShowLines(){
		return ((JRTimeSeriesPlot)parent).isShowLines();
	}
	
	public void setShowLines(){
	}
	
	public boolean isShowShapes(){
		return ((JRTimeSeriesPlot)parent).isShowShapes();
	}
	
	public void setShowShapes(){
	}
}
