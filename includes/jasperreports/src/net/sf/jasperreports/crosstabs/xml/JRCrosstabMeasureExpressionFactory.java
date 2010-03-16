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
package net.sf.jasperreports.crosstabs.xml;

import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabMeasure;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.xml.JRBaseFactory;

import org.xml.sax.Attributes;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRCrosstabMeasureExpressionFactory.java 1311 2006-06-23 09:19:26Z teodord $
 */
public class JRCrosstabMeasureExpressionFactory extends JRBaseFactory
{
	public Object createObject(Attributes attributes)
	{
		JRDesignCrosstabMeasure measure = (JRDesignCrosstabMeasure) digester.peek();

		JRDesignExpression expression = new JRDesignExpression();
		if (
			measure.getCalculation() == JRVariable.CALCULATION_COUNT
			|| measure.getCalculation() == JRVariable.CALCULATION_DISTINCT_COUNT
			)
		{
			expression.setValueClassName(Object.class.getName());
		}
		else
		{
			expression.setValueClassName(measure.getValueClassName());
		}

		return expression;
	}
}
