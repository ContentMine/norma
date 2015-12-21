<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="*[local-name()='journal-meta']">
	    <xsl:call-template name="container"/>
	</xsl:template>
	
		
	<xsl:template match="*[local-name()='journal-id']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='journal-title-group']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
		<xsl:template match="*[local-name()='journal-title']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
		<xsl:template match="*[local-name()='issn']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='publisher']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='publisher-name']">
	   <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='publisher-loc']">
	   <xsl:call-template name="debug"/>
	</xsl:template>


</xsl:stylesheet>