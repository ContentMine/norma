<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

<xsl:import href="../norma/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  CUP -->
    <xsl:variable name="publisher">Cambridge University Press</xsl:variable>
    <xsl:variable name="prefix">10.1017</xsl:variable>
	<xsl:variable name="publisherSelector">
    //*[local-name()='head' and
      contains(@prefix,'cambridgejournals')
    ]</xsl:variable>
	
	<xsl:template match="*[@display='none']"/>
	<xsl:template match="h:div[@id='header-options']"/>
<!-- 	<xsl:template match="h:div[@id='container']"/> -->
	<xsl:template match="h:div[@id='quick-search']"/>
	<xsl:template match="h:div[@id='h-menu-rl1-box']"/>
	<xsl:template match="h:div[@id='h-menu-rl5-box']"/>
	<xsl:template match="h:div[@id='h-menu-rl93-box']"/>
	<xsl:template match="h:div[@id='navigation-search']"/>
	<xsl:template match="h:div[@id='bottom-navigation-menu']"/>
	<xsl:template match="h:div[@id='top-navigation']"/>
	<xsl:template match="h:div[@id='navigation-help']"/>
	<xsl:template match="h:div[@id='footer']"/>
	<xsl:template match="h:div[@id='thirdparty-banners']"/>
	<xsl:template match="h:div[@id='windowLogin']"/>
	
	<xsl:template match="h:div[@class='tableofcontents']"/>
	<xsl:template match="h:div[@class='fulltxt-nav']"/>
	<xsl:template match="h:div[@class='sidebar-menu-container']"/>
	<xsl:template match="h:div[@class='breadcrumb']"/>
	<xsl:template match="h:div[@class='sh-right']"/>
	<xsl:template match="h:div[@class='authorQuery']"/>
	
	<xsl:template match="h:div[@class='fiw-right']/h:p[h:a[@href]]"/>

	<xsl:template match="h:ul[@id='nav']"/>
	<xsl:template match="h:ul[@class='breadcrumb']"/> 

	<xsl:template match="h:a[@class='skip']"/> 
	
	<!-- remove wrappers -->
	<!-- <div id="container">
<div class="page">
<div class="page-inner"> -->
	<xsl:template match="h:div[@id='container'] | h:div[@class='page'] | h:div[@class='page-inner']">
	  <xsl:apply-templates/>
	</xsl:template> 

<!-- 	
	<xsl:template match="h:div[@class='page']">
	  <xsl:apply-templates/>
	</xsl:template> 

	<xsl:template match="h:div[@class='page-inner']">
	  <xsl:apply-templates/>
	</xsl:template> 
-->

</xsl:stylesheet>
