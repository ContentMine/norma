<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xhtml"/>
    
    <xsl:variable name="doi" select="*[local-name()='article-id' and @pub-id-type='doi']"/>
    
	<xsl:template match="/">
	  <html xmlns="http://www.w3.org/1999/xhtml">
		<xsl:apply-templates />
	  </html>
	</xsl:template>

 	<xsl:template match="text()"><xsl:value-of select="."/></xsl:template>
	
	<!-- normalize whitespace -->
 	<xsl:template match="text()[normalize-space(.)='']"><xsl:text></xsl:text></xsl:template>

<!--  unmatched tags -->
	<xsl:template match="*" >
	    <div>
	        <xsl:attribute name="tagxxx"><xsl:value-of select="local-name()"/></xsl:attribute>
			<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="
		*[local-name()='caption'] | 
		*[local-name()='col'] | 
		*[local-name()='colgroup'] |
		*[local-name()='p'] | 
		*[local-name()='hr'] | 
		*[local-name()='sup'] | 
		*[local-name()='table'] | 
		*[local-name()='table-wrap-foot'] | 
		*[local-name()='td'] | 
		*[local-name()='tfoot'] | 
		*[local-name()='th'] | 
		*[local-name()='thead'] | 
		*[local-name()='tr'] 
		" >
        <xsl:copy>
           <xsl:apply-templates select=" * | text()" />
        </xsl:copy>
        </xsl:template>


<!--  SPANS, mainly in references -->
	<xsl:template match="
	    *[local-name()='given-names'] |
	 	*[local-name()='surname'] |
	 	*[local-name()='year'] |
	 	*[local-name()='article-title'] |
	 	*[local-name()='source'] |
	 	*[local-name()='volume'] |
	 	*[local-name()='fpage'] |
	 	*[local-name()='lpage']" >
		<span tagx="{local-name()}"><xsl:apply-templates select="*|text()" /></span>
	</xsl:template>

<!-- 
	<xsl:template match="*[local-name()='contrib-group' and *[local-name()='contrib' and @contrib-type='editor']]" >
	  EDITOR
	  <xsl:apply-templates></xsl:apply-templates>
	</xsl:template>
