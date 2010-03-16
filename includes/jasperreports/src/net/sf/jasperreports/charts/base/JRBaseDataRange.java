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

import net.sf.jasperreports.charts.JRDataRange;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;

/**
 * An immutable instantiation of a <code>JRDataRange</code>, suitable for holding
 * a range.
 *
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRBaseDataRange.java 1994 2007-12-05 13:48:10Z teodord $
 */
public class JRBaseDataRange implements JRDataRange, Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 * The expression used to calculate the lower bound of the range.
	 */
	protected JRExpression lowExpression = null;

	/**
	 * The expression used to calculate the upper bound of the range.
	 */
	protected JRExpression highExpression = null;


	/**
	 * Constructs a copy of an existing range.
	 *
	 * @param dataRange the range to copy
	 */
	public JRBaseDataRange(JRDataRange dataRange)
	{
		if (dataRange != null)
		{
			this.lowExpression = dataRange.getLowExpression();
			this.highExpression = dataRange.getHighExpression();
		}
}

	/**
	 * Creates a copy of an existing range and registers all of the expressions
	 * with a factory object.  Once the expressions have been registered they will
	 * be included when the report is compiled.
	 *
	 * @param dataRange the range to copy
	 * @param factory the factory to register the expressions with
	 */
	public JRBaseDataRange(JRDataRange dataRange, JRBaseObjectFactory factory)
	{
		factory.put(dataRange, this);

		lowExpression = factory.getExpression(dataRange.getLowExpression());
		highExpression = factory.getExpression(dataRange.getHighExpression());
	}


	/**
	 *
	 */
	public JRExpression getLowExpression()
	{
		return lowExpression;
	}
	/**
	 *
	 */
	public JRExpression getHighExpression()
	{
		return highExpression;
	}


	/**
	 * Registers all of the expressions with the collector.  If the expressions
	 * have been registered with one of the report's factory they will be included
	 * when the report is compiled.
	 *
	 * @param collector the expression collector to use
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	/**
	 *
	 */
	public Object clone() 
	{
		JRBaseDataRange clone = null;
		
		try
		{
			clone = (JRBaseDataRange)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		if (lowExpression != null)
		{
			clone.lowExpression = (JRExpression)lowExpression.clone();
		}
		if (highExpression != null)
		{
			clone.highExpression = (JRExpression)highExpression.clone();
		}
		
		return clone;
	}
}
