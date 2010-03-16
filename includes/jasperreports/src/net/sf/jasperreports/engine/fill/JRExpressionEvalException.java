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

import java.io.PrintStream;
import java.io.PrintWriter;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRExpressionEvalException.java 2003 2007-12-05 14:16:53Z teodord $
 */
public class JRExpressionEvalException extends JRException
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	

	/**
	 *
	 */
	private JRExpression expression = null;
	private Throwable nestedThrowable = null;


	/**
	 *
	 */
	public JRExpressionEvalException(JRExpression expr, Throwable e)
	{
		super(
			"Error evaluating expression : " 
			+ "\n\tSource text : " + expr.getText()
			);
			
		expression = expr;
		nestedThrowable = e;
	}


	/**
	 *
	 */
	public Throwable getCause()
	{
		return this.nestedThrowable;
	}


	/**
	 *
	 */
	public JRExpression getExpression()
	{
		return this.expression;
	}


	/**
	 *
	 */
	public void printStackTrace()
	{
		if (nestedThrowable != null)
		{
			nestedThrowable.printStackTrace();
			System.err.println("\nNESTED BY :");
		}

		super.printStackTrace();
	}
	

	/**
	 *
	 */
	public void printStackTrace(PrintStream s)
	{
		if (nestedThrowable != null)
		{
			nestedThrowable.printStackTrace(s);
			s.println("\nNESTED BY :");
		}

		super.printStackTrace(s);
	}
	

	/**
	 *
	 */
	public void printStackTrace(PrintWriter s)
	{
		if (nestedThrowable != null)
		{
			nestedThrowable.printStackTrace(s);
			s.println("\nNESTED BY :");
		}

		super.printStackTrace(s);
	}
	
	
}
