<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="*[local-name()='pub-date']">
	  <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='year']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='month']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='day']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	

</xsl:stylesheet>