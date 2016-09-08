<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  BMC -->
	
<!--  tags to omit -->
        <xsl:template match="
        h:script
        | h:noscript
        | h:styleß
        | h:link
        | comment()
        | h:div[@id='oas-campaign']
        | h:div[@id='oas-positions']
        | h:div[@id='branding']
        | h:div[@id='branding-inner']
        | h:div[@id='left-article-box']
        | h:div[@id='right-panel']
        | h:div[@id='article-alert-signup-div']
        | h:div[@id='footer']
        | h:div[@id='springer']
        | h:div[@class='hide' and h:dl[@class='google-ad']]
        ">
        </xsl:template>
	
	
</xsl:stylesheet>
