<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  Sage -->
	<xsl:variable name="publisher">SAGE Publications</xsl:variable>
    <xsl:variable name="prefix">10.1177</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='SAGE Publications') or 
      (@name='citation_doi' and contains(@content,concat('10.1177','/')))
      )
    ]</xsl:variable>


 	<xsl:template match="h:div[@id='header-initialNav']"/>
 	<xsl:template match="h:div[@id='header']"/>
 	<xsl:template match="h:div[@id='footer']"/>
 	<xsl:template match="h:div[@id='cb-art-soc']"/>
 	<xsl:template match="h:div[@id='cb-art-rel']"/>
 	<xsl:template match="h:div[@id='cb-art-gs']"/>
 	<xsl:template match="h:div[@id='cb-art-cit']"/>
 	<xsl:template match="h:div[@id='cb-letter-cit']"/>
 	<xsl:template match="h:div[@id='cb-art-svcs']"/>
 	<xsl:template match="h:div[@class='social-bookmarking']"/>
 	<!--  figures -->
 	<xsl:template match="h:div[@class='fig-inline']"/>
 	<!-- references -->
 	<!-- <a class="rev-xref-ref" href="#xref-ref-1-1" title="View reference 1 in text" id="ref-1">↵</a> -->
 	<xsl:template match="h:a[@class='rev-xref-ref']"/>
 	<xsl:template match="h:div[@class='cit-extra']"/>
 	
 	<xsl:template match="h:div[normalize-space(.)='Previous Section' or normalize-space(.)='Next Section']"/>
 	<xsl:template match="h:a[normalize-space(.)='Previous Section' or normalize-space(.)='Next Section']"/>
 	<xsl:template match="h:div[@id='col-2' or @id='col-3']"/>
 	

	<!-- remove wrappers -->
	<xsl:template match="h:div[@id='pageid-content'] | h:div[@id='content-block']">
	  <xsl:apply-templates/>
	</xsl:template> 
	
	<!-- references -->
	<!-- <div class="cit ref-cit ref-journal" id="cit-4.5.2325967116646534.1"><div class="cit-metadata"> -->
	<xsl:template match="h:div[contains(@class,'ref-cit')]">
	  <xsl:apply-templates/>
	</xsl:template> 
	
</xsl:stylesheet>
