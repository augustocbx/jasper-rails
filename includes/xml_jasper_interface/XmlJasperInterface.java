/*
 * An XML Jasper interface; Takes XML data from the standard input
 * and uses JRXmlDataSource to generate Jasper reports in the
 * specified output format using the specified compiled Jasper design.
 * 
 * Inspired by the xmldatasource sample application provided with
 * jasperreports-1.1.0
 */

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * @author Jonas Schwertfeger (jonas at schwertfeger dot ch)
 * @version $Id: XmlJasperInterface.java 97 2005-11-23 14:48:15Z js $
 */
public class XmlJasperInterface {
  private static final String TYPE_PDF = "pdf";
  private static final String TYPE_XML = "xml";
  private static final String TYPE_RTF = "rtf";
  private static final String TYPE_XLS = "xls";
  private static final String TYPE_CSV = "csv";
  
  private String outputType;
  private String compiledDesign;
  private String selectCriteria;
  private static String datePattern = null;
  private static String numberPattern = null;

  public static void main(String[] args) {
    String outputType = null;
    String compiledDesign = null;
    String selectCriteria = null;

    if (args.length != 3) {
      printUsage();
      return;
    }

    for (int k = 0; k < args.length; ++k) {
      if (args[k].startsWith("-o"))
        outputType = args[k].substring(2);
      else if (args[k].startsWith("-f"))
        compiledDesign = args[k].substring(2);
      else if (args[k].startsWith("-x"))
        selectCriteria = args[k].substring(2);
      else if (args[k].startsWith("-datePattern"))
        datePattern = args[k].substring(12);
      else if (args[k].startsWith("-numberPattern"))
        numberPattern = args[k].substring(14);
    }
    
    XmlJasperInterface jasperInterface = new XmlJasperInterface(outputType, compiledDesign, selectCriteria);
    if (!jasperInterface.report()) {
      System.exit(1);
    }
  }
  
  public XmlJasperInterface(
      String outputType,
      String compiledDesign,
      String selectCriteria) {
    this.outputType = outputType;
    this.compiledDesign = compiledDesign;
    this.selectCriteria = selectCriteria;
  }
  
  public boolean report() {
    try {
      JRXmlDataSource jrxmlDataSource = new JRXmlDataSource(System.in, selectCriteria);
      if (datePattern != null){
        jrxmlDataSource.setDatePattern(datePattern);
      }else{
        jrxmlDataSource.setDatePattern("yyyy-MM-dd");
      }
        jrxmlDataSource.setDatePattern("yyyy-MM-dd");
      if (numberPattern != null){
        jrxmlDataSource.setNumberPattern(numberPattern);
      }

      java.util.HashMap param = new java.util.HashMap();
      param.put("REPORT_LOCALE", new java.util.Locale("pt","BR"));

     // JasperPrint jasperPrint = JasperFillManager.fillReport(compiledDesign, null, jrxmlDataSource);
      JasperPrint jasperPrint = JasperFillManager.fillReport(compiledDesign, param, jrxmlDataSource);
      
      if (TYPE_PDF.equals(outputType)) {
        JasperExportManager.exportReportToPdfStream(jasperPrint, System.out);
      }
      else if (TYPE_XML.equals(outputType)) {
        JasperExportManager.exportReportToXmlStream(jasperPrint, System.out);
      }
      else if (TYPE_RTF.equals(outputType)) {
        JRRtfExporter rtfExporter = new JRRtfExporter();
        rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, System.out);
        rtfExporter.exportReport();
      }
      else if (TYPE_XLS.equals(outputType)) {
        JRXlsExporter xlsExporter = new JRXlsExporter();
        xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, System.out);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        xlsExporter.exportReport();
      }
      else if (TYPE_CSV.equals(outputType)) {
        JRCsvExporter csvExporter = new JRCsvExporter();
        csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        csvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, System.out);
        csvExporter.exportReport();
      } else {
        printUsage();
      }
    } catch (JRException e) {
      e.printStackTrace();
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  private static void printUsage() {
    System.out.println("XmlJasperInterface usage:");
    System.out.println("\tjava XmlJasperInterface -oOutputType -fCompiledDesign -xSelectExpression < input.xml > report\n");
    System.out.println("\tOutput types:\t\tpdf | xml | rtf | xls | csv");
    System.out.println("\tCompiled design:\tFilename of the compiled Jasper design");
    System.out.println("\tSelect expression:\tXPath expression that specifies the select criteria");
    System.out.println("\t\t\t\t(See net.sf.jasperreports.engine.data.JRXmlDataSource for further information)");
    System.out.println("\tStandard input:\t\tXML input data");
    System.out.println("\tStandard output:\tReport generated by Jasper");
  }
}
