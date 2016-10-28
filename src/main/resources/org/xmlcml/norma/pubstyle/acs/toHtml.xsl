<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  ACS -->
    <xsl:variable name="publisher">American Chemical Society</xsl:variable>
    <xsl:variable name="prefix">10.1021</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.Publisher' and @content='American Chemical Society') or 
      (@name='citation_doi' and contains(@content,concat('10.1021','/')))
      )
    ]</xsl:variable>
 	
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
	
	<!--  wrapppers -->
	<!--  This is heavily indented  
	<div class="widget layout-two-columns none article-main-cols widget-none widget-compact-all" id="6901cc5c-a7f6-4109-bf08-278038ba53a6">
<div class="wrapped ">
<div class="widget-body body body-none body-compact-all">
<div class="pb-columns row-fluid gutterless">
<div class="width_17_24">
<div data-pb-dropzone="left" class="pb-autoheight">
<div class="widget literatumPublicationContentWidget none widget-none widget-compact-all" id="d2a196f9-55e7-492a-9643-735a41b61a8c">
<div class="wrapped ">
<div class="widget-body body body-none body-compact-all">
<div class="publication-tabs ja">
<div class="tabs tabs-widget">
<div class="tab-content ">
<div class="tab tab-pane active">
<article class="article">
<h1 class="articleTitle">
-->
<!-- 
	<xsl:template match="h:div[*//*/h:article]">
	  <xsl:apply-templates/>
	</xsl:template> 
	-->
	<!-- 
	<xsl:template match="h:div[contains(@class,'widget') or @class='wrapped']" priority="-0.6">
	  <xsl:apply-templates/>
	</xsl:template> 
	-->
	<xsl:template match="h:div[count(*)=1 and (h:div or h:h3)]" priority="-0.6">
	  <xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="h:div[@data-pb-dropzone]" >
	  <xsl:apply-templates/>
	</xsl:template> 
	
	<xsl:template match="h:div[(count(*)=1 or normalize-space(@class)='tab-content') and h:article]" >
	  <xsl:apply-templates/>
	</xsl:template> 
	
	
</xsl:stylesheet>
