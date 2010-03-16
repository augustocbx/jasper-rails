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

import net.sf.jasperreports.charts.JRDataRange;
import net.sf.jasperreports.charts.JRThermometerPlot;
import net.sf.jasperreports.charts.JRValueDisplay;
import net.sf.jasperreports.engine.fill.JRFillChartPlot;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

import java.awt.Color;

/**
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRFillThermometerPlot.java 1794 2007-07-30 09:07:50Z teodord $
 */
public class JRFillThermometerPlot extends JRFillChartPlot implements JRThermometerPlot
{



	/**
	 *
	 */
	public JRFillThermometerPlot(JRThermometerPlot thermoPlot, JRFillObjectFactory factory)
	{
		super(thermoPlot, factory);
	}


	/**
	 *
	 */
	public JRDataRange getDataRange()
	{
		return ((JRThermometerPlot)parent).getDataRange();
	}

	/**
	 *
	 */
	public JRValueDisplay getValueDisplay()
	{
		return ((JRThermometerPlot)parent).getValueDisplay();
	}

	/**
	 *
	 */
	public boolean isShowValueLines()
	{
		return ((JRThermometerPlot)parent).isShowValueLines();
	}

	/**
	 *
	 */
	public byte getValueLocation()
	{
		return ((JRThermometerPlot)parent).getValueLocation();
	}

	/**
	 *
	 */
	public Color getMercuryColor()
	{
		return ((JRThermometerPlot)parent).getMercuryColor();
	}

	/**
	 *
	 */
	public JRDataRange getLowRange()
	{
		return ((JRThermometerPlot)parent).getLowRange();
	}

	/**
	 *
	 */
	public JRDataRange getMediumRange()
	{
		return ((JRThermometerPlot)parent).getMediumRange();
	}

	/**
	 *
	 */
	public JRDataRange getHighRange()
	{
		return ((JRThermometerPlot)parent).getHighRange();
	}
}
