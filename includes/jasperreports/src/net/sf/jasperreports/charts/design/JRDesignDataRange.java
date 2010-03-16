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

import net.sf.jasperreports.charts.JRDataRange;
import net.sf.jasperreports.charts.base.JRBaseDataRange;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;

/**
 * Contains a range of values.  Used to specify the set of acceptable values
 * for a meter or thermometer, and to divide those charts up into subsections.
 *
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRDesignDataRange.java 1923 2007-10-25 09:44:32Z lucianc $
 */
public class JRDesignDataRange extends JRBaseDataRange implements JRChangeEventsSupport
{
	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_HIGH_EXPRESSION = "highExpression";
	
	public static final String PROPERTY_LOW_EXPRESSION = "lowExpression";


	/**
	 * Constructs a new data range that is a copy of an existing one.
	 *
	 * @param dataRange the range to copy
	 */
	public JRDesignDataRange(JRDataRange dataRange)
	{
		super(dataRange);
	}

	/**
	 * Sets the low expression of the range.  The low expression is
	 * evaluted to get the lower bound of the range.
	 *
	 * @param lowExpression used to get the lower bound of the range
	 */
	public void setLowExpression(JRExpression lowExpression)
	{
		Object old = this.lowExpression;
		this.lowExpression = lowExpression;
		getEventSupport().firePropertyChange(PROPERTY_LOW_EXPRESSION, old, this.lowExpression);
	}

	/**
	 * Sets the high expression of the range.  The high expression is
	 * used to get the upper bound of the range.
	 *
	 * @param highExpression used to get the upper bound of the range
	 */
	public void setHighExpression(JRExpression highExpression)
	{
		Object old = this.highExpression;
		this.highExpression = highExpression;
		getEventSupport().firePropertyChange(PROPERTY_HIGH_EXPRESSION, old, this.highExpression);
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
