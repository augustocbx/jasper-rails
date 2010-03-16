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


/**
 * This class represents a dataset that consists of a single
 * value.  It is used by Meter and Thermometer charts, which
 * plot a single value against a range.
 * 
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRValueDataset.java 1385 2006-09-05 21:29:03Z bklawans $
 */
public interface JRValueDataset extends JRChartDataset
{
	
	/**
	 * Returns the expression that indicates the value held
	 * by this dataset.
	 * 
	 * @return the expression that indicates the value held by
	 * this dataset
	 */
	public JRExpression getValueExpression();

}
