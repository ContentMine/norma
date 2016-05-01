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

	<xsl:template match="comment()" priority="1.0"/>

<!--  cleaning -->
<!-- navigation -->
	<xsl:template match="h:div[@id='global-notification']" priority="0.6"/>
 	<xsl:template match="h:div[@id='xplore-header']"/> 
  	<xsl:template match="h:div[@id='browse-by-topic']"/> 
  	<xsl:template match="h:div[@id='article-page-hdr']"/> 
  	<xsl:template match="h:div[@id='at-glance']"/> 
  	<xsl:template match="h:div[@id='article-nav']"/> 
  	<xsl:template match="h:div[@id='colorbox']"/> 
  	<xsl:template match="h:div[@id='cboxWrapper']"/> 
  	<xsl:template match="h:div[@id='cboxOverlay']"/> 
  	<xsl:template match="h:div[@id='ref_popup']"/> 
  	<xsl:template match="h:ul[@id='ui-id-1']"/> 
  	<xsl:template match="h:div[@id='text-sizer']"/> 

    <xsl:template match="h:div[@class='Metanav stats-metanav']"/>
	<xsl:template match="h:div[@class='global-notification']"/>
	<xsl:template match="h:div[@class='Toolbar pure-g']"/>
	<xsl:template match="h:div[@class='Search pure-g ']"/>
	<xsl:template match="h:div[@class='Tools']"/>
	<xsl:template match="h:div[@class='article-tools']"/>
	<xsl:template match="h:div[@class='toc']"><!-- <p>TOC omitted</p>--></xsl:template>
	<xsl:template match="h:div[@class='pure-u-1-4']"/>
	<xsl:template match="h:div[@class='pure-g']"/>
	<xsl:template match="h:div[h:ul[@class='article-tools']]"/>
  	<xsl:template match="h:p[@class='JumpLink']"/> 
  	<xsl:template match="h:p[@class='links']"/> 
  	
	<xsl:template match="h:div[starts-with(@style,'visibility:')]"/>
	
<!-- some heading are of form <h2>A<small>BC</small></h2> -->	
	<xsl:template match="h:h2/h:small"><xsl:value-of select="text()"/></xsl:template>
	
<!-- publisher added value; maybe their IP -->
	<xsl:template match="h:div[@id='related-articles']"/>
	<xsl:template match="h:div[@id='cited-by']"/>
	<xsl:template match="h:div[@id='keywords']"/>

</xsl:stylesheet>
