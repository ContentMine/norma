<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  BMJ -->
    <xsl:variable name="publisher">British Medical Journal Publishing Group</xsl:variable>
    <xsl:variable name="prefix">10.1136</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='British Medical Journal Publishing Group') or 
      (@name='citation_doi' and contains(@content,concat('10.1136','/')))
      )
    ]</xsl:variable>
	
	<!--  FRONT -->
	<!--  skip to content -->
	<xsl:template match="h:div[@id='skip-link']"/>
	<xsl:template match="h:div[@id='cookie-notice']"/>
	<!--  membership -->	
	<xsl:template match="h:header"/>
	
	<!-- MAIN -->
	<!-- table -->
	<!-- 
	<div class="callout">
<span>View this table:</span>
-->
	<xsl:template match="h:div[h:span[starts-with(.,'View this table')]]"/>

    <!--  REFERENCES -->
    <xsl:template match="h:a[@class='rev-xref-ref']"/>
    <xsl:template match="h:div[@class='cit-extra']"/>
    
	<!--  FOOT -->
	<xsl:template match="h:div[@class='service-links']"/>
	<!-- 
	<div class="panel-pane pane-bmj-article-buttons">
<h2 class="pane-title">Article tools</h2>
-->
<xsl:template match="h:div[contains(@class,'pane-bmj-article-buttons')]"/>

<!-- <div>
<ul>
<li class="first">
<div class="pane-content">
<p>
<a href="/content/353/bmj.i2607/submit-a-rapid-response">Respond to this article</a>
 -->
 
<xsl:template match="h:div[h:ul[h:li[@class='first']]]" priority="-0.6"/>
<xsl:template match="h:footer"/>

<!-- 
<a href="http://www.bmj.com/content/353/bmj.i2607.abstract" class="hw-link hw-link-article-abstract">View Abstract</a>
 -->
<xsl:template match="h:a[.='View Abstract']"/>
 
<!-- 
div class="panel-panel col-xs-12 col-sm-12 col-md-12 col-lg-12">
<div class="pane-content">
<div class="bmj-article-links minipanel-model-wrapper">
-->
<xsl:template match="h:div[h:div[@class='pane-content' and h:div[contains(@class,'bmj-article-links')]]]" />

<!-- 
<div class="panel-panel col-xs-12 col-sm-12 col-md-12 col-lg-12">
<div class="minipanel-model-wrapper">
<div class="minipanel-model-link-link">
<a href="/#mini-panel-jnl-bmj-article-button-email-to-1" data-toggle="collapse" role="button" data-font-icon="icon-email">Email to a friend</a>
</div>
 -->
 <xsl:template match="h:div[h:div[h:div[h:a[contains(.,'Email to a friend')]]]]"/>
 
<!-- <div class="panel-pane pane-bmj-article-specialities">
<h2 class="pane-title">Topics</h2>
</div> -->
 <xsl:template match="h:div[contains(@class,'pane-bmj-article-specialities')]"/>

<!-- 
<div class="panel-pane pane-snippet pane-poll-international" id="poll-widget">
<h2 class="pane-title">This week's poll</h2> -->
 <xsl:template match="h:div[contains(@class,'pane-poll-international')]"/>

<!-- 
<div data-panels-ajax-tab-preloaded="jnl_bmj_feeds_uk_jobs" id="panels-ajax-tab-container-panel_jobshome" -->
 <xsl:template match="h:div[@id='panels-ajax-tab-container-panel_jobshome']"/>

<!-- 
<div class="pane-content">
<a href="#page" class="back-to-top">Back to top</a>
</div> -->
<xsl:template match="h:div[h:a[@class='back-to-top']]"/>

<!-- 
<div class="aside_UNKNOWN">
<div class="service-links">...</div>
  	<xsl:template match="h:div[@class='aside_UNKNOWN' and h:div[contains(@class,'service-links')]]"> 
-->
<!-- I don't know what is going on here but it works -->
  	<xsl:template match="h:div[@class='aside_UNKNOWN' and h:div[@class]]">
  	  <!-- <xsl:message>SERVICE[<xsl:value-of select="h:div/@class"/>]</xsl:message> -->
  	  <xsl:apply-templates></xsl:apply-templates>
	</xsl:template>
	
	<!--  WRAPPERS -->
	<xsl:template match="h:div[count(*)=1 and (h:div or h:h3)]" priority="-0.6">
	  <xsl:apply-templates/>
	</xsl:template>
	
	<!-- 
<div class="row">
<div class="left-content col-xs-12 col-sm-8 col-md-8 col-lg-8">
<article>...</article>
 -->	
	<xsl:template match="h:div[@class='row' and h:div[h:article]] | h:div[@class='row']/h:div[h:article]">
	  <xsl:apply-templates/>
	</xsl:template>
	
<!-- 
div data-panels-ajax-tab-preloaded="jnl_bmj_tab_art" id="panels-ajax-tab-container-highwire_article_tabs" class="panels-ajax-tab-container">
<div class="highwire-markup">...</div>
 -->
	<xsl:template match="h:div[@id='panels-ajax-tab-container-highwire_article_tabs'] |
	   h:div[@id='panels-ajax-tab-container-highwire_article_tabs']/h:div[@class='highwire-markup']">
	  <xsl:apply-templates/>
	</xsl:template>
	
	
	
</xsl:stylesheet>
