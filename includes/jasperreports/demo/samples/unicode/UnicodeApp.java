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
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: UnicodeApp.java 1938 2007-10-31 13:19:04Z teodord $
 */
public class UnicodeApp
{


	/**
	 *
	 */
	private static final String TASK_FILL = "fill";
	private static final String TASK_PRINT = "print";
	private static final String TASK_PDF = "pdf";
	private static final String TASK_XML = "xml";
	private static final String TASK_XML_EMBED = "xmlEmbed";
	private static final String TASK_HTML = "html";
	private static final String TASK_RTF = "rtf";
	private static final String TASK_XLS = "xls";
	private static final String TASK_JXL = "jxl";
	private static final String TASK_CSV = "csv";
	private static final String TASK_ODT = "odt";
	private static final String TASK_RUN = "run";
	
	
	/**
	 *
	 */
	public static void main(String[] args)
	{
		if(args.length == 0)
		{
			usage();
			return;
		}
				
		String taskName = args[0];
		String fileName = args[1];

		try
		{
			long start = System.currentTimeMillis();
			if (TASK_FILL.equals(taskName))
			{
				Map parameters = new HashMap();
				parameters.put("GreekText", "\u0393 \u0394 \u0398 \u039B \u039E \u03A0 \u03A3 \u03A6 \u03A8 \u03A9");
				parameters.put("CyrillicText", "\u0402 \u040B \u040F \u0414 \u0416 \u0418 \u041B \u0426 \u0429 \u042E");
				parameters.put("ArabicText", "\u0647\u0630\u0627 \u0639\u0631\u0636 \u0644\u0645\u062C\u0645\u0648\u0639\u0629 TextLayout");
				parameters.put("HebrewText", "\u05D0\u05E0\u05D9 \u05DC\u05D0 \u05DE\u05D1\u05D9\u05DF \u05E2\u05D1\u05E8\u05D9\u05EA");

				JasperFillManager.fillReportToFile(fileName, parameters, new JREmptyDataSource());
				System.err.println("Filling time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_PRINT.equals(taskName))
			{
				JasperPrintManager.printReport(fileName, true);
				System.err.println("Printing time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_PDF.equals(taskName))
			{
				JasperExportManager.exportReportToPdfFile(fileName);
				System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_RTF.equals(taskName))
			{
				File sourceFile = new File(fileName);

				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".rtf");

				JRRtfExporter exporter = new JRRtfExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

				exporter.exportReport();

				System.err.println("RTF creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_XML.equals(taskName))
			{
				JasperExportManager.exportReportToXmlFile(fileName, false);
				System.err.println("XML creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_XML_EMBED.equals(taskName))
			{
				JasperExportManager.exportReportToXmlFile(fileName, true);
				System.err.println("XML creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_HTML.equals(taskName))
			{
				JasperExportManager.exportReportToHtmlFile(fileName);
				System.err.println("HTML creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_XLS.equals(taskName))
			{
				File sourceFile = new File(fileName);

				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xls");

				JRXlsExporter exporter = new JRXlsExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

				exporter.exportReport();

				System.err.println("XLS creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_JXL.equals(taskName))
			{
				File sourceFile = new File(fileName);

				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".jxl.xls");

				JExcelApiExporter exporter = new JExcelApiExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

				exporter.exportReport();

				System.err.println("XLS creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_CSV.equals(taskName))
			{
				File sourceFile = new File(fileName);

				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".csv");

				JRCsvExporter exporter = new JRCsvExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
				exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");

				exporter.exportReport();

				System.err.println("CSV creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_ODT.equals(taskName))
			{
				File sourceFile = new File(fileName);

				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".odt");

				JROdtExporter exporter = new JROdtExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
				exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");

				exporter.exportReport();

				System.err.println("ODT creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_RUN.equals(taskName))
			{
				Map parameters = new HashMap();
				parameters.put("GreekText", "\u0393 \u0394 \u0398 \u039B \u039E \u03A0 \u03A3 \u03A6 \u03A8 \u03A9");
				parameters.put("CyrillicText", "\u0402 \u040B \u040F \u0414 \u0416 \u0418 \u041B \u0426 \u0429 \u042E");
				parameters.put("ArabicText", "\u0647\u0630\u0627 \u0639\u0631\u0636 \u0644\u0645\u062C\u0645\u0648\u0639\u0629 TextLayout");
				parameters.put("HebrewText", "\u05D0\u05E0\u05D9 \u05DC\u05D0 \u05DE\u05D1\u05D9\u05DF \u05E2\u05D1\u05E8\u05D9\u05EA");

				JasperRunManager.runReportToPdfFile(fileName, parameters, new JREmptyDataSource());
				System.err.println("PDF running time : " + (System.currentTimeMillis() - start));
			}
			else
			{
				usage();
			}
		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 *
	 */
	private static void usage()
	{
		System.out.println( "UnicodeApp usage:" );
		System.out.println( "\tjava UnicodeApp task file" );
		System.out.println( "\tTasks : fill | print | pdf | xml | xmlEmbed | html | rtf | xls | jxl | csv | odt | run" );
	}


}
