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

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRQueryChunk;
import net.sf.jasperreports.engine.base.JRBaseQueryChunk;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRDesignQueryChunk.java 1919 2007-10-23 14:22:19Z lucianc $
 */
public class JRDesignQueryChunk extends JRBaseQueryChunk implements JRChangeEventsSupport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_TEXT = "text";
	
	public static final String PROPERTY_TOKENS = "tokens";
	
	public static final String PROPERTY_TYPE = "type";

	/**
	 *
	 */
	public void setType(byte type)
	{
		byte old = this.type;
		this.type = type;
		getEventSupport().firePropertyChange(PROPERTY_TYPE, old, this.type);
	}
		
	/**
	 *
	 */
	public void setText(String text)
	{
		Object old = this.text;
		this.text = text;
		getEventSupport().firePropertyChange(PROPERTY_TEXT, old, this.text);
	}
	
	
	/**
	 * Sets the clause tokens for chunks of type {@link JRQueryChunk#TYPE_CLAUSE_TOKENS TYPE_CLAUSE_TOKENS}.
	 * 
	 * @param tokens the clause tokens
	 * @see #getTokens()
	 */
	public void setTokens(String[] tokens)
	{
		Object old = this.tokens;
		this.tokens = tokens;
		getEventSupport().firePropertyChange(PROPERTY_TOKENS, old, this.tokens);
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
