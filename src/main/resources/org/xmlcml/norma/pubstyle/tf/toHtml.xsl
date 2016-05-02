<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

    <xsl:output method="xhtml"/>

	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>

	<!--Identity template, strips PIs and comments -->
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="comment()" priority="1.0"/>

	<!--  header -->
  	<xsl:template match="h:div[@id='hd']"/>
  	<xsl:template match="h:div[@id='cookieBanner']"/>
  	<xsl:template match="h:div[@id='primarySubjects']"/>
  	<xsl:template match="h:div[@id='breadcrumb']"/>
  	<!--  This may be fragile
  	<div class="gutter"> 
      <h4 class="accordianHeader disclosure active"> <a href="#">Browse journal</a> 
      </h4> 
      <div class="accordianPanel">
 -->
  	<xsl:template match="h:div[@class='gutter' and h:div[contains(@class,'accordianPanel')]]"/>
  	
  	<xsl:template match="h:div/h:b/h:a[.='Publishing models and article dates explained']"/>
  	<!-- <div class="script_only alertDiv"> -->
  	<xsl:template match="h:div[contains(@class,'script_only')]"/>
  	<!-- <div class="access accessmodule access_free"/>  -->
  	<xsl:template match="h:div[contains(@class,'access')]"/> 
  	<!--         <div class="secondarySubjects module grid3 clear"> 
                  <div class="subUnit"> 
                     <h3>Librarians</h3> 
                  </div> 
                  <div class="subUnit">
                     <h3>Authors &amp;amp; Editors</h3>
                  </div>
                  <div class="subUnit">
                     <h3>Societies</h3>
                  </div>
                  <div class="subUnit">
                     <h3>Help &amp;amp; Information</h3>
                  </div> 
                  <div class="subUnit publishers last"> 
                     <h3>Taylor &amp;amp; Francis Group</h3> 
                  </div>
  	 -->
  	<xsl:template match="h:div[contains(@class,'secondarySubjects')]"/> 
  	
  	<xsl:template match="h:ul[@class='recommend']"/> 
  	<!--            <div id="unit3" class="unit last">  
                        <div id="articleMetrics" class="widget"> 
                           <h2>Article metrics</h2> 
                        </div> 
                        <div id="metricsInformation" class=""> <a href="HTTP://www.tandfonline.com/page/article-metrics">Article metrics information</a> 
                        </div>  
                        <div id="relatedArticles" class="module widget alsoRead"> 
                           <h2> Users also read </h2>  
                        </div>  
                     </div> 
     -->
  	<xsl:template match="h:div[@id='unit3']"/> 
  	<!--  footer -->
  	<xsl:template match="h:h3[.='Related articles']"/> 
  	<xsl:template match="h:a[.='View all related articles']"/> 
  	<xsl:template match="h:div[contains(@class,'social')]"/> 
  	<xsl:template match="h:ul[contains(@class,'tabsNav')]"/> 
  	<xsl:template match="h:div[@id='siteInfo']"/> 
  	<xsl:template match="h:div[contains(@class,'credits')]"/> 
  	<xsl:template match="h:a[starts-with(.,'[') and ends-with(.,']')]"/> 
  	<xsl:template match="h:a[.='View all references']"/> 
  	<xsl:template match="h:a[.='figureViewerArticleInfo']"/> 
  	<xsl:template match="h:div[@class='hidden']"/> 
  	<xsl:template match="h:span[contains(@class,'dropDownAlt')]"/> 
  	<xsl:template match="h:a[contains(@onclick,'showFigures')]"/> 
  	<xsl:template match="h:div[@class='figureDownloadOptions']"/> 
  	<xsl:template match="h:div[normalize-space(.)='']" priority="0.51"/> 

</xsl:stylesheet>
