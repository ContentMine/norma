<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  ELife -->
    <xsl:variable name="publisher">eLife Sciences Publications Limited</xsl:variable>
    <xsl:variable name="prefix">10.7554</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='eLife Sciences Publications Limited') or 
      (@name='citation_doi' and contains(@content,concat('10.7554','/')))
      )
    ]</xsl:variable>
	
	<xsl:template match="h:div[@id='mini-panel-jnl_elife_responsive_bar']"/>
	
</xsl:stylesheet>
