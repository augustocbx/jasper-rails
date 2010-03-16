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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.crosstabs.JRCrosstabCell;
import net.sf.jasperreports.crosstabs.JRCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.JRCrosstabRowGroup;
import net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.base.JRBasePrintFrame;
import net.sf.jasperreports.engine.design.JRDesignFrame;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRGraphics2DExporter.java 1811 2007-08-13 19:52:07Z teodord $
 */
public class CrosstabConverter extends FrameConverter
{

	/**
	 *
	 */
	private final static CrosstabConverter INSTANCE = new CrosstabConverter();
	
	/**
	 *
	 */
	private CrosstabConverter()
	{
	}

	/**
	 *
	 */
	public static ElementConverter getInstance()
	{
		return INSTANCE;
	}
	
	/**
	 *
	 */
	public JRPrintElement convert(ReportConverter reportConverter, JRElement element)
	{
		JRBasePrintFrame printFrame = new JRBasePrintFrame(reportConverter.getDefaultStyleProvider());
		JRCrosstab crosstab = (JRCrosstab)element; 
		
		copyElement(reportConverter, crosstab, printFrame);
		
		List children = getCrosstabChildren(reportConverter, crosstab);
		if (children != null && children.size() > 0)
		{
			ConvertVisitor convertVisitor = new ConvertVisitor(reportConverter, printFrame);
			for(int i = 0; i < children.size(); i++)
			{
				((JRChild)children.get(i)).visit(convertVisitor);
			}
		}
		
		return printFrame;
	}

	/**
	 *
	 */
	private List getCrosstabChildren(ReportConverter reportConverter, JRCrosstab crosstab)
	{
		List crosstabElements = new ArrayList();
		
		JRCrosstabRowGroup[] rowGroups = crosstab.getRowGroups();
		int rowHeadersXOffset = 0;
		for (int i = 0; i < rowGroups.length; i++)
		{
			rowHeadersXOffset += rowGroups[i].getWidth();
		}
		
		JRCrosstabColumnGroup[] columnGroups = crosstab.getColumnGroups();
		int colHeadersYOffset = 0;
		for (int i = 0; i < columnGroups.length; i++)
		{
			colHeadersYOffset += columnGroups[i].getHeight();
		}
		
		JRCellContents headerCell = crosstab.getHeaderCell();
		if (headerCell != null)
		{
			if (headerCell.getWidth() != 0 && headerCell.getHeight() != 0)
			{
				crosstabElements.add(
					getCrosstabCellFrame(
						reportConverter,
						headerCell, 
						0, 
						0, 
						false, 
						false, 
						false
						));

			}
			
		}
		
		addCrosstabColumnHeaders(
			reportConverter,
			crosstab, 
			rowHeadersXOffset, 
			crosstabElements
			);
		addCrosstabRows(
			reportConverter,
			crosstab, 
			rowHeadersXOffset, 
			colHeadersYOffset, 
			crosstabElements
			);
		
		if (crosstab.getRunDirection() == JRCrosstab.RUN_DIRECTION_RTL)
		{
			mirrorElements(crosstabElements, crosstab.getX(), crosstab.getWidth());
		}
		
		return crosstabElements;
	}
	
	/**
	 *
	 */
	private void mirrorElements(List elements, int x, int width)
	{
		for (Iterator it = elements.iterator(); it.hasNext();)
		{
			JRElement element = (JRElement) it.next();
			int mirrorX = 2 * x + width - element.getX() - element.getWidth();
			element.setX(mirrorX);
		}
	}
	
