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
package net.sf.jasperreports.engine.design;

import net.sf.jasperreports.engine.JRExpression;


/**
 * Default {@link JRCompilationSourceCode JRCompilationSourceCode} implementation that can receive
 * a list of expressions corresponding to lines in the code.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRDefaultCompilationSourceCode.java 1625 2007-03-09 17:21:31Z lucianc $
 */
public class JRDefaultCompilationSourceCode implements JRCompilationSourceCode
{
	
	private final String sourceCode;
	private final JRExpression[] lineExpressions;
	
	
	/**
	 * Constructor.
	 * 
	 * @param sourceCode the source code.
	 * @param lineExpressions an array of expressions corresponding to line numbers.
	 * 	Can be null.
	 */
	public JRDefaultCompilationSourceCode(String sourceCode, JRExpression[] lineExpressions)
	{
		this.sourceCode = sourceCode;
		this.lineExpressions = lineExpressions;
	}

	public String getCode()
	{
		return sourceCode;
	}

	public JRExpression getExpressionAtLine(int line)
	{
		if (lineExpressions == null || line <= 0 || line > lineExpressions.length)
		{
			return null;
		}
		
		return lineExpressions[line - 1];
	}

}
