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
package net.sf.jasperreports.engine.convert;

import java.util.List;

import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRBoxContainer;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JREllipse;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintGraphicElement;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVisitable;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.base.JRBasePrintFrame;
import net.sf.jasperreports.engine.base.JRBasePrintRectangle;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRGraphics2DExporter.java 1811 2007-08-13 19:52:07Z teodord $
 */
public class ConvertVisitor implements JRVisitor
{
	
	private ReportConverter reportConverter = null;
	private JRBasePrintFrame parentFrame = null;
	private JRPrintElement printElement = null;
	
	/**
	 *
	 */
	public ConvertVisitor(ReportConverter reportConverter)
	{
		this(reportConverter, null);
	}

	/**
	 *
	 */
	public ConvertVisitor(ReportConverter reportConverter, JRBasePrintFrame parentFrame)
	{
		this.reportConverter = reportConverter;
		this.parentFrame = parentFrame;
	}

	/**
	 *
	 */
	public JRPrintElement getVisitPrintElement(JRVisitable visitable)
	{
		if (visitable != null)
		{
			visitable.visit(this);
			return printElement;
		}
		return null;
	}

	/**
	 *
	 */
	public void visitBreak(JRBreak breakElement)
	{
		//FIXMECONVERT
	}

	/**
	 *
	 */
	public void visitChart(JRChart chart)
	{
		JRPrintElement printImage = ChartConverter.getInstance().convert(reportConverter, chart);
		addElement(parentFrame, printImage);
		addContour(reportConverter, parentFrame, printImage);
	}

	/**
	 *
	 */
	public void visitCrosstab(JRCrosstab crosstab)
	{
		JRPrintElement printFrame = CrosstabConverter.getInstance().convert(reportConverter, crosstab); 
		addElement(parentFrame, printFrame);
		addContour(reportConverter, parentFrame, printFrame);
	}

	/**
	 *
	 */
	public void visitElementGroup(JRElementGroup elementGroup)
	{
		List children = elementGroup.getChildren();
		if (children != null && children.size() > 0)
		{
			for(int i = 0; i < children.size(); i++)
			{
				((JRChild)children.get(i)).visit(this);
			}
		}
	}

	/**
	 *
	 */
	public void visitEllipse(JREllipse ellipse)
	{
		addElement(parentFrame, EllipseConverter.getInstance().convert(reportConverter, ellipse));
	}

	/**
	 *
	 */
	public void visitFrame(JRFrame frame)
	{
		JRPrintElement printFrame = FrameConverter.getInstance().convert(reportConverter, frame); 
		addElement(parentFrame, printFrame);
		addContour(reportConverter, parentFrame, printFrame);
	}

	/**
	 *
	 */
	public void visitImage(JRImage image)
	{
		JRPrintElement printImage = ImageConverter.getInstance().convert(reportConverter, image);
		addElement(parentFrame, printImage);
		addContour(reportConverter, parentFrame, printImage);
	}

	/**
	 *
	 */
	public void visitLine(JRLine line)
	{
		addElement(parentFrame, LineConverter.getInstance().convert(reportConverter, line));
	}

	/**
	 *
	 */
	public void visitRectangle(JRRectangle rectangle)
	{
		addElement(parentFrame, RectangleConverter.getInstance().convert(reportConverter, rectangle));
	}

	/**
	 *
	 */
	public void visitStaticText(JRStaticText staticText)
	{
		JRPrintElement printText = StaticTextConverter.getInstance().convert(reportConverter, staticText);
		addElement(parentFrame, printText);
		addContour(reportConverter, parentFrame, printText);
	}

	/**
	 *
	 */
	public void visitSubreport(JRSubreport subreport)
	{
		JRPrintElement printImage = SubreportConverter.getInstance().convert(reportConverter, subreport);
		addElement(parentFrame, printImage);
		addContour(reportConverter, parentFrame, printImage);
	}

	/**
	 *
	 */
	public void visitTextField(JRTextField textField)
	{
		JRPrintElement printText = TextFieldConverter.getInstance().convert(reportConverter, textField);
		addElement(parentFrame, printText);
		addContour(reportConverter, parentFrame, printText);
	}

	/**
	 *
	 */
	private void addElement(JRBasePrintFrame frame, JRPrintElement element)
	{
		printElement = element;
		if (frame != null)
		{
			frame.addElement(element);
		}
	}
	
	/**
	 *
	 */
	private void addContour(ReportConverter reportConverter, JRBasePrintFrame frame, JRPrintElement element)
	{
		if (frame != null)
		{
			boolean hasContour = false;
			JRLineBox box = element instanceof JRBoxContainer ? ((JRBoxContainer)element).getLineBox() : null; 
			if (box == null)
			{
				JRPrintGraphicElement graphicElement = element instanceof JRPrintGraphicElement ? (JRPrintGraphicElement)element : null;
				hasContour = (graphicElement == null) || graphicElement.getLinePen().getLineWidth().floatValue() <= 0f; 
			}
			else
			{
				hasContour = 
					box.getTopPen().getLineWidth().floatValue() <= 0f 
					&& box.getLeftPen().getLineWidth().floatValue() <= 0f 
					&& box.getRightPen().getLineWidth().floatValue() <= 0f 
					&& box.getBottomPen().getLineWidth().floatValue() <= 0f;
			}
			
			if (hasContour)
			{
				JRBasePrintRectangle rectangle = new JRBasePrintRectangle(reportConverter.getDefaultStyleProvider());
				rectangle.setX(element.getX());
				rectangle.setY(element.getY());
				rectangle.setWidth(element.getWidth());
				rectangle.setHeight(element.getHeight());
				rectangle.getLinePen().setLineWidth(0.1f);
				rectangle.getLinePen().setLineStyle(JRPen.LINE_STYLE_DASHED);
				rectangle.getLinePen().setLineColor(ReportConverter.GRID_LINE_COLOR);
				rectangle.setMode(JRElement.MODE_TRANSPARENT);
				frame.addElement(rectangle);
			}
		}
	}
	
}
