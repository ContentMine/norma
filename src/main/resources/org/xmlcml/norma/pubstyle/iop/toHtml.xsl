<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  IOP -->
	<xsl:variable name="publisher">IOP Publishing</xsl:variable>
    <xsl:variable name="prefix">10.1088</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='IOP Publishing') or 
      (@name='citation_doi' and contains(@content,concat('10.1088','/')))
      )
    ]</xsl:variable>
	
	
	<!-- HEAD -->
	<xsl:template match="h:header"/>
	<!-- <div class="accessibility" style="display: none;"> -->
	<xsl:template match="h:*[contains(translate(@style,' ',''),'display:none;')]"/>
	<xsl:template match="h:div[@class='print-hide']"/>
	<xsl:template match="h:div[@id='metrics']"/>
	<!-- 
<p>
<a href="https://pod-iopscience.org?doi=10.1088/0953-8984/28/22/224009&UTCDate=30052016_200519" id="wd-jnl-art-reprint">Buy this article in print</a>
</p>
-->
	<xsl:template match="h:p[normalize-space(.)='Buy this article in print']"/>

<!-- 
<p class="wd-jnl-rss print-hide">
<a href="/0953-8984/?rss=1">Journal RSS feed</a>
</p>
-->
	<xsl:template match="h:p[normalize-space(.)='Journal RSS feed']"/>

<!-- 
<p class="wd-jnl-email-alert print-hide">
<a href="https://ticket.iop.org/login?return=http%3A%2F%2Fiopscience.iop.org%2Fmyiopscience%2Falerts%2Fsubscribe%3Fjournal%3D0953-8984%26fromUrl%3Dhttp%253A%252F%252Fiopscience.iop.org%252Farticle%252F10.1088%252F0953-8984%252F28%252F22%252F224009" class="loginRequired" id="noId" title="Create journal email alert">Sign up for new issue notifications</a>
</p>
	 -->
	<xsl:template match="h:p[normalize-space(.)='Sign up for new issue notifications']"/>
	 
	 <!-- REFERENCES -->
	<xsl:template match="h:p[normalize-space(.)='CrossRef']"/>

	
	<!-- FOOT -->
	<xsl:template match="h:div[contains(@class,'nav-in-article')]"/>
	<xsl:template match="h:footer"/>
	<!-- <div class="da3 ta1">
<div class="side-and-below">
<div class="boxout bdt-6 related wd-related-articles hide">
<h3>Related content</h3> -->
	<xsl:template match="h:div[h:div[@class='side-and-below' and h:div[h:h3[.='Related content']]]]"/>
	<!-- 
	<p>
Export references:
-->
	<xsl:template match="h:p[contains(.,'Export references')]"/>
	
<!-- <div class="da2 ta2">
<div class="content-nav show-w-js">
<ul class="content-nav-ul wd-jnl-art-content-nav"></ul>
<a data-footer-backtotop="" class="btn btn-bottom back-top pl-0" href="#top">Back to top</a>
</div> -->
<!-- 	<xsl:template match="h:div[h:div[h:a[.='Back to top']]]"/> -->
	<xsl:template match="h:div[h:div[h:a[normalize-space(.)='Back to top']]]"/>
	
	<!--  wrapppers -->
	
</xsl:stylesheet>
