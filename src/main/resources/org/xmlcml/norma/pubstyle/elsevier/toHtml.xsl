<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  Elsevier -->
	
	<xsl:variable name="publisher">Elsevier BV</xsl:variable>
    <xsl:variable name="prefix">10.1016</xsl:variable>
	<xsl:variable name="publisherSelector">
//*[local-name()='img' and @id='logo' and contains(@src,'sciencedirect')]
</xsl:variable>
	
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
	
	<!--  redundant divs -->
<!-- 	
<div id="page-area">
<div id="centerPane" class="column">
<div id="centerContent" class="centerContent">
-->
	<xsl:template match="h:div[@id='page-area'] | h:div[@id='centerPane'] | h:div[@id='centerContent']">
	  <xsl:apply-templates/>
	</xsl:template> 
	
	<xsl:template match="h:div[h:div[@class='publicationHead']]">
	  <xsl:apply-templates/>
	</xsl:template> 
<!-- 
<div class="figTblUpiOuter svArticle" id="figure_f0005">
<div>	
-->
	<xsl:template match="h:div[starts-with(@id,'figure_')]/h:div[not(@*)]">
	  <xsl:apply-templates/>
	</xsl:template> 
	<xsl:template match="h:div[starts-with(@id,'table_')]/h:div[not(@*)]">
	  <xsl:apply-templates/>
	</xsl:template> 
	
	<!-- 
	<div class="articleText_indent">
<h2 class="h2 secHeading" id="ack001">Acknowledgments</h2>
-->
	<xsl:template match="h:div[h:h2[.='Acknowledgments']]">
	  <xsl:apply-templates/>
	</xsl:template> 
	
</xsl:stylesheet>
