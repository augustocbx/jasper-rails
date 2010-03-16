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


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: CustomBean.java 1229 2006-04-19 10:27:35Z teodord $
 */
public class CustomBean
{


	/**
	 *
	 */
	private String city = null;
	private Integer id = null;
	private String name = null;
	private String street = null;


	/**
	 *
	 */
	public CustomBean(
		String pcity,
		Integer pid,
		String pname,
		String pstreet
		)
	{
		city = pcity;
		id = pid;
		name = pname;
		street = pstreet;
	}


	/**
	 *
	 */
	public CustomBean getMe()
	{
		return this;
	}


	/**
	 *
	 */
	public String getCity()
	{
		return city;
	}


	/**
	 *
	 */
	public Integer getId()
	{
		return id;
	}


	/**
	 *
	 */
	public String getName()
	{
		return name;
	}


	/**
	 *
	 */
	public String getStreet()
	{
		return street;
	}


}
