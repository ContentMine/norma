<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output omit-xml-declaration="yes" indent="yes"/>
    <xsl:output method="text"/>
    <xsl:strip-space elements="*"/>

    <xsl:template match="*">
        <xsl:apply-templates select="node()|@*"/>
        <xsl:text> </xsl:text>
    </xsl:template>

<!-- 
    <xsl:template match="username">
       at -f <xsl:apply-templates select="*|@*"/>
    </xsl:template>
-->    
</xsl:stylesheet>