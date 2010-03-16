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
package net.sf.jasperreports.charts.base;

import java.awt.Color;

import net.sf.jasperreports.charts.JRBar3DPlot;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.util.JRStyleResolver;

import org.jfree.chart.renderer.category.BarRenderer3D;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRBaseBar3DPlot.java 1994 2007-12-05 13:48:10Z teodord $ 
 */
public class JRBaseBar3DPlot extends JRBaseChartPlot implements JRBar3DPlot {

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_SHOW_LABELS = "showLabels";
	
	public static final String PROPERTY_X_OFFSET = "xOffset";
	
	public static final String PROPERTY_Y_OFFSET = "yOffset";
	
	protected JRExpression categoryAxisLabelExpression = null;
	protected JRFont categoryAxisLabelFont = null;
	protected Color categoryAxisLabelColor = null;
	protected JRFont categoryAxisTickLabelFont = null;
	protected Color categoryAxisTickLabelColor = null;
	protected String categoryAxisTickLabelMask = null;
	protected Color categoryAxisLineColor = null;

	protected JRExpression valueAxisLabelExpression = null;
	protected JRFont valueAxisLabelFont = null;
	protected Color valueAxisLabelColor = null;
	protected JRFont valueAxisTickLabelFont = null;
	protected Color valueAxisTickLabelColor = null;
	protected String valueAxisTickLabelMask = null;
	protected Color valueAxisLineColor = null;

	protected double xOffset = BarRenderer3D.DEFAULT_X_OFFSET;
	protected double yOffset = BarRenderer3D.DEFAULT_Y_OFFSET;
	protected boolean isShowLabels = false;
	

	/**
	 * 
	 */
	public JRBaseBar3DPlot(JRChartPlot barPlot, JRChart chart){
		super(barPlot, chart);
	}


	/**
	 * 
	 */
	public JRBaseBar3DPlot( JRBar3DPlot barPlot, JRBaseObjectFactory factory ){
		super( barPlot, factory );
		
		xOffset = barPlot.getXOffset();
		yOffset = barPlot.getYOffset();
		isShowLabels = barPlot.isShowLabels();
		
		categoryAxisLabelExpression = factory.getExpression( barPlot.getCategoryAxisLabelExpression() );
		categoryAxisLabelFont = new JRBaseFont(null, null, barPlot.getChart(), barPlot.getCategoryAxisLabelFont());
		categoryAxisLabelColor = barPlot.getOwnCategoryAxisLabelColor();
		categoryAxisTickLabelFont = new JRBaseFont(null, null, barPlot.getChart(), barPlot.getCategoryAxisTickLabelFont());
		categoryAxisTickLabelColor = barPlot.getOwnCategoryAxisTickLabelColor();
		categoryAxisTickLabelMask = barPlot.getCategoryAxisTickLabelMask();
		categoryAxisLineColor = barPlot.getCategoryAxisLineColor();
		
		valueAxisLabelExpression = factory.getExpression( barPlot.getValueAxisLabelExpression() );
		valueAxisLabelFont = new JRBaseFont(null, null, barPlot.getChart(), barPlot.getValueAxisLabelFont());
		valueAxisLabelColor = barPlot.getOwnValueAxisLabelColor();
		valueAxisTickLabelFont = new JRBaseFont(null, null, barPlot.getChart(), barPlot.getValueAxisTickLabelFont());
		valueAxisTickLabelColor = barPlot.getOwnValueAxisTickLabelColor();
		valueAxisTickLabelMask = barPlot.getValueAxisTickLabelMask();
		valueAxisLineColor = barPlot.getValueAxisLineColor();
	}
	
	/**
	 * 
	 */
	public JRExpression getCategoryAxisLabelExpression(){
		return categoryAxisLabelExpression;
	}
	
	/**
	 * 
	 */
	public JRFont getCategoryAxisLabelFont()
	{
		return categoryAxisLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getCategoryAxisLabelColor()
	{
		return JRStyleResolver.getCategoryAxisLabelColor(this, this);
	}
	
	/**
	 * 
	 */
	public Color getOwnCategoryAxisLabelColor()
	{
		return categoryAxisLabelColor;
	}
	
	/**
	 * 
	 */
	public JRFont getCategoryAxisTickLabelFont()
	{
		return categoryAxisTickLabelFont;
	}
	
	/**
	 * 
	 */
	public Color getCategoryAxisTickLabelColor()
	{
		return JRStyleResolver.getCategoryAxisTickLabelColor(this, this);
	}

	/**
	 * 
	 */
	public Color getOwnCategoryAxisTickLabelColor()
	{
		return categoryAxisTickLabelColor;
	}

	/**
	 * 
	 */
	public String getCategoryAxisTickLabelMask()
	{
		return categoryAxisTickLabelMask;
	}

	/**
	 * 
	 */
	public Color getCategoryAxisLineColor()
	{
		return JRStyleResolver.getCategoryAxisLineColor(this, this);
	}
		
	/**
	 * 
	 */
	public Color getOwnCategoryAxisLineColor()
	{
		return categoryAxisLineColor;
	}
		
	/**
	 * 
	 */
	public JRExpression getValueAxisLabelExpression(){
		return valueAxisLabelExpression;
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
	public JRFont getValueAxisTickLabelFont()
	{
		return valueAxisTickLabelFont;
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
	public String getValueAxisTickLabelMask()
	{
		return valueAxisTickLabelMask;
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
	
	/**
	 * 
	 */
	public double getXOffset(){
		return xOffset;
	}
	
	/**
	 * 
	 */
	public void setXOffset( double xOffset ){
		double old = this.xOffset;
		this.xOffset = xOffset;
		getEventSupport().firePropertyChange(PROPERTY_X_OFFSET, old, this.xOffset);
	}
	
	/**
	 * 
	 */
	public double getYOffset(){
		return yOffset;
	}
	
	/**
	 * 
	 */
	public void setYOffset( double yOffset ){
		double old = this.yOffset;
		this.yOffset = yOffset;
		getEventSupport().firePropertyChange(PROPERTY_Y_OFFSET, old, this.yOffset);
	}
	
	/**
	 * 
	 */
	public boolean isShowLabels(){
		return isShowLabels;
	}
	
	/**
	 * 
	 */
	public void setShowLabels( boolean isShowLabels ){
		boolean old = this.isShowLabels;
		this.isShowLabels = isShowLabels;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_LABELS, old, this.isShowLabels);
	}

	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	/**
	 *
	 */
	public Object clone(JRChart parentChart) 
	{
		JRBaseBar3DPlot clone = (JRBaseBar3DPlot)super.clone(parentChart);
		if (categoryAxisLabelExpression != null)
		{
			clone.categoryAxisLabelExpression = (JRExpression)categoryAxisLabelExpression.clone();
		}
		if (valueAxisLabelExpression != null)
		{
			clone.valueAxisLabelExpression = (JRExpression)valueAxisLabelExpression.clone();
		}
		return clone;
	}
}
