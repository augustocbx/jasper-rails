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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.JRQueryChunk;


/**
 * Report query parser.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRQueryParser.java 1683 2007-03-29 16:04:15Z lucianc $
 */
public class JRQueryParser
{

	protected static final String CLAUSE_TOKEN_SEPARATOR = ",";
	
	private static final JRQueryParser singleton = new JRQueryParser();
	
	/**
	 * Returns a query parser instance.
	 * 
	 * @return a query parser instance
	 */
	public static JRQueryParser instance()
	{
		return singleton;
	}
	
	
	/**
	 * Parses a report query.
	 * 
	 * @param text the query text
	 * @param chunkHandler a handler that will be asked to handle parsed query chunks
	 */
	public void parse(String text, JRQueryChunkHandler chunkHandler)
	{
		if (text != null)
		{
			StringBuffer textChunk = new StringBuffer();
			
			StringTokenizer tkzer = new StringTokenizer(text, "$", true);
			boolean wasDelim = false;
			while (tkzer.hasMoreTokens())
			{
				String token = tkzer.nextToken();
	
				if (token.equals("$"))
				{
					if (wasDelim)
					{
						textChunk.append("$");
					}
	
					wasDelim = true;
				}
				else
				{
					if ( token.startsWith("P{") && wasDelim )
					{
						int end = token.indexOf('}');
						if (end > 0)
						{
							if (textChunk.length() > 0)
							{
								chunkHandler.handleTextChunk(textChunk.toString());					
							}
							String parameterChunk = token.substring(2, end);
							chunkHandler.handleParameterChunk(parameterChunk);					
							textChunk = new StringBuffer(token.substring(end + 1));
						}
						else
						{
							if (wasDelim)
							{
								textChunk.append("$");
							}
							textChunk.append(token);
						}
					}
					else if ( token.startsWith("P!{") && wasDelim )
					{
						int end = token.indexOf('}');
						if (end > 0)
						{
							if (textChunk.length() > 0)
							{
								chunkHandler.handleTextChunk(textChunk.toString());					
							}
							String parameterClauseChunk = token.substring(3, end);
							chunkHandler.handleParameterClauseChunk(parameterClauseChunk);					
							textChunk = new StringBuffer(token.substring(end + 1));
						}
						else
						{
							if (wasDelim)
							{
								textChunk.append("$");
							}
							textChunk.append(token);
						}
					}
					else if ( token.startsWith("X{") && wasDelim )
					{
						int end = token.indexOf('}');
						if (end > 0)
						{
							if (textChunk.length() > 0)
							{
								chunkHandler.handleTextChunk(textChunk.toString());					
							}
							String clauseChunk = token.substring(2, end);
							String[] tokens = parseClause(clauseChunk);
							chunkHandler.handleClauseChunk(tokens);					
							textChunk = new StringBuffer(token.substring(end + 1));
						}
						else
						{
							if (wasDelim)
							{
								textChunk.append("$");
							}
							textChunk.append(token);
						}
					}
					else
					{
						if (wasDelim)
						{
							textChunk.append("$");
						}
						textChunk.append(token);
					}
	
					wasDelim = false;
				}
			}
			if (wasDelim)
			{
				textChunk.append("$");
			}
			if (textChunk.length() > 0)
			{
				chunkHandler.handleTextChunk(textChunk.toString());					
			}
		}
	}
	
	
	protected String[] parseClause(String clauseChunk)
	{
		List tokens = new ArrayList();
		
		boolean wasClauseToken = false;
		StringTokenizer tokenizer = new StringTokenizer(clauseChunk, CLAUSE_TOKEN_SEPARATOR, true);
		while (tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			if (token.equals(CLAUSE_TOKEN_SEPARATOR))
			{
				if (!wasClauseToken)
				{
					tokens.add("");
				}
				wasClauseToken = false;
			}
			else
			{
				tokens.add(token);
				wasClauseToken = true;
			}
		}
		if (!wasClauseToken)
		{
			tokens.add("");
		}
		
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}


	/**
	 * (Re)creates the query text from a list of chunks.
	 * 
	 * @param chunks the chunks
	 * @return the recreated query text
	 */
	public String asText(JRQueryChunk[] chunks)
	{
		String text = "";
		
		if (chunks != null && chunks.length > 0)
		{
			StringBuffer sbuffer = new StringBuffer();

			for(int i = 0; i < chunks.length; i++)
			{
				JRQueryChunk queryChunk = chunks[i];
				switch(queryChunk.getType())
				{
					case JRQueryChunk.TYPE_PARAMETER :
					{
						sbuffer.append("$P{");
						sbuffer.append( queryChunk.getText() );
						sbuffer.append("}");
						break;
					}
					case JRQueryChunk.TYPE_PARAMETER_CLAUSE :
					{
						sbuffer.append("$P!{");
						sbuffer.append( queryChunk.getText() );
						sbuffer.append("}");
						break;
					}
					case JRQueryChunk.TYPE_CLAUSE_TOKENS :
					{
						sbuffer.append("$X{");
						sbuffer.append(queryChunk.getText());
						sbuffer.append("}");
						break;
					}
					case JRQueryChunk.TYPE_TEXT :
					default :
					{
						sbuffer.append( queryChunk.getText() );
						break;
					}
				}
			}

			text = sbuffer.toString();
		}
		
		return text;
	}
	
	/**
	 * (Re)constructs a query clause chunk from the chunk tokens.
	 * 
	 * @param tokens the chunk tokens
	 * @return the reconstructed query clause chunk
	 * @see JRQueryChunk#TYPE_CLAUSE_TOKENS
	 */
	public String asClauseText(String[] tokens)
	{
		StringBuffer sb = new StringBuffer();
		if (tokens != null && tokens.length > 0)
		{
			for (int i = 0; i < tokens.length; i++)
			{
				if (i > 0)
				{
					sb.append(',');
				}
				String token = tokens[i];
				if (token != null)
				{
					sb.append(token);
				}
			}
		}
		return sb.toString();
	}
	
}
