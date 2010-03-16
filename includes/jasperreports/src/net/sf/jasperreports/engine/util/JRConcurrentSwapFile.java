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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import net.sf.jasperreports.engine.JRRuntimeException;


/**
 * {@link net.sf.jasperreports.engine.util.JRSwapFile JRSwapFile} derived class that uses 
 * a {@link java.nio.channels.FileChannel FileChannel} to perform concurrent I/O on the
 * swap file.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRConcurrentSwapFile.java 1270 2006-05-29 17:13:26Z lucianc $
 */
public class JRConcurrentSwapFile extends JRSwapFile
{
	
	private final FileChannel fileChannel;

	/**
	 * Creates a swap file.
	 * 
	 * The file name is generated automatically.
	 * 
	 * @param directory the directory where the file should be created.
	 * @param blockSize the size of the blocks allocated by the swap file
	 * @param minGrowCount the minimum number of blocks by which the swap file grows when full
	 */
	public JRConcurrentSwapFile(String directory, int blockSize, int minGrowCount)
	{
		super(directory, blockSize, minGrowCount);

		fileChannel = file.getChannel();
	}

	protected void write(byte[] data, int dataSize, int dataOffset, long fileOffset) throws IOException
	{
		fileChannel.write(ByteBuffer.wrap(data, dataOffset, dataSize), fileOffset);
	}

	protected void read(byte[] data, int dataOffset, int dataLength, long fileOffset) throws IOException
	{
		ByteBuffer buffer = ByteBuffer.wrap(data, dataOffset, dataLength);
		int read, totalRead = 0;
		do
		{
			read = fileChannel.read(buffer, fileOffset + totalRead);
			if (read < 0)
			{
				throw new JRRuntimeException("Unable to read sufficient data from the swap file");
			}
			totalRead += read;
		}
		while (totalRead < dataLength);
	}
	
}
