<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  BMC -->
    <xsl:variable name="publisher">BioMed Central Ltd</xsl:variable>
    <xsl:variable name="prefix">10.1186</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='BioMed Central Ltd') or 
      (@name='citation_doi' and contains(@content,concat('10.1186','/')))
      )
    ]</xsl:variable>
		
<!--  tags to omit -->
        <xsl:template match="
        h:script
        | h:noscript
        | h:style
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
