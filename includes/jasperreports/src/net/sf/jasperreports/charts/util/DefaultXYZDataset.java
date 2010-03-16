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
package net.sf.jasperreports.charts.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRConstants;

import org.jfree.data.xy.AbstractXYZDataset;
import org.jfree.data.xy.XYZDataset;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: DefaultXYZDataset.java 1229 2006-04-19 10:27:35Z teodord $
 */
public class DefaultXYZDataset extends  AbstractXYZDataset implements XYZDataset 
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	/**
	 * 
	 */
	List dataset = null;
	
	/**
	 * 
	 */
	public DefaultXYZDataset()
	{
		dataset = new ArrayList();
	}
	
	/**
	 * 
	 */
	public void addValue( Comparable series, Number xValue, Number yValue, Number zValue ){
		boolean found = false;
		for( Iterator it = dataset.iterator(); it.hasNext(); ){
			XYZElement element = (XYZElement)it.next();
			if( element.getSeries().equals( series )){
				element.addElement( xValue, yValue, zValue );
				found = true;
			}
		}

		if( !found ){
			XYZElement element = new XYZElement();
			element.setSeries( series );
			element.addElement( xValue, yValue, zValue );

			dataset.add( element );
		}
	}
	
	/** 
	 *
	 */
	public int getSeriesCount() {
		int retVal = 0;
		if( dataset != null ){
			retVal = dataset.size();
		}
		
		return retVal;
	}

	/**
	 * 
	 */
	public Number getZ(int series, int index ) {
		Number retVal = null;
		if( dataset != null ){
			if( series < getSeriesCount() ){
				XYZElement element = (XYZElement)dataset.get( series );
				retVal = element.getZElement( index );
			}
		}
		return retVal;
	}

	/**
	 * 
	 */
	public int getItemCount(int series ) {
		int retVal = 0;
		if( dataset != null ){
			if( series < getSeriesCount() ){
				XYZElement element = (XYZElement)dataset.get( series );
				retVal = element.getCount();
			}
		}
		return retVal;
	}

	/**
	 * 
	 */
	public Number getX(int series, int index ) {
		Number retVal = null;
		if( dataset != null ){
			if( series < getSeriesCount() ){
				XYZElement element = (XYZElement)dataset.get( series );
				retVal = element.getXElement( index );
			}
		}
		return retVal;
	}
	
	/**
	 * 
	 */
	public Number getY(int series, int index ) {
		Number retVal = null;
		if( dataset != null ){
			if( series < getSeriesCount() ){
				XYZElement element = (XYZElement)dataset.get( series );
				retVal = element.getYElement( index );
			}
		}
		return retVal;
	}

	/**
	 * 
	 */
	public Comparable getSeriesKey(int index) {
		String retVal = null;
		if( dataset != null ){
			if( index < getSeriesCount() ){
				XYZElement element = (XYZElement)dataset.get( index );
				retVal = element.getSeries().toString();
			}
		}
		return retVal;
	}

}
