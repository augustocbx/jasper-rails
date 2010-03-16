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
package net.sf.jasperreports.engine.fill;

import net.sf.jasperreports.engine.JRVariable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRIntegerIncrementerFactory.java 1329 2006-07-07 15:42:53Z teodord $
 */
public class JRIntegerIncrementerFactory extends JRAbstractExtendedIncrementerFactory
{


	/**
	 *
	 */
	protected static final Integer ZERO = new Integer(0);


	/**
	 *
	 */
	private static JRIntegerIncrementerFactory mainInstance = new JRIntegerIncrementerFactory();


	/**
	 *
	 */
	private JRIntegerIncrementerFactory()
	{
	}


	/**
	 *
	 */
	public static JRIntegerIncrementerFactory getInstance()
	{
		return mainInstance;
	}


	/**
	 *
	 */
	public JRExtendedIncrementer getExtendedIncrementer(byte calculation)
	{
		JRExtendedIncrementer incrementer = null;

		switch (calculation)
		{
			case JRVariable.CALCULATION_COUNT :
			{
				incrementer = JRIntegerCountIncrementer.getInstance();
				break;
			}
			case JRVariable.CALCULATION_SUM :
			{
				incrementer = JRIntegerSumIncrementer.getInstance();
				break;
			}
			case JRVariable.CALCULATION_AVERAGE :
			{
				incrementer = JRIntegerAverageIncrementer.getInstance();
				break;
			}
			case JRVariable.CALCULATION_LOWEST :
			case JRVariable.CALCULATION_HIGHEST :
			{
				incrementer = JRComparableIncrementerFactory.getInstance().getExtendedIncrementer(calculation);
				break;
			}
			case JRVariable.CALCULATION_STANDARD_DEVIATION :
			{
				incrementer = JRIntegerStandardDeviationIncrementer.getInstance();
				break;
			}
			case JRVariable.CALCULATION_VARIANCE :
			{
				incrementer = JRIntegerVarianceIncrementer.getInstance();
				break;
			}
			case JRVariable.CALCULATION_DISTINCT_COUNT :
			{
				incrementer = JRIntegerDistinctCountIncrementer.getInstance();
				break;
			}
			case JRVariable.CALCULATION_SYSTEM :
			case JRVariable.CALCULATION_NOTHING :
			case JRVariable.CALCULATION_FIRST :
			default :
			{
				incrementer = JRDefaultIncrementerFactory.getInstance().getExtendedIncrementer(calculation);
				break;
			}
		}
		
		return incrementer;
	}


}


/**
 *
 */
class JRIntegerCountIncrementer extends JRAbstractExtendedIncrementer
{
	/**
	 *
	 */
	private static JRIntegerCountIncrementer mainInstance = new JRIntegerCountIncrementer();

	/**
	 *
	 */
	private JRIntegerCountIncrementer()
	{
	}

	/**
	 *
	 */
	public static JRIntegerCountIncrementer getInstance()
	{
		return mainInstance;
	}

	/**
	 *
	 */
	public Object increment(
		JRCalculable variable, 
		Object expressionValue,
		AbstractValueProvider valueProvider
		)
	{
		Number value = (Number)variable.getIncrementedValue();

		if (value == null || variable.isInitialized())
		{
			value = JRIntegerIncrementerFactory.ZERO;
		}

		if (expressionValue == null)
		{
			return value;
		}

		return new Integer(value.intValue() + 1);
	}

	
	public Object combine(JRCalculable calculable, JRCalculable calculableValue, AbstractValueProvider valueProvider)
	{
		Number value = (Number)calculable.getIncrementedValue();
		Number combineValue = (Number) calculableValue.getValue();

		if (value == null || calculable.isInitialized())
		{
			value = JRIntegerIncrementerFactory.ZERO;
		}

		if (combineValue == null)
		{
			return value;
		}

		return new Integer(value.intValue() + combineValue.intValue());
	}

	
	public Object initialValue()
	{
		return JRIntegerIncrementerFactory.ZERO;
	}
}


