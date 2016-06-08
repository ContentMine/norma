<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  Wolters Kluwer -->
	
	<!-- this uses tables to do layout (oh, dear) -->
	<!--  first table can be junked - this is fragile -->
	
	<xsl:template match="h:table[*//h:table[@id='table2']]"/>
	<xsl:template match="h:table[@class='FooterTAble']"/>

 	<!--  figures -->
 	<!-- references -->

	<!-- main section nav --> 	
	
</xsl:stylesheet>
