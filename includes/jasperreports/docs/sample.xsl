<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:jr="http://jasperreports.sourceforge.net/jasperreports">

<xsl:output method="html"/>
<xsl:param name="sf.net"/>
<xsl:param name="version"/>
<xsl:param name="svn"/>

<xsl:variable name="api.url">
 <xsl:choose>
  <xsl:when test="$sf.net = 'true'">../../api/</xsl:when>
  <xsl:otherwise>http://jasperreports.sourceforge.net/api/</xsl:otherwise>
 </xsl:choose>
</xsl:variable>

<xsl:template match="/">
<html>
<head>
<title>JasperReports <xsl:value-of select="$version"/> - <xsl:value-of select="sample/title"/></title>
<style type="text/css">
.title {
	font-family: Arial, Verdana, Helvetica, sans-serif;
	font-size: 28px;
	font-weight: normal;
}

.toc {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: normal;
}

.name {
	font-family: Courier New, Courier, serif;
	font-size: 16px;
	font-weight: bold;
}

.label {
	font-family: Arial, Verdana, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	font-style: italic;
}

.description {
	font-family: Arial, Verdana, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: normal;
}

.value {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: normal;
}

.element {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: normal;
}

.attribute {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: bold;
}

.code {
	font-family: Courier New, Courier, serif;
	font-size: 12px;
	font-weight: normal;
}
</style>
</head>
<body bgcolor="#FFFFFF">
<xsl:if test="$sf.net = 'true'">
<!-- Piwik -->
<script type="text/javascript">
var pkBaseURL = (("https:" == document.location.protocol) ? "https://sourceforge.net/apps/piwik/jasperreports/" : "http://sourceforge.net/apps/piwik/jasperreports/");
document.write(unescape("%3Cscript src='" + pkBaseURL + "piwik.js' type='text/javascript'%3E%3C/script%3E"));
</script><script type="text/javascript">
piwik_action_name = '';
piwik_idsite = 1;
piwik_url = pkBaseURL + "piwik.php";
piwik_log(piwik_action_name, piwik_idsite, piwik_url);
</script>
<object><noscript><p><img src="http://sourceforge.net/apps/piwik/jasperreports/piwik.php?idsite=1" alt="piwik"/></p></noscript></object>
<!-- End Piwik Tag -->
</xsl:if>

<a name="top"/>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
  <tr>
    <td colspan="2" align="right">
<span class="element"><xsl:element name="a">
<xsl:attribute name="href">../../sample.reference.html</xsl:attribute>Sample Reference</xsl:element> - <xsl:element name="a">
<xsl:attribute name="href">../../schema.reference.html</xsl:attribute>Schema Reference</xsl:element> - <xsl:element name="a">
<xsl:attribute name="href">../../config.reference.html</xsl:attribute>Configuration Reference</xsl:element> - <xsl:element name="a">
<xsl:attribute name="href"><xsl:value-of select="$api.url"/>index.html</xsl:attribute>API (Javadoc)</xsl:element></span>
<br/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <hr size="1"/>
    </td>
  </tr>
  <tr valign="middle">
    <td nowrap="true">
<span class="title">JasperReports - <xsl:value-of select="sample/title"/><xsl:if test="$version != ''"> (version <xsl:value-of select="$version"/>)</xsl:if></span>
    </td>
    <td align="right">
<img src="../../resources/jasperreports.png" border="0"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <hr size="1"/>
    </td>
  </tr>
</table>

<br/>

