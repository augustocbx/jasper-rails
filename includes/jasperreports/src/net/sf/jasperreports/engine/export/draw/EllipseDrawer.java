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

/*
 * Contributors:
 * Eugene D - eugenedruy@users.sourceforge.net 
 * Adrian Jackson - iapetus@users.sourceforge.net
 * David Taylor - exodussystems@users.sourceforge.net
 * Lars Kristensen - llk@users.sourceforge.net
 */
package net.sf.jasperreports.engine.export.draw;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintEllipse;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRGraphics2DExporter.java 1811 2007-08-13 19:52:07Z teodord $
 */
public class EllipseDrawer extends ElementDrawer
{

	/**
	 *
	 */
	public void draw(Graphics2D grx, JRPrintElement element, int offsetX, int offsetY)
	{
		JRPrintEllipse ellipse = (JRPrintEllipse)element;
		
		if (ellipse.getMode() == JRElement.MODE_OPAQUE)
		{
			grx.setColor(ellipse.getBackcolor());
			grx.fillOval(
				ellipse.getX() + offsetX, 
				ellipse.getY() + offsetY, 
				ellipse.getWidth(),
				ellipse.getHeight()
				);
		}

		grx.setColor(ellipse.getLinePen().getLineColor());

		Stroke stroke = getStroke(ellipse.getLinePen(), BasicStroke.CAP_SQUARE);

		if (stroke != null)
		{
			grx.setStroke(stroke);
			
			if (ellipse.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
			{
				float lineWidth = ellipse.getLinePen().getLineWidth().floatValue();
				
				AffineTransform oldTx = grx.getTransform();

				grx.translate(
					ellipse.getX() + offsetX - lineWidth / 3, 
					ellipse.getY() + offsetY - lineWidth / 3
					);
				grx.scale(
					(element.getWidth() + 2 * lineWidth / 3) 
						/ element.getWidth(), 
					(element.getHeight() + 2 * lineWidth / 3) 
						/ element.getHeight() 
					);
				grx.drawOval(
					0, 
					0, 
					ellipse.getWidth(),
					ellipse.getHeight()
					);
				
				grx.setTransform(oldTx);

				grx.translate(
					ellipse.getX() + offsetX + lineWidth / 3, 
					ellipse.getY() + offsetY + lineWidth / 3
					);
				grx.scale(
					(element.getWidth() - 2 * lineWidth / 3) 
						/ element.getWidth(), 
					(element.getHeight() - 2 * lineWidth / 3) 
						/ element.getHeight() 
					);
				grx.drawOval(
					0, 
					0, 
					ellipse.getWidth(),
					ellipse.getHeight()
					);

				grx.setTransform(oldTx);
			}
			else
			{
				grx.drawOval(
					ellipse.getX() + offsetX, 
					ellipse.getY() + offsetY, 
					ellipse.getWidth(),
					ellipse.getHeight()
					);
			}
		}
	}


}
