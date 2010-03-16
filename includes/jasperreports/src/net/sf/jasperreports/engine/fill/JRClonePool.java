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
package net.sf.jasperreports.engine.fill;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import net.sf.jasperreports.engine.JRRuntimeException;

/**
 * Working clones pooling utility used at fill time.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRClonePool.java 1944 2007-11-06 14:14:05Z teodord $
 */
public class JRClonePool
{
	private final JRFillCloneable original;
	private final LinkedList availableClones;
	private final boolean trackLockedClones;
	private final Set lockedClones;

	
	/**
	 * Creates a clone pool.
	 * 
	 * @param original the original element that will be cloned
	 * @param trackLockedClones whether to track clones retrieved from the pool
	 * 		<p>
	 * 		If set, the pool will keep a set of in-use clones and the caller will always
	 * 		have to release the clones back to the pool.
	 * @param useOriginal whether the original object can be used as a working clone
	 */
	public JRClonePool(JRFillCloneable original, boolean trackLockedClones, boolean useOriginal)
	{
		this.original = original;
		
		availableClones = new LinkedList();
		
		this.trackLockedClones = trackLockedClones;
		if (trackLockedClones)
		{
			lockedClones = new HashSet();
		}
		else
		{
			lockedClones = null;
		}
		
		if (useOriginal)
		{
			availableClones.add(original);
		}
	}
	
	
	/**
	 * Retrieves a clone from the pool.
	 * <p>
	 * The clone is reserved to the caller who will need to call
	 * {@link #releaseClone(Object) releaseClone(Object)} to release it back to the pool.
	 * 
	 * @return a clone of the original object
	 */
	public Object getClone()
	{
		JRFillCloneable clone;
		
		if (availableClones.isEmpty())
		{
			JRFillCloneFactory factory = new JRFillCloneFactory();
			clone = original.createClone(factory);
		}
		else
		{
			clone = (JRFillCloneable) availableClones.removeFirst();
		}
		
		if (trackLockedClones)
		{
			lockedClones.add(clone);
		}
		
		return clone;
	}
	
	
	/**
	 * Release the clone back to the pool.
	 * The clone will be available for other clients.
	 * 
	 * @param clone the clone to be released
	 */
	public void releaseClone(Object clone)
	{
		if (trackLockedClones)
		{
			if (!lockedClones.remove(clone))
			{
				throw new JRRuntimeException("Cannot release clone.");
			}
		}
		
		availableClones.addLast(clone);
	}
}
