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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.base.JRBaseHyperlink;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;


/**
 * Stand-alone implementation of {@link JRHyperlink JRHyperlink}
 * which should be used for report design purposes. 
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRDesignHyperlink.java 1926 2007-10-25 16:40:53Z lucianc $
 */
public class JRDesignHyperlink extends JRBaseHyperlink implements JRChangeEventsSupport
{
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_HYPERLINK_ANCHOR_EXPRESSION = "hyperlinkAnchorExpression";
	
	public static final String PROPERTY_HYPERLINK_PAGE_EXPRESSION = "hyperlinkPageExpression";
	
	public static final String PROPERTY_HYPERLINK_REFERENCE_EXPRESSION = "hyperlinkReferenceExpression";
	
	public static final String PROPERTY_HYPERLINK_TARGET = "hyperlinkTarget";
	
	public static final String PROPERTY_HYPERLINK_TOOLTIP_EXPRESSION = "hyperlinkTooltipExpression";
	
	public static final String PROPERTY_LINK_TYPE = "linkType";
	
	public static final String PROPERTY_HYPERLINK_PARAMETERS = "hyperlinkParameters";
	
	private List hyperlinkParameters;
	
	public JRDesignHyperlink()
	{
		hyperlinkParameters = new ArrayList();
	}

	
	/**
	 * Sets the link type as a built-in hyperlink type.
	 * 
	 * @param hyperlinkType the built-in hyperlink type
	 * @see #getLinkType()
	 */
	public void setHyperlinkType(byte hyperlinkType)
	{
		setLinkType(JRHyperlinkHelper.getLinkType(hyperlinkType));
	}

	
	/**
	 * Sets the hyperlink target.
	 * 
	 * @param hyperlinkTarget the hyperlink target, one of
	 * <ul>
	 * <li>{@link JRHyperlink#HYPERLINK_TARGET_SELF JRHyperlink.HYPERLINK_TARGET_SELF}</li>
	 * <li>{@link JRHyperlink#HYPERLINK_TARGET_BLANK JRHyperlink.HYPERLINK_TARGET_BLANK}</li>
	 * </ul>
	 * @see #getHyperlinkTarget()
	 */
	public void setHyperlinkTarget(byte hyperlinkTarget)
	{
		byte old = this.hyperlinkTarget;
		this.hyperlinkTarget = hyperlinkTarget;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_TARGET, old, this.hyperlinkTarget);
	}


	/**
	 * Sets the expression that will generate the hyperlink reference URL
	 * or the referred document location.
	 * <p>
	 * This expression is used when the hyperlink type is
	 * {@link JRHyperlink#HYPERLINK_TYPE_REFERENCE JRHyperlink.HYPERLINK_TYPE_REFERENCE},
	 * {@link JRHyperlink#HYPERLINK_TYPE_REMOTE_ANCHOR JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR} or
	 * {@link JRHyperlink#HYPERLINK_TYPE_REMOTE_PAGE JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE}.
	 * The type of the expression should be <code>java.lang.String</code>
	 * </p>
	 * 
	 * @param hyperlinkReferenceExpression the reference expression
	 * @see #getHyperlinkReferenceExpression()
	 */
	public void setHyperlinkReferenceExpression(JRExpression hyperlinkReferenceExpression)
	{
		Object old = this.hyperlinkReferenceExpression;
		this.hyperlinkReferenceExpression = hyperlinkReferenceExpression;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_REFERENCE_EXPRESSION, old, this.hyperlinkReferenceExpression);
	}


	/**
	 * Sets the expression that will generate the referred anchor.
	 * <p>
	 * This expression is used when the hyperlink type is
	 * {@link JRHyperlink#HYPERLINK_TYPE_LOCAL_ANCHOR JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR} or
	 * {@link JRHyperlink#HYPERLINK_TYPE_REMOTE_ANCHOR JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR}.
	 * The type of the expression should be <code>java.lang.String</code>
	 * </p>
	 * 
	 * @param hyperlinkAnchorExpression the anchor expression
	 * @see #getHyperlinkAnchorExpression()
	 */
	public void setHyperlinkAnchorExpression(JRExpression hyperlinkAnchorExpression)
	{
		Object old = this.hyperlinkAnchorExpression;
		this.hyperlinkAnchorExpression = hyperlinkAnchorExpression;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_ANCHOR_EXPRESSION, old, this.hyperlinkAnchorExpression);
	}


	/**
	 * Sets the expression that will generate the referred page.
	 * <p>
	 * This expression is used when the hyperlink type is
	 * {@link JRHyperlink#HYPERLINK_TYPE_LOCAL_PAGE JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE} or
	 * {@link JRHyperlink#HYPERLINK_TYPE_REMOTE_PAGE JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE}.
	 * The type of the expression should be <code>java.lang.Integer</code>
	 * </p>
	 * 
	 * @param hyperlinkPageExpression the page expression
	 * @see #getHyperlinkPageExpression()
	 */
	public void setHyperlinkPageExpression(JRExpression hyperlinkPageExpression)
	{
		Object old = this.hyperlinkPageExpression;
		this.hyperlinkPageExpression = hyperlinkPageExpression;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_PAGE_EXPRESSION, old, this.hyperlinkPageExpression);
	}


	/**
	 * Sets the hyperlink type.
	 * <p>
	 * The type can be one of the built-in types
	 * (Reference, LocalAnchor, LocalPage, RemoteAnchor, RemotePage),
	 * or can be an arbitrary type.
	 * </p>
	 * @param type the hyperlink type
	 */
	public void setLinkType(String type)
	{
		Object old = this.linkType;
		this.linkType = type;
		getEventSupport().firePropertyChange(PROPERTY_LINK_TYPE, old, this.linkType);
	}


	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		JRHyperlinkParameter[] parameters;
		if (hyperlinkParameters.isEmpty())
		{
			parameters = null;
		}
		else
		{
			parameters = new JRHyperlinkParameter[hyperlinkParameters.size()];
			hyperlinkParameters.toArray(parameters);
		}
		return parameters;
	}
	
	
	/**
	 * Returns the list of custom hyperlink parameters.
	 * 
	 * @return the list of custom hyperlink parameters
	 */
	public List getHyperlinkParametersList()
	{
		return hyperlinkParameters;
	}
	
	
	/**
	 * Adds a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to add
	 */
	public void addHyperlinkParameter(JRHyperlinkParameter parameter)
	{
		hyperlinkParameters.add(parameter);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_HYPERLINK_PARAMETERS, 
				parameter, hyperlinkParameters.size() - 1);
	}
	

	/**
	 * Removes a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to remove
	 */
	public void removeHyperlinkParameter(JRHyperlinkParameter parameter)
	{
		int idx = hyperlinkParameters.indexOf(parameter);
		if (idx >= 0)
		{
			hyperlinkParameters.remove(idx);
			getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_HYPERLINK_PARAMETERS, 
					parameter, idx);
		}
	}
	
	
	/**
	 * Removes a custom hyperlink parameter.
	 * <p>
	 * If multiple parameters having the specified name exist, all of them
	 * will be removed
	 * </p>
	 * 
	 * @param parameterName the parameter name
	 */
	public void removeHyperlinkParameter(String parameterName)
	{
		for (ListIterator it = hyperlinkParameters.listIterator(); it.hasNext();)
		{
			JRHyperlinkParameter parameter = (JRHyperlinkParameter) it.next();
			if (parameter.getName() != null && parameter.getName().equals(parameterName))
			{
				it.remove();
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_HYPERLINK_PARAMETERS, 
						parameter, it.nextIndex());
			}
		}
	}

	
	/**
	 * Sets the expression which will be used to generate the hyperlink tooltip.
	 * The type of the expression should be <code>java.lang.String</code>.
	 * 
	 * @param hyperlinkTooltipExpression the expression which will be used to generate the hyperlink tooltip
	 * @see #getHyperlinkTooltipExpression()
	 */
	public void setHyperlinkTooltipExpression(JRExpression hyperlinkTooltipExpression)
	{
		Object old = this.hyperlinkTooltipExpression;
		this.hyperlinkTooltipExpression = hyperlinkTooltipExpression;
		getEventSupport().firePropertyChange(PROPERTY_HYPERLINK_TARGET, old, this.hyperlinkTooltipExpression);
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
