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
package net.sf.jasperreports.engine.design;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRReportTemplate;
import net.sf.jasperreports.engine.base.JRBaseReportTemplate;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;


/**
 * {@link JRReportTemplate} implementation to be used at report design time.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRDesignReportTemplate.java 1919 2007-10-23 14:22:19Z lucianc $
 */
public class JRDesignReportTemplate extends JRBaseReportTemplate implements JRChangeEventsSupport
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_SOURCE_EXPRESSION = "sourceExpression";

	/**
	 * Creates an empty report template.
	 */
	public JRDesignReportTemplate()
	{
		super();
	}

	/**
	 * Creates a report template for a template source expression.
	 */
	public JRDesignReportTemplate(JRExpression sourceExpression)
	{
		super();

		this.sourceExpression = sourceExpression;
	}
	
	/**
	 * Sets the template source expression.
	 * 
	 * @param sourceExpression the template source expression
	 * @see #getSourceExpression()
	 */
	public void setSourceExpression(JRExpression sourceExpression)
	{
		Object old = this.sourceExpression;
		this.sourceExpression = sourceExpression;
		getEventSupport().firePropertyChange(PROPERTY_SOURCE_EXPRESSION, old, this.sourceExpression);
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
