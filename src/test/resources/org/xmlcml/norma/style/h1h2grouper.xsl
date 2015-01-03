<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns="http://www.w3.org/1999/xhtml"
    xpath-default-namespace="http://www.w3.org/1999/xhtml"
    version="2.0">

<!--  see http://stackoverflow.com/questions/27702940/creating-structured-documents-using-nested-xslt2-grouping -->

<xsl:output method="xhtml" indent="yes"/>

<xsl:template match="@* | node()">
  <xsl:copy>
    <xsl:apply-templates select="@* | node()"/>
  </xsl:copy>
</xsl:template>

<xsl:template match="body">
  <xsl:copy>
    <xsl:for-each-group select="*" group-starting-with="h1">
      <div class="h1">
        <xsl:apply-templates select="."/>
        <xsl:for-each-group select="current-group() except ." group-starting-with="h2">
          <xsl:choose>
            <xsl:when test="self::h2">
              <div class="h2">
                <xsl:apply-templates select="current-group()"/>
              </div>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="current-group()"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:for-each-group>
      </div>
    </xsl:for-each-group>
  </xsl:copy>
</xsl:template>

</xsl:stylesheet>