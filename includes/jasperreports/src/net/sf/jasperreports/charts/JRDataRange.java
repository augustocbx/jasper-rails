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
package net.sf.jasperreports.charts;

import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRExpression;


/**
 * Represents a numeric range.
 *
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 * @version $Id: JRDataRange.java 1951 2007-11-12 18:02:56Z teodord $
 */
public interface JRDataRange extends JRCloneable
{
	/**
	 * Returns the expression that generates the lower bound of the range.
	 *
	 * @return the expression that generates the lower bound of the range
	 */
	public JRExpression getLowExpression();

	/**
	 * Returns the expression that generates the upper bound of the range.
	 *
	 * @return the expression that generates the upper bound of the range
	 */
	public JRExpression getHighExpression();
}
