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

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: Scriptlet.java 1908 2007-10-19 11:17:08Z teodord $
 */
public class Scriptlet extends JRDefaultScriptlet
{


	/**
	 *
	 */
	public void beforeReportInit() throws JRScriptletException
	{
		System.out.println("call beforeReportInit");
	}


	/**
	 *
	 */
	public void afterReportInit() throws JRScriptletException
	{
		System.out.println("call afterReportInit");
	}


	/**
	 *
	 */
	public void beforePageInit() throws JRScriptletException
	{
		System.out.println("call   beforePageInit : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
	}


	/**
	 *
	 */
	public void afterPageInit() throws JRScriptletException
	{
		System.out.println("call   afterPageInit  : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
	}


	/**
	 *
	 */
	public void beforeColumnInit() throws JRScriptletException
	{
		System.out.println("call     beforeColumnInit");
	}


	/**
	 *
	 */
	public void afterColumnInit() throws JRScriptletException
	{
		System.out.println("call     afterColumnInit");
	}


	/**
	 *
	 */
	public void beforeGroupInit(String groupName) throws JRScriptletException
	{
		if (groupName.equals("CityGroup"))
		{
			System.out.println("call       beforeGroupInit : City = " + this.getFieldValue("City"));
		}
	}


	/**
	 *
	 */
	public void afterGroupInit(String groupName) throws JRScriptletException
	{
		if (groupName.equals("CityGroup"))
		{
			System.out.println("call       afterGroupInit  : City = " + this.getFieldValue("City"));
		
			String allCities = (String)this.getVariableValue("AllCities");
			String city = (String)this.getFieldValue("City");
			StringBuffer sbuffer = new StringBuffer();
		
			if (allCities != null)
			{
				sbuffer.append(allCities);
				sbuffer.append(", ");
			}
		
			sbuffer.append(city);
			this.setVariableValue("AllCities", sbuffer.toString());
		}
	}


	/**
	 *
	 */
	public void beforeDetailEval() throws JRScriptletException
	{
		System.out.println("        detail");
	}


	/**
	 *
	 */
	public void afterDetailEval() throws JRScriptletException
	{
	}


	/**
	 *
	 */
	public String hello() throws JRScriptletException
	{
		return "Hello! I'm the report's scriptlet object.";
	}


}