<span class="description"><xsl:apply-templates select="sample/description"/></span>
<br/>
<br/>
<span class="element"><xsl:element name="a"><xsl:attribute name="href">http://sourceforge.net/projects/jasperreports/files/jasperreports/JasperReports%20<xsl:value-of select="$version"/>/jasperreports-<xsl:value-of select="$version"/>-project.zip/download</xsl:attribute><xsl:attribute name="target">_blank</xsl:attribute>Download All Sample Source Files</xsl:element></span>
<br/>
<span class="element"><xsl:element name="a"><xsl:attribute name="href">http://jasperforge.org/scm/viewvc.php/tags/<xsl:value-of select="$svn"/>/jasperreports/demo/samples/<xsl:value-of select="sample/name"/>/?root=jasperreports</xsl:attribute><xsl:attribute name="target">_blank</xsl:attribute>Browse Sample Source Files on SVN</xsl:element></span>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td style="width: 20px;"><br/></td>
    <td><br/></td>
  </tr>
  <xsl:if test="count(sample/mainFeature) > 0">
  <tr>
    <td colspan="2">
      <span class="label">Main Features in This Sample</span>
    </td>
  </tr>
  <xsl:for-each select="sample/mainFeature">
    <xsl:apply-templates select="."/>
  </xsl:for-each>
  </xsl:if>
  <xsl:if test="count(sample/secondaryFeature) > 0">
  <tr>
    <td colspan="2"><br/></td>
  </tr>
  <tr>
    <td colspan="2">
      <span class="label">Secondary Features</span>
    </td>
  </tr>
  <xsl:for-each select="sample/secondaryFeature">
    <xsl:apply-templates select="."/>
  </xsl:for-each>
  </xsl:if>
</table>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
  <tr>
    <td><img src="../../resources/px.gif" border="0" width="20" height="1"/></td>
    <td><img src="../../resources/px.gif" border="0" width="20" height="1"/></td>
    <td><img src="../../resources/px.gif" border="0" width="20" height="1"/></td>
    <td><img src="../../resources/px.gif" border="0" width="20" height="1"/></td>
    <td width="100%"><br/></td>
  </tr>
  <xsl:for-each select="sample/feature">
  <xsl:sort select="@title"/>
  <tr>
    <td colspan="5" align="right"><xsl:element name="a"><xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute></xsl:element><a href="#top" class="toc">top</a></td>
  </tr>
  <tr>
    <td colspan="5"><hr size="1"/></td>
  </tr>
  <tr valign="top">
    <td><img src="../../resources/jr-16x16.png" border="0"/></td>
    <td colspan="4"><span class="name"><xsl:value-of select="@title"/></span></td>
  </tr>
  <tr>
    <td colspan="5"><br/></td>
  </tr>
  <tr valign="top">
    <td><br/></td>
    <td nowrap="true"><span class="label">Description / Goal</span></td>
    <td><br/></td>
    <td colspan="2"><xsl:apply-templates select="description"/></td>
  </tr>
  <tr valign="top">
    <td><br/></td>
    <td colspan="1"><span class="label">Since</span></td>
    <td><br/></td>
    <td colspan="2"><span class="description"><xsl:value-of select="since"/></span></td>
  </tr>
  <xsl:if test="count(otherSample) > 0">
  <tr valign="top">
    <td><br/></td>
    <td colspan="1" nowrap="true"><span class="label">Other Samples</span></td>
    <td><br/></td>
    <td colspan="2">
      <table width="100%" cellspacing="0" cellpadding="0" border="0">
<xsl:for-each select="otherSample">
        <tr>
          <td><xsl:apply-templates select="."/></td>
        </tr>
</xsl:for-each>
      </table>
    </td>
  </tr>
  </xsl:if>
  <tr>
    <td colspan="5"><br/></td>
  </tr>
  <tr>
    <td><br/></td>
    <td colspan="4"><xsl:apply-templates select="content"/></td>
  </tr>
  <tr>
    <td colspan="5"><br/></td>
  </tr>
  </xsl:for-each>
</table>

<br/>

<table cellspacing="0" cellpadding="0" border="0" width="100%">
  <tr>
    <td><hr size="1"/></td>
  </tr>
  <tr>
    <td align="center">
