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
package net.sf.jasperreports.engine.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandlerFactory;

import net.sf.jasperreports.engine.JRException;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRLoader.java 1507 2006-11-27 15:12:17Z teodord $
 */
public class JRLoader
{


	/**
	 *
	 */
	//private static boolean wasWarning = false;


	/**
	 *
	 */
	public static Object loadObject(String fileName) throws JRException
	{
		return loadObject(new File(fileName));
	}


	/**
	 *
	 */
	public static Object loadObject(File file) throws JRException
	{
		if (!file.exists() || !file.isFile())
		{
			throw new JRException( new FileNotFoundException(String.valueOf(file)) );
		}

		Object obj = null;

		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try
		{
			fis = new FileInputStream(file);
			BufferedInputStream bufferedIn = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bufferedIn);
			obj = ois.readObject();
		}
		catch (IOException e)
		{
			throw new JRException("Error loading object from file : " + file, e);
		}
		catch (ClassNotFoundException e)
		{
			throw new JRException("Class not found when loading object from file : " + file, e);
		}
		finally
		{
			if (ois != null)
			{
				try
				{
					ois.close();
				}
				catch(IOException e)
				{
				}
			}

			if (fis != null)
			{
				try
				{
					fis.close();
				}
				catch(IOException e)
				{
				}
			}
		}

