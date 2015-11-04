<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">
    <xsl:output method="xhtml"/>

<!--     
	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>
-->

	<!--Identity template, strips PIs and comments -->
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

  	<xsl:template match="h:head">
  	  <head>
  	    <style>
  	    div {
  	        border: 1px solid red;
  	        margin: 2 2 2 2;
  	        }
  	    </style>
  	  </head>
  	</xsl:template> 

	<xsl:template match="comment()" priority="1.0"/>


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
  	<xsl:template match="h:div[@id='top-content-most-read']"/> 
  	<xsl:template match="h:div[@id='related-content-articles']"/> 
  	<xsl:template match="h:div[@id='nature-science-events']"/> 
  	<xsl:template match="h:div[@id='nature-jobs-events-box']"/> 
</xsl:stylesheet>
