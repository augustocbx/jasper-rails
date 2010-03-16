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
package net.sf.jasperreports.engine.xml;

import java.util.Map;

import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JRDesignStyle;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRPrintStyleFactory.java 1759 2007-06-20 16:47:34Z lucianc $
 */
public class JRPrintStyleFactory extends JRAbstractStyleFactory
{

	protected void setParentStyle(JRDesignStyle currentStyle, String parentStyleName)
	{
		JRPrintXmlLoader printXmlLoader = (JRPrintXmlLoader) digester.peek(digester.getCount() - 1);
		JasperPrint jasperPrint = (JasperPrint) digester.peek(digester.getCount() - 2);
		Map stylesMap = jasperPrint.getStylesMap();

		if (!stylesMap.containsKey(parentStyleName))
		{
			printXmlLoader.addError(new Exception("Unknown report style : " + parentStyleName));
		}
		
		JRStyle parent = (JRStyle) stylesMap.get(parentStyleName);
		currentStyle.setParentStyle(parent);
	}

}