		return obj;
	}


	/**
	 *
	 */
	public static Object loadObject(URL url) throws JRException
	{
		Object obj = null;

		InputStream is = null;
		ObjectInputStream ois = null;

		try
		{
			is = url.openStream();
			ois = new ObjectInputStream(is);
			obj = ois.readObject();
		}
		catch (IOException e)
		{
			throw new JRException("Error loading object from URL : " + url, e);
		}
		catch (ClassNotFoundException e)
		{
			throw new JRException("Class not found when loading object from URL : " + url, e);
		}
		finally
		{
			if (ois != null)
			{
				try
				{
					ois.close();
				}
				catch(IOException e)
				{
				}
			}

			if (is != null)
			{
				try
				{
					is.close();
				}
				catch(IOException e)
				{
				}
			}
		}

		return obj;
	}


	/**
	 *
	 */
	public static Object loadObject(InputStream is) throws JRException
	{
		Object obj = null;

		ObjectInputStream ois = null;

		try
		{
			ois = new ObjectInputStream(is);
			obj = ois.readObject();
		}
		catch (IOException e)
		{
			throw new JRException("Error loading object from InputStream", e);
		}
		catch (ClassNotFoundException e)
		{
			throw new JRException("Class not found when loading object from InputStream", e);
		}
		finally
		{
			//FIXMENOW should not close the stream
			if (ois != null)
			{
				try
				{
					ois.close();
				}
				catch(IOException e)
				{
				}
			}
		}

		return obj;
	}


	/**
	 *
	 */
	public static Object loadObjectFromLocation(String location) throws JRException
	{
		return loadObjectFromLocation(location, null, null);
	}


	/**
	 *
	 */
	public static Object loadObjectFromLocation(String location, ClassLoader classLoader) throws JRException
	{
		return loadObjectFromLocation(location, classLoader, null);
	}

	
	/**
	 *
	 */
	public static Object loadObjectFromLocation(
		String location, 
		ClassLoader classLoader,
		URLStreamHandlerFactory urlHandlerFactory
		) throws JRException
	{
		URL url = JRResourcesUtil.createURL(location, urlHandlerFactory);
		if (url != null)
		{
			return loadObject(url);
		}

		File file = new File(location);
		if (file.exists() && file.isFile())
		{
			return loadObject(file);
		}

		url = JRResourcesUtil.findClassLoaderResource(location, classLoader, JRLoader.class);
		if (url != null)
		{
			return loadObject(url);
		}

		throw new JRException("Could not load object from location : " + location);
	}


	/**
	 *
	 */
	public static byte[] loadBytes(File file) throws JRException
	{
		ByteArrayOutputStream baos = null;
		FileInputStream fis = null;

		try
		{
			fis = new FileInputStream(file);
			baos = new ByteArrayOutputStream();

			byte[] bytes = new byte[10000];
			int ln = 0;
			while ((ln = fis.read(bytes)) > 0)
			{
				baos.write(bytes, 0, ln);
			}

			baos.flush();
		}
		catch (IOException e)
		{
			throw new JRException("Error loading byte data : " + file, e);
		}
		finally
		{
			if (baos != null)
			{
				try
				{
					baos.close();
				}
				catch(IOException e)
				{
				}
			}

			if (fis != null)
			{
				try
				{
					fis.close();
				}
				catch(IOException e)
				{
				}
			}
		}

		return baos.toByteArray();
	}


	/**
	 *
	 */
	public static byte[] loadBytes(URL url) throws JRException
	{
		ByteArrayOutputStream baos = null;
		InputStream is = null;

		try
		{
			is = url.openStream();
			baos = new ByteArrayOutputStream();

			byte[] bytes = new byte[10000];
			int ln = 0;
			while ((ln = is.read(bytes)) > 0)
			{
				baos.write(bytes, 0, ln);
			}

			baos.flush();
		}
		catch (IOException e)
		{
			throw new JRException("Error loading byte data : " + url, e);
		}
		finally
		{
			if (baos != null)
			{
				try
				{
					baos.close();
				}
				catch(IOException e)
				{
				}
			}

			if (is != null)
			{
				try
				{
					is.close();
				}
				catch(IOException e)
				{
				}
			}
		}

		return baos.toByteArray();
	}


	/**
	 *
	 */
	public static byte[] loadBytes(InputStream is) throws JRException
	{
		ByteArrayOutputStream baos = null;

		try
		{
			baos = new ByteArrayOutputStream();

			byte[] bytes = new byte[10000];
			int ln = 0;
			while ((ln = is.read(bytes)) > 0)
			{
				baos.write(bytes, 0, ln);
			}

			baos.flush();
		}
		catch (IOException e)
		{
			throw new JRException("Error loading byte data from input stream.", e);
		}
		finally
		{
			if (baos != null)
			{
				try
				{
					baos.close();
				}
				catch(IOException e)
				{
				}
			}
		}

		return baos.toByteArray();
	}


	/**
	 *
	 */
	public static byte[] loadBytesFromLocation(String location) throws JRException
	{
		return loadBytesFromLocation(location, null, null);
	}


	/**
	 *
	 */
	public static byte[] loadBytesFromLocation(String location, ClassLoader classLoader) throws JRException
	{
		return loadBytesFromLocation(location, classLoader, null);
	}
		
	
	/**
	 *
	 */
	public static byte[] loadBytesFromLocation(
		String location, 
		ClassLoader classLoader,
		URLStreamHandlerFactory urlHandlerFactory
		) throws JRException
	{
		URL url = JRResourcesUtil.createURL(location, urlHandlerFactory);
		if (url != null)
		{
			return loadBytes(url);
		}

		File file = new File(location);
		if (file.exists() && file.isFile())
		{
			return loadBytes(file);
		}

		url = JRResourcesUtil.findClassLoaderResource(location, classLoader, JRLoader.class);
		if (url != null)
		{
			return loadBytes(url);
		}

		throw new JRException("Byte data not found at location : " + location);
	}


	/**
	 * Tries to open an input stream for a location.
	 * <p>
	 * The method tries to interpret the location as a file name, a resource name or
	 * an URL.  If any of these succeed, an input stream is created and returned.
	 * 
	 * @param location the location
	 * @return an input stream if the location is an existing file name, a resource name on
	 * the classpath or an URL or <code>null</code> otherwise.
	 * 
	 * @throws JRException
	 */
	public static InputStream getLocationInputStream(String location) throws JRException
	{
		InputStream is = null;
		
		is = getResourceInputStream(location);
		
		if (is == null)
		{
			is = getFileInputStream(location);
		}
		
		if (is == null)
		{
			is = getURLInputStream(location);
		}
		
		return is;
	}


	/**
	 * Tries to open a file for reading.
	 * 
	 * @param filename the file name
	 * @return an input stream for the file or <code>null</code> if the file was not found
	 * @throws JRException
	 */
	public static InputStream getFileInputStream(String filename) throws JRException
	{
		InputStream is = null;
		
		File file = new File(filename);
		if (file.exists() && file.isFile())
		{
			try
			{
				is = new FileInputStream(file);
			}
			catch (FileNotFoundException e)
			{
				throw new JRException("Error opening file " + filename);
			}
		}
		
		return is;
	}


	/**
	 * Tries to open an input stream for a resource.
	 *  
	 * @param resource the resource name
	 * @return an input stream for the resource or <code>null</code> if the resource was not found
	 */
	public static InputStream getResourceInputStream(String resource)
	{
		InputStream is = null;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();		
		if (classLoader != null)
		{
			is = classLoader.getResourceAsStream(resource);
		}
		
		if (is == null)
		{
			classLoader = JRLoader.class.getClassLoader();
			if (classLoader != null)
			{
				is = classLoader.getResourceAsStream(resource);
			}
			
			if (is == null)
			{
				is = JRProperties.class.getResourceAsStream("/" + resource);
			}
		}

		return is;
	}


	/**
	 * Tries to open an input stream for an URL.
	 * 
	 * @param spec the string to parse as an URL
	 * @return an input stream for the URL or null if <code>spec</code> is not a valid URL
	 * @throws JRException
	 */
	public static InputStream getURLInputStream(String spec) throws JRException
	{
		InputStream is = null;
		
		try
		{
			URL url = new URL(spec);
			is = url.openStream();
		}
		catch (MalformedURLException e)
		{
		}
		catch (IOException e)
		{
			throw new JRException("Error opening URL " + spec);
		}
		
		return is;
	}
}
