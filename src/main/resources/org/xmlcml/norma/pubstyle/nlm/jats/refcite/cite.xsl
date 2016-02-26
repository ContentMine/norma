<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="*[local-name()='mixed-citation']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>


	<xsl:template match="*[local-name()='element-citation']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
</xsl:stylesheet>