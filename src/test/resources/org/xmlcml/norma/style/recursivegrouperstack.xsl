<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:mf="http://example.com/mf"
    exclude-result-prefixes="xs mf"
    version="2.0">

<xsl:param name="prefix" as="xs:string" select="'R'"/>

<xsl:output indent="yes"/>

<xsl:function name="mf:group" as="element()*">
  <xsl:param name="elements" as="element()*"/>
  <xsl:param name="level" as="xs:integer"/>
  <xsl:for-each-group select="$elements" group-starting-with="*[local-name() = concat($prefix, $level)]">
    <xsl:element name="{name()}">
      <xsl:sequence select="mf:group(current-group() except ., $level + 1)"/>
    </xsl:element>
  </xsl:for-each-group>
</xsl:function>

<xsl:template match="body">
  <xsl:copy>
    <xsl:sequence select="mf:group(*, 1)"/>
  </xsl:copy>
</xsl:template>

</xsl:stylesheet>