<span style="font-decoration:none;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:8pt;font-style:normal;color:#000000;">&#169; 2001-2010 Jaspersoft Corporation <a href="http://www.jaspersoft.com" target="_blank" style="color:#000000;">www.jaspersoft.com</a></span>
	</td>
  </tr>
</table>

</body>
</html>
</xsl:template>


<xsl:template match="content">
<span class="description"><xsl:apply-templates/></span>
</xsl:template>


<xsl:template match="description">
<span class="description"><xsl:apply-templates/></span>
</xsl:template>


<xsl:template match="mainFeature">
  <xsl:variable name="ref" select="@ref"/>
  <tr>
    <td><br/></td>
    <td>
    <xsl:for-each select="/sample/feature">
      <xsl:if test="@name=$ref">
    <span class="element"><xsl:element name="a"><xsl:attribute name="href">#<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@title"/></xsl:element></span>
	    </xsl:if>
    </xsl:for-each>
    </td>
  </tr>
</xsl:template>


<xsl:template match="secondaryFeature">
  <tr>
    <td></td>
    <td>
      <span class="element"><xsl:element name="a"><xsl:attribute name="href">../<xsl:value-of select="@sample"/>/index.html#<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@title"/></xsl:element></span>
    </td>
  </tr>
</xsl:template>


<xsl:template match="otherSample">
  <span class="element"><xsl:element name="a"><xsl:attribute name="href">../<xsl:value-of select="@ref"/>/index.html</xsl:attribute><xsl:value-of select="concat('/demo/samples/', @ref)"/></xsl:element></span>
</xsl:template>


<!--
<xsl:template match="*" mode="copy">
  <span class="description"><xsl:copy-of select="."/></span>
</xsl:template>


<xsl:template match="text()">
  <span class="description"><xsl:value-of select="."/></span>
</xsl:template>


<xsl:template match="p/text()">
  <p><span class="description"><xsl:value-of select="." disable-output-escaping="yes" /></span></p>
</xsl:template>
-->


<xsl:template match="p/text()">
<p>
<xsl:value-of select="." disable-output-escaping="yes"/>
</p>
</xsl:template>


<xsl:template match="br">
<br/>
</xsl:template>


<xsl:template match="a">
<span class="element"><xsl:element name="a"><xsl:attribute name="href"><xsl:value-of select="./@href"/></xsl:attribute><xsl:attribute name="target">_blank</xsl:attribute><xsl:value-of select="."/></xsl:element></span>
</xsl:template>


<xsl:template match="elem">
  <span class="element"><xsl:element name="a"><xsl:attribute name="href">../../schema.reference.html#<xsl:value-of select="."/></xsl:attribute>&lt;<xsl:value-of select="."/>&gt;</xsl:element></span>
</xsl:template>


<xsl:template match="api">
<span class="element"><xsl:element name="a"><xsl:attribute name="href"><xsl:value-of select="$api.url"/><xsl:value-of select="./@href"/></xsl:attribute><xsl:value-of select="."/></xsl:element></span>
</xsl:template>


<xsl:template match="sample">
<span class="element"><xsl:element name="a"><xsl:attribute name="href">../<xsl:value-of select="text()"/>/index.html</xsl:attribute>/demo/samples/<xsl:value-of select="."/></xsl:element></span>
</xsl:template>


<xsl:template match="code">
<xsl:element name="code"><xsl:apply-templates/></xsl:element>
</xsl:template>


<xsl:template match="pre">
<xsl:element name="pre"><xsl:apply-templates/></xsl:element>
</xsl:template>


<xsl:template match="ul">
  <xsl:element name="ul"><xsl:apply-templates/></xsl:element>
</xsl:template>


<xsl:template match="ol">
  <xsl:element name="ol"><xsl:apply-templates/></xsl:element>
</xsl:template>


<xsl:template match="li">
  <xsl:element name="li"><xsl:apply-templates/></xsl:element>
</xsl:template>


</xsl:stylesheet>
