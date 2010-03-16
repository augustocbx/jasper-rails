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
package net.sf.jasperreports.engine;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRPrintGraphicElement.java 2036 2007-12-12 15:31:16Z teodord $
 */
public interface JRPrintGraphicElement extends JRPrintElement, JRCommonGraphicElement
{

	/**
	 * Indicates the pen type used for this element.
	 * @return one of the pen constants in this class
	 * @deprecated Replaced by {@link #getLinePen()}
	 */
	public byte getPen();

	/**
	 * @deprecated Replaced by {@link #getLinePen()}
	 */
	public Byte getOwnPen();

	/**
	 * Sets the pen type that will used for this element.
	 * @param pen one of the pen constants in this class
	 * @deprecated Replaced by {@link #getLinePen()}
	 */
	public void setPen(byte pen);

	/**
	 * @deprecated Replaced by {@link #getLinePen()}
	 */
	public void setPen(Byte pen);
		
	/**
	 * Sets the fill type used for this element.
	 * @param fill one of the pen constants in this class
	 */
	public void setFill(byte fill);
	
	public void setFill(Byte fill);
		
	
}
