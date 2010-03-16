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
package net.sf.jasperreports.engine;

import net.sf.jasperreports.engine.design.JRVerifier;


/**
 * Datasets are used to represent the actual data needed to generate a chart. The dataset structure may vary with each chart type. This
 * is the superinterface for all datasets and contains common dataset properties.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRChartDataset.java 1393 2006-09-05 22:04:21Z bklawans $
 */
public interface JRChartDataset extends JRElementDataset
{
	public static final byte PIE_DATASET = 1;
	public static final byte CATEGORY_DATASET = 2;
	public static final byte XY_DATASET = 3;
	public static final byte XYZ_DATASET = 4;
	public static final byte TIMEPERIOD_DATASET = 5;
	public static final byte TIMESERIES_DATASET = 6;
	public static final byte HIGHLOW_DATASET = 7;
	public static final byte VALUE_DATASET = 8;
	
	/**
	 * Gets the dataset type. Must be one of the dataset type constants defined in this class.
	 */
	public byte getDatasetType();

	
	/**
	 * Validates the dataset using a verifier.
	 * <p>
	 * Broken rules are collected by the verifier.
	 * </p>
	 * 
	 * @param verifier the verifier to use for validation
	 */
	public void validate(JRVerifier verifier);
	
}
