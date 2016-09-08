<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  AIP -->
    <xsl:variable name="publisher">AIP Publishing</xsl:variable>
    <xsl:variable name="prefix">10.1063</xsl:variable>
	<xsl:variable name="publisherSelector">
    //*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='AIP Publishing') or 
      (@name='citation_doi' and contains(@content,'10.1063/'))
      )
    ]</xsl:variable>

	<xsl:template match="h:div[contains(@class,'pageHeader')]"/>
	
	<!--  wrapppers -->
	
</xsl:stylesheet>
