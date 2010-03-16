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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;


/**
 * Thread-based {@link net.sf.jasperreports.engine.fill.JRSubreportRunner JRSubreportRunner}
 * implementation.
 * <p>
 * The subreport fill is launched in a new thread which coordinates suspend/resume actions with
 * the master thread.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRThreadSubreportRunner.java 1621 2007-03-06 13:55:00Z lucianc $
 */
public class JRThreadSubreportRunner extends JRSubreportRunnable implements JRSubreportRunner
{
	
	private static final Log log = LogFactory.getLog(JRThreadSubreportRunner.class);
	
	private final JRBaseFiller subreportFiller;
	
	private Thread fillThread;
	
	public JRThreadSubreportRunner(JRFillSubreport fillSubreport, JRBaseFiller subreportFiller)
	{
		super(fillSubreport);
		this.subreportFiller = subreportFiller;
	}

	public boolean isFilling()
	{
		return fillThread != null;
	}

	public JRSubreportRunResult start()
	{
		fillThread = new Thread(this, subreportFiller.getJasperReport().getName() + " subreport filler");
		
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": starting thread " + fillThread);
		}
		
		fillThread.start();

		return waitResult();
	}

	public JRSubreportRunResult resume()
	{
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": notifying to continue");
		}
		
		//notifing the subreport fill thread that it can continue on the next page
		subreportFiller.notifyAll();

		return waitResult();
	}

	protected JRSubreportRunResult waitResult()
	{
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": waiting for fill result");
		}

		try
		{
			// waiting for the subreport fill thread to fill the current page
			subreportFiller.wait(); // FIXME maybe this is useless since you cannot enter 
									// the synchornized bloc if the subreport filler hasn't 
									// finished the page and passed to the wait state.
		}
		catch (InterruptedException e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Fill " + subreportFiller.fillerId + ": exception", e);
			}
			
			throw new JRRuntimeException("Error encountered while waiting on the report filling thread.", e);
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": notified of fill result");
		}
		
		return runResult();
	}

	public void reset()
	{
		fillThread = null;
	}
	
	public void cancel() throws JRException
	{
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": notifying to continue on cancel");
		}

		// notifying the subreport filling thread that it can continue.
		// it will stop anyway when trying to fill the current band
		subreportFiller.notifyAll();

		if (isRunning())
		{
			if (log.isDebugEnabled())
			{
				log.debug("Fill " + subreportFiller.fillerId + ": still running, waiting");
			}
			
			try
			{
				//waits until the master filler notifies it that can continue with the next page
				subreportFiller.wait();
			}
			catch(InterruptedException e)
			{
				if (log.isErrorEnabled())
				{
					log.error("Fill " + subreportFiller.fillerId + ": exception", e);
				}
				
				throw new JRException("Error encountered while waiting on the subreport filling thread.", e);
			}
			
			if (log.isDebugEnabled())
			{
				log.debug("Fill " + subreportFiller.fillerId + ": wait ended");
			}
		}
	}

	public void suspend() throws JRException
	{
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": notifying on suspend");
		}
		
		//signals to the master filler that is has finished the page
		subreportFiller.notifyAll();
		
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": waiting to continue");
		}

		try
		{
			//waits until the master filler notifies it that can continue with the next page
			subreportFiller.wait();
		}
		catch(InterruptedException e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Fill " + subreportFiller.fillerId + ": exception", e);
			}
			
			throw new JRException("Error encountered while waiting on the subreport filling thread.", e);
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": notified to continue");
		}
	}

	public void run()
	{
		super.run();

		if (log.isDebugEnabled())
		{
			log.debug("Fill " + subreportFiller.fillerId + ": notifying of completion");
		}

		synchronized (subreportFiller)
		{
			//main filler notified that the subreport has finished
			subreportFiller.notifyAll();
		}

/*
		if (error != null)
		{
			synchronized (subreportFiller)
			{
				//if an exception occured then we should notify the main filler that we have finished the subreport
				subreportFiller.notifyAll();
			}
		}
		*/
	}
}
