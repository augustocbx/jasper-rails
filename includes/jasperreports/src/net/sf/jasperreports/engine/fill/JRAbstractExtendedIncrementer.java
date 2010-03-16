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

import net.sf.jasperreports.engine.JRException;

/**
 * Base class for extended incrementers.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRAbstractExtendedIncrementer.java 1229 2006-04-19 10:27:35Z teodord $
 */
public abstract class JRAbstractExtendedIncrementer implements JRExtendedIncrementer
{

	/**
	 * This implementation simply calls {@link JRExtendedIncrementer#increment(JRCalculable, Object, AbstractValueProvider) increment(JRCalculable, Object, AbstractValueProvider)}.
	 */
	public Object increment(JRFillVariable variable, Object expressionValue, AbstractValueProvider valueProvider) throws JRException
	{
		return increment((JRCalculable) variable, expressionValue, valueProvider);
	}

	/**
	 * This implementation calls {@link JRExtendedIncrementer#increment(JRCalculable, Object, AbstractValueProvider) increment(calculable, calculableValue.getValue(), valueProvider)}.
	 */
	public Object combine(JRCalculable calculable, JRCalculable calculableValue, AbstractValueProvider valueProvider) throws JRException
	{
		return increment(calculable, calculableValue.getValue(), valueProvider);
	}

}
