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
 package net.sf.jasperreports.engine.data;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;

import net.sf.jasperreports.engine.JRDataSourceProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignField;


/**
 * The base implementation for JRBeanXXXDataSource providers. It provides a common
 * implementation for bean properties introspection.
 * 
 * @author Peter Severin (peter_s@sourceforge.net, contact@jasperassistant.com)
 * @version $Id: JRAbstractBeanDataSourceProvider.java 1229 2006-04-19 10:27:35Z teodord $
 */
public abstract class JRAbstractBeanDataSourceProvider implements JRDataSourceProvider 
{

	/** The introspected bean class */
	private Class beanClass;

	/**
	 * Creates the provider. Superclasses must pass a valid class that will be
	 * used to introspect the available bean fields.
	 * @param beanClass the bean class to be introspected.
	 */
	public JRAbstractBeanDataSourceProvider(Class beanClass) {
		if (beanClass == null)
			throw new NullPointerException("beanClass must not be null");

		this.beanClass = beanClass;
	}

	/**
	 * @see net.sf.jasperreports.engine.JRDataSourceProvider#supportsGetFieldsOperation()
	 */
	public boolean supportsGetFieldsOperation() {
		return true;
	}
	
	/**
	 * @see net.sf.jasperreports.engine.JRDataSourceProvider#getFields(net.sf.jasperreports.engine.JasperReport)
	 */
	public JRField[] getFields(JasperReport report) throws JRException {
		BeanInfo beanInfo = null;

		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			throw new JRException(e);
		}

		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		if(descriptors != null) {
			ArrayList fields = new ArrayList(descriptors.length);
			
			for (int i = 0; i < descriptors.length; i++) {
				PropertyDescriptor descriptor = descriptors[i];
				
				if (!(descriptor instanceof IndexedPropertyDescriptor) && descriptor.getReadMethod() != null) {
					JRDesignField field = new JRDesignField();
					field.setValueClassName(normalizeClass(descriptor.getPropertyType()).getName());
					field.setName(descriptor.getName());
					
					fields.add(field);
				}
			}
	
			return (JRField[]) fields.toArray(new JRField[fields.size()]);
		}

		return new JRField[0];
	}

	/**
	 * Converts a primitive class to its object counterpart
	 */
	private static Class normalizeClass(Class clazz) {
		if(clazz.isPrimitive()) {
			if(clazz == boolean.class)
				return Boolean.class;
			if(clazz == byte.class)
				return Byte.class;
			if(clazz == char.class)
				return Character.class;
			if(clazz == short.class)
				return Short.class;
			if(clazz == int.class)
				return Integer.class;
			if(clazz == long.class)
				return Long.class;
			if(clazz == float.class)
				return Float.class;
			if(clazz == double.class)
				return Double.class;
		}
		
		return clazz;
	}
}
