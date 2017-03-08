<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  Wiley -->
	<xsl:variable name="publisher">Wiley</xsl:variable>
    <xsl:variable name="prefix">10.1002</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='Wiley') or 
      (@name='citation_doi' and contains(@content,concat('10.1002','/')))
      )
    ]</xsl:variable>

	<xsl:template match="h:div[@id='skip']"/> 
	<xsl:template match="h:div[@id='header']"/> 
	<xsl:template match="h:div[@id='banner']"/> 
	
	<xsl:template match="h:div[@class='imgShadow']"/> 
	<xsl:template match="h:div[@class='tabbedContent']"/> 
	<xsl:template match="h:div[@class='viewFullArticleAndPdf']"/> 
	<xsl:template match="h:div[@class='menuGroup']"/> 
	<xsl:template match="h:div[@class='internalReferences']"/> 
	
	<xsl:template match="h:ol[@class='jumpList']"/> 	
	<xsl:template match="h:ul[@class='extraSearchOptions']"/> 	
	
	<xsl:template match="h:div[@id='breadcrumb']"/> 
 	<xsl:template match="h:div[@id='pageNavAndTools']"/> 
 	<xsl:template match="h:div[@id='relatedArticles']"/> 
 	<xsl:template match="h:div[@id='footer']"/> 
 	
 	
 	<xsl:template match="h:span[@class='freeAccess']"/> 
 	
	
</xsl:stylesheet>
