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

import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintLine;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRGraphics2DExporter.java 1811 2007-08-13 19:52:07Z teodord $
 */
public class LineDrawer extends ElementDrawer
{

	/**
	 *
	 */
	public void draw(Graphics2D grx, JRPrintElement element, int offsetX, int offsetY)
	{
		JRPrintLine line = (JRPrintLine)element;
		
		grx.setColor(line.getLinePen().getLineColor());
		
		Stroke stroke = getStroke(line.getLinePen(), BasicStroke.CAP_BUTT);

		if (stroke != null)
		{
			grx.setStroke(stroke);
			
			float lineWidth = line.getLinePen().getLineWidth().floatValue();
			
			if (line.getWidth() == 1)
			{
				if (line.getHeight() == 1)
				{
					//Nothing to draw
				}
				else
				{
					//Vertical line
					if (line.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
					{
						grx.translate(0.5 - lineWidth / 3, 0);
						grx.drawLine(
							line.getX() + offsetX, 
							line.getY() + offsetY,
							line.getX() + offsetX,  
							line.getY() + offsetY + line.getHeight()
							);
						grx.translate(2 * lineWidth / 3, 0);
						grx.drawLine(
							line.getX() + offsetX, 
							line.getY() + offsetY,
							line.getX() + offsetX,  
							line.getY() + offsetY + line.getHeight()
							);
						grx.translate(-0.5 - lineWidth / 3, 0);
					}
					else
					{
						grx.translate(0.5, 0);
						grx.drawLine(
							line.getX() + offsetX, 
							line.getY() + offsetY,
							line.getX() + offsetX,  
							line.getY() + offsetY + line.getHeight()
							);
						grx.translate(-0.5, 0);
					}
				}
			}
			else
			{
				if (line.getHeight() == 1)
				{
					//Horizontal line
					if (line.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
					{
						grx.translate(0, 0.5 - lineWidth / 3);
						grx.drawLine(
							line.getX() + offsetX, 
							line.getY() + offsetY,
							line.getX() + offsetX + line.getWidth(),  
							line.getY() + offsetY
							);
						grx.translate(0, 2 * lineWidth / 3);
						grx.drawLine(
							line.getX() + offsetX, 
							line.getY() + offsetY,
							line.getX() + offsetX + line.getWidth(),  
							line.getY() + offsetY
							);
						grx.translate(0, -0.5 - lineWidth / 3);
					}
					else
					{
						grx.translate(0, 0.5);
						grx.drawLine(
							line.getX() + offsetX, 
							line.getY() + offsetY,
							line.getX() + offsetX + line.getWidth(),  
							line.getY() + offsetY
							);
						grx.translate(0, -0.5);
					}
				}
				else
				{
					//Oblique line
					if (line.getDirection() == JRLine.DIRECTION_TOP_DOWN)
					{
						if (line.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
						{
							double xtrans = lineWidth / (3 * Math.sqrt(1 + Math.pow(line.getWidth(), 2) / Math.pow(line.getHeight(), 2))); 
							double ytrans = lineWidth / (3 * Math.sqrt(1 + Math.pow(line.getHeight(), 2) / Math.pow(line.getWidth(), 2))); 
							grx.translate(xtrans, -ytrans);
							grx.drawLine(
								line.getX() + offsetX, 
								line.getY() + offsetY,
								line.getX() + offsetX + line.getWidth(),  
								line.getY() + offsetY + line.getHeight()
								);
							grx.translate(-2 * xtrans, 2 * ytrans);
							grx.drawLine(
								line.getX() + offsetX, 
								line.getY() + offsetY,
								line.getX() + offsetX + line.getWidth(),  
								line.getY() + offsetY + line.getHeight()
								);
							grx.translate(xtrans, -ytrans);
						}
						else
						{
							grx.drawLine(
								line.getX() + offsetX, 
								line.getY() + offsetY,
								line.getX() + offsetX + line.getWidth(),  
								line.getY() + offsetY + line.getHeight()
								);
						}
					}
					else
					{
						if (line.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
						{
							double xtrans = lineWidth / (3 * Math.sqrt(1 + Math.pow(line.getWidth(), 2) / Math.pow(line.getHeight(), 2))); 
							double ytrans = lineWidth / (3 * Math.sqrt(1 + Math.pow(line.getHeight(), 2) / Math.pow(line.getWidth(), 2))); 
							grx.translate(-xtrans, -ytrans);
							grx.drawLine(
								line.getX() + offsetX, 
								line.getY() + offsetY + line.getHeight(),
								line.getX() + offsetX + line.getWidth(),  
								line.getY() + offsetY
								);
							grx.translate(2 * xtrans, 2 * ytrans);
							grx.drawLine(
								line.getX() + offsetX, 
								line.getY() + offsetY + line.getHeight(),
								line.getX() + offsetX + line.getWidth(),  
								line.getY() + offsetY
								);
							grx.translate(-xtrans, -ytrans);
						}
						else
						{
							grx.drawLine(
								line.getX() + offsetX, 
								line.getY() + offsetY + line.getHeight(),
								line.getX() + offsetX + line.getWidth(),  
								line.getY() + offsetY
								);
						}
					}
				}
			}
		}
	}

}
