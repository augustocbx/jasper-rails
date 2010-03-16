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

import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import net.sf.jasperreports.engine.JRRuntimeException;

import com.lowagie.text.SplitCharacter;
import com.lowagie.text.pdf.PdfChunk;


/**
 * Implementation of {@link com.lowagie.text.SplitCharacter SplitCharacter} that
 * uses the same logic as AWT to break texts into lines.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: BreakIteratorSplitCharacter.java 1571 2007-01-29 13:41:53Z lucianc $
 * 
 * @see net.sf.jasperreports.engine.export.JRPdfExporterParameter#FORCE_LINEBREAK_POLICY
 * @see net.sf.jasperreports.engine.util.JRProperties#PDF_FORCE_LINEBREAK_POLICY
 */
public class BreakIteratorSplitCharacter implements SplitCharacter
{

	private char[] chars;
	private int start, end;
	private boolean[] boundary;
	private int lastBoundary;
	private final BreakIterator breakIter;
	
	public BreakIteratorSplitCharacter()
	{
		this(BreakIterator.getLineInstance());
	}
	
	public BreakIteratorSplitCharacter(BreakIterator breakIter)
	{
		this.breakIter = breakIter;
	}

	public boolean isSplitCharacter(int startIdx, int current, int endIdx, char[] cc, PdfChunk[] ck)
	{
		++current;
		if (current == endIdx)
		{
			return false;
		}

		if (!(chars == cc && this.start == startIdx && this.end == endIdx))
		{
			chars = cc;
			this.start = startIdx;
			this.end = endIdx;

			breakIter.setText(new ArrayCharIterator(cc, startIdx, endIdx));

			boundary = new boolean[endIdx - startIdx + 1];

			lastBoundary = breakIter.first();
			if (lastBoundary != BreakIterator.DONE)
			{
				boundary[lastBoundary - startIdx] = true;
			}
		}

		while (current > lastBoundary)
		{
			lastBoundary = breakIter.next();

			if (lastBoundary == BreakIterator.DONE)
			{
				lastBoundary = Integer.MAX_VALUE;
			}
			else
			{
				boundary[lastBoundary - startIdx] = true;
			}
		}

		return boundary[current - startIdx];
	}

	protected static class ArrayCharIterator implements CharacterIterator
	{

		private char[] chars;
		private int start;
		private int end;
		private int curr;

		public ArrayCharIterator(char[] chars, int start, int end)
		{
			this.chars = chars;
			this.start = start;
			this.end = end;
		}

		public char first()
		{
			curr = start;
			return current();
		}

		public char last()
		{
			if (end == start)
			{
				curr = end;
			}
			else
			{
				curr = end - 1;
			}
			return current();
		}

		public char setIndex(int position)
		{
			if (position < start || position > end)
			{
				throw new JRRuntimeException("Invalid index " + position + " (start = " + start + ", end = " + end + ")");
			}
			curr = position;
			return current();
		}

		public char current()
		{
			if (curr < start || curr >= end)
			{
				return DONE;
			}
			return chars[curr];
		}

		public char next()
		{
			if (curr >= end - 1)
			{
				curr = end;
				return DONE;
			}
			curr++;
			return chars[curr];
		}

		public char previous()
		{
			if (curr <= start)
			{
				return DONE;
			}
			curr--;
			return chars[curr];
		}

		public int getBeginIndex()
		{
			return start;
		}

		public int getEndIndex()
		{
			return end;
		}

		public int getIndex()
		{
			return curr;
		}

		public Object clone()
		{
			try
			{
				StringCharacterIterator other = (StringCharacterIterator) super.clone();
				return other;
			}
			catch (CloneNotSupportedException e)
			{
				throw new InternalError();
			}
		}
	}

}
