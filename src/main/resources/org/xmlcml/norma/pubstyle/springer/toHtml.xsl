<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  Springer -->
	<xsl:variable name="publisher">Springer Berlin Heidelberg</xsl:variable>
    <xsl:variable name="prefix">10.1007</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='Springer Berlin Heidelberg') or 
      (@name='citation_doi' and contains(@content,concat('10.1007','/')))
      )
    ]</xsl:variable>

	

	<xsl:template match="h:p[@class='SkipToMain']"/> 
	<xsl:template match="h:header[@class='Header']"/> 
	
	<xsl:template match="h:div[@class='SideBox']"/> 
	<xsl:template match="h:div[contains(@class,'identity__cover')]"/> 
	<xsl:template match="h:footer[@class='footer']"/> 
	
	<xsl:template match="h:nav[@class='identity__nav']"/> 
	
</xsl:stylesheet>
