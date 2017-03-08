<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  APA -->
	<xsl:variable name="publisher">American Psychological Association</xsl:variable>
    <xsl:variable name="prefix">10.1037</xsl:variable>
	<xsl:variable name="publisherSelector">//*[local-name()='a' and contains(.,'www.apa.org')]</xsl:variable>

	<!-- <a xmlns="http://www.w3.org/1999/xhtml" href="http://www.apa.org">APA</a>-->
	
	<!-- HEAD -->
	<!-- <div id="ftPage">
<div id="ftBrandingBar">
<div id="ftAPABranding">...</div>
</div>
<ul id="ftTopNav">...</ul>
<ul id="ftJournalTitle">
<li>Archives of Scientific Psychology</li>
<li class="JournalEditor">Cecil R. Reynolds and Gary R. VandenBos, Editors</li>
</ul>
<div id="ftThisLabel">
<a href="http://www.apa.org/pubs/journals/arc/">
<img class="paCover" alt="journal cover" src="http://www.apa.org/pubs/journals/images/arc-150.gif"/>
</a>
	 -->
	<xsl:template match="h:div[@id='ftBrandingBar']"/>	 
	<xsl:template match="h:div[@id='ftAPABranding']"/>	 
	<xsl:template match="h:ul[@id='ftTopNav']"/>	 
	<xsl:template match="h:div[@id='ftThisLabel']"/>	 
	<xsl:template match="h:a[h:img[@class='paCover']]"/>	 

	<!-- MAIN -->
	<!-- References -->
	<xsl:template match="h:ul[@class='refListItemLinks']"/>
	
	<!-- FOOT -->
	<xsl:template match="h:div[@id='menucolumn']"/>	
	<xsl:template match="h:div[@id='rightcolumn']"/>
	
	<!--  wrappers -->
	<!-- 
	<div id="contentwrapper">
 	  <div id="contentcolumn" class="contentcolumnCollapsed">
        <div id="contentInnerWrapper">
          <div id="content">
            <div id="psycnet_fulltext_article_content">
              <div class="contentItem">
              -->
	<xsl:template match="h:div[(@id='contentwrapper' 
	                        or @id='contentcolumn'
	                        or @id='contentInnerWrapper'
	                        or @id='content'
	                        or @id='psycnet_fulltext_article_content')
	                        and descendant::h:div[@class='contentItem']]
	                        ">
	  <xsl:apply-templates/>
	</xsl:template> 
	
	
</xsl:stylesheet>
