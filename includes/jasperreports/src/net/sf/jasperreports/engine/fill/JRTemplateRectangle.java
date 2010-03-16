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
package net.sf.jasperreports.engine.fill;

import net.sf.jasperreports.engine.JRCommonRectangle;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.base.JRBasePen;
import net.sf.jasperreports.engine.util.JRStyleResolver;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRTemplateRectangle.java 2006 2007-12-05 14:28:33Z teodord $
 */
public class JRTemplateRectangle extends JRTemplateGraphicElement implements JRCommonRectangle
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 *
	 */
	private Integer radius = null;


	/**
	 *
	 */
	protected JRTemplateRectangle(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider, JRRectangle rectangle)
	{
		super(origin, defaultStyleProvider);

		setRectangle(rectangle);
	}


	/**
	 *
	 */
	protected JRTemplateRectangle(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider, JRSubreport subreport)
	{
		super(origin, defaultStyleProvider);
		
		setSubreport(subreport);
	}


	/**
	 *
	 */
	protected void setRectangle(JRRectangle rectangle)
	{
		super.setGraphicElement(rectangle);

		setRadius(rectangle.getRadius());
	}


	/**
	 *
	 */
	protected void setSubreport(JRSubreport subreport)
	{
		super.setElement(subreport);

		// don't want to inherit mode because of different defaults for rectangles and subreports
		setMode(subreport.getMode());
		
		linePen = new JRBasePen(this);
		
		getLinePen().setLineWidth(0f);
		setFill(JRGraphicElement.FILL_SOLID);
	}


	/**
	 *
	 */
	public int getRadius()
	{
		return JRStyleResolver.getRadius(this);
	}

	/**
	 *
	 */
	public Integer getOwnRadius()
	{
		return radius;
	}

	/**
	 *
	 */
	protected void setRadius(int radius)
	{
		this.radius = new Integer(radius);
	}

	/**
	 *
	 */
	protected void setRadius(Integer radius)
	{
		this.radius = radius;
	}


}
