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

import net.sf.jasperreports.charts.JRValueDataset;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.base.JRBaseChartDataset;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.design.JRVerifier;


/**
 * An immutable version of a dataset that generates a single value.  A value
 * dataset is suitable for using with charts that show a single value against
 * a potential range, such as meter chart or a thermometer chart.
 *
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRBaseValueDataset.java 1994 2007-12-05 13:48:10Z teodord $
 */
public class JRBaseValueDataset extends JRBaseChartDataset implements JRValueDataset
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 * The expression that returns the single value contained in this dataset.
	 */
	protected JRExpression valueExpression = null;


	/**
	 * Construct a new dataset that is a copy of an existing one.
	 *
	 * @param dataset the dataset to copy
	 */
	public JRBaseValueDataset(JRChartDataset dataset)
	{
		super(dataset);
	}


	/**
	 * Constructs a new dataset that is a copy of an existing one, and
	 * register all of the new dataset's expressions with the specified
	 * factory.
	 *
	 * @param dataset the datast to copy
	 * @param factory the factory to register the new dataset's expressions with
	 */
	public JRBaseValueDataset(JRValueDataset dataset, JRBaseObjectFactory factory)
	{
		super(dataset, factory);

		valueExpression = factory.getExpression(dataset.getValueExpression());
	}


	/**
	 *
	 */
	public JRExpression getValueExpression()
	{
		return valueExpression;
	}


	/**
	 *
	 */
	public byte getDatasetType() {
		return JRChartDataset.VALUE_DATASET;
	}

	/**
	 * Adds all the expression used by this plot with the specified collector.
	 * All collected expression that are also registered with a factory will
	 * be included with the report is compiled.
	 *
	 * @param collector the expression collector to use
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
		JRBaseValueDataset clone = (JRBaseValueDataset)super.clone();
		
		if (valueExpression != null)
		{
			clone.valueExpression = (JRExpression)valueExpression.clone();
		}
		
		return clone;
	}
}
