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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRRuntimeException;

/**
 * Base class for the dynamically generated expression evaluator classes.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JREvaluator.java 1841 2007-09-05 15:49:32Z teodord $
 */
public abstract class JREvaluator
{
	/**
	 * The resource bundle parameter.
	 */
	private JRFillParameter resourceBundle = null;
	
	/**
	 * The resource missing type.
	 */
	private byte whenResourceMissingType;

	/**
	 * The report Locale used when parsing the bundle message.
	 */
	private JRFillParameter locale;
	
	/**
	 * Default constructor.
	 */
	protected JREvaluator()
	{
	}

	
	/**
	 * Initializes the evaluator by setting the parameter, field and variable objects.
	 * 
	 * @param parametersMap the parameters indexed by name
	 * @param fieldsMap the fields indexed by name
	 * @param variablesMap the variables indexed by name
	 * @param resourceMissingType the resource missing type
	 * @throws JRException
	 */
	public void init(Map parametersMap, Map fieldsMap, Map variablesMap, byte resourceMissingType) throws JRException
	{
		this.whenResourceMissingType = resourceMissingType;
		this.resourceBundle = (JRFillParameter)parametersMap.get(JRParameter.REPORT_RESOURCE_BUNDLE);
		this.locale = (JRFillParameter)parametersMap.get(JRParameter.REPORT_LOCALE);
		customizedInit(parametersMap, fieldsMap, variablesMap);
	}

	
	/**
	 * Constructs a message using a pattern with one parameter.
	 * 
	 * @param pattern the message pattern
	 * @param arg0 the message parameter
	 * @return the constructed message
	 * @see MessageFormat#format(java.lang.Object[],java.lang.StringBuffer, java.text.FieldPosition)
	 */
	public String msg(String pattern, Object arg0)
	{
		return getMessageFormat(pattern).format(new Object[] { arg0 }, new StringBuffer(), null).toString();
	}

	/**
	 * Constructs a message using a pattern with two parameters.
	 * 
	 * @param pattern the message pattern
	 * @param arg0 the first message parameter
	 * @param arg1 the second message paramter
	 * @return the constructed message
	 * @see MessageFormat#format(java.lang.Object[],java.lang.StringBuffer, java.text.FieldPosition)
	 */
	public String msg(String pattern, Object arg0, Object arg1)
	{
		return getMessageFormat(pattern).format(new Object[] { arg0, arg1 }, new StringBuffer(), null).toString();
	}

	
	/**
	 * Constructs a message using a pattern with three parameters.
	 * 
	 * @param pattern the message pattern
	 * @param arg0 the first message parameter
	 * @param arg1 the second message paramter
	 * @param arg2 the third parameter
	 * @return the constructed message
	 * @see MessageFormat#format(java.lang.Object[],java.lang.StringBuffer, java.text.FieldPosition)
	 */
	public String msg(String pattern, Object arg0, Object arg1, Object arg2)
	{
		return getMessageFormat(pattern).format(new Object[] { arg0, arg1, arg2 }, new StringBuffer(), null).toString();
	}

	/**
	 * Constructs a message using a pattern with an Object array parameter.
	 * 
	 * @param pattern the message pattern
	 * @param args the parameter Object array
	 * @return the constructed message
	 * @see MessageFormat#format(java.lang.Object[],java.lang.StringBuffer, java.text.FieldPosition)
	 */
	public String msg(String pattern, Object[] args)
	{
		return getMessageFormat(pattern).format(args, new StringBuffer(), null).toString();
	}
	
	/**
	 * Returns a string for a given key from the resource bundle associated with the evaluator.
	 * 
	 * @param key the key
	 * @return the string for the given key
	 * @see ResourceBundle#getString(java.lang.String)
	 */
	public String str(String key)
	{
		String str = null;

		try
		{
			str = ((ResourceBundle) resourceBundle.getValue()).getString(key);
		}
		catch (NullPointerException e)
		{
			str = handleMissingResource(key, e);
		}
		catch (MissingResourceException e)
		{
			str = handleMissingResource(key, e);
		}

		return str;
	}


