<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="*[local-name()='ref']">
	    <xsl:call-template name="container"/>
	</xsl:template>
	
</xsl:stylesheet>