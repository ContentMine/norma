<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- tables -->

	<xsl:template match="*[local-name()='table']">
	   <xsl:call-template name="apply"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='tr']">
	   <xsl:call-template name="apply"/>
	</xsl:template>

	<xsl:template match="*[local-name()='td']">
	   <xsl:call-template name="apply"/>
	</xsl:template>

	<xsl:template match="*[local-name()='table-wrap-foot']">
	   <xsl:call-template name="apply"/>
	</xsl:template>

	<xsl:template match="*[local-name()='tbody']">
	   <xsl:call-template name="apply"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='th']">
	   <xsl:call-template name="apply"/>
	</xsl:template>

	<xsl:template match="*[local-name()='colgroup']">
	   <xsl:call-template name="apply"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='col']">
	   <xsl:call-template name="apply"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='thead']">
	   <xsl:call-template name="apply"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='table-wrap']">
	   <xsl:call-template name="apply"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='table-count']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
</xsl:stylesheet>