-->
	<xsl:template match="*[local-name()='contrib-group']" >
	  CONT
	  <xsl:apply-templates></xsl:apply-templates>
	</xsl:template>

	<xsl:template match="*[local-name()='art']">
		<xsl:apply-templates />
	</xsl:template>
	
	<xsl:template match="*[local-name()='front']">
	    <head>
      	    <style type="text/css"> table, tr, td {border : thin;}</style>
			<xsl:apply-templates />
	    </head>
	</xsl:template>

	<xsl:template match="*[local-name()='front']/*[local-name()='journal-meta']" mode="invisible">
	  <meta name="citation_publisher" content="{*[local-name()='publisher']/*[local-name()='publisher-name']}"></meta>
	</xsl:template>
	
	<xsl:template match="*[local-name()='front']/*[local-name()='journal-meta']">
	  <span class="citation_publisher"><xsl:value-of select="*[local-name()='publisher']/*[local-name()='publisher-name']"/></span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='front']/*[local-name()='article-meta']" mode="invisible">
	  <meta name="citation_doi" content="{*[local-name()='article-id' and @pub-id-type='doi']}"></meta>
	  <meta name="citation_title" content="{*[local-name()='title-group']/*[local-name()='article-title']}"></meta>
	  <title><xsl:value-of select="*[local-name()='title-group']/*[local-name()='article-title']"/></title>
	  <xsl:apply-templates select="*[local-name()='contrib-group']"/>
	  <meta name="email" content="{*[local-name()='author-notes']/*[local-name()='corresp']/*[local-name()='email']}"/>
	  <meta name="conflict" content="{*[local-name()='author-notes']/*[local-name()='fn' and @fn-type='conflict']}"/>
	  <meta name="contribs" content="{*[local-name()='author-notes']/*[local-name()='fn' and @fn-type='con']}"/>
	  <meta name="citation_pubdate" content="{concat(pub-date[@pub-type='epub']/
	      *[local-name()='year'],'-',*[local-name()='pub-date' and @pub-type='epub']/
	      *[local-name()='month'],'-',*[local-name()='pub-date' and @pub-type='epub']/*[local-name()='day'])}"/>
	  <meta name="citation_copyright" content="{concat('Copyright',' ',*[local-name()='permissions']/
	  *[local-name()='copyright-year'],': ',*[local-name()='permissions']/*[local-name()='copyright-holder'])}"/>
	  <meta name="citation_licence" content="{concat('Licence',' ',*[local-name()='permissions']/*[local-name()='license'])}"/>
	  <meta name="citation_funding" content="{*[local-name()='funding-group']}"/>
	  <meta name="citation_pagecount" content="{*[local-name()='counts']/*[local-name()='page-count' and @count]}"/>
	  <meta name="citation_data" content="{*[local-name()='custom-meta-group']/*[local-name()='custom-meta' and @id='data-availability']}"/>
	</xsl:template>

	<xsl:template match="*[local-name()='front']/*[local-name()='article-meta']" >
	  <h2 class="citation-title"><xsl:value-of select="*[local-name()='title-group']/*[local-name()='article-title']"/></h2>
	  <span class="doi"><xsl:value-of select="*[local-name()='article-id' and @pub-id-type='doi']"/></span>
	  <div class="contrib-group">contrib: <xsl:apply-templates select="*[local-name()='contrib-group']"/></div>
	  <span class="email">[<xsl:value-of select="*[local-name()='author-notes']/*[local-name()='corresp']/*[local-name()='email']"/>]</span>
	  <span class="conflict">[<xsl:value-of select="*[local-name()='author-notes']/*[local-name()='fn' and @fn-type='conflict']"/>]</span>
	  <span class="contribs">[<xsl:value-of select="*[local-name()='author-notes']/*[local-name()='fn' and @fn-type='con']"/>]</span>
	  <span class="citation_pubdate">[<xsl:value-of select="concat(pub-date[@pub-type='epub']/
	      *[local-name()='year'],'-',*[local-name()='pub-date' and @pub-type='epub']/
	      *[local-name()='month'],'-',*[local-name()='pub-date' and @pub-type='epub']/*[local-name()='day'])"/>]</span>
	  <span class="citation_copyright">[<xsl:value-of select="concat('Copyright',' ',*[local-name()='permissions']/
	  *[local-name()='copyright-year'],': ',*[local-name()='permissions']/*[local-name()='copyright-holder'])"/>]</span>
	  <span class="citation_licence">[<xsl:value-of select="concat('Licence',' ',*[local-name()='permissions']/*[local-name()='license'])"/>]</span>
	  <span class="citation_funding">[<xsl:value-of select="*[local-name()='funding-group']"/>]</span>
	  <span class="citation_pagecount">[<xsl:value-of select="*[local-name()='counts']/*[local-name()='page-count' and @count]"/>]</span>
	  <span class="citation_data">[<xsl:value-of select="*[local-name()='custom-meta-group']/*[local-name()='custom-meta' and @id='data-availability']"/>]</span>
	</xsl:template>

	<xsl:template match="*[local-name()='contrib' and @contrib-type='author']" mode="invisible">
	  <meta name="citation_author" content="{concat(*[local-name()='name']/*[local-name()='given-names'],
	    ' ', *[local-name()='name']/*[local-name()='surname'])}"/>
	  <meta name="citation_author_institution_ref" content="{*[local-name()='xref']/*[local-name()='sup']}"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='contrib' and @contrib-type='author']">
	  <span class="citation_author"><xsl:value-of select="concat(*[local-name()='name']/*[local-name()='given-names'],
	    ' ', *[local-name()='name']/*[local-name()='surname'])"/></span>
	  <span class="citation_author_institution_ref"><xsl:value-of select="*[local-name()='xref']/*[local-name()='sup']"/></span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='aff']" mode="invisible">
	  <meta name="citation_author_institution" 
	      content="{concat(*[local-name()='label'],' ',*[local-name()='addr-line'])}"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='aff']">
	AFF
	  <span class="citation_author_institution">[<xsl:value-of select="concat(*[local-name()='label'],' ',*[local-name()='addr-line'])"/>]</span>
	</xsl:template>
	
	<!--  xref ref-type="aff" rid="AF0001">1</xref> -->
	<!-- 
	<xsl:template match="*[local-name()='xref' and @ref-type='aff']">
	AFF
	  <sup class="aff"><xsl:value-of select="."/></sup>
	</xsl:template>
 -->
 	
	<xsl:template match="*[local-name()='body']">
	  <xsl:apply-templates select="/*[local-name()='article']/*[local-name()='front']/
	      *[local-name()='article-meta']/*[local-name()='abstract']"/>
	  <xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='abstract']">
	    <div id="abstract" tag="abstract">
	      <h2>Abstract</h2>
			<xsl:apply-templates select="*|text()"/>
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='article']">
	    <div tagx="article">
			<xsl:apply-templates select="*|text()"/>
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='strong'] | *[local-name()='bold']">
	    <b><xsl:apply-templates  select="*|text()"/></b>
	</xsl:template>
	
	<xsl:template match="*[local-name()='em'] | *[local-name()='italic'] | *[local-name()='i']">
	    <i><xsl:apply-templates select="*|text()"/></i>
	</xsl:template>
	
	<xsl:template match="*[not(self::*[local-name()='sec'])]/*[local-name()='sec']/*[local-name()='title']">
	    <h2><xsl:apply-templates /></h2>
	</xsl:template>
	
	<xsl:template match="*[local-name()='sec' and *[local-name()='title']]/*[local-name()='sec']/*[local-name()='title']">
	    <h3><xsl:apply-templates select="*|text()"/></h3>
	</xsl:template>
	
	
	<xsl:template match="*[local-name()='sec']/*[local-name()='title']/*[local-name()='sec']/*[local-name()='title']/
	*[local-name()='sec']/*[local-name()='title']">
	    <h4><xsl:apply-templates select="*|text()"/></h4>
	</xsl:template>
	
	<xsl:template match="*[local-name()='list']">
	    <ul><xsl:apply-templates  select="*|text()"/></ul>
	</xsl:template>
	
	<xsl:template match="*[local-name()='list-item']">
	    <li><xsl:apply-templates  select="*|text()"/></li>
	</xsl:template>
	
	<xsl:template match="*[local-name()='ref-list']">
	    <div tag="ref-list"><ul><xsl:apply-templates  select="*|text()"/></ul></div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='ref']">
	    <li tag="ref"><a name="{@id}"/><xsl:apply-templates  select="*|text()"/></li>
	</xsl:template>
	
	<xsl:template match="*[local-name()='label']">
	    <span class="label"><xsl:value-of select="."/></span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='element-citation']">
	    <span class="element-citation'"><xsl:apply-templates/></span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='person-group']">
	    <span class="person-group'"><xsl:apply-templates/></span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='name']">
	    <span class="name'"><xsl:apply-templates/>,</span>
	</xsl:template>
		
	<xsl:template match="*[local-name()='pub-id']">
	    <span class="pub-id'">[<xsl:apply-templates/>]</span>
	</xsl:template>
		
	<xsl:template match="*[local-name()='collab']">
	    <span class="collab'">[<xsl:apply-templates/>]</span>
	</xsl:template>
		
	<xsl:template match="*[local-name()='publisher-loc']">
	    <span class="publisher-loc'">[<xsl:apply-templates/>]</span>
	</xsl:template>
		
	<xsl:template match="*[local-name()='publisher-name']">
	    <span class="publisher-name'">[<xsl:apply-templates/>]</span>
	</xsl:template>
		
	<xsl:template match="*[local-name()='etal']">
	    <i>et al.</i>
	</xsl:template>
		
	<xsl:template match="*[local-name()='sec']">
	    <div id="{@id}"><xsl:apply-templates select="* | text()"/></div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='table-wrap']">
	    <div id="{@id}"><xsl:apply-templates select="* | text()"/></div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='underline']">
	    <u><xsl:apply-templates select="* | text()"/></u>
	</xsl:template>

	<!-- <xref xref ref-type="table-fn"">4</xref> -->	
	<xsl:template match="*[local-name()='xref' and @ref-type='table-fn']" priority="-0.4">
 	    <span rid="{@rid}" ref-type="{@ref-type}"><sup><a href="#{@rid}"><xsl:value-of select="."/></a></sup></span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='fn']" >
 	    <div id="{@id}" class="fn"><xsl:apply-templates/></div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='xref']" priority="-0.45">
	  XREF************
 	    <span rid="{@rid}" ref-type="{@ref-type}"><xsl:apply-templates select="* | text()"/></span>
	</xsl:template>
	
	<!-- <xref ref-type="bibr" rid="CIT0004">4</xref> -->
	<xsl:template match="*[local-name()='xref' and @ref-type='bibr']" priority="-0.4">
 	    <span rid="{@rid}" ref-type="{@ref-type}"><sup><a href="#{@rid}"><xsl:value-of select="."/></a></sup></span>
	</xsl:template>
	
	<!-- <xref ref-type="table" rid="CIT0004">4</xref> -->
	<xsl:template match="*[local-name()='xref' and @ref-type='table']" priority="-0.4">
 	    <span rid="{@rid}" ref-type="{@ref-type}"><a href="#{@rid}"><xsl:value-of select="."/></a></span>
	</xsl:template>
	
</xsl:stylesheet>
