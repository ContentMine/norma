<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="*[local-name()='permissions']">
	  <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='copyright-year']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='copyright-holder']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='copyright-statement']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='license']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='license-p']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	

</xsl:stylesheet>