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
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: I18nApp.java 1907 2007-10-19 10:33:27Z teodord $
 */
public class I18nApp
{


	/**
	 *
	 */
	private static final String TASK_FILL = "fill";
	private static final String TASK_FILL_DEFAULT = "fillDefault";
	private static final String TASK_VIEW = "view";
	private static final String TASK_PDF = "pdf";
	private static final String TASK_XML = "xml";
	private static final String TASK_XML_EMBED = "xmlEmbed";
	private static final String TASK_HTML = "html";
	private static final String TASK_RTF = "rtf";
	private static final String TASK_XLS = "xls";
	private static final String TASK_JXL = "jxl";
	private static final String TASK_CSV = "csv";
	private static final String TASK_ODT = "odt";
	
	
	/**
	 *
	 */
	public static void main(String[] args)
	{
		if(args.length != 2)
		{
			usage();
			return;
		}

		String taskName = args[0];
		String fileName = args[1];

		try
		{
			if (TASK_FILL.equals(taskName))
			{
				Locale locale = chooseLocale();
				if (locale != null)
				{
//					Object[] aw = new Object[] {new Double(1000000.45), "$", "Ferrari", new Integer(20),new Integer(88)};
					Map parameters = new HashMap();
					parameters.put("number", new Double(1234567 + Math.random()));
//					parameters.put("array", aw);
					parameters.put(JRParameter.REPORT_LOCALE, locale);
					long start = System.currentTimeMillis();
					JasperFillManager.fillReportToFile(fileName, parameters, new JREmptyDataSource());
					System.err.println("Filling time : " + (System.currentTimeMillis() - start));
				}
			}
			else if (TASK_FILL_DEFAULT.equals(taskName))
			{
				Map parameters = new HashMap();
				parameters.put("number", new Double(1234567 + Math.random()));
				long start = System.currentTimeMillis();
				JasperFillManager.fillReportToFile(fileName, parameters, new JREmptyDataSource());
				System.err.println("Filling time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_VIEW.equals(taskName))
			{
				Locale locale = chooseLocale();
				if (locale != null)
				{
					JasperViewer.viewReport(fileName, false, true, locale);
				}
			}
			else if (TASK_PDF.equals(taskName))
			{
				long start = System.currentTimeMillis();
				JasperExportManager.exportReportToPdfFile(fileName);
				System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_XML.equals(taskName))
			{
				long start = System.currentTimeMillis();
				JasperExportManager.exportReportToXmlFile(fileName, false);
				System.err.println("XML creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_XML_EMBED.equals(taskName))
			{
				long start = System.currentTimeMillis();
				JasperExportManager.exportReportToXmlFile(fileName, true);
				System.err.println("XML creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_HTML.equals(taskName))
			{
				long start = System.currentTimeMillis();
				JasperExportManager.exportReportToHtmlFile(fileName);
				System.err.println("HTML creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_RTF.equals(taskName))
			{
				long start = System.currentTimeMillis();

				File sourceFile = new File(fileName);
		
				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		
				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".rtf");
				
				JRRtfExporter exporter = new JRRtfExporter();
				
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
				
				exporter.exportReport();

				System.err.println("RTF creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_XLS.equals(taskName))
			{
				long start = System.currentTimeMillis();

				File sourceFile = new File(fileName);
		
				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		
				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xls");
				
				JRXlsExporter exporter = new JRXlsExporter();
				
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				
				exporter.exportReport();

				System.err.println("XLS creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_JXL.equals(taskName))
			{
				long start = System.currentTimeMillis();

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
				long start = System.currentTimeMillis();

				File sourceFile = new File(fileName);
		
				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		
				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".csv");
				
				JRCsvExporter exporter = new JRCsvExporter();
				
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
				
				exporter.exportReport();

				System.err.println("CSV creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_ODT.equals(taskName))
			{
				long start = System.currentTimeMillis();

				File sourceFile = new File(fileName);
		
				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		
				File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".odt");
				
				JROdtExporter exporter = new JROdtExporter();
				
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
				
				exporter.exportReport();

				System.err.println("ODT creation time : " + (System.currentTimeMillis() - start));
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
	private static Locale chooseLocale()
	{
		LocaleWrapper[] locales = 
			new LocaleWrapper[]
			{
				new LocaleWrapper(new Locale("cs", "CZ")),
				new LocaleWrapper(Locale.GERMANY),
				new LocaleWrapper(Locale.US),
				new LocaleWrapper(Locale.FRANCE),
				new LocaleWrapper(new Locale("pt", "PT")),
				new LocaleWrapper(new Locale("ro", "RO")),
				new LocaleWrapper(new Locale("sk", "SK"))
			};
									  
		LocaleWrapper locale = 
			(LocaleWrapper)JOptionPane.showInputDialog(
				null,
				"Choose the locale",
				"Locale",
				JOptionPane.PLAIN_MESSAGE,
				null,
				locales,
				null
				);
		
		if (locale != null)
			return locale.getLocale();
		
		return null;
	}

	/**
	 *
	 */
	private static void usage()
	{
		System.out.println( "I18nApp usage:" );
		System.out.println( "\tjava I18nApp task file" );
		System.out.println( "\tTasks : fill | fillDefault | view | pdf | xml | xmlEmbed | html | rtf | xls | jxl | csv | odt" );
	}


}

class LocaleWrapper
{
	Locale locale = null;
	
	public LocaleWrapper(Locale locale)
	{
		this.locale = locale;
	}
	
	public Locale getLocale()
	{
		return locale;
	}
	
	public String toString()
	{
		return locale.toString() + " - " + locale.getDisplayName(locale);
	}
}
