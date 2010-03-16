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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLStreamHandlerFactory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import net.sf.jasperreports.engine.JRAbstractScriptlet;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JRVirtualizer;
import net.sf.jasperreports.engine.base.JRBaseDataset;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;
import net.sf.jasperreports.engine.util.FormatFactory;
import net.sf.jasperreports.engine.util.JRQueryExecuterUtils;

/**
 * Implementation of {@link net.sf.jasperreports.engine.JRDataset JRDataset} to be used for report desing.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRDesignDataset.java 2002 2007-12-05 14:11:45Z teodord $
 */
public class JRDesignDataset extends JRBaseDataset
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public static final String PROPERTY_FIELDS = "fields";

	public static final String PROPERTY_FILTER_EXPRESSION = "filterExpression";

	public static final String PROPERTY_GROUPS = "groups";
	
	public static final String PROPERTY_NAME = "name";
	
	public static final String PROPERTY_PARAMETERS = "parameters";
	
	public static final String PROPERTY_QUERY = "query";
	
	public static final String PROPERTY_RESOURCE_BUNDLE = "resourceBundle";
	
	public static final String PROPERTY_SCRIPTLET_CLASS = "scriptletClass";
	
	public static final String PROPERTY_SORT_FIELDS = "sortFields";
	
	public static final String PROPERTY_VARIABLES = "variables";

	/**
	 * Parameters mapped by name.
	 */
	protected Map parametersMap = new HashMap();
	protected List parametersList = new ArrayList();

	/**
	 * Fields mapped by name.
	 */
	protected Map fieldsMap = new HashMap();
	protected List fieldsList = new ArrayList();


	/**
	 * Sort fields mapped by name.
	 */
	protected Map sortFieldsMap = new HashMap();
	protected List sortFieldsList = new ArrayList();


	/**
	 * Variables mapped by name.
	 */
	protected Map variablesMap = new HashMap();
	protected List variablesList = new ArrayList();


	/**
	 * Groups mapped by name.
	 */
	protected Map groupsMap = new HashMap();
	protected List groupsList = new ArrayList();

	private class QueryLanguageChangeListener implements PropertyChangeListener, Serializable
	{
		private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

		public void propertyChange(PropertyChangeEvent evt)
		{
			queryLanguageChanged((String) evt.getOldValue(), (String) evt.getNewValue());
		}
	}
	
	private PropertyChangeListener queryLanguageChangeListener = new QueryLanguageChangeListener();
	
	
	/**
	 * An array containing the built-in parameters that can be found and used in any report dataset.
	 */
	private static final Object[] BUILT_IN_PARAMETERS = new Object[] { 
		JRParameter.REPORT_PARAMETERS_MAP, java.util.Map.class, 
		JRParameter.REPORT_CONNECTION, Connection.class,
		JRParameter.REPORT_MAX_COUNT, Integer.class, 
		JRParameter.REPORT_DATA_SOURCE, JRDataSource.class, 
		JRParameter.REPORT_SCRIPTLET, JRAbstractScriptlet.class, 
		JRParameter.REPORT_LOCALE, Locale.class, 
		JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.class,
		JRParameter.REPORT_TIME_ZONE, TimeZone.class, 
		JRParameter.REPORT_FORMAT_FACTORY, FormatFactory.class, 
		JRParameter.REPORT_CLASS_LOADER, ClassLoader.class,
		JRParameter.REPORT_URL_HANDLER_FACTORY, URLStreamHandlerFactory.class};


	
	/**
	 * An array containing the built-in parameters that can be found and used in any report/main dataset.
	 */
	private static final Object[] BUILT_IN_PARAMETERS_MAIN = new Object[] { 
		JRParameter.REPORT_VIRTUALIZER, JRVirtualizer.class, 
		JRParameter.IS_IGNORE_PAGINATION, Boolean.class,
		JRParameter.REPORT_TEMPLATES, Collection.class };

	
	/**
	 * Create a dataset.
	 * 
	 * @param isMain whether this is the main dataset of the report or a sub dataset
	 * @see net.sf.jasperreports.engine.JRDataset#isMainDataset()
	 */
	public JRDesignDataset(boolean isMain)
	{
		super(isMain);
		
		addBuiltinParameters(BUILT_IN_PARAMETERS);
		
		if (isMain)
		{
			addBuiltinParameters(BUILT_IN_PARAMETERS_MAIN);
		}

		try 
		{
			if (isMain)
			{
				addVariable(createPageNumberVariable());
				addVariable(createColumnNumberVariable());
			}
			addVariable(createReportCountVariable());
			if (isMain)
			{
				addVariable(createPageCountVariable());
				addVariable(createColumnCountVariable());
			}
		}
		catch (JRException e)
		{
			//never reached
		}
	}

	private static JRDesignVariable createPageCountVariable()
	{
		JRDesignExpression expression;
		JRDesignVariable variable;
		variable = new JRDesignVariable();
		variable.setName(JRVariable.PAGE_COUNT);
		variable.setValueClass(Integer.class);
		variable.setResetType(JRVariable.RESET_TYPE_PAGE);
		variable.setCalculation(JRVariable.CALCULATION_COUNT);
		variable.setSystemDefined(true);
		expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		expression.setText("new Integer(1)");
		variable.setExpression(expression);
		expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		expression.setText("new Integer(0)");
		variable.setInitialValueExpression(expression);
		return variable;
	}

	private static JRDesignVariable createColumnNumberVariable()
	{
		JRDesignExpression expression;
		JRDesignVariable variable;
		variable = new JRDesignVariable();
		variable.setName(JRVariable.COLUMN_NUMBER);
		variable.setValueClass(Integer.class);
		//variable.setResetType(JRVariable.RESET_TYPE_COLUMN);
		variable.setResetType(JRVariable.RESET_TYPE_PAGE);
		variable.setCalculation(JRVariable.CALCULATION_SYSTEM);
		variable.setSystemDefined(true);
		expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		//expression.setText("($V{COLUMN_NUMBER} != null)?(new Integer($V{COLUMN_NUMBER}.intValue() + 1)):(new Integer(1))");
		expression.setText("new Integer(1)");
		variable.setInitialValueExpression(expression);
		return variable;
	}

	private static JRDesignVariable createPageNumberVariable()
	{
		JRDesignVariable variable = new JRDesignVariable();
		variable.setName(JRVariable.PAGE_NUMBER);
		variable.setValueClass(Integer.class);
		//variable.setResetType(JRVariable.RESET_TYPE_PAGE);
		variable.setResetType(JRVariable.RESET_TYPE_REPORT);
		variable.setCalculation(JRVariable.CALCULATION_SYSTEM);
		variable.setSystemDefined(true);
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		//expression.setText("($V{PAGE_NUMBER} != null)?(new Integer($V{PAGE_NUMBER}.intValue() + 1)):(new Integer(1))");
		expression.setText("new Integer(1)");
		variable.setInitialValueExpression(expression);
		return variable;
	}

	private static JRDesignVariable createColumnCountVariable()
	{
		JRDesignVariable variable;
		JRDesignExpression expression;
		variable = new JRDesignVariable();
		variable.setName(JRVariable.COLUMN_COUNT);
		variable.setValueClass(Integer.class);
		variable.setResetType(JRVariable.RESET_TYPE_COLUMN);
		variable.setCalculation(JRVariable.CALCULATION_COUNT);
		variable.setSystemDefined(true);
		expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		expression.setText("new Integer(1)");
		variable.setExpression(expression);
		expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		expression.setText("new Integer(0)");
		variable.setInitialValueExpression(expression);
		return variable;
	}

	private void addBuiltinParameters(Object[] parametersArray)
	{
		for (int i = 0; i < parametersArray.length; i++)
		{
			JRDesignParameter parameter = new JRDesignParameter();
			parameter.setName((String) parametersArray[i++]);
			parameter.setValueClass((Class) parametersArray[i]);
			parameter.setSystemDefined(true);
			try
			{
				addParameter(parameter);
			}
			catch (JRException e)
			{
				// never reached
			}
		}
	}

	private static JRDesignVariable createReportCountVariable()
	{
		JRDesignVariable variable = new JRDesignVariable();
		variable.setName(JRVariable.REPORT_COUNT);
		variable.setValueClass(Integer.class);
		variable.setResetType(JRVariable.RESET_TYPE_REPORT);
		variable.setCalculation(JRVariable.CALCULATION_COUNT);
		variable.setSystemDefined(true);
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		expression.setText("new Integer(1)");
		variable.setExpression(expression);
		expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		expression.setText("new Integer(0)");
		variable.setInitialValueExpression(expression);
		return variable;
	}

	
	/**
	 * Sets the name of the dataset.
	 * @param name the name of the dataset
	 * @see net.sf.jasperreports.engine.JRDataset#getName()
	 */
	public void setName(String name)
	{
		Object old = this.name;
		this.name = name;
		getEventSupport().firePropertyChange(PROPERTY_NAME, old, this.name);
	}

	
	
	public JRParameter[] getParameters()
	{
		JRParameter[] parametersArray = new JRParameter[parametersList.size()];

		parametersList.toArray(parametersArray);

		return parametersArray;
	}

	
	/**
	 * Returns the list of parameters, including build-in ones.
	 * 
	 * @return list of {@link JRParameter JRParameter} objects
	 */
	public List getParametersList()
	{
		return parametersList;
	}

	
	/**
	 * Returns the map of parameters, including build-in ones, indexed by name.
	 * 
	 * @return {@link JRParameter JRParameter} objects indexed by name
	 */
	public Map getParametersMap()
	{
		return parametersMap;
	}

	
	/**
	 * Adds a parameter to the dataset.
	 * @param parameter the parameter to add
	 * @throws JRException
	 * @see net.sf.jasperreports.engine.JRDataset#getParameters()
	 */
	public void addParameter(JRParameter parameter) throws JRException
	{
		if (parametersMap.containsKey(parameter.getName()))
		{
			throw new JRException("Duplicate declaration of parameter : " + parameter.getName());
		}

		parametersList.add(parameter);
		parametersMap.put(parameter.getName(), parameter);
		
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_PARAMETERS, parameter, parametersList.size() - 1);
	}

	
	/**
	 * Removes a parameter from the dataset.
	 * 
	 * @param parameterName the parameter name
	 * @return the removed parameter, or <code>null</code> if the parameter was not found
	 */
	public JRParameter removeParameter(String parameterName)
	{
		return removeParameter((JRParameter) parametersMap.get(parameterName));
	}

	
	/**
	 * Removes a parameter from the dataset.
	 * 
	 * @param parameter the parameter to be removed
	 * @return the parameter to be removed
	 */
	public JRParameter removeParameter(JRParameter parameter)
	{
		if (parameter != null)
		{
			int idx = parametersList.indexOf(parameter);
			if (idx >= 0)
			{
				parametersList.remove(idx);
				parametersMap.remove(parameter.getName());
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_PARAMETERS, parameter, idx);
			}
		}

		return parameter;
	}

	
	/**
	 * Sets the dataset query.
	 * 
	 * @param query the query
	 * @see net.sf.jasperreports.engine.JRDataset#getQuery()
	 */
	public void setQuery(JRDesignQuery query)
	{
		Object old = query;
		String oldLanguage = null;
		if (this.query != null)
		{
			((JRDesignQuery) this.query).removePropertyChangeListener(JRDesignQuery.PROPERTY_LANGUAGE, queryLanguageChangeListener);
			oldLanguage = this.query.getLanguage();
		}
		this.query = query;
		String newLanguage = null;
		if (query != null)
		{
			query.addPropertyChangeListener(JRDesignQuery.PROPERTY_LANGUAGE, queryLanguageChangeListener);
			newLanguage = query.getLanguage();
		}
		queryLanguageChanged(oldLanguage, newLanguage);
		getEventSupport().firePropertyChange(PROPERTY_QUERY, old, this.query);
	}

	
	/**
	 * Sets the scriptlet class name.
	 * <p>
	 * If no scriptlet class name is specified, a default scriptlet is used.
	 * 
	 * @param scriptletClass the class name of the scriptlet
	 * @see net.sf.jasperreports.engine.JRDataset#getScriptletClass()
	 */
	public void setScriptletClass(String scriptletClass)
	{
		Object old = this.scriptletClass;
		this.scriptletClass = scriptletClass;
		if (scriptletClass == null)
		{
			((JRDesignParameter) parametersMap.get(JRParameter.REPORT_SCRIPTLET)).setValueClass(JRAbstractScriptlet.class);
		}
		else
		{
			((JRDesignParameter) parametersMap.get(JRParameter.REPORT_SCRIPTLET)).setValueClassName(scriptletClass);
		}
		getEventSupport().firePropertyChange(PROPERTY_SCRIPTLET_CLASS, old, this.scriptletClass);
	}

	
	public JRField[] getFields()
	{
		JRField[] fieldsArray = new JRField[fieldsList.size()];

		fieldsList.toArray(fieldsArray);

		return fieldsArray;
	}


	/**
	 * Returns the list of fields.
	 * 
	 * @return list of {@link JRField JRField} objects
	 */
	public List getFieldsList()
	{
		return fieldsList;
	}

	
	/**
	 * Returns the map of fields indexed by name.
	 * 
	 * @return {@link JRField JRField} objects indexed by name
	 */
	public Map getFieldsMap()
	{
		return fieldsMap;
	}

	
	/**
	 * Adds a field to the dataset.
	 * @param field the field to add
	 * @throws JRException
	 * @see net.sf.jasperreports.engine.JRDataset#getFields()
	 */
	public void addField(JRField field) throws JRException
	{
		if (fieldsMap.containsKey(field.getName()))
		{
			throw new JRException("Duplicate declaration of field : " + field.getName());
		}

		fieldsList.add(field);
		fieldsMap.put(field.getName(), field);
		
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_FIELDS, field, fieldsList.size() - 1);
	}

	
	/**
	 * Removes a field from the dataset.
	 * 
	 * @param fieldName the field name
	 * @return the removed field, or <code>null</code> if the field was not found
	 */
	public JRField removeField(String fieldName)
	{
		return removeField((JRField) fieldsMap.get(fieldName));
	}

	
	/**
	 * Removes a field from the dataset.
	 * 
	 * @param field the field to be removed
	 * @return the field to be removed
	 */
	public JRField removeField(JRField field)
	{
		if (field != null)
		{
			int idx = fieldsList.indexOf(field);
			if (idx >= 0)
			{
				fieldsList.remove(idx);
				fieldsMap.remove(field.getName());
				
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_FIELDS, field, idx);
			}
		}

		return field;
	}

	
	public JRSortField[] getSortFields()
	{
		JRSortField[] sortFieldsArray = new JRSortField[sortFieldsList.size()];

		sortFieldsList.toArray(sortFieldsArray);

		return sortFieldsArray;
	}


	/**
	 * Returns the list of sort fields.
	 * 
	 * @return list of {@link JRSortField JRSortField} objects
	 */
	public List getSortFieldsList()
	{
		return sortFieldsList;
	}

	
	/**
	 * Adds a sort field to the dataset.
	 * @param sortField the sort field to add
	 * @throws JRException
	 * @see net.sf.jasperreports.engine.JRDataset#getSortFields()
	 */
	public void addSortField(JRSortField sortField) throws JRException
	{
		if (sortFieldsMap.containsKey(sortField.getName()))
		{
			throw new JRException("Duplicate declaration of sort field : " + sortField.getName());
		}

		sortFieldsList.add(sortField);
		sortFieldsMap.put(sortField.getName(), sortField);
		
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_SORT_FIELDS, sortField, sortFieldsList.size() - 1);
	}

	
	/**
	 * Removes a sort field from the dataset.
	 * 
	 * @param fieldName the field name
	 * @return the removed sort field, or <code>null</code> if the sort field was not found
	 */
	public JRSortField removeSortField(String fieldName)
	{
		return removeSortField((JRSortField) sortFieldsMap.get(fieldName));
	}

	
	/**
	 * Removes a sort field from the dataset.
	 * 
	 * @param sortField the sort field to be removed
	 * @return the sort field to be removed
	 */
	public JRSortField removeSortField(JRSortField sortField)
	{
		if (sortField != null)
		{
			int idx = sortFieldsList.indexOf(sortField);
			if (idx >= 0)
			{
				sortFieldsList.remove(idx);
				sortFieldsMap.remove(sortField.getName());
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_SORT_FIELDS, sortField, idx);
			}
		}

		return sortField;
	}

	
	public JRVariable[] getVariables()
	{
		JRVariable[] variablesArray = new JRVariable[variablesList.size()];

		variablesList.toArray(variablesArray);

		return variablesArray;
	}

	
	/**
	 * Returns the list of variables, including build-in ones.
	 * 
	 * @return list of {@link JRVariable JRVariable} objects
	 */
	
	public List getVariablesList()
	{
		return variablesList;
	}

	
	/**
	 * Returns the map of variable, including build-in ones, indexed by name.
	 * 
	 * @return {@link JRVariable JRVariable} objects indexed by name
	 */
	public Map getVariablesMap()
	{
		return variablesMap;
	}

	
	/**
	 * Adds a variable to the dataset.
	 * @param variable the variable to add
	 * @throws JRException
	 * @see net.sf.jasperreports.engine.JRDataset#getVariables()
	 */
	public void addVariable(JRDesignVariable variable) throws JRException
	{
		addVariable(variable, false);
	}
	
	
	/**
	 * Adds a variable to the dataset.
	 * 
	 * @param variable the variable to add
	 * @param system whether the variable should be added before user defined variables
	 * or at the end of the variables list
	 * @throws JRException
	 */
	protected void addVariable(JRDesignVariable variable, boolean system) throws JRException
	{
		if (variablesMap.containsKey(variable.getName()))
		{
			throw new JRException("Duplicate declaration of variable : " + variable.getName());
		}

		int addedIdx;
		if (system)
		{
			// add the variable vefore the first non-system variable
			ListIterator it = variablesList.listIterator();
			while (it.hasNext())
			{
				JRVariable var = (JRVariable) it.next();
				if (!var.isSystemDefined())
				{
					it.previous();
					break;
				}
			}
			it.add(variable);
			addedIdx = it.previousIndex();
		}
		else
		{
			variablesList.add(variable);
			addedIdx = variablesList.size() - 1;
		}
		
		variablesMap.put(variable.getName(), variable);
		
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_VARIABLES, variable, addedIdx);
	}

	
	/**
	 * Removes a variable from the dataset.
	 * 
	 * @param variableName the variable name
	 * @return the removed variable, or <code>null</code> if the variable was not found
	 */
	public JRVariable removeVariable(String variableName)
	{
		return removeVariable((JRVariable) variablesMap.get(variableName));
	}

	
	/**
	 * Removes a variable from the dataset.
	 * 
	 * @param variable the variable to be removed
	 * @return the variable to be removed
	 */
	public JRVariable removeVariable(JRVariable variable)
	{
		if (variable != null)
		{
			int idx = variablesList.indexOf(variable);
			if (idx >= 0)
			{
				variablesList.remove(idx);
				variablesMap.remove(variable.getName());
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_VARIABLES, variable, idx);
			}
		}

		return variable;
	}

	
	public JRGroup[] getGroups()
	{
		JRGroup[] groupsArray = new JRGroup[groupsList.size()];

		groupsList.toArray(groupsArray);

		return groupsArray;
	}

	
	/**
	 * Returns the list of groups.
	 * 
	 * @return list of {@link JRGroup JRGroup} objects
	 */
	public List getGroupsList()
	{
		return groupsList;
	}

	
	/**
	 * Returns the map of groups indexed by name.
	 * 
	 * @return {@link JRGroup JRGroup} objects indexed by name
	 */
	public Map getGroupsMap()
	{
		return groupsMap;
	}

	
	/**
	 * Adds a group to the dataset.
	 * @param group the group to add
	 * @throws JRException
	 * @see net.sf.jasperreports.engine.JRDataset#getGroups()
	 */
	public void addGroup(JRDesignGroup group) throws JRException
	{
		if (groupsMap.containsKey(group.getName()))
		{
			throw new JRException("Duplicate declaration of group : " + group.getName());
		}

		JRDesignVariable countVariable = new JRDesignVariable();
		countVariable.setName(group.getName() + "_COUNT");
		countVariable.setValueClass(Integer.class);
		countVariable.setResetType(JRVariable.RESET_TYPE_GROUP);
		countVariable.setResetGroup(group);
		countVariable.setCalculation(JRVariable.CALCULATION_COUNT);
		countVariable.setSystemDefined(true);
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		expression.setText("new Integer(1)");
		countVariable.setExpression(expression);
		expression = new JRDesignExpression();
		expression.setValueClass(Integer.class);
		expression.setText("new Integer(0)");
		countVariable.setInitialValueExpression(expression);

		addVariable(countVariable, true);

		group.setCountVariable(countVariable);

		groupsList.add(group);
		groupsMap.put(group.getName(), group);
		
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_GROUPS, group, groupsList.size() - 1);
	}

	
	
	/**
	 * Removes a group from the dataset.
	 * 
	 * @param groupName the group name
	 * @return the removed group, or <code>null</code> if the group was not found
	 */
	public JRGroup removeGroup(String groupName)
	{
		return removeGroup((JRGroup) groupsMap.get(groupName));
	}

	
	/**
	 * Removes a group from the dataset.
	 * 
	 * @param group the group to be removed
	 * @return the group to be removed
	 */
	public JRGroup removeGroup(JRGroup group)
	{
		if (group != null)
		{
			removeVariable(group.getCountVariable());
			int idx = groupsList.indexOf(group);
			if (idx >= 0)
			{
				groupsList.remove(idx);
				groupsMap.remove(group.getName());
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_GROUPS, group, idx);
			}
		}

		return group;
	}
	
	
	/**
	 * Sets the base name of resource bundle to be used by the dataset.
	 * 
	 * @param resourceBundle the resource bundle base name
	 */
	public void setResourceBundle(String resourceBundle)
	{
		Object old = this.resourceBundle;
		this.resourceBundle = resourceBundle;
		getEventSupport().firePropertyChange(PROPERTY_RESOURCE_BUNDLE, old, this.resourceBundle);
	}
	
	
	protected void queryLanguageChanged(String oldLanguage, String newLanguage)
	{
		try
		{
			if (oldLanguage != null)
			{
				JRQueryExecuterFactory queryExecuterFactory = JRQueryExecuterUtils.getQueryExecuterFactory(oldLanguage);
				Object[] builtinParameters = queryExecuterFactory.getBuiltinParameters();
				if (builtinParameters != null)
				{
					removeBuiltinParameters(builtinParameters);
				}
			}

			if (newLanguage != null)
			{
				JRQueryExecuterFactory queryExecuterFactory = JRQueryExecuterUtils.getQueryExecuterFactory(newLanguage);
				Object[] builtinParameters = queryExecuterFactory.getBuiltinParameters();
				if (builtinParameters != null)
				{
					addBuiltinParameters(builtinParameters);
					sortSystemParamsFirst();
				}
			}
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	
	private void sortSystemParamsFirst()
	{
		Collections.sort(parametersList, new Comparator()
				{
					public int compare(Object o1, Object o2)
					{
						JRParameter p1 = (JRParameter) o1;
						JRParameter p2 = (JRParameter) o2;
						boolean s1 = p1.isSystemDefined();
						boolean s2 = p2.isSystemDefined();
						
						return s1 ? (s2 ? 0 : -1) : (s2 ? 1 : 0);
					}
				});
	}

	private void removeBuiltinParameters(Object[] builtinParameters)
	{
		for (int i = 0; i < builtinParameters.length; i += 2)
		{
			String parameterName = (String) builtinParameters[i];
			JRParameter parameter = (JRParameter) parametersMap.get(parameterName);
			if (parameter.isSystemDefined())
			{
				removeParameter(parameter);
			}
		}
	}
	
	
	/**
	 * Adds/sets a property value.
	 * 
	 * @param propName the name of the property
	 * @param value the value of the property
	 */
	public void setProperty(String propName, String value)
	{
		//TODO event
		getPropertiesMap().setProperty(propName, value);
	}
	
	
	/**
	 * Sets the dataset filter expression.
	 * <p>
	 * The expression value class should be <code>java.lang.Boolean</code>.
	 * </p>
	 * 
	 * @param expression the boolean expression to use as filter expression
	 * @see JRDataset#getFilterExpression()
	 */
	public void setFilterExpression(JRExpression expression)
	{
		Object old = this.filterExpression;
		this.filterExpression = expression;
		getEventSupport().firePropertyChange(PROPERTY_FILTER_EXPRESSION, old, this.filterExpression);
	}


	/**
	 * 
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		
		if (sortFieldsMap == null)
			sortFieldsMap = new HashMap();
			
		if (sortFieldsList == null)
			sortFieldsList = new ArrayList();
	}

	/**
	 * 
	 */
	public Object clone() 
	{
		JRDesignDataset clone = (JRDesignDataset)super.clone();
		
		if (parametersList != null)
		{
			clone.parametersList = new ArrayList(parametersList.size());
			clone.parametersMap = new HashMap(parametersList.size());
			for(int i = 0; i < parametersList.size(); i++)
			{
				JRParameter parameter = 
					(JRParameter)((JRParameter)parametersList.get(i)).clone();
				clone.parametersList.add(parameter);
				clone.parametersMap.put(parameter.getName(), parameter);
			}
		}
		
		if (fieldsList != null)
		{
			clone.fieldsList = new ArrayList(fieldsList.size());
			clone.fieldsMap = new HashMap(fieldsList.size());
			for(int i = 0; i < fieldsList.size(); i++)
			{
				JRField field = 
					(JRField)((JRField)fieldsList.get(i)).clone();
				clone.fieldsList.add(field);
				clone.fieldsMap.put(field.getName(), field);
			}
		}
		
		if (sortFieldsList != null)
		{
			clone.sortFieldsList = new ArrayList(sortFieldsList.size());
			clone.sortFieldsMap = new HashMap(sortFieldsList.size());
			for(int i = 0; i < sortFieldsList.size(); i++)
			{
				JRSortField sortField = 
					(JRSortField)((JRSortField)sortFieldsList.get(i)).clone();
				clone.sortFieldsList.add(sortField);
				clone.sortFieldsMap.put(sortField.getName(), sortField);
			}
		}
		
		if (variablesList != null)
		{
			clone.variablesList = new ArrayList(variablesList.size());
			clone.variablesMap = new HashMap(variablesList.size());
			for(int i = 0; i < variablesList.size(); i++)
			{
				JRVariable variable = 
					(JRVariable)((JRVariable)variablesList.get(i)).clone();
				clone.variablesList.add(variable);
				clone.variablesMap.put(variable.getName(), variable);
			}
		}
		
		if (groupsList != null)
		{
			clone.groupsList = new ArrayList(groupsList.size());
			clone.groupsMap = new HashMap(groupsList.size());
			for(int i = 0; i < groupsList.size(); i++)
			{
				JRGroup group = 
					(JRGroup)((JRGroup)groupsList.get(i)).clone();
				clone.groupsList.add(group);
				clone.groupsMap.put(group.getName(), group);
			}
		}
		
		return clone;
	}
	
}
