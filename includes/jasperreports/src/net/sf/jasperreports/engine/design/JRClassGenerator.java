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
 * Gaganis Giorgos - gaganis@users.sourceforge.net
 * Peter Severin - peter_p_s@users.sourceforge.net
 */
package net.sf.jasperreports.engine.design;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.util.JRStringUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRClassGenerator.java 2048 2007-12-19 14:18:21Z lucianc $
 */
public class JRClassGenerator
{
	
	
	/**
	 *
	 */
	private static final int EXPR_MAX_COUNT_PER_METHOD = 100;

	protected static final String SOURCE_EXPRESSION_ID_START = "$JR_EXPR_ID=";
	protected static final int sOURCE_EXPRESSION_ID_START_LENGTH = SOURCE_EXPRESSION_ID_START.length();
	protected static final String SOURCE_EXPRESSION_ID_END = "$";

	private static Map fieldPrefixMap = null;
	private static Map variablePrefixMap = null;
	private static Map methodSuffixMap = null;
	
	static
	{
		fieldPrefixMap = new HashMap();
		fieldPrefixMap.put(new Byte(JRExpression.EVALUATION_OLD),       "Old");
		fieldPrefixMap.put(new Byte(JRExpression.EVALUATION_ESTIMATED), "");
		fieldPrefixMap.put(new Byte(JRExpression.EVALUATION_DEFAULT),   "");
		
		variablePrefixMap = new HashMap();
		variablePrefixMap.put(new Byte(JRExpression.EVALUATION_OLD),       "Old");
		variablePrefixMap.put(new Byte(JRExpression.EVALUATION_ESTIMATED), "Estimated");
		variablePrefixMap.put(new Byte(JRExpression.EVALUATION_DEFAULT),   "");
		
		methodSuffixMap = new HashMap();
		methodSuffixMap.put(new Byte(JRExpression.EVALUATION_OLD),       "Old");
		methodSuffixMap.put(new Byte(JRExpression.EVALUATION_ESTIMATED), "Estimated");
		methodSuffixMap.put(new Byte(JRExpression.EVALUATION_DEFAULT),   "");		
	}

	protected final JRSourceCompileTask sourceTask;
	
	protected Map parametersMap;
	protected Map fieldsMap;
	protected Map variablesMap;
	protected JRVariable[] variables;
	
	protected JRClassGenerator(JRSourceCompileTask sourceTask)
	{
		this.sourceTask = sourceTask;
		
		parametersMap = sourceTask.getParametersMap();
		fieldsMap = sourceTask.getFieldsMap();
		variablesMap = sourceTask.getVariablesMap();
		variables = sourceTask.getVariables();
	}

	
	/**
	 * Generates Java source code for evaluating the expressions of a report/dataset/crosstab.
	 *
	 * @param sourceTask the source task containing data required to generate the source file
	 * @return the source code
	 * @throws JRException
	 */
	public static JRCompilationSourceCode generateClass(JRSourceCompileTask sourceTask) throws JRException
	{
		JRClassGenerator generator = new JRClassGenerator(sourceTask);
		return generator.generateClass();
	}
	

	protected JRCompilationSourceCode generateClass() throws JRException
	{
		StringBuffer sb = new StringBuffer();

		generateClassStart(sb);

		generateDeclarations(sb);

		generateInitMethod(sb);
		generateInitParamsMethod(sb);
		if (fieldsMap != null)
		{
			generateInitFieldsMethod(sb);
		}
		generateInitVarsMethod(sb);

		List expressions = sourceTask.getExpressions();
		sb.append(generateMethod(JRExpression.EVALUATION_DEFAULT, expressions));
		if (sourceTask.isOnlyDefaultEvaluation())
		{
			List empty = new ArrayList();
			sb.append(generateMethod(JRExpression.EVALUATION_OLD, empty));
			sb.append(generateMethod(JRExpression.EVALUATION_ESTIMATED, empty));
		}
		else
		{
			sb.append(generateMethod(JRExpression.EVALUATION_OLD, expressions));
			sb.append(generateMethod(JRExpression.EVALUATION_ESTIMATED, expressions));
		}
		
		sb.append("}\n");

		String code = sb.toString();
		JRExpression[] lineExpressions = parseSourceLines(code);
		return new JRDefaultCompilationSourceCode(code, lineExpressions);
	}


