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
package net.sf.jasperreports.engine.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRPrintImage;

import org.w3c.tools.codec.Base64Decoder;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRPrintImageSourceObject.java 1811 2007-08-13 19:52:07Z teodord $
 */
public class JRPrintImageSourceObject
{


	/**
	 *
	 */
	private JRPrintImage printImage = null;

	/**
	 *
	 */
	private boolean isEmbedded = false;


	/**
	 *
	 */
	public void setPrintImage(JRPrintImage printImage)
	{
		this.printImage = printImage;
	}
	

	/**
	 *
	 */
	public void setEmbedded(boolean isEmbedded)
	{
		this.isEmbedded = isEmbedded;
	}
	

	/**
	 *
	 */
	public void setImageSource(String imageSource) throws JRException
	{
		if (isEmbedded)
		{
			try
			{
				ByteArrayInputStream bais = new ByteArrayInputStream(imageSource.getBytes("UTF-8"));//FIXMENOW other encodings ?
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				Base64Decoder decoder = new Base64Decoder(bais, baos);
				decoder.process();
				
				printImage.setRenderer(JRImageRenderer.getInstance(baos.toByteArray()));//, JRImage.ON_ERROR_TYPE_ERROR));
			}
			catch (Exception e)
			{
				throw new JRException("Error decoding embedded image.", e);
			}
		}
		else
		{
			printImage.setRenderer(JRImageRenderer.getInstance(imageSource));
		}
	}
	

}
