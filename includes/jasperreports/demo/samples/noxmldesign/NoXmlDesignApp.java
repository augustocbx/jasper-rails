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
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: NoXmlDesignApp.java 1938 2007-10-31 13:19:04Z teodord $
 */
public class NoXmlDesignApp
{


	/**
	 *
	 */
	private static final String TASK_COMPILE = "compile";
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
	private static final String TASK_WRITE_XML = "writeXml";
	
	
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
			if (TASK_COMPILE.equals(taskName))
			{
				JasperDesign jasperDesign = getJasperDesign();
				JasperCompileManager.compileReportToFile(jasperDesign, fileName);
				System.err.println("Compile time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_FILL.equals(taskName))
			{
				//Preparing parameters
				Map parameters = new HashMap();
				parameters.put("ReportTitle", "Address Report");
				parameters.put("OrderByClause", "ORDER BY City");

				JasperFillManager.fillReportToFile(fileName, parameters, getConnection());
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
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				
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
				
				exporter.exportReport();

				System.err.println("ODT creation time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_RUN.equals(taskName))
			{
				//Preparing parameters
				Map parameters = new HashMap();
				parameters.put("ReportTitle", "Address Report");
				parameters.put("OrderByClause", "ORDER BY City");
				
				JasperRunManager.runReportToPdfFile(fileName, parameters, getConnection());
				System.err.println("PDF running time : " + (System.currentTimeMillis() - start));
			}
			else if (TASK_WRITE_XML.equals(taskName))
			{
				JasperCompileManager.writeReportToXmlFile(fileName);
				System.err.println("XML design creation time : " + (System.currentTimeMillis() - start));
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
		System.out.println( "NoXmlDesignApp usage:" );
		System.out.println( "\tjava NoXmlDesignApp task file" );
		System.out.println( "\tTasks : compile | fill | print | pdf | xml | xmlEmbed | html | rtf | xls | jxl | csv | odt | run | writeXml" );
	}


	/**
	 *
	 */
	private static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		//Change these settings according to your local configuration
		String driver = "org.hsqldb.jdbcDriver";
		String connectString = "jdbc:hsqldb:hsql://localhost";
		String user = "sa";
		String password = "";


		Class.forName(driver);
		Connection conn = DriverManager.getConnection(connectString, user, password);
		return conn;
	}


	/**
	 *
	 */
	private static JasperDesign getJasperDesign() throws JRException
	{
		//JasperDesign
		JasperDesign jasperDesign = new JasperDesign();
		jasperDesign.setName("NoXmlDesignReport");
		jasperDesign.setPageWidth(595);
		jasperDesign.setPageHeight(842);
		jasperDesign.setColumnWidth(515);
		jasperDesign.setColumnSpacing(0);
		jasperDesign.setLeftMargin(40);
		jasperDesign.setRightMargin(40);
		jasperDesign.setTopMargin(50);
		jasperDesign.setBottomMargin(50);
		
		//Fonts
		JRDesignStyle normalStyle = new JRDesignStyle();
		normalStyle.setName("Arial_Normal");
		normalStyle.setDefault(true);
		normalStyle.setFontName("Arial");
		normalStyle.setFontSize(12);
		normalStyle.setPdfFontName("Helvetica");
		normalStyle.setPdfEncoding("Cp1252");
		normalStyle.setPdfEmbedded(false);
		jasperDesign.addStyle(normalStyle);

		JRDesignStyle boldStyle = new JRDesignStyle();
		boldStyle.setName("Arial_Bold");
		boldStyle.setFontName("Arial");
		boldStyle.setFontSize(12);
		boldStyle.setBold(true);
		boldStyle.setPdfFontName("Helvetica-Bold");
		boldStyle.setPdfEncoding("Cp1252");
		boldStyle.setPdfEmbedded(false);
		jasperDesign.addStyle(boldStyle);

		JRDesignStyle italicStyle = new JRDesignStyle();
		italicStyle.setName("Arial_Italic");
		italicStyle.setFontName("Arial");
		italicStyle.setFontSize(12);
		italicStyle.setItalic(true);
		italicStyle.setPdfFontName("Helvetica-Oblique");
		italicStyle.setPdfEncoding("Cp1252");
		italicStyle.setPdfEmbedded(false);
		jasperDesign.addStyle(italicStyle);
		
		//Parameters
		JRDesignParameter parameter = new JRDesignParameter();
		parameter.setName("ReportTitle");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);

		parameter = new JRDesignParameter();
		parameter.setName("OrderByClause");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);

		//Query
		JRDesignQuery query = new JRDesignQuery();
		query.setText("SELECT * FROM Address $P!{OrderByClause}");
		jasperDesign.setQuery(query);

		//Fields
		JRDesignField field = new JRDesignField();
		field.setName("Id");
		field.setValueClass(java.lang.Integer.class);
		jasperDesign.addField(field);

		field = new JRDesignField();
		field.setName("FirstName");
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);

		field = new JRDesignField();
		field.setName("LastName");
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);

