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
 * An interface providing hyperlink functionality. It must be implemented by elements that can contain hyperlinks.
 *
 * There are three types of hyperlinks: reference, anchor and page. The reference type just points to an external resource.
 * The anchor type can point to an anchor in the current document or inside an external referenced document. In the latter
 * case, users have to specify both an anchor expression and a reference expression. The page type can point to the
 * beginning of a specific page in the current document or an external document (in the same way that anchor type does).
 *
 * @see JRAnchor
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRHyperlink.java 1950 2007-11-12 15:21:54Z teodord $
 */
public interface JRHyperlink extends JRCloneable
{


	/**
	 * Constant useful for specifying that the element does not contain a hyperlink. This is the default value
	 * for a hyperlink type.
	 */
	public static final byte HYPERLINK_TYPE_NONE = 1;

	/**
	 * Constant useful for specifying that the hyperlink points to an external resource specified by the
	 * hyperlink reference expression.
	 * @see JRHyperlink#getHyperlinkReferenceExpression()
	 */
	public static final byte HYPERLINK_TYPE_REFERENCE = 2;

	/**
	 * Constant useful for specifying that the hyperlink points to a local anchor, specified by the hyperlink
	 * anchor expression.
	 * @see JRHyperlink#getHyperlinkAnchorExpression()
	 */
	public static final byte HYPERLINK_TYPE_LOCAL_ANCHOR = 3;

	/**
	 * Constant useful for specifying that the hyperlink points to a 1 based page index within the current document.
	 */
	public static final byte HYPERLINK_TYPE_LOCAL_PAGE = 4;

	/**
	 * Constant useful for specifying that the hyperlink points to a remote anchor (specified by the hyperlink
	 * anchor expression) within an external document (specified by the hyperlink reference expression).
	 * @see JRHyperlink#getHyperlinkAnchorExpression()
	 * @see JRHyperlink#getHyperlinkReferenceExpression()
	 */
	public static final byte HYPERLINK_TYPE_REMOTE_ANCHOR = 5;

	/**
	 * Constant useful for specifying that the hyperlink points to a 1 based page index within an external document
	 * (specified by the hyperlink reference expression).
	 */
	public static final byte HYPERLINK_TYPE_REMOTE_PAGE = 6;

	
	/**
	 * Not set hyperlink type.
	 */
	public static final byte HYPERLINK_TYPE_NULL = 0;
	
	
	/**
	 * Custom hyperlink type.
	 * <p>
	 * The specific type is determined by {@link #getLinkType() getLinkType()}.
	 * </p>
	 */
	public static final byte HYPERLINK_TYPE_CUSTOM = 7;


	/**
	 * Constant useful for specifying that the hyperlink will be opened in the same window.
	 */
	public static final byte HYPERLINK_TARGET_SELF = 1;

	/**
	 * Constant useful for specifying that the hyperlink will be opened in a new window.
	 */
	public static final byte HYPERLINK_TARGET_BLANK = 2;


	/**
	 * Retrieves the hyperlink type for the element.
	 * <p>
	 * The actual hyperlink type is determined by {@link #getLinkType() getLinkType()}.
	 * This method can is used to determine whether the hyperlink type is one of the
	 * built-in types or a custom type. 
	 * When hyperlink is of custom type, {@link #HYPERLINK_TYPE_CUSTOM HYPERLINK_TYPE_CUSTOM} is returned.
	 * </p>
	 * @return one of the hyperlink type constants
	 * @see #getLinkType()
	 */
	public byte getHyperlinkType();


	/**
	 * Retrieves the hyperlink target for the element.
	 * @return one of the hyperlink target constants
	 */
	public byte getHyperlinkTarget();


	/**
	 * Returns the expression whose value represents the hyperlink reference. It is only used when the hyperlink type is
	 * reference or anchor
	 */
	public JRExpression getHyperlinkReferenceExpression();


	/**
	 * Returns the expression whose value represents the anchor. It is only used when the hyperlink type is anchor.
	 */
	public JRExpression getHyperlinkAnchorExpression();


	/**
	 * Returns an integer representing the page index of the link. It is only used when the hyperlink type is page.
	 * If the expression does not evaluate to an integer, an exception will be thrown.
	 */
	public JRExpression getHyperlinkPageExpression();

	
	/**
	 * Returns the hyperlink type.
	 * <p>
	 * The type can be one of the built-in types
	 * (Reference, LocalAnchor, LocalPage, RemoteAnchor, RemotePage),
	 * or can be an arbitrary type.
	 * </p>
	 * @return the hyperlink type
	 */
	public String getLinkType();
	
	
	/**
	 * Returns the list of hyperlink parameters.
	 * <p>
	 * The parameters can be used by custom hyperlink types to generate
	 * dynamic links.
	 * </p>
	 * @return the list of hyperlink parameters
	 */
	public JRHyperlinkParameter[] getHyperlinkParameters();
	
	
	/**
	 * Returns the expression which will generate the hyperlink tooltip.
	 * 
	 * @return the expression which will generate the hyperlink tooltip
	 */
	public JRExpression getHyperlinkTooltipExpression();

}
