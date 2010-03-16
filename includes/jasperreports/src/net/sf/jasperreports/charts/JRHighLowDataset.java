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
package net.sf.jasperreports.charts;

import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;


/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id: JRHighLowDataset.java 1364 2006-08-31 15:13:20Z lucianc $
 */
public interface JRHighLowDataset extends JRChartDataset
{
	/**
	 *
	 */
	public JRExpression getSeriesExpression();


	/**
	 *
	 */
	public JRExpression getDateExpression();


	/**
	 *
	 */
	public JRExpression getHighExpression();


	/**
	 *
	 */
	public JRExpression getLowExpression();


	/**
	 *
	 */
	public JRExpression getOpenExpression();


	/**
	 *
	 */
	public JRExpression getCloseExpression();


	/**
	 *
	 */
	public JRExpression getVolumeExpression();
	
	
	/**
	 * Returns the hyperlink specification for chart items.
	 * <p>
	 * The hyperlink will be evaluated for every chart item and a image map will be created for the chart.
	 * </p>
	 * 
	 * @return hyperlink specification for chart items
	 */
	public JRHyperlink getItemHyperlink();
	
}