		field = new JRDesignField();
		field.setName("Street");
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);

		field = new JRDesignField();
		field.setName("City");
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);

		//Variables
		JRDesignVariable variable = new JRDesignVariable();
		variable.setName("CityNumber");
		variable.setValueClass(java.lang.Integer.class);
		variable.setResetType(JRVariable.RESET_TYPE_GROUP);
		JRDesignGroup group = new JRDesignGroup();
		group.setName("CityGroup");
		variable.setResetGroup(group);
		variable.setCalculation(JRVariable.CALCULATION_SYSTEM);
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(java.lang.Integer.class);
		expression.setText("($V{CityNumber} != null)?(new Integer($V{CityNumber}.intValue() + 1)):(new Integer(1))");
		variable.setInitialValueExpression(expression);
		jasperDesign.addVariable(variable);

		variable = new JRDesignVariable();
		variable.setName("AllCities");
		variable.setValueClass(java.lang.String.class);
		variable.setResetType(JRVariable.RESET_TYPE_REPORT);
		variable.setCalculation(JRVariable.CALCULATION_SYSTEM);
		jasperDesign.addVariable(variable);

		//Groups
		group.setMinHeightToStartNewPage(60);
		expression = new JRDesignExpression();
		expression.setValueClass(java.lang.String.class);
		expression.setText("$F{City}");
		group.setExpression(expression);

		JRDesignBand band = new JRDesignBand();
		band.setHeight(20);
		JRDesignRectangle rectangle = new JRDesignRectangle();
		rectangle.setX(0);
		rectangle.setY(4);
		rectangle.setWidth(515);
		rectangle.setHeight(15);
		rectangle.setForecolor(new Color(0xC0, 0xC0, 0xC0));
		rectangle.setBackcolor(new Color(0xC0, 0xC0, 0xC0));
		band.addElement(rectangle);
		JRDesignTextField textField = new JRDesignTextField();
		textField.setX(0);
		textField.setY(4);
		textField.setWidth(515);
		textField.setHeight(15);
		textField.setBackcolor(new Color(0xC0, 0xC0, 0xC0));
		textField.setMode(JRElement.MODE_OPAQUE);
		textField.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_LEFT);
		textField.setStyle(boldStyle);
		expression = new JRDesignExpression();
		expression.setValueClass(java.lang.String.class);
		expression.setText("\"  \" + String.valueOf($V{CityNumber}) + \". \" + String.valueOf($F{City})");
		textField.setExpression(expression);
		band.addElement(textField);
		JRDesignLine line = new JRDesignLine();
		line.setX(0);
		line.setY(19);
		line.setWidth(515);
		line.setHeight(0);
		band.addElement(line);
		group.setGroupHeader(band);

		band = new JRDesignBand();
		band.setHeight(20);
		line = new JRDesignLine();
		line.setX(0);
		line.setY(-1);
		line.setWidth(515);
		line.setHeight(0);
		band.addElement(line);
		JRDesignStaticText staticText = new JRDesignStaticText();
		staticText.setX(400);
		staticText.setY(0);
		staticText.setWidth(60);
		staticText.setHeight(15);
		staticText.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_RIGHT);
		staticText.setStyle(boldStyle);
		staticText.setText("Count : ");
		band.addElement(staticText);
		textField = new JRDesignTextField();
		textField.setX(460);
		textField.setY(0);
		textField.setWidth(30);
		textField.setHeight(15);
		textField.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_RIGHT);
		textField.setStyle(boldStyle);
		expression = new JRDesignExpression();
		expression.setValueClass(java.lang.Integer.class);
		expression.setText("$V{CityGroup_COUNT}");
		textField.setExpression(expression);
		band.addElement(textField);
		group.setGroupFooter(band);

		jasperDesign.addGroup(group);

		//Title
		band = new JRDesignBand();
		band.setHeight(50);
		line = new JRDesignLine();
		line.setX(0);
		line.setY(0);
		line.setWidth(515);
		line.setHeight(0);
		band.addElement(line);
		textField = new JRDesignTextField();
		textField.setBlankWhenNull(true);
		textField.setX(0);
		textField.setY(10);
		textField.setWidth(515);
		textField.setHeight(30);
		textField.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_CENTER);
		textField.setStyle(normalStyle);
		textField.setFontSize(22);
		expression = new JRDesignExpression();
		expression.setValueClass(java.lang.String.class);
		expression.setText("$P{ReportTitle}");
		textField.setExpression(expression);
		band.addElement(textField);
		jasperDesign.setTitle(band);
		
		//Page header
		band = new JRDesignBand();
		band.setHeight(20);
		rectangle = new JRDesignRectangle();
		rectangle.setX(0);
		rectangle.setY(5);
		rectangle.setWidth(515);
		rectangle.setHeight(15);
		rectangle.setForecolor(new Color(0x33, 0x33, 0x33));
		rectangle.setBackcolor(new Color(0x33, 0x33, 0x33));
		band.addElement(rectangle);
		staticText = new JRDesignStaticText();
		staticText.setX(0);
		staticText.setY(5);
		staticText.setWidth(55);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(JRElement.MODE_OPAQUE);
		staticText.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_CENTER);
		staticText.setStyle(boldStyle);
		staticText.setText("ID");
		band.addElement(staticText);
		staticText = new JRDesignStaticText();
		staticText.setX(55);
		staticText.setY(5);
		staticText.setWidth(205);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(JRElement.MODE_OPAQUE);
		staticText.setStyle(boldStyle);
		staticText.setText("Name");
		band.addElement(staticText);
		staticText = new JRDesignStaticText();
		staticText.setX(260);
		staticText.setY(5);
		staticText.setWidth(255);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(JRElement.MODE_OPAQUE);
		staticText.setStyle(boldStyle);
		staticText.setText("Street");
		band.addElement(staticText);
		jasperDesign.setPageHeader(band);

		//Column header
		band = new JRDesignBand();
		jasperDesign.setColumnHeader(band);

		//Detail
		band = new JRDesignBand();
		band.setHeight(20);
		textField = new JRDesignTextField();
		textField.setX(0);
		textField.setY(4);
		textField.setWidth(50);
		textField.setHeight(15);
		textField.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_RIGHT);
		textField.setStyle(normalStyle);
		expression = new JRDesignExpression();
		expression.setValueClass(java.lang.Integer.class);
		expression.setText("$F{Id}");
		textField.setExpression(expression);
		band.addElement(textField);
		textField = new JRDesignTextField();
		textField.setStretchWithOverflow(true);
		textField.setX(55);
		textField.setY(4);
		textField.setWidth(200);
		textField.setHeight(15);
		textField.setPositionType(JRElement.POSITION_TYPE_FLOAT);
		textField.setStyle(normalStyle);
		expression = new JRDesignExpression();
		expression.setValueClass(java.lang.String.class);
		expression.setText("$F{FirstName} + \" \" + $F{LastName}");
		textField.setExpression(expression);
		band.addElement(textField);
		textField = new JRDesignTextField();
		textField.setStretchWithOverflow(true);
		textField.setX(260);
		textField.setY(4);
		textField.setWidth(255);
		textField.setHeight(15);
		textField.setPositionType(JRElement.POSITION_TYPE_FLOAT);
		textField.setStyle(normalStyle);
		expression = new JRDesignExpression();
		expression.setValueClass(java.lang.String.class);
		expression.setText("$F{Street}");
		textField.setExpression(expression);
		band.addElement(textField);
		line = new JRDesignLine();
		line.setX(0);
		line.setY(19);
		line.setWidth(515);
		line.setHeight(0);
		line.setForecolor(new Color(0x80, 0x80, 0x80));
		line.setPositionType(JRElement.POSITION_TYPE_FLOAT);
		band.addElement(line);
		jasperDesign.setDetail(band);

		//Column footer
		band = new JRDesignBand();
		jasperDesign.setColumnFooter(band);

		//Page footer
		band = new JRDesignBand();
		jasperDesign.setPageFooter(band);

		//Summary
		band = new JRDesignBand();
		jasperDesign.setSummary(band);
		
		return jasperDesign;
	}
	

}
