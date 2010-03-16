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

import java.util.Collection;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: HeadingsScriptlet.java 1908 2007-10-19 11:17:08Z teodord $
 */
public class HeadingsScriptlet extends JRDefaultScriptlet
{


	/**
	 *
	 */
	public Boolean addHeading(String groupName) throws JRScriptletException
	{
		Collection headings = (Collection)this.getVariableValue("HeadingsCollection");

		Integer type = null;
		String text = null;
		String reference = null;
		Integer pageIndex = (Integer)this.getVariableValue("PAGE_NUMBER");
		
		if ("FirstLetterGroup".equals(groupName))
		{
			type = new Integer(1);
			text = "Letter " + this.getVariableValue("FirstLetter");
			reference = "FirstLetterGroup_" + this.getVariableValue("FirstLetter");
		
			headings.add(new HeadingBean(type, text, reference, pageIndex));
		}
		else if ("ShipCountryGroup".equals(groupName))
		{
			type = new Integer(2);
			text = (String)this.getFieldValue("ShipCountry");
			reference = "ShipCountryGroup_" + this.getVariableValue("ShipCountryNumber");
		
			headings.add(new HeadingBean(type, text, reference, pageIndex));
		}
		
		return Boolean.FALSE;
	}


}
