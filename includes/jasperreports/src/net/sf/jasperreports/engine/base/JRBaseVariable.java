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
package net.sf.jasperreports.engine.base;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.util.JRClassLoader;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseVariable.java 2000 2007-12-05 14:07:13Z teodord $
 */
public class JRBaseVariable implements JRVariable, Serializable
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 *
	 */
	protected String name = null;
	protected String valueClassName = java.lang.String.class.getName();
	protected String valueClassRealName = null;
	protected String incrementerFactoryClassName = null;
	protected String incrementerFactoryClassRealName = null;
	protected byte resetType = RESET_TYPE_REPORT;
	protected byte incrementType = RESET_TYPE_NONE;
	protected byte calculation = CALCULATION_NOTHING;
	protected boolean isSystemDefined = false;

	protected transient Class valueClass = null;
	protected transient Class incrementerFactoryClass = null;

	/**
	 *
	 */
	protected JRExpression expression = null;
	protected JRExpression initialValueExpression = null;
	protected JRGroup resetGroup = null;
	protected JRGroup incrementGroup = null;


	/**
	 *
	 */
	protected JRBaseVariable()
	{
	}
	
	
	/**
	 *
	 */
	protected JRBaseVariable(JRVariable variable, JRBaseObjectFactory factory)
	{
		factory.put(variable, this);
		
		name = variable.getName();
		valueClassName = variable.getValueClassName();
		incrementerFactoryClassName = variable.getIncrementerFactoryClassName();
		resetType = variable.getResetType();
		incrementType = variable.getIncrementType();
		calculation = variable.getCalculation();
		isSystemDefined = variable.isSystemDefined();
		
		expression = factory.getExpression(variable.getExpression());
		initialValueExpression = factory.getExpression(variable.getInitialValueExpression());

		resetGroup = factory.getGroup(variable.getResetGroup());
		incrementGroup = factory.getGroup(variable.getIncrementGroup());
	}
		

	/**
	 *
	 */
	public String getName()
	{
		return this.name;
	}
		
	/**
	 *
	 */
	public Class getValueClass()
	{
		if (valueClass == null)
		{
			String className = getValueClassRealName();
			if (className != null)
			{
				try
				{
					valueClass = JRClassLoader.loadClassForName(className);
				}
				catch(ClassNotFoundException e)
				{
					throw new JRRuntimeException(e);
				}
			}
		}
		
		return valueClass;
	}
		
	/**
	 *
	 */
	public String getValueClassName()
	{
		return valueClassName;
	}

	/**
	 *
	 */
	private String getValueClassRealName()
	{
		if (valueClassRealName == null)
		{
			valueClassRealName = JRClassLoader.getClassRealName(valueClassName);
		}
		
		return valueClassRealName;
	}

	/**
	 *
	 */
	public Class getIncrementerFactoryClass()
	{
		if (incrementerFactoryClass == null)
		{
			String className = getIncrementerFactoryClassRealName();
			if (className != null)
			{
				try
				{
					incrementerFactoryClass = JRClassLoader.loadClassForName(className);
				}
				catch(ClassNotFoundException e)
				{
					throw new JRRuntimeException(e);
				}
			}
		}
		
		return incrementerFactoryClass;
	}
		
	/**
	 *
	 */
	public String getIncrementerFactoryClassName()
	{
		return incrementerFactoryClassName;
	}

	/**
	 *
	 */
	private String getIncrementerFactoryClassRealName()
	{
		if (incrementerFactoryClassRealName == null)
		{
			incrementerFactoryClassRealName = JRClassLoader.getClassRealName(incrementerFactoryClassName);
		}
		
		return incrementerFactoryClassRealName;
	}

	/**
	 *
	 */
	public byte getResetType()
	{
		return this.resetType;
	}
		
	/**
	 *
	 */
	public byte getIncrementType()
	{
		return this.incrementType;
	}
		
	/**
	 *
	 */
	public byte getCalculation()
	{
		return this.calculation;
	}

	/**
	 *
	 */
	public boolean isSystemDefined()
	{
		return this.isSystemDefined;
	}

	/**
	 *
	 */
	public JRExpression getExpression()
	{
		return this.expression;
	}
		
	/**
	 *
	 */
	public JRExpression getInitialValueExpression()
	{
		return this.initialValueExpression;
	}
		
	/**
	 *
	 */
	public JRGroup getResetGroup()
	{
		return this.resetGroup;
	}
		
	/**
	 *
	 */
	public JRGroup getIncrementGroup()
	{
		return this.incrementGroup;
	}

	/**
	 *
	 */
	public Object clone() 
	{
		JRBaseVariable clone = null;
		
		try
		{
			clone = (JRBaseVariable)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		if (expression != null)
		{
			clone.expression = (JRExpression)expression.clone();
		}
		if (initialValueExpression != null)
		{
			clone.initialValueExpression = (JRExpression)initialValueExpression.clone();
		}
		return clone;
	}
		
}
