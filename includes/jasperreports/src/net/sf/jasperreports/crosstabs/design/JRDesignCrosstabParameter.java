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
package net.sf.jasperreports.crosstabs.design;

import net.sf.jasperreports.crosstabs.JRCrosstabParameter;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignParameter;

/**
 * Implementation of crosstab parameters to be used for report designing.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRDesignCrosstabParameter.java 1921 2007-10-23 16:50:55Z lucianc $
 */
public class JRDesignCrosstabParameter extends JRDesignParameter implements JRCrosstabParameter
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public static final String PROPERTY_VALUE_EXPRESSION = "valueExpression";

	protected JRExpression valueExpression;

	
	/**
	 * Creates a crosstab parameter.
	 */
	public JRDesignCrosstabParameter()
	{
	}
	
	public JRExpression getExpression()
	{
		return valueExpression;
	}

	
	/**
	 * Sets the parameter value expression.
	 * 
	 * @param expression the parameter value expression
	 */
	public void setExpression(JRExpression expression)
	{
		Object old = this.valueExpression;
		this.valueExpression = expression;
		getEventSupport().firePropertyChange(PROPERTY_VALUE_EXPRESSION, old, this.valueExpression);
	}
}
