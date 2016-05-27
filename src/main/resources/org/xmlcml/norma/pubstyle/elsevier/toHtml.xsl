<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  Elsevier -->
	
	<xsl:template match="h:div[@id='header']"/> <!--  there are two of these - think these are both unwanted -->
	<xsl:template match="h:div[@id='ieWarningMsg']"/>
	<xsl:template match="h:div[@id='rightPane']"/>
	<xsl:template match="h:div[@id='articleToolbar']"/>
	<xsl:template match="h:div[@id='articleToolbarDummy']"/>
	<xsl:template match="h:div[@id='leftPane']"/>
	<xsl:template match="h:div[@id='pdfModalWindow']"/>
	<xsl:template match="h:div[@class='padding' and contains(.,'Cookies')]"/>
	<xsl:template match="h:div[@id='ddmMainHelp']"/>
	<xsl:template match="h:div[@id='ddmNewformatHelp']"/>
	<xsl:template match="h:div[contains(@class,'publicationCover')]"/>

	<xsl:template match="h:dd[@class='menuButtonLinks']"/>
<!-- <dl class="figure" -->	
	<xsl:template match="h:img[contains(@class,'imgLazyJSB')]"/>
	
	<xsl:template match="h:h1[@class='headline' and contains(.,'Download PDFs')]"/>
	
	<xsl:template match="h:img[@id='logo']"/>
	<xsl:template match="h:img[@class='refimgLoader']"/>
	
	<xsl:template match="h:a[@id='closeWindow']"/>
	<xsl:template match="h:a[@href and normalize-space(.)='']"/>
	
</xsl:stylesheet>
