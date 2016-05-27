<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  ACS -->
	
	<xsl:template match="h:div[contains(@class,'pageHeader')]"/>
	<xsl:template match="h:div[contains(@class,'pageFooter')]"/>
	<xsl:template match="h:div[contains(@class,'publicationFormatLinks')]"/>
	<xsl:template match="h:div[contains(@class,'chemworx-sidebar')]"/>
	<xsl:template match="h:div[contains(@class,'citingRelatedContent')]"/>
	<xsl:template match="h:div[contains(@class,'literatumArticleToolsWidget')]"/>
	<xsl:template match="h:div[contains(@class,'literatumSciFinderWidget')]"/>
	<xsl:template match="h:div[contains(@class,'issueNavPageContainer')]"/>
	<xsl:template match="h:div[contains(@class,'cen-feed')]"/>
	
	<xsl:template match="h:ul[@id='citedBy']"/>
	<xsl:template match="h:li[contains(@class,'refQuivkViewLink')]"/>
	<xsl:template match="h:div[@id='relatedArticles']"/>
	<xsl:template match="h:div[@id='notes-1']"/>
	<xsl:template match="h:div[@id='dfp-large-leaderboard']"/>
	
</xsl:stylesheet>
