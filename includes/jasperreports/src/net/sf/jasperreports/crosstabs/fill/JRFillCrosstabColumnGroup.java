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
package net.sf.jasperreports.crosstabs.fill;

import net.sf.jasperreports.crosstabs.JRCrosstabColumnGroup;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

/**
 * Crosstab column group implementation used at fill time.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRFillCrosstabColumnGroup.java 1229 2006-04-19 10:27:35Z teodord $
 */
public class JRFillCrosstabColumnGroup extends JRFillCrosstabGroup implements JRCrosstabColumnGroup
{
	
	public JRFillCrosstabColumnGroup(JRCrosstabColumnGroup group, JRFillObjectFactory factory)
	{
		super(group, factory);
	}


	public byte getPosition()
	{
		return ((JRCrosstabColumnGroup) parentGroup).getPosition();
	}


	public int getHeight()
	{
		return ((JRCrosstabColumnGroup) parentGroup).getHeight();
	}

}
