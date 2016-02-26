<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="*[local-name()='alternatives']">
	   <xsl:call-template name="container"/>
	</xsl:template>

	<xsl:template match="*[local-name()='graphic']">
	   <xsl:call-template name="container"/>
	</xsl:template>

	<xsl:template match="*[local-name()='inline-graphic']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
    <xsl:include href="figure.xsl"/>
    <xsl:include href="table.xsl"/>

</xsl:stylesheet>