	/**
	 * 
	 */
	private JRFrame getCrosstabCellFrame(
		ReportConverter reportConverter,
		JRCellContents cell, 
		int x, 
		int y, 
		boolean left, 
		boolean right, 
		boolean top
		)
	{
		JRDesignFrame frame = new JRDesignFrame(cell.getDefaultStyleProvider());
		frame.setX(x);
		frame.setY(y);
		frame.setWidth(cell.getWidth());
		frame.setHeight(cell.getHeight());
		
		frame.setMode(cell.getMode());
		frame.setBackcolor(cell.getBackcolor());
		frame.setStyle(reportConverter.resolveStyle(cell));
		
		JRLineBox box = cell.getLineBox();
		if (box != null)
		{
			frame.copyBox(box);
			
			boolean copyLeft = left && box.getLeftPen().getLineWidth().floatValue() <= 0f && box.getRightPen().getLineWidth().floatValue() > 0f;
			boolean copyRight = right && box.getRightPen().getLineWidth().floatValue() <= 0f && box.getLeftPen().getLineWidth().floatValue() > 0f;
			boolean copyTop = top && box.getTopPen().getLineWidth().floatValue() <= 0f && box.getBottomPen().getLineWidth().floatValue() > 0f;
			
			if (copyLeft)
			{
				((JRBaseLineBox)frame.getLineBox()).copyLeftPen(box.getRightPen());
			}
			
			if (copyRight)
			{
				((JRBaseLineBox)frame.getLineBox()).copyRightPen(box.getLeftPen());
			}
			
			if (copyTop)
			{
				((JRBaseLineBox)frame.getLineBox()).copyTopPen(box.getBottomPen());
			}
		}
		
		List children = cell.getChildren();
		if (children != null)
		{
			for (Iterator it = children.iterator(); it.hasNext();)
			{
				JRChild child = (JRChild) it.next();
				if (child instanceof JRElement)
				{
					frame.addElement((JRElement) child);
				}
				else if (child instanceof JRElementGroup)
				{
					frame.addElementGroup((JRElementGroup) child);
				}
			}
		}
		
		return frame;
	}
	
	/**
	 * 
	 */
	private void addCrosstabColumnHeaders(
		ReportConverter reportConverter,
		JRCrosstab crosstab, 
		int rowHeadersXOffset, 
		List crosstabElements
		)
	{
		JRCrosstabColumnGroup[] groups = crosstab.getColumnGroups();
		
		for (int i = 0, x = 0, y = 0; i < groups.length; i++)
		{
			JRCrosstabColumnGroup group = groups[i];
			
			if (group.getTotalPosition() == BucketDefinition.TOTAL_POSITION_START)
			{
				JRCellContents totalHeader = group.getTotalHeader();
				if (totalHeader.getWidth() != 0 && totalHeader.getHeight() != 0)
				{
					boolean firstOnRow = x == 0 && crosstab.getHeaderCell() == null;
					crosstabElements.add(
						getCrosstabCellFrame(
							reportConverter,
							totalHeader, 
							rowHeadersXOffset + x, 
							y, 
							firstOnRow && crosstab.getRunDirection() == JRCrosstab.RUN_DIRECTION_LTR,
							firstOnRow && crosstab.getRunDirection() == JRCrosstab.RUN_DIRECTION_RTL,
							false
							));
	
					x += totalHeader.getWidth();
				}
			}
			
			JRCellContents header = group.getHeader();
			if (header.getWidth() != 0 && header.getHeight() != 0) {
				boolean firstOnRow = x == 0 && crosstab.getHeaderCell() == null;
				crosstabElements.add(
					getCrosstabCellFrame(
						reportConverter,
						header, 
						rowHeadersXOffset + x, 
						y, 
						firstOnRow && crosstab.getRunDirection() == JRCrosstab.RUN_DIRECTION_LTR,
						firstOnRow && crosstab.getRunDirection() == JRCrosstab.RUN_DIRECTION_RTL,
						false
						));
			}
			
			if (group.getTotalPosition() == BucketDefinition.TOTAL_POSITION_END)
			{
				JRCellContents totalHeader = group.getTotalHeader();
				if (totalHeader.getWidth() != 0 && totalHeader.getHeight() != 0)
					crosstabElements.add(
						getCrosstabCellFrame(
							reportConverter,
							totalHeader, 
							rowHeadersXOffset + x + header.getWidth(), 
							y, 
							false, 
							false, 
							false
							));
				
			}
			
			y += group.getHeight();
		}
	}