/**
 *
 */
class JRIntegerDistinctCountIncrementer extends JRAbstractExtendedIncrementer
{
	/**
	 *
	 */
	private static JRIntegerDistinctCountIncrementer mainInstance = new JRIntegerDistinctCountIncrementer();

	/**
	 *
	 */
	private JRIntegerDistinctCountIncrementer()
	{
	}

	/**
	 *
	 */
	public static JRIntegerDistinctCountIncrementer getInstance()
	{
		return mainInstance;
	}

	/**
	 *
	 */
	public Object increment(
		JRCalculable variable, 
		Object expressionValue,
		AbstractValueProvider valueProvider
		)
	{
		DistinctCountHolder holder = 
			(DistinctCountHolder)valueProvider.getValue(variable.getHelperVariable(JRCalculable.HELPER_COUNT));
		
		if (variable.isInitialized())
		{
			holder.init();
		}

		return new Integer((int)holder.getCount());
	}

	public Object combine(JRCalculable calculable, JRCalculable calculableValue, AbstractValueProvider valueProvider)
	{
		DistinctCountHolder holder = 
			(DistinctCountHolder)valueProvider.getValue(calculable.getHelperVariable(JRCalculable.HELPER_COUNT));
		
		return new Integer((int)holder.getCount());
	}
	
	public Object initialValue()
	{
		return JRIntegerIncrementerFactory.ZERO;
	}
}


/**
 *
 */
class JRIntegerSumIncrementer extends JRAbstractExtendedIncrementer
{
	/**
	 *
	 */
	private static JRIntegerSumIncrementer mainInstance = new JRIntegerSumIncrementer();

	/**
	 *
	 */
	private JRIntegerSumIncrementer()
	{
	}

	/**
	 *
	 */
	public static JRIntegerSumIncrementer getInstance()
	{
		return mainInstance;
	}

	/**
	 *
	 */
	public Object increment(
		JRCalculable variable, 
		Object expressionValue,
		AbstractValueProvider valueProvider
		)
	{
		Number value = (Number)variable.getIncrementedValue();
		Number newValue = (Number)expressionValue;

		if (newValue == null)
		{
			if (variable.isInitialized())
			{
				return null;
			}

			return value;
		}

		if (value == null || variable.isInitialized())
		{
			value = JRIntegerIncrementerFactory.ZERO;
		}

		return new Integer(value.intValue() + newValue.intValue());
	}

	
	public Object initialValue()
	{
		return JRIntegerIncrementerFactory.ZERO;
	}
}


/**
 *
 */
class JRIntegerAverageIncrementer extends JRAbstractExtendedIncrementer
{
	/**
	 *
	 */
	private static JRIntegerAverageIncrementer mainInstance = new JRIntegerAverageIncrementer();

	/**
	 *
	 */
	private JRIntegerAverageIncrementer()
	{
	}

	/**
	 *
	 */
	public static JRIntegerAverageIncrementer getInstance()
	{
		return mainInstance;
	}

	/**
	 *
	 */
	public Object increment(
		JRCalculable variable, 
		Object expressionValue,
		AbstractValueProvider valueProvider
		)
	{
		if (expressionValue == null)
		{
			if (variable.isInitialized())
			{
				return null;
			}
			return variable.getValue();
		}
		Number countValue = (Number)valueProvider.getValue(variable.getHelperVariable(JRCalculable.HELPER_COUNT));
		Number sumValue = (Number)valueProvider.getValue(variable.getHelperVariable(JRCalculable.HELPER_SUM));
		return new Integer(sumValue.intValue() / countValue.intValue());
	}

	
	public Object initialValue()
	{
		return JRIntegerIncrementerFactory.ZERO;
	}
}


/**
 *
 */
class JRIntegerStandardDeviationIncrementer extends JRAbstractExtendedIncrementer
{
	/**
	 *
	 */
	private static JRIntegerStandardDeviationIncrementer mainInstance = new JRIntegerStandardDeviationIncrementer();

	/**
	 *
	 */
	private JRIntegerStandardDeviationIncrementer()
	{
	}

