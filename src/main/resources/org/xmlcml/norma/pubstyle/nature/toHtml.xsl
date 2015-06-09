<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

    <xsl:output method="xhtml"/>

<!--     
	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>
-->

	<!--Identity template, strips PIs and comments -->
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="comment()" priority="1.0"/>

<!-- navigation -->
  	<xsl:template match="h:div[@id='foreword']"/> 
  	<xsl:template match="h:div[@id='top-ads']"/> 
  	<xsl:template match="h:div[@id='header']"/> 
  	<xsl:template match="h:div[@class='header-bottom']"/> 
  	<xsl:template match="h:div[@class='comm-tool-box']"/> 
  	
	

</xsl:stylesheet>
