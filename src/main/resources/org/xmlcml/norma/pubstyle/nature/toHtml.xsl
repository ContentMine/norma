<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!-- Nature -->

<!-- navigation -->
  	<xsl:template match="h:div[@id='foreword']"><xsl:message>FOREWORD</xsl:message></xsl:template> 
  	<xsl:template match="h:div[@id='top-ads']"/> 
  	<xsl:template match="h:div[@id='header']"/> 
  	<xsl:template match="h:div[@id='constrain-header']"></xsl:template> 
  	<xsl:template match="h:div[@class='header-bottom']"/> 
  	<xsl:template match="h:div[@class='footer-main']"/> 
  	<xsl:template match="h:div[@class='comm-tool-box']"/> 
  	<xsl:template match="h:div[@class='author-search']"/> 
  	<xsl:template match="h:header/h:div[@class='top-links cleared']"/>  	
  	<xsl:template match="h:div[starts-with(@class,'aside')]"/> 
  	<xsl:template match="h:nav"/> 
  	<xsl:template match="h:div[@id='extranav']"/> 
  	<!-- figures -->
	<xsl:template match="h:div[@class='figures-at-a-glance']"/>
	<!-- <a href="/ncomms/2016/160527/ncomms11560/fig_tab/ncomms11560_F1.html" class="fig-link "> -->
	<xsl:template match="h:a[normalize-space(@class)='fig-link']"/>
	
	<xsl:template match="h:ul[@class='resources' and h:li[@class='full']]"/>
	
  	<!-- references -->
  	<xsl:template match="h:ul[@class='cleared']"/> 
  	
  	<xsl:template match="h:div[@id='top-content-most-read']"/> 
  	<xsl:template match="h:div[@id='related-content-articles']"/> 
  	<xsl:template match="h:div[@id='nature-science-events']"/> 
  	<xsl:template match="h:div[@id='nature-jobs-events-box']"/> 
  	
  	<!-- wrapper -->
  	<xsl:template match="h:div[@id='content' or @id='constrain']">
  	  <xsl:apply-templates/>
  	</xsl:template>
  	<!-- figures -->
	<xsl:template match="h:div[@class='item-content box']">
  	  <xsl:apply-templates/>
  	</xsl:template>
  	
</xsl:stylesheet>
