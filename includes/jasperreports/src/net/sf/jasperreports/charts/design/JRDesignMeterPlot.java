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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.charts.JRDataRange;
import net.sf.jasperreports.charts.JRMeterPlot;
import net.sf.jasperreports.charts.JRValueDisplay;
import net.sf.jasperreports.charts.base.JRBaseMeterPlot;
import net.sf.jasperreports.charts.util.JRMeterInterval;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;

/**
 * A meter plot that displays a single value against a range of values.  The
 * range can be further subdivided into multiple color coded regions.
 *
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRDesignMeterPlot.java 1983 2007-11-29 11:23:03Z lucianc $
 */
public class JRDesignMeterPlot extends JRBaseMeterPlot
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_DATA_RANGE = "dataRange";
	
	public static final String PROPERTY_METER_ANGLE = "meterAngle";
	
	public static final String PROPERTY_METER_BACKGROUND_COLOR = "meterBackgroundColor";
	
	public static final String PROPERTY_NEEDLE_COLOR = "needleColor";
	
	public static final String PROPERTY_SHAPE = "shape";
	
	public static final String PROPERTY_TICK_COLOR = "tickColor";
	
	public static final String PROPERTY_TICK_INTERVAL = "tickInterval";
	
	public static final String PROPERTY_UNITS = "units";
	
	public static final String PROPERTY_VALUE_DISPLAY = "valueDisplay";
	
	public static final String PROPERTY_INTERVALS = "intervals";


	/**
	 * Construct a new meter plot by copying an existing one.
	 *
	 * @param meterPlot the plot to copy
	 */
	public JRDesignMeterPlot(JRChartPlot meterPlot, JRChart chart)
	{
		super(meterPlot, chart);
	}

	/**
	 * Sets the range of values that the meter can display.  Before changing
	 * this for an existing meter you should clear any existing intervals to
	 * ensure that you don't end up with intervals that are outside of the new
	 * range.
	 *
	 * @param dataRange the range of values that the meter can display
	 */
	public void setDataRange(JRDataRange dataRange) throws JRException
	{
		Object old = this.dataRange;
		this.dataRange = dataRange;
		getEventSupport().firePropertyChange(PROPERTY_DATA_RANGE, old, this.dataRange);
	}

	/**
	 * Sets the value display formatting options.
	 *
	 * @param valueDisplay how to show the textual representation of the value
	 */
	public void setValueDisplay(JRValueDisplay valueDisplay)
	{
		Object old = this.valueDisplay;
		this.valueDisplay = valueDisplay;
		getEventSupport().firePropertyChange(PROPERTY_VALUE_DISPLAY, old, this.valueDisplay);
	}

	/**
	 * Sets the shape of the meter.  Must be one of
	 * <code>JRMeterPlot.SHAPE_CHORD</code>, <code>JRMeterPlot.SHAPE_CIRCLE</code>
	 * or <code>JRMeterPlot.SHAPE_PIE</code>.
	 *
	 * @param shape the shape of the meter
	 * @throws JRException invalid shape was specified
	 */
	public void setShape(byte shape) throws JRException
	{
		if (shape < 0 || shape > JRMeterPlot.SHAPE_PIE)
		{
			throw new JRException("Unknown shape for MeterPlot");
		}

		byte old = this.shape;
		this.shape = shape;
		getEventSupport().firePropertyChange(PROPERTY_SHAPE, old, this.shape);
	}

	/**
	 * Adds an interval to the meter.  An interval is used to indicate a
	 * section of the meter.
	 *
	 * @param interval the interval to add to the meter
	 */
	public void addInterval(JRMeterInterval interval)
	{
		intervals.add(interval);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_INTERVALS, interval, intervals.size() - 1);
	}

	/**
	 * Removes all the intervals for the meter.
	 */
	public void clearIntervals()
	{
		setIntervals(null);
	}

	/**
	 * Sets the meter intervals.
	 * 
	 * @param intervals the list of meter intervals ({@link JRMeterInterval} instances)
	 * @see #addInterval(JRMeterInterval)
	 */
	public void setIntervals(Collection intervals)
	{
		Object old = new ArrayList(this.intervals);
		this.intervals.clear();
		if (intervals != null)
		{
			this.intervals.addAll(intervals);
		}
		getEventSupport().firePropertyChange(PROPERTY_INTERVALS, old, this.intervals);
	}
	
	/**
	 * Sets the size of the meter face in degrees.
	 *
	 * @param meterAngle the size of the meter in degrees
	 */
	public void setMeterAngle(int meterAngle)
	{
		int old = this.meterAngle;
		this.meterAngle = meterAngle;
		getEventSupport().firePropertyChange(PROPERTY_METER_ANGLE, old, this.meterAngle);
	}

	/**
	 * Sets the units string to use.  This string is appended to the value
	 * when it is displayed.
	 *
	 * @param units the units string to use
	 */
	public void setUnits(String units)
	{
		Object old = this.units;
		this.units = units;
		getEventSupport().firePropertyChange(PROPERTY_UNITS, old, this.units);
	}

	/**
	 * Sets the space between tick marks on the face of the meter.  The
	 * spacing is relative to the range of the meter.  If the meter is
	 * displaying the range 100 to 200 and the tick interval is 20, four
	 * tick marks will be shown, one each at 120, 140, 160 and 180.
	 *
	 * @param tickInterval the space between tick marks on the meter
	 */
	public void setTickInterval(double tickInterval)
	{
		double old = this.tickInterval;
		this.tickInterval = tickInterval;
		getEventSupport().firePropertyChange(PROPERTY_TICK_INTERVAL, old, this.tickInterval);
	}

	/**
	 * Sets the color to use for the meter face.
	 *
	 * @param meterBackgroundColor the color to use for the meter face
	 */
	public void setMeterBackgroundColor(Color meterBackgroundColor)
	{
		Object old = this.meterBackgroundColor;
		this.meterBackgroundColor = meterBackgroundColor;
		getEventSupport().firePropertyChange(PROPERTY_METER_BACKGROUND_COLOR, old, this.meterBackgroundColor);
	}

	/**
	 * Sets the color to use for the meter pointer.
	 *
	 * @param needleColor the color to use for the meter pointer
	 */
	public void setNeedleColor(Color needleColor)
	{
		Object old = this.needleColor;
		this.needleColor = needleColor;
		getEventSupport().firePropertyChange(PROPERTY_NEEDLE_COLOR, old, this.needleColor);
	}

	/**
	 * Sets the color to use when drawing tick marks on the meter.
	 *
	 * @param tickColor the color to use when drawing tick marks
	 */
	public void setTickColor(Color tickColor)
	{
		Object old = this.tickColor;
		this.tickColor = tickColor;
		getEventSupport().firePropertyChange(PROPERTY_TICK_COLOR, old, this.tickColor);
	}
}