	/**
	 *
	 */
	public Object evaluate(JRExpression expression) throws JRExpressionEvalException
	{
		Object value = null;
		
		if (expression != null)
		{
			try
			{
				value = evaluate(expression.getId());
			}
			catch (NullPointerException e)
			{
			}
			catch (OutOfMemoryError e)
			{
				throw e;
			}
			catch (Throwable e)
			{
				throw new JRExpressionEvalException(expression, e);
			}
		}
		
		return value;
	}
	

	/**
	 *
	 */
	public Object evaluateOld(JRExpression expression) throws JRExpressionEvalException
	{
		Object value = null;
		
		if (expression != null)
		{
			try
			{
				value = evaluateOld(expression.getId());
			}
			catch (NullPointerException e)
			{
			}
			catch (OutOfMemoryError e)
			{
				throw e;
			}
			catch (Throwable e)
			{
				throw new JRExpressionEvalException(expression, e);
			}
		}
		
		return value;
	}


	/**
	 *
	 */
	public Object evaluateEstimated(JRExpression expression) throws JRExpressionEvalException
	{
		Object value = null;
		
		if (expression != null)
		{
			try
			{
				value = evaluateEstimated(expression.getId());
			}
			catch (NullPointerException e)
			{
			}
			catch (OutOfMemoryError e)
			{
				throw e;
			}
			catch (Throwable e)
			{
				throw new JRExpressionEvalException(expression, e);
			}
		}
		
		return value;
	}

	
	/**
	 * Handles the case when a resource is missing.
	 * 
	 * @param key
	 *            the resource key
	 * @param e
	 *            the exception
	 * @return the value to use for the resource
	 * @throws JRRuntimeException
	 *             when the resource missing handling type is Error
	 */
	protected String handleMissingResource(String key, Exception e) throws JRRuntimeException
	{
		String str;
		switch (whenResourceMissingType)
		{
			case JRReport.WHEN_RESOURCE_MISSING_TYPE_EMPTY:
			{
				str = "";
				break;
			}
			case JRReport.WHEN_RESOURCE_MISSING_TYPE_KEY:
			{
				str = key;
				break;
			}
			case JRReport.WHEN_RESOURCE_MISSING_TYPE_ERROR:
			{
				throw new JRRuntimeException("Resource not found for key \"" + key + "\".", e);
			}
			case JRReport.WHEN_RESOURCE_MISSING_TYPE_NULL:
			default:
			{
				str = null;
				break;
			}
		}

		return str;
	}


	/**
	 * Initializes the parameters, fields and variables of the evaluator.
	 * 
	 * @param parametersMap the parameters indexed by name
	 * @param fieldsMap the fields indexed by name
	 * @param variablesMap the variables indexed by name
	 * @throws JRException
	 */
	protected abstract void customizedInit(Map parametersMap, Map fieldsMap, Map variablesMap) throws JRException;


	/**
	 * Evaluates an expression using current fields and variables values.
	 * 
	 * @param id the expression id
	 * @return the result of the evaluation
	 * @throws Throwable
	 * @see net.sf.jasperreports.engine.JRExpression#EVALUATION_DEFAULT
	 * @see JRFillVariable#getValue()
	 * @see JRFillField#getValue()
	 */
	protected abstract Object evaluate(int id) throws Throwable;


	/**
	 * Evaluates an expression using old fields and variables values.
	 * 
	 * @param id the expression id
	 * @return the result of the evaluation
	 * @throws Throwable
	 * @see net.sf.jasperreports.engine.JRExpression#EVALUATION_OLD
	 * @see JRFillVariable#getOldValue()
	 * @see JRFillField#getOldValue()
	 */
	protected abstract Object evaluateOld(int id) throws Throwable;


	/**
	 * Evaluates an expression using estimated variables values.
	 * 
	 * @param id the expression id
	 * @return the result of the evaluation
	 * @throws Throwable
	 * @see net.sf.jasperreports.engine.JRExpression#EVALUATION_ESTIMATED
	 * @see JRFillVariable#getEstimatedValue()
	 */
	protected abstract Object evaluateEstimated(int id) throws Throwable;


	/**
	 * 
	 */
	private MessageFormat getMessageFormat(String pattern)
	{
		MessageFormat messageFormat = new MessageFormat("");
		messageFormat.setLocale((Locale)locale.getValue());
		messageFormat.applyPattern(pattern);
		return messageFormat;
	}

}
