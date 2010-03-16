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
package net.sf.jasperreports.crosstabs.xml;

import org.xml.sax.Attributes;

import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabBucket;
import net.sf.jasperreports.engine.xml.JRBaseFactory;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRCrosstabBucketFactory.java 1578 2007-02-09 16:27:52Z shertage $
 */
public class JRCrosstabBucketFactory extends JRBaseFactory
{
	public static final String ELEMENT_bucket = "bucket";
	public static final String ELEMENT_bucketExpression = "bucketExpression";
	public static final String ELEMENT_comparatorExpression = "comparatorExpression";
	
	public static final String ATTRIBUTE_order = "order";
	
	public Object createObject(Attributes attributes)
	{
		JRDesignCrosstabBucket bucket = new JRDesignCrosstabBucket();
		
		String orderAttr = attributes.getValue(ATTRIBUTE_order);
		if (orderAttr != null)
		{
			Byte order = (Byte) JRXmlConstants.getCrosstabBucketOrderMap().get(orderAttr);
			bucket.setOrder(order.byteValue());
		}
		
		return bucket;
	}

}
