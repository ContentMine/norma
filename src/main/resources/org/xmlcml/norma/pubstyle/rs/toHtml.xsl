<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">
	
	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

<!--  Royal Society -->

  	<xsl:template match="h:script"/>
  	<xsl:template match="h:link"/>
  	<xsl:template match="h:style"/>
  	<xsl:template match="h:footer"/>
  	
  	<xsl:template match="h:i[normalize-space(.)='']"/>
  	<xsl:template match="@style[.='display:none;']"/> <!--  hides all the text -->
  	
  	<xsl:template match="h:div[
  	    @id='skip-link' or
  	    @id='zone-user' or 
  	    @id='zone-user-wrapper' or
  	    @id='region-user-first']">
  	    </xsl:template>
  	<xsl:template match="h:a[contains(@class,'hw-link-article-abstract')]"/>
  	    
  	<xsl:template match="h:header[@id='section-header']"/>
  	
  	<!-- <ul class="tabs inline panels-ajax-tab"> -->
  	<xsl:template match="h:ul[contains(@class,'panels-ajax-tab')]"/>
  	<xsl:template match="h:ul[contains(@class,'highwire-figure-links')]"/>
  	<xsl:template match="h:ul[contains(@class,'author-tooltip-find-more')]"/>
  	<xsl:template match="h:a[@title='View reference in text']"/>
  	<xsl:template match="h:div[contains(@class,'cit-extra')]"/>
  	
  	<xsl:template match="h:div[contains(@class,'pane-highwire-article-crossmark')]"/>
  	<xsl:template match="h:div[contains(@class,'pane-highwire-node-pager')]"/>
  	<xsl:template match="h:div[contains(@class,'pane-highwire-back-to-top')]"/>
  	<xsl:template match="h:div[contains(@class,'pane-highwire-variant-list')]"/>
  	<xsl:template match="h:div[contains(@class,'pane-highwire-share-link')]"/>
  	<xsl:template match="h:div[contains(@class,'pane-roysoc-art-email')]"/>
  	<xsl:template match="h:div[contains(@class,'sidebar-right-wrapper')]"/>
  	
  	
  	
  	<!--  ========= TRANSFORMS ========= -->
  	<xsl:template match="h:div[@class='paragraph']">
  	  <p>
  	    <xsl:apply-templates/>
  	  </p>
  	</xsl:template> 

</xsl:stylesheet>