	/**
	 *
	 */
	public static JRIntegerStandardDeviationIncrementer getInstance()
	{
		return mainInstance;
	}

	/**
	 *
	 */
	public Object increment(
		JRCalculable variable, 
		Object expressionValue,
		AbstractValueProvider valueProvider
		)
	{
		if (expressionValue == null)
		{
			if (variable.isInitialized())
			{
				return null;
			}
			return variable.getValue(); 
		}
		Number varianceValue = (Number)valueProvider.getValue(variable.getHelperVariable(JRCalculable.HELPER_VARIANCE));
		return new Integer( (int)Math.sqrt(varianceValue.doubleValue()) );
	}

	
	public Object initialValue()
	{
		return JRIntegerIncrementerFactory.ZERO;
	}
}


/**
 *
 */
class JRIntegerVarianceIncrementer extends JRAbstractExtendedIncrementer
{
	/**
	 *
	 */
	private static JRIntegerVarianceIncrementer mainInstance = new JRIntegerVarianceIncrementer();

	/**
	 *
	 */
	private JRIntegerVarianceIncrementer()
	{
	}

	/**
	 *
	 */
	public static JRIntegerVarianceIncrementer getInstance()
	{
		return mainInstance;
	}

	/**
	 *
	 */
	public Object increment(
		JRCalculable variable, 
		Object expressionValue,
		AbstractValueProvider valueProvider
		)
	{
		Number value = (Number)variable.getIncrementedValue();
		Number newValue = (Number)expressionValue;
		
		if (newValue == null)
		{
			if (variable.isInitialized())
			{
				return null;
			}
			return value;
		}
		else if (value == null || variable.isInitialized())
		{
			return JRIntegerIncrementerFactory.ZERO;
		}
		else
		{
			Number countValue = (Number)valueProvider.getValue(variable.getHelperVariable(JRCalculable.HELPER_COUNT));
			Number sumValue = (Number)valueProvider.getValue(variable.getHelperVariable(JRCalculable.HELPER_SUM));
			return
				new Integer(
					(countValue.intValue() - 1) * value.intValue() / countValue.intValue() +
					( sumValue.intValue() / countValue.intValue() - newValue.intValue() ) *
					( sumValue.intValue() / countValue.intValue() - newValue.intValue() ) /
					(countValue.intValue() - 1)
					);
		}
	}

	public Object combine(JRCalculable calculable, JRCalculable calculableValue, AbstractValueProvider valueProvider)
	{
		Number value = (Number)calculable.getIncrementedValue();
		
		if (calculableValue.getValue() == null)
		{
			if (calculable.isInitialized())
			{
				return null;
			}

			return value;
		}
		else if (value == null || calculable.isInitialized())
		{
			return new Integer(((Number) calculableValue.getIncrementedValue()).intValue());
		}

		double v1 = value.doubleValue();
		double c1 = ((Number) valueProvider.getValue(calculable.getHelperVariable(JRCalculable.HELPER_COUNT))).doubleValue();
		double s1 = ((Number) valueProvider.getValue(calculable.getHelperVariable(JRCalculable.HELPER_SUM))).doubleValue();

		double v2 = ((Number) calculableValue.getIncrementedValue()).doubleValue();
		double c2 = ((Number) valueProvider.getValue(calculableValue.getHelperVariable(JRCalculable.HELPER_COUNT))).doubleValue();
		double s2 = ((Number) valueProvider.getValue(calculableValue.getHelperVariable(JRCalculable.HELPER_SUM))).doubleValue();

		c1 -= c2;
		s1 -= s2;
		
		double c = c1 + c2;

		return new Integer(
				(int) (
				c1 / c * v1 +
				c2 / c * v2 +
				c2 / c1 * s1 / c * s1 / c +
				c1 / c2 * s2 / c * s2 / c -
				2 * s1 / c * s2 /c
				));
	}

	
	public Object initialValue()
	{
		return JRIntegerIncrementerFactory.ZERO;
	}
}
