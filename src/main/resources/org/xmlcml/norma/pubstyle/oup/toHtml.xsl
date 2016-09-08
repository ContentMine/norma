<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  OUP -->

	<xsl:variable name="publisher">Oxford University Press</xsl:variable>
    <xsl:variable name="prefix">10.1093</xsl:variable>
	<xsl:variable name="publisherSelector">
    //*[local-name()='meta' and
      (
      (@name='DC.Publisher' and @content='Oxford University Press') or 
      (@name='DC.Identifier' and contains(@content,'10.1093/'))
      )
    ]</xsl:variable>

	<xsl:template match="h:div[@id='secondary_nav']"/>
	<xsl:template match="h:div[@id='primary_nav']"/>
	<xsl:template match="h:div[@id='col-2']"/>
	<xsl:template match="h:div[@id='col-3']"/>
	<xsl:template match="h:div[@id='primary_footer']"/>
	
	<xsl:template match="h:div[@class='fig-inline']"/>
	<xsl:template match="h:div[@class='table-inline']"/>
	<xsl:template match="h:div[@class='cb-contents']"/>
	<xsl:template match="h:div[@class='cit-extra']"/>
	<xsl:template match="h:div[@class='section-nav']"/>

	<xsl:template match="h:p[@class='hide']"/>

</xsl:stylesheet>
