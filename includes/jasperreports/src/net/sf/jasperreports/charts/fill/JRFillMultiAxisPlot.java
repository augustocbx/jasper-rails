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

import net.sf.jasperreports.charts.JRChartAxis;
import net.sf.jasperreports.charts.JRMultiAxisPlot;
import net.sf.jasperreports.engine.fill.JRFillChartDataset;
import net.sf.jasperreports.engine.fill.JRFillChartPlot;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRFillMultiAxisPlot.java 1794 2007-07-30 09:07:50Z teodord $
 */
public class JRFillMultiAxisPlot extends JRFillChartPlot implements JRMultiAxisPlot
{

	private List axes;

	public JRFillMultiAxisPlot(JRMultiAxisPlot multiAxisPlot, JRFillObjectFactory factory)
	{
		super(multiAxisPlot, factory);

		List parentAxes = multiAxisPlot.getAxes();
		this.axes = new ArrayList(parentAxes.size());
		Iterator iter = parentAxes.iterator();
		while (iter.hasNext())
		{
			JRChartAxis axis = (JRChartAxis)iter.next();
			this.axes.add(factory.getChartAxis(axis));
		}
	}

	public List getAxes()
	{
		return axes;
	}

	public JRFillChartDataset getMainDataset()
	{
		return (JRFillChartDataset) ((JRFillChartAxis) axes.get(0)).getFillChart().getDataset();
	}
}
