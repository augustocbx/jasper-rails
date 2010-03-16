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
 * Artur Biesiadowski - abies@users.sourceforge.net 
 */
package net.sf.jasperreports.engine.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRTemplate;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 * Utility class that loads {@link JRTemplate templates} from XML representations.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRXmlTemplateLoader.java 1763 2007-06-21 08:47:21Z lucianc $
 */
public class JRXmlTemplateLoader
{
	
	private static final Log log = LogFactory.getLog(JRXmlTemplateLoader.class);
	
	protected JRXmlTemplateLoader()
	{
	}
	
	/**
	 * Parses a template XML found at a specified location into a {@link JRTemplate template object}.
	 * 
	 * @param location the template XML location.
	 * 	Can be a URL, a file path or a classloader resource name.
	 * @return the template object
	 * @throws JRException when the location cannot be resolved or read
	 * @see JRLoader#loadBytesFromLocation(String)
	 */
	public static JRTemplate load(String location) throws JRException
	{
		byte[] data = JRLoader.loadBytesFromLocation(location);
		return load(new ByteArrayInputStream(data));
	}
	
	/**
	 * Parses a template XML file into a {@link JRTemplate template object}.
	 * 
	 * @param file the template XML file
	 * @return the template object
	 */
	public static JRTemplate load(File file)
	{
		BufferedInputStream fileIn;
		try
		{
			fileIn = new BufferedInputStream(new FileInputStream(file));
		}
		catch (FileNotFoundException e)
		{
			throw new JRRuntimeException("Template XML file not found", e);
		}

		try
		{
			return load(fileIn);
		}
		finally
		{
			try
			{
				fileIn.close();
			}
			catch (IOException e)
			{
				log.warn("Error closing XML file", e);
			}
		}		
	}
	
	/**
	 * Parses a template XML located at a URL into a {@link JRTemplate template object}.
	 * 
	 * @param url the location of the template XML
	 * @return the template object
	 */
	public static JRTemplate load(URL url)
	{
		InputStream input;
		try
		{
			input = url.openStream();
		}
		catch (IOException e)
		{
			throw new JRRuntimeException("Error opening connection to template URL " + url, e);
		}

		try
		{
			return load(input);
		}
		finally
		{
			try
			{
				input.close();
			}
			catch (IOException e)
			{
				log.warn("Error closing connection to template URL " + url, e);
			}
		}		
	}
	
	/**
	 * Parses a template XML data stream into a {@link JRTemplate template object}.
	 * 
	 * @param data the data stream
	 * @return the template object
	 */
	public static JRTemplate load(InputStream data)
	{
		JRXmlTemplateLoader loader = new JRXmlTemplateLoader();
		return loader.loadTemplate(data);
	}
	
	protected JRTemplate loadTemplate(InputStream data)
	{
		JRXmlDigester digester = JRXmlTemplateDigesterFactory.instance().createDigester();
		try
		{
			JRTemplate template = (JRTemplate) digester.parse(data);
			return template;
		}
		catch (IOException e)
		{
			throw new JRRuntimeException("Error reading template XML", e);
		}
		catch (SAXException e)
		{
			throw new JRRuntimeException("Error parsing template XML", e);
		}
	}

}
