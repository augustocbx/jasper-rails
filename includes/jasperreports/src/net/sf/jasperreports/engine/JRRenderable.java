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
 * Adrian Jackson - iapetus@users.sourceforge.net
 * David Taylor - exodussystems@users.sourceforge.net
 * Lars Kristensen - llk@users.sourceforge.net
 */
package net.sf.jasperreports.engine;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRRenderable.java 1942 2007-11-01 15:23:32Z lucianc $
 */
public interface JRRenderable extends Serializable
{


	/**
	 *
	 */
	public static final byte TYPE_IMAGE = 0;
	public static final byte TYPE_SVG = 1;

	/**
	 * A constant used for specifying that the image is of unknown type 
	 */
	public static final byte IMAGE_TYPE_UNKNOWN = 0;
	
	/**
	 * A constant used for specifying that the image is of GIF type
	 */
	public static final byte IMAGE_TYPE_GIF = 1;
	
	/**
	 * A constant used for specifying that the image is of the JPEG type
	 */
	public static final byte IMAGE_TYPE_JPEG = 2;
	
	/**
	 * A constant used for specifying that the image is of the PNG type
	 */
	public static final byte IMAGE_TYPE_PNG = 3;
	
	/**
	 * A constant used for specifying that the image is of the TIFF type
	 */
	public static final byte IMAGE_TYPE_TIFF = 4; 

	/**
	 * image mime type constants
	 */
	public static final String MIME_TYPE_GIF  = "image/gif";
	public static final String MIME_TYPE_JPEG = "image/jpeg";
	public static final String MIME_TYPE_PNG  = "image/png";
	public static final String MIME_TYPE_TIFF = "image/tiff";
	

	/**
	 *
	 */
	public String getId();

	/**
	 *
	 */
	public byte getType();

	/**
	 *
	 */
	public byte getImageType();

	/**
	 *
	 */
	public Dimension2D getDimension() throws JRException;


	/**
	 *
	 */
	public byte[] getImageData() throws JRException;


	/**
	 *
	 */
	public void render(Graphics2D grx, Rectangle2D rectangle) throws JRException;


}
