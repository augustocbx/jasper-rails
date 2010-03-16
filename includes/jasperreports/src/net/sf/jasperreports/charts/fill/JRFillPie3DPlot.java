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

import net.sf.jasperreports.charts.JRPie3DPlot;
import net.sf.jasperreports.engine.fill.JRFillChartPlot;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillPie3DPlot.java 1782 2007-07-16 13:55:27Z shertage $
 */
public class JRFillPie3DPlot extends JRFillChartPlot implements JRPie3DPlot
{


	/**
	 *
	 */
	public JRFillPie3DPlot(
		JRPie3DPlot pie3DPlot, 
		JRFillObjectFactory factory
		)
	{
		super(pie3DPlot, factory);
	}
		

	/**
	 *
	 */
	public double getDepthFactor()
	{
		return ((JRPie3DPlot)parent).getDepthFactor();
	}
	
	public boolean isCircular()
	{
		return ((JRPie3DPlot)parent).isCircular();
	}
}
