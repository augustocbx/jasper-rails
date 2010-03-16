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
package net.sf.jasperreports.engine.base;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseGroup.java 2000 2007-12-05 14:07:13Z teodord $
 */
public class JRBaseGroup implements JRGroup, Serializable, JRChangeEventsSupport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_MIN_HEIGHT_TO_START_NEW_PAGE = "minHeightToStartNewPage";
	
	public static final String PROPERTY_RESET_PAGE_NUMBER = "resetPageNumber";
	
	public static final String PROPERTY_REPRINT_HEADER_ON_EACH_PAGE = "reprintHeaderOnEachPage";
	
	public static final String PROPERTY_START_NEW_COLUMN = "startNewColumn";
	
	public static final String PROPERTY_START_NEW_PAGE = "startNewPage";

	/**
	 *
	 */
	protected String name = null;
	protected boolean isStartNewColumn = false;
	protected boolean isStartNewPage = false;
	protected boolean isResetPageNumber = false;
	protected boolean isReprintHeaderOnEachPage = false;
	protected int minHeightToStartNewPage = 0;

	/**
	 *
	 */
	protected JRExpression expression = null;
	protected JRBand groupHeader = null;
	protected JRBand groupFooter = null;
	protected JRVariable countVariable = null;
	

	/**
	 *
	 */
	protected JRBaseGroup()
	{
	}
		

	/**
	 *
	 */
	protected JRBaseGroup(JRGroup group, JRBaseObjectFactory factory)
	{
		factory.put(group, this);
		
		name = group.getName();
		isStartNewColumn = group.isStartNewColumn();
		isStartNewPage = group.isStartNewPage();
		isResetPageNumber = group.isResetPageNumber();
		isReprintHeaderOnEachPage = group.isReprintHeaderOnEachPage();
		minHeightToStartNewPage = group.getMinHeightToStartNewPage();
		
		expression = factory.getExpression(group.getExpression());

		groupHeader = factory.getBand(group.getGroupHeader());
		groupFooter = factory.getBand(group.getGroupFooter());
		countVariable = factory.getVariable(group.getCountVariable());
	}
		

	/**
	 *
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 *
	 */
	public boolean isStartNewColumn()
	{
		return this.isStartNewColumn;
	}
		
	/**
	 *
	 */
	public void setStartNewColumn(boolean isStart)
	{
		boolean old = this.isStartNewColumn;
		this.isStartNewColumn = isStart;
		getEventSupport().firePropertyChange(PROPERTY_START_NEW_COLUMN, old, this.isStartNewColumn);
	}
		
	/**
	 *
	 */
	public boolean isStartNewPage()
	{
		return this.isStartNewPage;
	}
		
	/**
	 *
	 */
	public void setStartNewPage(boolean isStart)
	{
		boolean old = this.isStartNewPage;
		this.isStartNewPage = isStart;
		getEventSupport().firePropertyChange(PROPERTY_START_NEW_PAGE, old, this.isStartNewPage);
	}
		
	/**
	 *
	 */
	public boolean isResetPageNumber()
	{
		return this.isResetPageNumber;
	}
		
	/**
	 *
	 */
	public void setResetPageNumber(boolean isReset)
	{
		boolean old = this.isResetPageNumber;
		this.isResetPageNumber = isReset;
		getEventSupport().firePropertyChange(PROPERTY_RESET_PAGE_NUMBER, old, this.isResetPageNumber);
	}
		
	/**
	 *
	 */
	public boolean isReprintHeaderOnEachPage()
	{
		return this.isReprintHeaderOnEachPage;
	}
		
	/**
	 *
	 */
	public void setReprintHeaderOnEachPage(boolean isReprint)
	{
		boolean old = this.isReprintHeaderOnEachPage;
		this.isReprintHeaderOnEachPage = isReprint;
		getEventSupport().firePropertyChange(PROPERTY_REPRINT_HEADER_ON_EACH_PAGE, old, this.isReprintHeaderOnEachPage);
	}
		
	/**
	 *
	 */
	public int getMinHeightToStartNewPage()
	{
		return this.minHeightToStartNewPage;
	}

	/**
	 *
	 */
	public void setMinHeightToStartNewPage(int minHeight)
	{
		int old = this.minHeightToStartNewPage;
		this.minHeightToStartNewPage = minHeight;
		getEventSupport().firePropertyChange(PROPERTY_MIN_HEIGHT_TO_START_NEW_PAGE, old, this.minHeightToStartNewPage);
	}
		
	/**
	 *
	 */
	public JRExpression getExpression()
	{
		return this.expression;
	}
	
	/**
	 *
	 */
	public JRBand getGroupHeader()
	{
		return this.groupHeader;
	}
		
	/**
	 *
	 */
	public JRBand getGroupFooter()
	{
		return this.groupFooter;
	}
		
	/**
	 *
	 */
	public JRVariable getCountVariable()
	{
		return this.countVariable;
	}

	
	/**
	 *
	 */
	public Object clone() 
	{
		JRBaseGroup clone = null;

		try
		{
			clone = (JRBaseGroup)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}
		
		if (expression != null)
		{
			clone.expression = (JRExpression)expression.clone();
		}
		if (groupHeader != null)
		{
			clone.groupHeader = (JRBand)groupHeader.clone();
		}
		if (groupFooter != null)
		{
			clone.groupFooter = (JRBand)groupFooter.clone();
		}
		if (countVariable != null)
		{
			clone.countVariable = (JRVariable)countVariable.clone();
		}
		
		return clone;
	}

	
	private transient JRPropertyChangeSupport eventSupport;
	
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}

}
