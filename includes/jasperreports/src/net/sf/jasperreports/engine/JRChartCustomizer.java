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
 * Michael McMahon - Michael.McMahon@activewire.net
 */

package net.sf.jasperreports.engine;

import org.jfree.chart.JFreeChart;

/**
 * This interface allows users to provide pluggable chart customizers. A class that implements this interface can
 * be defined and its name must be specified in the report template. At fill time, the corresponding <i>customize()</i>
 * method of this class will be called, if advanced chart customization is needed.
 * <p/>
 * If values of report parameters, variables or fields are needed in the customization, the customizer implementation
 * should extend {@link net.sf.jasperreports.engine.JRAbstractChartCustomizer JRAbstractChartCustomizer}
 * to gain access to such values.
 *
 * @author Michael McMahon (Michael.McMahon@activewire.net)
 * @version $Id: JRChartCustomizer.java 1229 2006-04-19 10:27:35Z teodord $
 */
public interface JRChartCustomizer {

	/**
	 * This method is called at fill time, before the chart is rendered.
 	 * @param chart the JFreeChart object, which can be accessed and modified
	 * @param jasperChart the JasperReports version of the chart, containing data that might be needed in
	 * customization
	 */
	public void customize(JFreeChart chart, JRChart jasperChart);

}

