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
package net.sf.jasperreports.crosstabs;

import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRVariable;


/**
 * Base interface for crosstab row and column groups.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRCrosstabGroup.java 1955 2007-11-13 17:06:44Z teodord $
 */
public interface JRCrosstabGroup extends JRCloneable
{
	/**
	 * Returns the name of the group.
	 * 
	 * @return the name of the group
	 * @see #getVariable()
	 */
	public String getName();
	
	/**
	 * Returns the position of the total row/column for this group.
	 * <p>
	 * A group can have a total row/column summing the values for all the
	 * entries in the group.  E.g. if there is a Year group having Month as
	 * a subgroup, the total row/column for the Year group would sum the values
	 * for all the years and the total row/column for the Month group would sum
	 * the values for all the months of an year.
	 * <p>
	 * Possible values for this attribute are:
	 * <ul>
	 * 	<li>{@link net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition#TOTAL_POSITION_NONE Bucket.TOTAL_POSITION_NONE}
	 * - the group will not display a total row/column</li>
	 * 	<li>{@link net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition#TOTAL_POSITION_START Bucket.TOTAL_POSITION_START}
	 * - the group will display the total row/column before the group rows/columns</li>
	 * 	<li>{@link net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition#TOTAL_POSITION_END Bucket.TOTAL_POSITION_END}
	 * - the group will display the total row/column at the end of the group rows/columns</li>
	 * 
	 * @return the position of the total row/column for this group
	 */
	public byte getTotalPosition();
	
	
	/**
	 * Returns the bucketing information for this group.
	 * <p>
	 * The bucketing information consists of grouping expression and  
	 * group ordering.
	 * 
	 * @return the bucketing information for this group
	 */
	public JRCrosstabBucket getBucket();
	
	
	/**
	 * Returns the group header cell.
	 * <p>
	 * The size of the header cell is computed based on the following rules
	 * (only the row header rules are listed, the ones for columns can be
	 * deducted by simmetrical duality):
	 * <ul>
	 * 	<li>the width of the header is given by {@link JRCrosstabRowGroup#getWidth() JRCrosstabRowGroup.getWidth()}</li>
	 * 	<li>the height of the last row group header is given by the height of the base cell</li>
	 * 	<li>the height of a non-last row group header is the sum of the next group header's height and
	 * the next group total header's height (0 if the next group doesn't have a total header)</li>  
	 * </ul>
	 * <p>
	 * Should never return null, but empty cell contents instead.
	 * 
	 * @return the group header cell
	 */
	public JRCellContents getHeader();
	
	
	/**
	 * Returns the group total header cell.
	 * <p>
	 * The size of a row group total header is computed based on the following rules:
	 * <ul>
	 * 	<li>the width is the sum the widths of this and subsequent row groups</li>
	 * 	<li>the height is the height of the base cell for this total row</li>
	 * </ul>
	 * <p>
	 * Should never return null, but empty cell contents instead.
	 * 
	 * @return the group total header cell
	 */
	public JRCellContents getTotalHeader();
	
	
	/**
	 * Returns the variable associated to this group.
	 * <p>
	 * Each group in the crosstab has a variable that can be used
	 * inside the group header as the current group value.
	 * The variable has the same name as the group and the same type
	 * as the bucket expression of the group.
	 * 
	 * @return the variable associated to this group
	 */
	public JRVariable getVariable();
	
	
	/**
	 * Returns whether the group has a total row/column.
	 * <p>
	 * This method is currently equivalent to 
	 * <code>getTotalPosition() != Bucket.TOTAL_POSITION_NONE</code> and is therefore
	 * redundant.
	 * 
	 * @return whether the group has a total row/column
	 */
	public boolean hasTotal();
}
