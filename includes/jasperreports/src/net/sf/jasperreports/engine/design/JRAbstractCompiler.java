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

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.fill.JREvaluator;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.util.JRStringUtil;

/**
 * Base class for report compilers.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRAbstractCompiler.java 1625 2007-03-09 17:21:31Z lucianc $
 */
public abstract class JRAbstractCompiler implements JRCompiler
{
	private static final int NAME_SUFFIX_RANDOM_MAX = 1000000;
	private static final Random random = new Random();
	
	private final boolean needsSourceFiles;

	/**
	 * Constructor.
	 * 
	 * @param needsSourceFiles whether the compiler needs source files or is able to do in memory compilation
	 * <p>
	 * If true, the generated code is saved in source files to be used by the compiler.
	 */
	protected JRAbstractCompiler(boolean needsSourceFiles)
	{
		this.needsSourceFiles = needsSourceFiles;
	}

	
	/**
	 * Returns the name of the expression evaluator unit for a dataset of a report.
	 * 
	 * @param report the report
	 * @param dataset the dataset
	 * @return the generated expression evaluator unit name
	 */
	public static String getUnitName(JasperReport report, JRDataset dataset)
	{
		return getUnitName(report, dataset, report.getCompileNameSuffix());
	}

	protected static String getUnitName(JRReport report, JRDataset dataset, String nameSuffix)
	{
		String className;
		if (dataset.isMainDataset())
		{
			className = report.getName();
		}
		else
		{
			className = report.getName() + "_" + dataset.getName();
		}
		
		className = JRStringUtil.getLiteral(className) + nameSuffix;
		
		return className;
	}
	
	/**
	 * Returns the name of the expression evaluator unit for a crosstab of a report.
	 * 
	 * @param report the report
	 * @param crosstab the crosstab
	 * @return the generated expression evaluator unit name
	 */
	public static String getUnitName(JasperReport report, JRCrosstab crosstab)
	{
		return getUnitName(report, crosstab.getId(), report.getCompileNameSuffix());
	}

	
	protected static String getUnitName(JRReport report, JRCrosstab crosstab, JRExpressionCollector expressionCollector, String nameSuffix)
	{
		Integer crosstabId = expressionCollector.getCrosstabId(crosstab);
		if (crosstabId == null)
		{
			throw new JRRuntimeException("Crosstab ID not found.");
		}
		
		return getUnitName(report, crosstabId.intValue(), nameSuffix);
	}

	protected static String getUnitName(JRReport report, int crosstabId, String nameSuffix)
	{
		return JRStringUtil.getLiteral(report.getName()) + "_CROSSTAB" + crosstabId + nameSuffix;
	}
	
