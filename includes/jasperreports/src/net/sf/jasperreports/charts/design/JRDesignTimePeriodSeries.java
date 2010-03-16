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

import net.sf.jasperreports.charts.base.JRBaseTimePeriodSeries;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRDesignTimePeriodSeries.java 1923 2007-10-25 09:44:32Z lucianc $
 */
public class JRDesignTimePeriodSeries extends JRBaseTimePeriodSeries implements JRChangeEventsSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_END_DATE_EXPRESSION = "endDateExpression";
	
	public static final String PROPERTY_ITEM_HYPERLINK = "itemHyperlink";
	
	public static final String PROPERTY_LABEL_EXPRESSION = "labelExpression";
	
	public static final String PROPERTY_SERIES_EXPRESSION = "seriesExpression";
	
	public static final String PROPERTY_START_DATE_EXPRESSION = "startDateExpression";
	
	public static final String PROPERTY_VALUE_EXPRESSION = "valueExpression";
	
	/**
	 * 
	 */
	public void setSeriesExpression( JRExpression seriesExpression ){
		Object old = this.seriesExpression;
		this.seriesExpression = seriesExpression;
		getEventSupport().firePropertyChange(PROPERTY_SERIES_EXPRESSION, old, this.seriesExpression);
	}
	
	/**
	 * 
	 */
	public void setStartDateExpression( JRExpression startDateExpression ){
		Object old = this.startDateExpression;
		this.startDateExpression = startDateExpression;
		getEventSupport().firePropertyChange(PROPERTY_START_DATE_EXPRESSION, old, this.startDateExpression);
	}

	/**
	 * 
	 */
	public void setEndDateExpression( JRExpression endDateExpression ){
		Object old = this.endDateExpression;
		this.endDateExpression = endDateExpression;
		getEventSupport().firePropertyChange(PROPERTY_END_DATE_EXPRESSION, old, this.endDateExpression);
	}
	
	/**
	 * 
	 */
	public void setValueExpression( JRExpression valueExpression ){
		Object old = this.valueExpression;
		this.valueExpression = valueExpression;
		getEventSupport().firePropertyChange(PROPERTY_VALUE_EXPRESSION, old, this.valueExpression);
	}
	
	/**
	 * 
	 */
	public void setLabelExpression( JRExpression labelExpression ){
		Object old = this.labelExpression;
		this.labelExpression = labelExpression;
		getEventSupport().firePropertyChange(PROPERTY_LABEL_EXPRESSION, old, this.labelExpression);
	}

	/**
	 * Sets the hyperlink specification for chart items.
	 * 
	 * @param itemHyperlink the hyperlink specification
	 * @see #getItemHyperlink()
	 */
	public void setItemHyperlink(JRHyperlink itemHyperlink)
	{
		Object old = this.itemHyperlink;
		this.itemHyperlink = itemHyperlink;
		getEventSupport().firePropertyChange(PROPERTY_ITEM_HYPERLINK, old, this.itemHyperlink);
	}
	
	private transient JRPropertyChangeSupport eventSupport;
	
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}
}
