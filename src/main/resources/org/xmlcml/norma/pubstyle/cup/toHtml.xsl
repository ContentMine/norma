<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  CUP -->

	<xsl:template match="*[@display='none']"/>
	<xsl:template match="h:div[@id='header-options']"/>
	<xsl:template match="h:div[@id='quick-search']"/>
	<xsl:template match="h:div[@id='h-menu-rl1-box']"/>
	<xsl:template match="h:div[@id='h-menu-rl5-box']"/>
	<xsl:template match="h:div[@id='h-menu-rl93-box']"/>
	<xsl:template match="h:div[@id='navigation-search']"/>
	<xsl:template match="h:div[@id='bottom-navigation-menu']"/>
	<xsl:template match="h:div[@id='top-navigation']"/>
	
	
	<xsl:template match="h:div[@class='fulltxt-nav']"/>
	<xsl:template match="h:div[@class='fiw-right']/h:p[h:a[@href]]"/>

	<xsl:template match="h:ul[@id='nav']"/>
	
</xsl:stylesheet>