	public final JasperReport compileReport(JasperDesign jasperDesign) throws JRException
	{
		// check if the language is supported by the compiler
		checkLanguage(jasperDesign.getLanguage());
		
		// collect all report expressions
		JRExpressionCollector expressionCollector = JRExpressionCollector.collector(jasperDesign);
		
		// verify the report design
		verifyDesign(jasperDesign, expressionCollector);

		String nameSuffix = createNameSuffix();
		
		// check if saving source files is required
		boolean isKeepJavaFile = JRProperties.getBooleanProperty(JRProperties.COMPILER_KEEP_JAVA_FILE);
		File tempDirFile = null;
		if (isKeepJavaFile || needsSourceFiles)
		{
			String tempDirStr = JRProperties.getProperty(JRProperties.COMPILER_TEMP_DIR);

			tempDirFile = new File(tempDirStr);
			if (!tempDirFile.exists() || !tempDirFile.isDirectory())
			{
				throw new JRException("Temporary directory not found : " + tempDirStr);
			}
		}

		List datasets = jasperDesign.getDatasetsList();
		List crosstabs = jasperDesign.getCrosstabs();
		
		JRCompilationUnit[] units = new JRCompilationUnit[datasets.size() + crosstabs.size() + 1];
		
		// generating source code for the main report dataset
		units[0] = createCompileUnit(jasperDesign, jasperDesign.getMainDesignDataset(), expressionCollector, tempDirFile, nameSuffix);

		int sourcesCount = 1;
		for (Iterator it = datasets.iterator(); it.hasNext(); ++sourcesCount)
		{
			JRDesignDataset dataset = (JRDesignDataset) it.next();
			// generating source code for a sub dataset
			units[sourcesCount] = createCompileUnit(jasperDesign, dataset, expressionCollector, tempDirFile, nameSuffix);
		}
		
		for (Iterator it = crosstabs.iterator(); it.hasNext(); ++sourcesCount)
		{
			JRDesignCrosstab crosstab = (JRDesignCrosstab) it.next();
			// generating source code for a sub dataset
			units[sourcesCount] = createCompileUnit(jasperDesign, crosstab, expressionCollector, tempDirFile, nameSuffix);
		}
		
		String classpath = JRProperties.getProperty(JRProperties.COMPILER_CLASSPATH);
		
		try
		{
			// compiling generated sources
			String compileErrors = compileUnits(units, classpath, tempDirFile);
			if (compileErrors != null)
			{
				throw new JRException("Errors were encountered when compiling report expressions class file:\n" + compileErrors);
			}

			// creating the report compile data
			JRReportCompileData reportCompileData = new JRReportCompileData();
			reportCompileData.setMainDatasetCompileData(units[0].getCompileData());
			
			for (ListIterator it = datasets.listIterator(); it.hasNext();)
			{
				JRDesignDataset dataset = (JRDesignDataset) it.next();
				reportCompileData.setDatasetCompileData(dataset, units[it.nextIndex()].getCompileData());
			}
			
			for (ListIterator it = crosstabs.listIterator(); it.hasNext();)
			{
				JRDesignCrosstab crosstab = (JRDesignCrosstab) it.next();
				Integer crosstabId = expressionCollector.getCrosstabId(crosstab);
				reportCompileData.setCrosstabCompileData(crosstabId.intValue(), units[datasets.size() + it.nextIndex()].getCompileData());
			}

			// creating the report
			JasperReport jasperReport = 
				new JasperReport(
					jasperDesign,
					getCompilerClass(),
					reportCompileData,
					expressionCollector,
					nameSuffix
					);
			
			return jasperReport;
		}
		catch (JRException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new JRException("Error compiling report design.", e);
		}
		finally
		{
			if (needsSourceFiles && !isKeepJavaFile)
			{
				deleteSourceFiles(units);
			}
		}
	}


	private static String createNameSuffix()
	{
		return "_" + System.currentTimeMillis() + "_" + random.nextInt(NAME_SUFFIX_RANDOM_MAX);
	}


	protected String getCompilerClass()
	{
		return getClass().getName();
	}

	
	private void verifyDesign(JasperDesign jasperDesign, JRExpressionCollector expressionCollector) throws JRException
	{
		Collection brokenRules = JRVerifier.verifyDesign(jasperDesign, expressionCollector);
		if (brokenRules != null && brokenRules.size() > 0)
		{
			throw new JRValidationException(brokenRules);
		}
	}
	
	private JRCompilationUnit createCompileUnit(JasperDesign jasperDesign, JRDesignDataset dataset, JRExpressionCollector expressionCollector, File saveSourceDir, String nameSuffix) throws JRException
	{		
		String unitName = JRAbstractCompiler.getUnitName(jasperDesign, dataset, nameSuffix);
		
		JRSourceCompileTask sourceTask = new JRSourceCompileTask(jasperDesign, dataset, expressionCollector, unitName);
		JRCompilationSourceCode sourceCode = generateSourceCode(sourceTask);
		
		File sourceFile = getSourceFile(saveSourceDir, unitName, sourceCode);

		return new JRCompilationUnit(unitName, sourceCode, sourceFile, expressionCollector.getExpressions(dataset));
	}
	
