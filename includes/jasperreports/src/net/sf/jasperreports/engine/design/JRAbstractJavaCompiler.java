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
 * Peter Severin - peter_p_s@users.sourceforge.net 
 */
package net.sf.jasperreports.engine.design;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.collections.ReferenceMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.fill.JREvaluator;
import net.sf.jasperreports.engine.util.JRClassLoader;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRAbstractJavaCompiler.java 1289 2006-06-13 11:54:52Z teodord $
 */
public abstract class JRAbstractJavaCompiler extends JRAbstractCompiler
{

	// @JVM Crash workaround
	// Reference to the loaded class class in a per thread map
	private static ThreadLocal classFromBytesRef = new ThreadLocal();


	private static Object CLASS_CACHE_NULL_KEY = new Object();
	private static Map classCache = new ReferenceMap(ReferenceMap.WEAK, ReferenceMap.SOFT);

	
	protected JRAbstractJavaCompiler(boolean needsSourceFiles)
	{
		super(needsSourceFiles);
	}


	protected JREvaluator loadEvaluator(Serializable compileData, String className) throws JRException
	{
		JREvaluator evaluator = null;

		try
		{
			Class clazz = getClassFromCache(className);
			if (clazz == null)
			{
				clazz = JRClassLoader.loadClassFromBytes(className, (byte[]) compileData);
				putClassInCache(className, clazz);
			}
			
			//FIXME multiple classes per thread?
			classFromBytesRef.set(clazz);
		
			evaluator = (JREvaluator) clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new JRException("Error loading expression class : " + className, e);
		}
		
		return evaluator;
	}
	
	
	protected static Object classCacheKey()
	{
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Object key = contextClassLoader == null ? CLASS_CACHE_NULL_KEY : contextClassLoader;
		return key;
	}

	
	protected static synchronized Class getClassFromCache(String className)
	{
		Object key = classCacheKey();
		Map contextMap = (Map) classCache.get(key);
		Class cachedClass = null;
		if (contextMap != null)
		{
			cachedClass = (Class) contextMap.get(className);
		}
		return cachedClass;
	}


	protected static synchronized void putClassInCache(String className, Class loadedClass)
	{
		Object key = classCacheKey();
		Map contextMap = (Map) classCache.get(key);
		if (contextMap == null)
		{
			contextMap = new ReferenceMap(ReferenceMap.HARD, ReferenceMap.SOFT);
			classCache.put(key, contextMap);
		}
		contextMap.put(className, loadedClass);
	}
}
