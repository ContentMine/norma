<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xhtml"/>
    
	<xsl:template match="/">
		<xsl:apply-templates />
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

	<xsl:template match="*" >
	    <div>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			<xsl:apply-templates />
	    </div>
	</xsl:template>

<!--  COPY (must relearn <xs:copy> ) 
	<xsl:template match="p |
	    sup | sub |
	    tr | td | table | thead | tbody | tgroup | colspec | caption | col | colgroup " >
		<xsl:element name="{local-name()}">
			<xsl:for-each select="@*"><xsl:attribute name="{local-name()}"><xsl:value-of select="."/></xsl:attribute></xsl:for-each>
			<xsl:apply-templates select="*|text()" />
		</xsl:element>
	</xsl:template>
-->
<!--  COPY -->
	<xsl:template match="p |
	    sup | sub |
	    tr | td | table | thead | tbody | tgroup | colspec | caption | col | colgroup " >
		<xsl:copy>
			<xsl:apply-templates select="*|text()" />
		</xsl:copy>
	</xsl:template>

<!--  SPANS, mainly in references -->
	<xsl:template match="name | given-names | surname | year | article-title | source | volume | fpage | lpage | 
	     source | issn | pubdate | issue | history/rec | history/acc | history/pub | date/day | date/month |
	     date/year | snm | fnm | mnm | insr | mi | pubid |
	     abbrgrp | abbr | tblr" >
		<span tagx="{local-name()}"><xsl:apply-templates select="*|text()" /></span>
	</xsl:template>

<!--  LINKS, mainly in references -->
	<xsl:template match=" url | email" >
		<a href="{.}"><xsl:value-of select="."/></a>
	</xsl:template>

<!--  LISTS -->
	<xsl:template match="kwdg | insg | aug" >
		<ul tagx="{local-name()}"><xsl:apply-templates/></ul>
	</xsl:template>
	<xsl:template match="kwd | ins | au" >
		<li tagx="{local-name()}"><xsl:apply-templates/></li>
	</xsl:template>

<!--  TABLES -->
	<xsl:template match="row" >
		<tr><xsl:apply-templates/></tr>
	</xsl:template>
	<xsl:template match="entry" >
		<td><xsl:apply-templates/></td>
	</xsl:template>

<!--  IGNORES -->
	<xsl:template match="contrib-group/contrib[@contrib-type='editor']" />

	<xsl:template match="art">
	    <html>
			<xsl:apply-templates />
	    </html>
	</xsl:template>
	
	<xsl:template match="fm">
	    <head>
			<xsl:apply-templates />
	    </head>
	</xsl:template>

	<xsl:template match="front/journal-meta">
	  <meta name="citation_publisher" content="{publisher/publisher-name}"></meta>
	</xsl:template>
	
	<xsl:template match="front/article-meta">
	  <meta name="citation_doi" content="{article-id[@pub-id-type='doi']}"></meta>
	  <meta name="citation_title" content="{title-group/article-title}"></meta>
	  <title><xsl:value-of select="title-group/article-title"/></title>
	  <xsl:apply-templates select="contrib-group"/>
	  <meta name="email" content="{author-notes/corresp/email}"/>
	  <meta name="conflict" content="{author-notes/fn[@fn-type='conflict']}"/>
	  <meta name="contribs" content="{author-notes/fn[@fn-type='con']}"/>
	  <meta name="citation_pubdate" content="{concat(pub-date[@pub-type='epub']/year,'-',pub-date[@pub-type='epub']/month,'-',pub-date[@pub-type='epub']/day)}"/>
	  <meta name="citation_copyright" content="{concat('Copyright',' ',permissions/copyright-year,': ',permissions/copyright-holder)}"/>
	  <meta name="citation_licence" content="{concat('Licence',' ',permissions/license)}"/>
	  <meta name="citation_funding" content="{funding-group}"/>
	  <meta name="citation_pagecount" content="{counts/page-count/@count}"/>
	  <meta name="citation_data" content="{custom-meta-group/custom-meta[@id='data-availability']}"/>
	</xsl:template>

	<xsl:template match="contrib[@contrib-type='author']">
	  <meta name="citation_author" content="{concat(name/given-names,' ', name/surname)}"/>
	  <meta name="citation_author_institution_ref" content="{xref/sup}"/>
	</xsl:template>
	
	<xsl:template match="aff">
	  <meta name="citation_author_institution" content="{concat(label,' ',addr-line)}"/>
	</xsl:template>
	
	<xsl:template match="bdy">
	  <xsl:apply-templates select="/art/fm/abs"/>
	  <xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="abs">
	    <div id="abstract" tag="abstract">
			<xsl:apply-templates select="*|text()"/>
	    </div>
	</xsl:template>
	
	<xsl:template match="strong | bold | b">
	    <b><xsl:apply-templates  select="*|text()"/></b>
	</xsl:template>
	
	<xsl:template match="em | italic | i | it">
	    <i><xsl:apply-templates select="*|text()"/></i>
	</xsl:template>
	
	<xsl:template match="*[not(self::sec)]/sec/st/p">
	    <h2><xsl:apply-templates /></h2>
	</xsl:template>
	
	<xsl:template match="*[not(self::sec)]/sec[st]/sec/st/p">
	    <h3><xsl:apply-templates select="*|text()"/></h3>
	</xsl:template>
	
	<xsl:template match="*[not(self::sec)]/sec[st]/sec[st]/sec/st/p">
	    <h4><xsl:apply-templates select="*|text()"/></h4>
	</xsl:template>
	
	<xsl:template match="sec/sec[st]/sec[st]/sec/st/p">
	    <h5><xsl:apply-templates select="*|text()"/></h5>
	</xsl:template>
	
	<xsl:template match="ref-list">
	    <div tag="ref-list"><ul><xsl:apply-templates  select="*|text()"/></ul></div>
	</xsl:template>
	
	<xsl:template match="ref">
	    <li tag="ref"><xsl:apply-templates  select="*|text()"/></li>
	</xsl:template>
	
	<xsl:template match="sec">
	    <div id="{@id}"><xsl:apply-templates select="* | text()"/></div>
	</xsl:template>
	
	<xsl:template match="underline">
	    <u><xsl:apply-templates select="* | text()"/></u>
	</xsl:template>
	
	<xsl:template match="xref">
 	    <span rid="{@rid}" ref-type="{@ref-type}"><xsl:apply-templates select="* | text()"/></span>
	</xsl:template>
	
</xsl:stylesheet>
