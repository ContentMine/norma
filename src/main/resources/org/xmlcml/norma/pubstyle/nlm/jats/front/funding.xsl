<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="*[local-name()='funding-group']">
	  <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='funding-statement']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='funding-source']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	

</xsl:stylesheet>