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

import net.sf.jasperreports.charts.JRXyzDataset;
import net.sf.jasperreports.charts.JRXyzSeries;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.base.JRBaseChartDataset;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.design.JRVerifier;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRBaseXyzDataset.java 1994 2007-12-05 13:48:10Z teodord $
 */
public class JRBaseXyzDataset extends JRBaseChartDataset implements JRXyzDataset {
	
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	protected JRXyzSeries[] xyzSeries = null;
	

	public JRBaseXyzDataset( JRChartDataset dataset){
		super( dataset);
	}

	public JRBaseXyzDataset( JRXyzDataset dataset, JRBaseObjectFactory factory ){
		super( dataset, factory );
		
		JRXyzSeries[] srcXyzSeries = dataset.getSeries();
		
		if( srcXyzSeries != null && srcXyzSeries.length > 0 ){
			
			xyzSeries = new JRXyzSeries[ srcXyzSeries.length ];
			for( int i = 0; i < srcXyzSeries.length; i++ ){
				xyzSeries[i] = factory.getXyzSeries( srcXyzSeries[i] );
			}
		}
	}
	
	public JRXyzSeries[] getSeries(){
		return xyzSeries;
	}

	/**
	 * 
	 */
	public byte getDatasetType() {
		return JRChartDataset.XYZ_DATASET;
	}

	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}

	/**
	 * 
	 */
	public Object clone() 
	{
		JRBaseXyzDataset clone = (JRBaseXyzDataset)super.clone();
		
		if (xyzSeries != null)
		{
			clone.xyzSeries = new JRXyzSeries[xyzSeries.length];
			for(int i = 0; i < xyzSeries.length; i++)
			{
				xyzSeries[i] = (JRXyzSeries)xyzSeries[i].clone();
			}
		}
		
		return clone;
	}
}
