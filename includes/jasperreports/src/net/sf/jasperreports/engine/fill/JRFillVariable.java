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

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRVariable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillVariable.java 2004 2007-12-05 14:18:45Z teodord $
 */
public class JRFillVariable implements JRVariable, JRCalculable
{


	/**
	 *
	 */
	protected JRVariable parent = null;

	/**
	 *
	 */
	private JRGroup resetGroup = null;
	private JRGroup incrementGroup = null;

	/**
	 *
	 */
	private Object previousOldValue = null;
	private Object oldValue = null;
	private Object estimatedValue = null;
	private Object incrementedValue = null;
	private Object value = null;
	private boolean isInitialized = false;
	private Object savedValue;
	
	private JRFillVariable[] helperVariables;

	/**
	 *
	 */
	private JRIncrementer incrementer = null;


	/**
	 *
	 */
	protected JRFillVariable(
		JRVariable variable, 
		JRFillObjectFactory factory
		)
	{
		factory.put(variable, this);

		parent = variable;
		
		resetGroup = factory.getGroup(variable.getResetGroup());
		incrementGroup = factory.getGroup(variable.getIncrementGroup());
		
		helperVariables = new JRFillVariable[JRCalculable.HELPER_SIZE];
	}


	/**
	 *
	 */
	public String getName()
	{
		return parent.getName();
	}
		
	/**
	 *
	 */
	public Class getValueClass()
	{
		return parent.getValueClass();
	}
		
	/**
	 *
	 */
	public String getValueClassName()
	{
		return parent.getValueClassName();
	}
		
	/**
	 *
	 */
	public Class getIncrementerFactoryClass()
	{
		return parent.getIncrementerFactoryClass();
	}
		
	/**
	 *
	 */
	public String getIncrementerFactoryClassName()
	{
		return parent.getIncrementerFactoryClassName();
	}
		
	/**
	 *
	 */
	public JRExpression getExpression()
	{
		return parent.getExpression();
	}
		
	/**
	 *
	 */
	public JRExpression getInitialValueExpression()
	{
		return parent.getInitialValueExpression();
	}
		
	/**
	 *
	 */
	public byte getResetType()
	{
		return parent.getResetType();
	}
		
	/**
	 *
	 */
	public byte getIncrementType()
	{
		return parent.getIncrementType();
	}
		
	/**
	 *
	 */
	public byte getCalculation()
	{
		return parent.getCalculation();
	}
		
	/**
	 *
	 */
	public boolean isSystemDefined()
	{
		return parent.isSystemDefined();
	}

	/**
	 *
	 */
	public JRGroup getResetGroup()
	{
		return resetGroup;
	}
		
	/**
	 *
	 */
	public JRGroup getIncrementGroup()
	{
		return incrementGroup;
	}
	
	/**
	 *
	 */
	public Object getOldValue()
	{
		return oldValue;
	}
		
	/**
	 *
	 */
	public void setOldValue(Object oldValue)
	{
		this.oldValue = oldValue;
	}

	/**
	 *
	 */
	public Object getEstimatedValue()
	{
		return estimatedValue;
	}
		
	/**
	 *
	 */
	public void setEstimatedValue(Object estimatedValue)
	{
		this.estimatedValue = estimatedValue;
	}

	/**
	 *
	 */
	public Object getIncrementedValue()
	{
		return incrementedValue;
	}
		
	/**
	 *
	 */
	public void setIncrementedValue(Object incrementedValue)
	{
		this.incrementedValue = incrementedValue;
	}

	/**
	 *
	 */
	public Object getValue()
	{
		return value;
	}
		
	/**
	 *
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}

	/**
	 *
	 */
	public boolean isInitialized()
	{
		return isInitialized;
	}
		
	/**
	 *
	 */
	public void setInitialized(boolean isInitialized)
	{
		this.isInitialized = isInitialized;
	}

		
	/**
	 *
	 */
	public JRIncrementer getIncrementer()
	{
		if (incrementer == null)
		{
			Class incrementerFactoryClass = getIncrementerFactoryClass();
			
			JRIncrementerFactory incrementerFactory;
			if (incrementerFactoryClass == null)
			{
				incrementerFactory = JRDefaultIncrementerFactory.getFactory(getValueClass());
			}
			else
			{
				incrementerFactory = JRIncrementerFactoryCache.getInstance(incrementerFactoryClass); 
			}
			
			incrementer = incrementerFactory.getIncrementer(getCalculation());
		}
		
		return incrementer;
	}

	
	/**
	 * Sets a helper variable.
	 * 
	 * @param helperVariable the helper variable
	 * @param type the helper type
	 * @return the previous helper variable for the type
	 */
	public JRFillVariable setHelperVariable(JRFillVariable helperVariable, byte type)
	{
		JRFillVariable old = helperVariables[type]; 
		helperVariables[type] = helperVariable;
		return old;
	}
	
	
	/**
	 * Returns a helper variable.
	 * 
	 * @param type the helper type
	 * @return the helper variable for the specified type
	 */
	public JRCalculable getHelperVariable(byte type)
	{
		return helperVariables[type];
	}
	
	
	public Object getValue(byte evaluation)
	{
		Object returnValue;
		switch (evaluation)
		{
			case JRExpression.EVALUATION_OLD:
				returnValue = oldValue;
				break;
			case JRExpression.EVALUATION_ESTIMATED:
				returnValue = estimatedValue;
				break;
			default:
				returnValue = value;
				break;
		}
		return returnValue;
	}
	
	public void overwriteValue(Object newValue, byte evaluation)
	{
		switch (evaluation)
		{
			case JRExpression.EVALUATION_OLD:
				savedValue = oldValue;
				oldValue = newValue;
				break;
			case JRExpression.EVALUATION_ESTIMATED:
				savedValue = estimatedValue;
				estimatedValue = newValue;
				break;
			default:
				savedValue = value;
				value = newValue;
				break;
		}
	}
	
	public void restoreValue(byte evaluation)
	{
		switch (evaluation)
		{
			case JRExpression.EVALUATION_OLD:
				oldValue = savedValue;
				break;
			case JRExpression.EVALUATION_ESTIMATED:
				estimatedValue = savedValue;
				break;
			default:
				value = savedValue;
				break;
		}
		savedValue = null;
	}


	
	public Object getPreviousOldValue()
	{
		return previousOldValue;
	}


	
	public void setPreviousOldValue(Object previousOldValue)
	{
		this.previousOldValue = previousOldValue;
	}

	/**
	 *
	 */
	public Object clone() 
	{
		return null;
	}

}
