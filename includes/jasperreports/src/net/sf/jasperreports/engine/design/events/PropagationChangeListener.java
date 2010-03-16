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
package net.sf.jasperreports.engine.design.events;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: PropagationChangeListener.java 1869 2007-09-21 15:45:08Z lucianc $
 */
public class PropagationChangeListener implements PropertyChangeListener
{

	private final JRPropertyChangeSupport propertyChangeSupport; 
	
	public PropagationChangeListener(JRPropertyChangeSupport propertyChangeSupport)
	{
		this.propertyChangeSupport = propertyChangeSupport;
	}

	public void propertyChange(PropertyChangeEvent evt)
	{
		propertyChangeSupport.firePropertyChange(evt);
	}

}