	private JRCompilationUnit createCompileUnit(JasperDesign jasperDesign, JRDesignCrosstab crosstab, JRExpressionCollector expressionCollector, File saveSourceDir, String nameSuffix) throws JRException
	{		
		String unitName = JRAbstractCompiler.getUnitName(jasperDesign, crosstab, expressionCollector, nameSuffix);
		
		JRSourceCompileTask sourceTask = new JRSourceCompileTask(jasperDesign, crosstab, expressionCollector, unitName);
		JRCompilationSourceCode sourceCode = generateSourceCode(sourceTask);
		
		File sourceFile = getSourceFile(saveSourceDir, unitName, sourceCode);

		return new JRCompilationUnit(unitName, sourceCode, sourceFile, expressionCollector.getExpressions(crosstab));
	}


	private File getSourceFile(File saveSourceDir, String unitName, JRCompilationSourceCode sourceCode) throws JRException
	{
		File sourceFile = null;
		if (saveSourceDir != null)
		{
			String fileName = getSourceFileName(unitName);
			sourceFile = new File(saveSourceDir,  fileName);

			JRSaver.saveClassSource(sourceCode.getCode(), sourceFile);
		}
		return sourceFile;
	}

	private void deleteSourceFiles(JRCompilationUnit[] units)
	{
		for (int i = 0; i < units.length; i++)
		{
			units[i].getSourceFile().delete();
		}
	}

	public JREvaluator loadEvaluator(JasperReport jasperReport) throws JRException
	{
		return loadEvaluator(jasperReport, jasperReport.getMainDataset());
	}

	public JREvaluator loadEvaluator(JasperReport jasperReport, JRDataset dataset) throws JRException
	{
		String unitName = JRAbstractCompiler.getUnitName(jasperReport, dataset);
		JRReportCompileData reportCompileData = (JRReportCompileData) jasperReport.getCompileData();
		Serializable compileData = reportCompileData.getDatasetCompileData(dataset);
		return loadEvaluator(compileData, unitName);
	}

	public JREvaluator loadEvaluator(JasperReport jasperReport, JRCrosstab crosstab) throws JRException
	{
		String unitName = JRAbstractCompiler.getUnitName(jasperReport, crosstab);
		JRReportCompileData reportCompileData = (JRReportCompileData) jasperReport.getCompileData();
		Serializable compileData = reportCompileData.getCrosstabCompileData(crosstab);
		return loadEvaluator(compileData, unitName);
	}

	
	/**
	 * Creates an expression evaluator instance from data saved when the report was compiled.
	 * 
	 * @param compileData the data saved when the report was compiled
	 * @param unitName the evaluator unit name
	 * @return an expression evaluator instance
	 * @throws JRException
	 */
	protected abstract JREvaluator loadEvaluator(Serializable compileData, String unitName) throws JRException;

	
	/**
	 * Checks that the report language is supported by the compiler.
	 * 
	 * @param language the report language
	 * @throws JRException
	 */
	protected abstract void checkLanguage(String language) throws JRException;

	
	/**
	 * Generates expression evaluator code.
	 *
	 * @param sourceTask the source code generation information
	 * @return generated expression evaluator code
	 * @throws JRException
	 */
	protected abstract JRCompilationSourceCode generateSourceCode(JRSourceCompileTask sourceTask) throws JRException;

	
	/**
	 * Compiles several expression evaluator units.
	 * <p>
	 * The result of the compilation should be set by calling 
	 * {@link JRCompilationUnit#setCompileData(Serializable) setCompileData} on all compile units.
	 * 
	 * @param units the compilation units
	 * @param classpath the compilation classpath
	 * @param tempDirFile temporary directory
	 * @return a string containing compilation errors, or null if the compilation was successfull
	 * @throws JRException
	 */
	protected abstract String compileUnits(JRCompilationUnit[] units, String classpath, File tempDirFile) throws JRException;

	
	/**
	 * Returns the name of the source file where generated source code for an unit is saved.
	 * <p>
	 * If the compiler needs source files for compilation
	 * or {@link JRProperties#COMPILER_KEEP_JAVA_FILE COMPILER_KEEP_JAVA_FILE} is set, the generated source
	 * will be saved in a file having the name returned by this method.
	 * 
	 * @param unitName the unit name
	 * @return the source file name
	 */
	protected abstract String getSourceFileName(String unitName);
}
