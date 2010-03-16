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
package net.sf.jasperreports.charts.design;

import net.sf.jasperreports.charts.JRChartAxis;
import net.sf.jasperreports.charts.base.JRBaseMultiAxisPlot;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.design.JRDesignChart;

/**
 * Contains information on how a multiple axis chart should be
 * displayed.  This information overrides the display information
 * for all the charts sharing the single domain axis in the multiple
 * axis chart.
 *
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRDesignMultiAxisPlot.java 1927 2007-10-26 10:02:24Z lucianc $
 */
public class JRDesignMultiAxisPlot extends JRBaseMultiAxisPlot
{
	
	public static final String PROPERTY_CHART = "chart";
	
	public static final String PROPERTY_AXES = "axes";
	
	private JRDesignChart chart = null;
	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;


	/**
	 * Constructs a new plot by copying an existing one.
	 *
	 * @param multiAxisPlot the plot to copy
	 */
	public JRDesignMultiAxisPlot(JRChartPlot multiAxisPlot, JRChart chart)
	{
		super(multiAxisPlot, chart);
	}

	/**
	 * Adds an axis to the plot.  The axis contains the complete information on
	 * the data and rendering to use as well as where to draw the axis.
	 *
	 * @param axis the axis to add to the plot
	 */
	public void addAxis(JRChartAxis axis)
	{
		axes.add(axis);
		if (axes.size() == 1)
		{
			chart.setDataset(axis.getChart().getDataset());
		}
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_AXES, axis, axes.size() - 1);
	}

	/**
	 * Removes all the axes from the plot.
	 */
	public void clearAxes()
	{
		//FIXME event?
		axes.clear();
		chart.setDataset(null);
	}

	/**
	 * Returns the definition of the multiple axis chart.  This is separate
	 * from and distinct that the definition of the nested charts.
	 *
	 * @return the chart object for this plot
	 */
	public JRChart getChart()
	{
			return chart;
	}

	/**
	 * Sets the chart object that this plot belongs to.  The chart object defines
	 * all the information about the multiple axis chart.
	 *
	 * @param chart the chart that this plot belongs to
	 */
	public void setChart(JRDesignChart chart)
	{
		Object old = this.chart;
		this.chart = chart;
		getEventSupport().firePropertyChange(PROPERTY_CHART, old, this.chart);
	}
}