	private void generateInitMethod(StringBuffer sb)
	{
		sb.append("\n");
		sb.append("\n");
		sb.append("    /**\n");
		sb.append("     *\n");
		sb.append("     */\n");
		sb.append("    public void customizedInit(\n"); 
		sb.append("        Map pm,\n");
		sb.append("        Map fm,\n"); 
		sb.append("        Map vm\n");
		sb.append("        )\n");
		sb.append("    {\n");
		sb.append("        initParams(pm);\n");
		if (fieldsMap != null)
		{
			sb.append("        initFields(fm);\n");
		}
		sb.append("        initVars(vm);\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("\n");
	}


	protected final void generateClassStart(StringBuffer sb)
	{
		sb.append("/*\n");
		sb.append(" * Generated by JasperReports - ");
		sb.append((new SimpleDateFormat()).format(new java.util.Date()));
		sb.append("\n");
		sb.append(" */\n");
		sb.append("import net.sf.jasperreports.engine.*;\n");
		sb.append("import net.sf.jasperreports.engine.fill.*;\n");
		sb.append("\n");
		sb.append("import java.util.*;\n");
		sb.append("import java.math.*;\n");
		sb.append("import java.text.*;\n");
		sb.append("import java.io.*;\n");
		sb.append("import java.net.*;\n");
		sb.append("\n");
		
		/*   */
		String[] imports = sourceTask.getImports();
		if (imports != null && imports.length > 0)
		{
			for (int i = 0; i < imports.length; i++)
			{
				sb.append("import ");
				sb.append(imports[i]);
				sb.append(";\n");
			}
		}

		/*   */
		sb.append("\n");
		sb.append("\n");
		sb.append("/**\n");
		sb.append(" *\n");
		sb.append(" */\n");
		sb.append("public class ");
		sb.append(sourceTask.getUnitName());
		sb.append(" extends JREvaluator\n");
		sb.append("{\n"); 
		sb.append("\n");
		sb.append("\n");
		sb.append("    /**\n");
		sb.append("     *\n");
		sb.append("     */\n");
	}


	protected final void generateDeclarations(StringBuffer sb)
	{
		if (parametersMap != null && parametersMap.size() > 0)
		{
			Collection parameterNames = parametersMap.keySet();
			for (Iterator it = parameterNames.iterator(); it.hasNext();)
			{
				sb.append("    private JRFillParameter parameter_");
				sb.append(JRStringUtil.getLiteral((String)it.next()));
				sb.append(" = null;\n");
			}
		}
		
		if (fieldsMap != null && fieldsMap.size() > 0)
		{
			Collection fieldNames = fieldsMap.keySet();
			for (Iterator it = fieldNames.iterator(); it.hasNext();)
			{
				sb.append("    private JRFillField field_");
				sb.append(JRStringUtil.getLiteral((String)it.next()));
				sb.append(" = null;\n");
			}
		}
		
		if (variables != null && variables.length > 0)
		{
			for (int i = 0; i < variables.length; i++)
			{
				sb.append("    private JRFillVariable variable_");
				sb.append(JRStringUtil.getLiteral(variables[i].getName()));
				sb.append(" = null;\n");
			}
		}
	}


	protected final void generateInitParamsMethod(StringBuffer sb) throws JRException
	{
		Iterator parIt = null;
		if (parametersMap != null && parametersMap.size() > 0) 
		{
			parIt = parametersMap.keySet().iterator();
		}
		else
		{
			parIt = Collections.EMPTY_SET.iterator();
		}
		generateInitParamsMethod(sb, parIt, 0);
	}		


	protected final void generateInitFieldsMethod(StringBuffer sb) throws JRException
	{
		Iterator fieldIt = null;
		if (fieldsMap != null && fieldsMap.size() > 0) 
		{
			fieldIt = fieldsMap.keySet().iterator();
		}
		else
		{
			fieldIt = Collections.EMPTY_SET.iterator();
		}
		generateInitFieldsMethod(sb, fieldIt, 0);
	}


	protected final void generateInitVarsMethod(StringBuffer sb) throws JRException
	{
		Iterator varIt = null;
		if (variables != null && variables.length > 0) 
		{
			varIt = Arrays.asList(variables).iterator();
		}
		else
		{
			varIt = Collections.EMPTY_LIST.iterator();
		}
		generateInitVarsMethod(sb, varIt, 0);
	}


	/**
	 *
	 */
	private void generateInitParamsMethod(StringBuffer sb, Iterator it, int index) throws JRException
	{
		sb.append("    /**\n");
		sb.append("     *\n");
		sb.append("     */\n");
		sb.append("    private void initParams");
		if(index > 0)
		{
			sb.append(index);
		}
		sb.append("(Map pm)\n");
		sb.append("    {\n");
		for (int i = 0; i < EXPR_MAX_COUNT_PER_METHOD && it.hasNext(); i++)
		{
			String parameterName = (String)it.next();
			sb.append("        parameter_");
			sb.append(JRStringUtil.getLiteral(parameterName));
			sb.append(" = (JRFillParameter)pm.get(\"");
			sb.append(parameterName);
			sb.append("\");\n");
		}
		if(it.hasNext())
		{
			sb.append("        initParams");
			sb.append(index + 1);
			sb.append("(pm);\n");
		}
		sb.append("    }\n");
		sb.append("\n");
		sb.append("\n");

		if(it.hasNext())
		{
			generateInitParamsMethod(sb, it, index + 1);
		}
	}		


	/**
	 *
	 */
	private void generateInitFieldsMethod(StringBuffer sb, Iterator it, int index) throws JRException
	{
		sb.append("    /**\n");
		sb.append("     *\n");
		sb.append("     */\n");
		sb.append("    private void initFields");
		if(index > 0)
		{
			sb.append(index);
		}
		sb.append("(Map fm)\n");
		sb.append("    {\n");
		for (int i = 0; i < EXPR_MAX_COUNT_PER_METHOD && it.hasNext(); i++)
		{
			String fieldName = (String)it.next();
			sb.append("        field_");
			sb.append(JRStringUtil.getLiteral(fieldName));
			sb.append(" = (JRFillField)fm.get(\"");
			sb.append(fieldName);
			sb.append("\");\n");
		}
		if(it.hasNext())
		{
			sb.append("        initFields");
			sb.append(index + 1);
			sb.append("(fm);\n");
		}
		sb.append("    }\n");
		sb.append("\n");
		sb.append("\n");

		if(it.hasNext())
		{
			generateInitFieldsMethod(sb, it, index + 1);
		}
	}		


	/**
	 *
	 */
	private void generateInitVarsMethod(StringBuffer sb, Iterator it, int index) throws JRException
	{
		sb.append("    /**\n");
		sb.append("     *\n");
		sb.append("     */\n");
		sb.append("    private void initVars");
		if(index > 0)
		{
			sb.append(index);
		}
		sb.append("(Map vm)\n");
		sb.append("    {\n");
		for (int i = 0; i < EXPR_MAX_COUNT_PER_METHOD && it.hasNext(); i++)
		{
			String variableName = ((JRVariable) it.next()).getName();
			sb.append("        variable_");
			sb.append(JRStringUtil.getLiteral(variableName));
			sb.append(" = (JRFillVariable)vm.get(\"");
			sb.append(variableName);
			sb.append("\");\n");
		}
		if(it.hasNext())
		{
			sb.append("        initVars");
			sb.append(index + 1);
			sb.append("(vm);\n");
		}
		sb.append("    }\n");
		sb.append("\n");
		sb.append("\n");

		if(it.hasNext())
		{
			generateInitVarsMethod(sb, it, index + 1);
		}
	}		


	protected final String generateMethod(byte evaluationType, List expressionsList) throws JRException
	{
		StringBuffer sb = new StringBuffer();

		if (expressionsList.size() > 0)
		{
			sb.append(generateMethod(expressionsList.listIterator(), 0, evaluationType));
		}
		else
		{
			/*   */
			sb.append("    /**\n");
			sb.append("     *\n");
			sb.append("     */\n");
			sb.append("    public Object evaluate");
			sb.append((String)methodSuffixMap.get(new Byte(evaluationType)));
			sb.append("(int id) throws Throwable\n");
			sb.append("    {\n");
			sb.append("        return null;\n");
			sb.append("    }\n");
			sb.append("\n");
			sb.append("\n");
		}
		
		return sb.toString();
	}


	/**
	 *
	 */
	private String generateMethod(Iterator it, int index, byte evaluationType) throws JRException
	{
		StringBuffer sb = new StringBuffer();

		/*   */
		sb.append("    /**\n");
		sb.append("     *\n");
		sb.append("     */\n");
		if (index > 0)
		{
			sb.append("    private Object evaluate");
			sb.append((String)methodSuffixMap.get(new Byte(evaluationType)));
			sb.append(index);
		}
		else
		{
			sb.append("    public Object evaluate");
			sb.append((String)methodSuffixMap.get(new Byte(evaluationType)));
		}
		sb.append("(int id) throws Throwable\n");
		sb.append("    {\n");
		sb.append("        Object value = null;\n");
		sb.append("\n");
		sb.append("        switch (id)\n");
		sb.append("        {\n");

		for (int i = 0; it.hasNext() && i < EXPR_MAX_COUNT_PER_METHOD; i++)
		{
			JRExpression expression = (JRExpression)it.next();
			
			sb.append("            case "); 
			sb.append(sourceTask.getExpressionId(expression)); 
			sb.append(" : \n");
			sb.append("            {\n");
			sb.append("                value = (");
			sb.append(expression.getValueClassName());
			sb.append(")(");
			sb.append(this.generateExpression(expression, evaluationType));
			sb.append(");");
			appendExpressionComment(sb, expression);
			sb.append("\n");
			sb.append("                break;\n");
			sb.append("            }\n");
		}

		/*   */
		sb.append("           default :\n");
		sb.append("           {\n");
		if (it.hasNext())
		{
			sb.append("               value = evaluate");
			sb.append((String) methodSuffixMap.get(new Byte(evaluationType)));
			sb.append(index + 1);
			sb.append("(id);\n");
		}
		sb.append("           }\n");
		sb.append("        }\n");
		sb.append("        \n");
		sb.append("        return value;\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("\n");
		
		if (it.hasNext())
		{
			sb.append(generateMethod(it, index + 1, evaluationType));
		}
		
		return sb.toString();
	}


	/**
	 *
	 */
	private String generateExpression(
		JRExpression expression,
		byte evaluationType
		)
	{
		JRParameter jrParameter = null;
		JRField jrField = null;
		JRVariable jrVariable = null;

		StringBuffer sb = new StringBuffer();

		JRExpressionChunk[] chunks = expression.getChunks();
		JRExpressionChunk chunk = null;
		String chunkText = null;
		if (chunks != null && chunks.length > 0)
		{
			for(int i = 0; i < chunks.length; i++)
			{
				chunk = chunks[i];

				chunkText = chunk.getText();
				if (chunkText == null)
				{
					chunkText = "";
				}
				
				switch (chunk.getType())
				{
					case JRExpressionChunk.TYPE_TEXT :
					{
						appendExpressionText(expression, sb, chunkText);
						break;
					}
					case JRExpressionChunk.TYPE_PARAMETER :
					{
						jrParameter = (JRParameter)parametersMap.get(chunkText);
	
						sb.append("((");
						sb.append(jrParameter.getValueClassName());
						sb.append(")parameter_");
						sb.append(JRStringUtil.getLiteral(chunkText));
						sb.append(".getValue())");
	
						break;
					}
					case JRExpressionChunk.TYPE_FIELD :
					{
						jrField = (JRField)fieldsMap.get(chunkText);
	
						sb.append("((");
						sb.append(jrField.getValueClassName());
						sb.append(")field_");
						sb.append(JRStringUtil.getLiteral(chunkText)); 
						sb.append(".get");
						sb.append((String)fieldPrefixMap.get(new Byte(evaluationType))); 
						sb.append("Value())");
	
						break;
					}
					case JRExpressionChunk.TYPE_VARIABLE :
					{
						jrVariable = (JRVariable)variablesMap.get(chunkText);
	
						sb.append("((");
						sb.append(jrVariable.getValueClassName());
						sb.append(")variable_"); 
						sb.append(JRStringUtil.getLiteral(chunkText)); 
						sb.append(".get");
						sb.append((String)variablePrefixMap.get(new Byte(evaluationType))); 
						sb.append("Value())");
	
						break;
					}
					case JRExpressionChunk.TYPE_RESOURCE :
					{
						sb.append("str(\"");
						sb.append(chunkText);
						sb.append("\")");
	
						break;
					}
				}
			}
		}
		
		if (sb.length() == 0)
		{
			sb.append("null");
		}

		return sb.toString();
	}


	protected void appendExpressionText(JRExpression expression, StringBuffer sb, String chunkText)
	{
		for (StringTokenizer tokenizer = new StringTokenizer(chunkText, "\n", true);
				tokenizer.hasMoreTokens();)
		{
			String token = tokenizer.nextToken();
			if (token.equals("\n"))
			{
				appendExpressionComment(sb, expression);
			}
			sb.append(token);
		}
	}

	protected void appendExpressionComment(StringBuffer sb, JRExpression expression)
	{
		sb.append("//");
		sb.append(SOURCE_EXPRESSION_ID_START);
		sb.append(sourceTask.getExpressionId(expression));
		sb.append(SOURCE_EXPRESSION_ID_END);
	}
	
	protected JRExpression[] parseSourceLines(String sourceCode)
	{
		List expressions = new ArrayList();
		int start = 0;
		int end = sourceCode.indexOf('\n');
		while (end >= 0)
		{
			JRExpression expression = null;
			if (start < end)
			{
				String line = sourceCode.substring(start, end);
				expression = getLineExpression(line);
			}
			expressions.add(expression);
			
			start = end + 1;
			end = sourceCode.indexOf('\n', start);
		}
		return (JRExpression[]) expressions.toArray(new JRExpression[expressions.size()]);
	}


	protected JRExpression getLineExpression(String line)
	{
		JRExpression expression = null;
		int exprIdStart = line.indexOf(SOURCE_EXPRESSION_ID_START);
		if (exprIdStart >= 0)
		{
			exprIdStart += sOURCE_EXPRESSION_ID_START_LENGTH;
			int exprIdEnd = line.indexOf("$", exprIdStart);
			if (exprIdEnd >= 0)
			{
				try
				{
					int exprId = Integer.parseInt(line.substring(exprIdStart, exprIdEnd));
					expression = sourceTask.getExpression(exprId);
				}
				catch (NumberFormatException e)
				{
					// ignore
				}							
			}
		}
		return expression;
	}
	
}
