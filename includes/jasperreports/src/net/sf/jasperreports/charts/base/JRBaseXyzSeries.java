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

import java.io.Serializable;

import net.sf.jasperreports.charts.JRXyzSeries;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRBaseXyzSeries.java 1994 2007-12-05 13:48:10Z teodord $
 */
public class JRBaseXyzSeries implements JRXyzSeries, Serializable {

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	protected JRExpression seriesExpression = null;
	protected JRExpression xValueExpression = null;
	protected JRExpression yValueExpression = null;
	protected JRExpression zValueExpression = null;
	protected JRHyperlink itemHyperlink;
	
	public JRBaseXyzSeries(){
	}
	
	public JRBaseXyzSeries( JRXyzSeries xyzSeries, JRBaseObjectFactory factory ){
		factory.put( xyzSeries, this );
		
		seriesExpression = factory.getExpression( xyzSeries.getSeriesExpression() );
		xValueExpression = factory.getExpression( xyzSeries.getXValueExpression() );
		yValueExpression = factory.getExpression( xyzSeries.getYValueExpression() );
		zValueExpression = factory.getExpression( xyzSeries.getZValueExpression() );
		itemHyperlink = factory.getHyperlink(xyzSeries.getItemHyperlink());
	}
	
	
	public JRExpression getSeriesExpression(){
		return seriesExpression;
	}
	
	public JRExpression getXValueExpression(){
		return xValueExpression;
	}
	
	public JRExpression getYValueExpression(){
		return yValueExpression;
	}
	
	public JRExpression getZValueExpression(){
		return zValueExpression;
	}
	
	public JRHyperlink getItemHyperlink()
	{
		return itemHyperlink;
	}
	
	/**
	 * 
	 */
	public Object clone() 
	{
		JRBaseXyzSeries clone = null;
		
		try
		{
			clone = (JRBaseXyzSeries)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		if (seriesExpression != null)
		{
			clone.seriesExpression = (JRExpression)seriesExpression.clone();
		}
		if (xValueExpression != null)
		{
			clone.xValueExpression = (JRExpression)xValueExpression.clone();
		}
		if (yValueExpression != null)
		{
			clone.yValueExpression = (JRExpression)yValueExpression.clone();
		}
		if (zValueExpression != null)
		{
			clone.zValueExpression = (JRExpression)zValueExpression.clone();
		}
		if (itemHyperlink != null)
		{
			clone.itemHyperlink = (JRHyperlink)itemHyperlink.clone();
		}
		
		return clone;
	}
}
