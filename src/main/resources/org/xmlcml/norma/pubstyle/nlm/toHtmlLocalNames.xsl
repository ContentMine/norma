<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xhtml"/>
    
	<xsl:template match="/">
	  <html xmlns="http://www.w3.org/1999/xhtml">
		<xsl:apply-templates />
	  </html>
	</xsl:template>

	<!--Identity template, strips PIs and comments -->
	<!-- 
	<xsl:template match="@*|*|text()">
		<xsl:copy>
			<xsl:apply-templates select="@*|*|text()" />
		</xsl:copy>
	</xsl:template>
	-->

 	<xsl:template match="text()"><xsl:value-of select="."/></xsl:template>
	
	<!-- normalize whitespace -->
 	<xsl:template match="text()[normalize-space(.)='']"><xsl:text></xsl:text></xsl:template>

<!--  unmatched tags -->
	<xsl:template match="*" >
	    <div>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			<xsl:apply-templates />
	    </div>
	</xsl:template>

<!--  COPY (must relearn <xs:copy> ) -->
	<xsl:template match="
		*[local=name()='p'] | 
		*[local=name()='sup'] | 
		*[local=name()='tr'] | 
		*[local=name()='td'] | 
		*[local=name()='table'] | 
		*[local=name()='thead'] | 
		*[local=name()='caption'] | 
		*[local=name()='col'] | 
		*[local=name()='colgroup'] " >
		<xsl:element name="{local-name()}">
			<xsl:for-each select="@*"><xsl:attribute name="{local-name()}"><xsl:value-of select="."/></xsl:attribute></xsl:for-each>
			<xsl:apply-templates select="*|text()" />
		</xsl:element>
	</xsl:template>

<!--  SPANS, mainly in references -->
	<xsl:template match="*[local-name()='given-names'] |
	 	*[local-name()='surname'] |
	 	*[local-name()='year'] |
	 	*[local-name()='article-title'] |
	 	*[local-name()='source'] |
	 	*[local-name()='volume'] |
	 	*[local-name()='fpage'] |
	 	*[local-name()='lpage']" >
		<span tagx="{local-name()}"><xsl:apply-templates select="*|text()" /></span>
	</xsl:template>

<!--  IGNORES -->
	<xsl:template match="*[local-name()='contrib-group' and *[local-name()='contrib' and @contrib-type='editor']]" />

	<xsl:template match="*[local-name()='art']">
		<xsl:apply-templates />
	</xsl:template>
	
	<xsl:template match="*[local-name()='front']">
	    <head>
			<xsl:apply-templates />
	    </head>
	</xsl:template>

	<xsl:template match="*[local-name()='front']/*[local-name()='journal-meta']">
	  <meta name="citation_publisher" content="{*[local-name()='publisher']/*[local-name()='publisher-name']}"></meta>
	</xsl:template>
	
	<xsl:template match="*[local-name()='front']/*[local-name()='article-meta']">
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

	<xsl:template match="*[local-name()='contrib' and @contrib-type='author']">
	  <meta name="citation_author" content="{concat(*[local-name()='name']/*[local-name()='given-names'],
	    ' ', *[local-name()='name']/*[local-name()='surname'])}"/>
	  <meta name="citation_author_institution_ref" content="{*[local-name()='xref']/*[local-name()='sup']}"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='aff']">
	  <meta name="citation_author_institution" 
	      content="{concat(*[local-name()='label'],' ',*[local-name()='addr-line'])}"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='body']">
	  <xsl:apply-templates select="/*[local-name()='article']/*[local-name()='front']/
	      *[local-name()='article-meta']/*[local-name()='abstract']"/>
	  <xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='abstract']">
	    <div id="abstract" tag="abstract">
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
	
	<xsl:template match="*[local-name()='ref-list']">
	    <div tag="ref-list"><ul><xsl:apply-templates  select="*|text()"/></ul></div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='ref']">
	    <li tag="ref"><xsl:apply-templates  select="*|text()"/></li>
	</xsl:template>
	
	<xsl:template match="*[local-name()='sec']">
	    <div id="{@id}"><xsl:apply-templates select="* | text()"/></div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='underline']">
	    <u><xsl:apply-templates select="* | text()"/></u>
	</xsl:template>
	
	<xsl:template match="*[local-name()='xref']">
 	    <span rid="{@rid}" ref-type="{@ref-type}"><xsl:apply-templates select="* | text()"/></span>
	</xsl:template>
	
</xsl:stylesheet>