	/**
	 * 
	 */
	private void addCrosstabRows(
		ReportConverter reportConverter,
		JRCrosstab crosstab, 
		int rowHeadersXOffset, 
		int colHeadersYOffset, 
		List crosstabElements
		)
	{
		JRCrosstabRowGroup[] groups = crosstab.getRowGroups();
		
		for (int i = 0, x = 0, y = 0; i < groups.length; i++)
		{
			JRCrosstabRowGroup group = groups[i];
			
			if (group.getTotalPosition() == BucketDefinition.TOTAL_POSITION_START)
			{
				JRCellContents totalHeader = group.getTotalHeader();
				if (totalHeader.getWidth() != 0 && totalHeader.getHeight() != 0)
				{
					crosstabElements.add(
						getCrosstabCellFrame(
							reportConverter,
							totalHeader, 
							x, 
							colHeadersYOffset + y, 
							false, 
							false, 
							y == 0 && crosstab.getHeaderCell() == null
							));
					
					addCrosstabDataCellsRow(
						reportConverter,
						crosstab, 
						rowHeadersXOffset, 
						colHeadersYOffset + y, 
						i, 
						crosstabElements
						);
					y += totalHeader.getHeight();
				}
			}
			
			JRCellContents header = group.getHeader();
			if (header.getWidth() != 0 && header.getHeight() != 0)
				crosstabElements.add(
					getCrosstabCellFrame(
						reportConverter,
						header, 
						x, 
						colHeadersYOffset + y, 
						false, 
						false, 
						y == 0 && crosstab.getHeaderCell() == null
						));
			
			if (i == groups.length - 1)
			{
				addCrosstabDataCellsRow(
					reportConverter,
					crosstab, 
					rowHeadersXOffset, 
					colHeadersYOffset + y, 
					groups.length, 
					crosstabElements
					);				
			}
			
			if (group.getTotalPosition() == BucketDefinition.TOTAL_POSITION_END)
			{
				JRCellContents totalHeader = group.getTotalHeader();
				if (totalHeader.getWidth() != 0 && totalHeader.getHeight() != 0)
				{
					crosstabElements.add(
						getCrosstabCellFrame(
							reportConverter,
							totalHeader, 
							x, 
							colHeadersYOffset + y + header.getHeight(), 
							false, 
							false, 
							false
							));
					
					addCrosstabDataCellsRow(
						reportConverter,
						crosstab, 
						rowHeadersXOffset, 
						colHeadersYOffset + y + header.getHeight(), 
						i, 
						crosstabElements
						);
				}
			}
			
			x += group.getWidth();
		}
	}

	/**
	 * 
	 */
	private void addCrosstabDataCellsRow(
		ReportConverter reportConverter,
		JRCrosstab crosstab, 
		int rowOffsetX, 
		int rowOffsetY, 
		int rowIndex,
		List crosstabElements
		)
	{
		JRCrosstabCell[][] cells = crosstab.getCells();
		if (cells != null)
		{
			JRCrosstabColumnGroup[] colGroups = crosstab.getColumnGroups();
			int crosstabX = rowOffsetX;
			int crosstabY = rowOffsetY;

			for (int i = 0, x = 0; i < colGroups.length; i++)
			{
				JRCrosstabColumnGroup group = colGroups[i];
				
				if (group.getTotalPosition() == BucketDefinition.TOTAL_POSITION_START)
				{
					JRCrosstabCell cell = cells[rowIndex][i];
					if (cell != null)
					{
						JRCellContents contents = cell.getContents();
						if (contents.getWidth() != 0 && contents.getHeight() != 0)
						{
							crosstabElements.add(
								getCrosstabCellFrame(
									reportConverter,
									contents, 
									crosstabX + x, 
									crosstabY, 
									false, 
									false, 
									false
									));
							x += cells[rowIndex][i].getContents().getWidth();
						}
					}
				}
				
				if (i == colGroups.length - 1)
				{
					JRCrosstabCell cell = cells[rowIndex][colGroups.length];
					if (cell != null)
					{
						JRCellContents contents = cell.getContents();
						if (contents.getWidth() != 0 && contents.getHeight() != 0)
							crosstabElements.add(
								getCrosstabCellFrame(
									reportConverter,
									contents, 
									crosstabX + x, 
									crosstabY, 
									false, 
									false, 
									false
									));
					}
				}
				
				if (group.getTotalPosition() == BucketDefinition.TOTAL_POSITION_END)
				{
					JRCrosstabCell cell = cells[rowIndex][i];
					if (cell != null)
					{
						JRCellContents contents = cell.getContents();
						if (contents.getWidth() != 0 && contents.getHeight() != 0)
							crosstabElements.add(
								getCrosstabCellFrame(
									reportConverter,
									contents, 
									crosstabX + x + group.getHeader().getWidth(), 
									crosstabY, 
									false, 
									false, 
									false
									));
					}
				}
			}
		}
	}

}
