<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:mml="http://www.w3.org/1998/Math/MathML"
	xmlns:h="http://www.w3.org/1999/xhtml">

    <xsl:output method="xhtml"/>
    
    <xsl:variable name="doiroot">https://dx.doi.org/</xsl:variable>
    <xsl:variable name="nlmroot">http://www.ncbi.nlm.nih.gov/pubmed/</xsl:variable>    
	<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'" />
	<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
    <xsl:variable name="apos">'</xsl:variable>
    <xsl:variable name="pipe">|</xsl:variable>
    <xsl:variable name="under">_</xsl:variable>
    
	<xsl:template match="/">
	  <html>
		<xsl:apply-templates />
	  </html>
	</xsl:template>

 	<xsl:template match="text()"><xsl:value-of select="."/></xsl:template>
	
	<!-- normalize whitespace -->
 	<xsl:template match="text()[normalize-space(.)='']"><xsl:text></xsl:text></xsl:template>

<!--  unmatched tags -->
	<xsl:template match="*" >
	    <div class="unknown" title="{local-name()}">
	    <xsl:message>UNKNOWN: <xsl:value-of select="local-name()"/>: <xsl:value-of select="."/></xsl:message>
	        <xsl:attribute name="tagxxx"><xsl:value-of select="local-name()"/></xsl:attribute>
			<xsl:apply-templates />
	    </div>
	</xsl:template>

<!--  HTML5 -->
	<xsl:template match="
		*[local-name()='caption'] | 
		*[local-name()='col'] | 
		*[local-name()='colgroup'] |
		*[local-name()='graphic'] | 
		*[local-name()='hr'] | 
		*[local-name()='p'] | 
		*[local-name()='sc'] | 
		*[local-name()='sub'] | 
		*[local-name()='sup'] | 
		*[local-name()='table'] | 
		*[local-name()='table-wrap'] | 
		*[local-name()='table-wrap-foot'] | 
		*[local-name()='tbody'] | 
		*[local-name()='td'] | 
		*[local-name()='tfoot'] | 
		*[local-name()='th'] | 
		*[local-name()='th'] | 
		*[local-name()='thead'] | 
		*[local-name()='tr'] 
		" >
        <xsl:copy>
           <xsl:apply-templates select=" * | text()" />
        </xsl:copy>
        </xsl:template>


<!--  NON-HTML DIV -->
	<xsl:template match="
	 	*[local-name()='article-meta'] |
		*[local-name()='boxed-text'] | 
		*[local-name()='citation'] | 
		*[local-name()='bio'] | 
		*[local-name()='conference'] | 
		*[local-name()='title'] | 
		*[local-name()='floats-wrap'] | 
	 	*[local-name()='journal-meta'] |
	 	*[local-name()='journal-title-group'] |
	 	*[local-name()='table-wrap-group'] 
		">
		<div tagx="{local-name()}" class="{local-name()}" title="{local-name()}"><xsl:apply-templates select="*|text()" /></div>
	</xsl:template>

<!--  SPANS, -->
	<xsl:template match="
	 	*[local-name()='article-title'] |
	 	*[local-name()='attrib'] |
	 	*[local-name()='award-id'] |
	 	*[local-name()='chapter-title'] |
	 	*[local-name()='citation-author'] |
	 	*[local-name()='conf-date'] |
	 	*[local-name()='conf-loc'] |
	 	*[local-name()='conf-name'] |
	 	*[local-name()='conf-sponsor'] |
	 	*[local-name()='contrib-id'] |
	 	*[local-name()='copyright-holder'] |
	 	*[local-name()='copyright-statement'] |
	 	*[local-name()='copyright-year'] |
	 	*[local-name()='count'] |
	 	*[local-name()='country'] |
	 	*[local-name()='date-in-citation'] |
	 	*[local-name()='degrees'] |
	 	*[local-name()='edition'] |
	 	*[local-name()='email'] |
	 	*[local-name()='fax'] |
	 	*[local-name()='phone'] |
	 	*[local-name()='abbrev-journal-title'] |
	 	*[local-name()='journal-title'] |
	 	*[local-name()='mixed-citation'] |
	 	*[local-name()='access-date'] |
	 	*[local-name()='publisher-loc'] |
	 	*[local-name()='related-article'] |
	 	*[local-name()='related-object'] |
	 	*[local-name()='role'] |
	 	
	 	*[local-name()='day'] |
	 	*[local-name()='month'] |
	 	*[local-name()='year'] |
	 	
	 	*[local-name()='conf-name'] |
	 	*[local-name()='conf-loc'] |
	 	*[local-name()='conf-date'] |
	 	*[local-name()='on-behalf-of'] |
	 	*[local-name()='on-behalf-of'] |
	 	*[local-name()='publisher-name'] |
	 	*[local-name()='source'] |
	 	*[local-name()='series-title'] |
	 	*[local-name()='subtitle'] |
	 	*[local-name()='trans-source'] |
	 	*[local-name()='volume'] |
	 	*[local-name()='volume-id'] |
	 	*[local-name()='supplement'] |
	 	*[local-name()='issue'] |
	 	*[local-name()='issue-id'] |
	 	*[local-name()='issue-title'] |
	 	*[local-name()='elocation'] |
	 	*[local-name()='elocation-id'] |
	 	*[local-name()='fpage'] |
	    *[local-name()='given-names'] |
	    *[local-name()='series'] |
	    *[local-name()='suffix'] |
	    *[local-name()='styled-content'] |
		*[local-name()='label'] | 
		*[local-name()='name'] | 
	 	*[local-name()='surname'] |
	 	*[local-name()='season'] |
	 	*[local-name()='year'] |
	 	
	 	*[local-name()='inline-supplementary-material'] |
	 	*[local-name()='note'] |
	 	*[local-name()='trans-abstract'] |
	 	*[local-name()='trans-title'] |
	 	*[local-name()='trans-title-group'] |
	 	*[local-name()='patent'] |
	 	*[local-name()='target'] |
	 	
	 	*[local-name()='lpage']" >
		<span tagx="{local-name()}" class="{local-name()}" title="{local-name()}"><xsl:apply-templates select="*|text()" /></span>
	</xsl:template>

	<xsl:template match="*[local-name()='art']">
		<xsl:apply-templates />
	</xsl:template>
	
	<xsl:template match="*[local-name()='article']">
	    <head>
      	    <style type="text/css">
      	     		 
      	    a                          {background : #ffffff; }
      	    article                    {border-style : dotted; border-width : 2px; }
		 	
		 	div                        {background : #ffffcc;}
		 	div.abstract-title         {font-weight : bold ; font-size : 16pt;}
		 	div.ack                    {border-style : solid ; border-color : red; margin : 2em; }
		 	div.article-meta           {border-style : solid; border-width : 1 pt; margin 1em;}
		 	div.boxed-text             {margin : 5em; border-style : solid;}
		 	div.contrib-group          {margin : 1em; }
		 	div.fig                    {border-style : solid; border-width : 2px; margin : 2em; }
		 	div.funding                {font-weight : bold ; font-size : 16pt;}
		 	div.given-names            {font-style : italic;}
		 	div.intro                  {border-style : inset; margin : 5px;}
		 	div.introduction           {border-style : inset; margin : 5px;}
		 	div.journal-meta           {border-style : solid; border-width : 1 pt; margin 1em;}
		 	div.journal-title-group    {background : #ffeeee;}
		 	div.article-title          {font-weight : bold ; font-size : 18pt;}
		 	div.kwd                    {font-style : italic}
		 	div.meta-name              {font-weight : bold ; font-size : 16pt;}
		 	div.name                   {font-weight : bold;}
		 	div.alt-title              {font-style : italic; font-size : 12px;}
		 	
		 	div.sec                    {border : 2px; margin 5px; padding 2px;}
		 	div.title                  {font-family : courier; font-weight : bold;
		 	                            font-size : 16pt; margin : 5px;}
		 	
		 	div.materials_methods      {border-style : double; margin : 5px; }
		 	div.methods                {border-style : double; margin : 5px; }
		 	
		 	div.results                {border-style : solid; margin : 5px;}
		 	div.background             {border-style : dotted; margin : 5px;}
		 	
		 	div.discussion             {border-style : groove; margin : 5px;}
		 	div.conclusion             {border-style : ridge; margin : 5px;}
		 	div.conclusions             {border-style : ridge; margin : 5px;}
		 	div.supplementary-material {border-style : inset; margin : 5px;}
		 	div.abbreviations          {border-style : double; border-color : red; margin : 5px;}
		 	div.competinginterests     {border-style : double; border-color : blue; margin : 5px;}
		 	div.acknowledgements       {border-style : double; border-color : green; margin : 5px;}
		 	div.authors_contributions   {border-style : double; border-color : purple; margin : 5px;}
		 	
		 	div.publisher              {border-style : outset; margin : 5px;}
		 	div.fn-type-conflict       {background : #f88; }
		 	div.fn-type-con            {background : #ddf; }
		 	div.fn-type-other          {background : #ddd; }
		 	
		 	div.unknown                {background : #ffd;
									 	  border-style : solid;
									 	  border-width : 1px;
									 	  padding : 2 px;}
		 	  
      	    table                      {background : #ffffdd;}
		 	tr                         {background : #ddddff; padding : 1px;}
		 	
		 	span                       {background : #ffcccc;}
		 	
		 	span.citation-author       {font-family : helvetica; background : #ffeeee;}
		 	span.collab                {background : #ddffff; }
		 	span.comment               {font-family : courier; font-size : 6px; background : #ffaaff;}
		 	span.contrib               {background : #ffffff;}
		 	span.corresp               {background : #ddffdd; }
		 	span.doi                   {background : #ffffff;}
		 	span.email                 {font-family : courier; }
		 	span.etal                  {font-style : italic;}
		 	span.fpage                 {font-family : courier;}
		 	span.given-names           {background : #ffffff;}
		 	span.iso-abbrev            {background : #ffffff;}
		 	span.issn-epub             {background : #ffffff;}
		 	span.issn-ppub             {background : #ffffff;}
		 	span.journal-title         {background : #ffffff;}
		 	span.lpage                 {font-family : courier;}
		 	span.mixed-article-title   {font-style : italic ;}
		 	span.nlm-ta                {background : #ffffff;}
		 	span.pmc                   {background : #ffffff;}
		 	span.pmcid                 {background : #ffffff;}
		 	span.pmid                  {background : #ffffff;}
		 	span.publisher             {background : #ffffff;}
		 	span.publisher-id          {background : #ffffff;}
		 	span.publisher-name        {background : #ffffff;}
		 	span.source                {background : #ffffff;}
		 	span.subject               {background : #ffffff;}
		 	span.surname               {background : #ffffff;}
		 	span.volume                {font-family : courier; font-weight : bold;}
		 	span.year                  {font-family : courier ; font-style : italic;}
			</style>
	    </head>
	    <body>
			<xsl:apply-templates select="*[local-name()='front']"/>
			<xsl:apply-templates select="*[local-name()='body']"/>
			<xsl:apply-templates select="*[local-name()='back']"/>
	    </body>
	</xsl:template>

<!-- 
<journal-meta>
<journal-id journal-id-type="nlm-ta">PLoS Negl Trop Dis</journal-id>
<journal-id journal-id-type="iso-abbrev">PLoS Negl Trop Dis</journal-id>
<journal-id journal-id-type="publisher-id">plos</journal-id>
<journal-id journal-id-type="pmc">plosntds</journal-id>
<journal-title-group>
<journal-title>PLoS Neglected Tropical Diseases</journal-title>
</journal-title-group>
<issn pub-type="ppub">1935-2727</issn>
<issn pub-type="epub">1935-2735</issn>
<publisher>
<publisher-name>Public Library of Science</publisher-name>
<publisher-loc>San Francisco, USA</publisher-loc>
</publisher>
</journal-meta>
-->

<!--  FRONT -->
	<xsl:template match="*[local-name()='front']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

<!--  JOURNAL-meta -->
	<xsl:template match="*[local-name()='journal-id']">
	  <span class="{@journal-id-type}" title="{@journal-id-type}"><xsl:value-of select="."/></span>
	</xsl:template>
	
	<xsl:template match="
	 	*[local-name()='journal-meta'] |
	 	*[local-name()='journal-title-group'] 
		">
		<div tagx="{local-name()}" class="{local-name()}" title="{local-name()}"><xsl:apply-templates select="*|text()" /></div>
	</xsl:template>

	<xsl:template match="
	 	*[local-name()='issn'] 
		">
		<span tagx="{local-name()}" class="{local-name()}-{@pub-type}" title="{local-name()}-{@pub-type}"><xsl:apply-templates select="*|text()" /></span>
	</xsl:template>

<!--  copy divs -->
	<xsl:template match="
	 	*[local-name()='publisher'] 
		">
		<div tagx="{local-name()}" class="publisher" title="publisher"><xsl:apply-templates select="*|text()" /></div>
	</xsl:template>

<!--  ARTICLE meta -->
<!-- 
<article-meta>
<article-id pub-id-type="pmcid">4288720</article-id>
<article-id pub-id-type="pmid">25569210</article-id>
<article-id pub-id-type="publisher-id">PNTD-D-14-01562</article-id>
<article-id pub-id-type="doi">10.1371/journal.pntd.0003414</article-id>
 -->
	<xsl:template match="*[local-name()='article-id']" priority="0.5">
	  <span class="{@pub-id-type}" title="{@pub-id-type}"><xsl:value-of select="."/></span>
	</xsl:template>

	<xsl:template match="*[local-name()='article-id' and @pub-id-type='doi']"
		priority="0.6">
		<span class="{@pub-id-type}" title="{@pub-id-type}">
			doi:
			<xsl:call-template name="makeurl">
				<xsl:with-param name="urlroot" select="$doiroot" />
			</xsl:call-template>
		</span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='article-id' and @pub-id-type='pmcid']"
		priority="0.6">
		<span class="{@pub-id-type}" title="{@pub-id-type}">
			pmcid:
			<xsl:call-template name="makeurl">
				<xsl:with-param name="urlroot" select="$nlmroot" />
			</xsl:call-template>
		</span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='article-id' and @pub-id-type='pmid']"
		priority="0.6">
		<span class="{@pub-id-type}" title="{@pub-id-type}">
			pmid:
			<xsl:call-template name="makeurl">
				<xsl:with-param name="urlroot" select="$nlmroot" />
			</xsl:call-template>
		</span>
	</xsl:template>

<!-- 
<article-categories>
<subj-group subj-group-type="heading">
<subject>Research Article</subject>
</subj-group>
<subj-group subj-group-type="Discipline-v2">
<subject>Biology and Life Sciences</subject>
<subj-group>
<subject>Immunology</subject>
</subj-group>
</subj-group>
 -->
 	<xsl:template match="*[local-name()='article-categories']">
	  <div class="{local-name()}" title="{local-name()}"><xsl:apply-templates/></div>
	</xsl:template>
 
	<xsl:template match="*[local-name()='subj-group']">
	  <xsl:choose>
		  <xsl:when test="*[local-name()='subj-group']">
		    <div class="sub-group" title="subj-group">
		      <xsl:text>: </xsl:text>
		      <xsl:apply-templates/>
		    </div>
		  </xsl:when>
		  <xsl:otherwise>
		      <xsl:text>: </xsl:text>
		      <xsl:apply-templates select="*[local-name()='subject']"/>
		  </xsl:otherwise>
	  </xsl:choose>
	</xsl:template>
	<xsl:template match="*[local-name()='subject']">
	  <span class="subject" title="subject"><xsl:apply-templates/></span>
	</xsl:template>

<!-- 	
	<title-group>
		<article-title>
			Hyperreactive Onchocerciasis is Characterized by a Combination of Th17-Th2 Immune
			Responses and Reduced Regulatory T Cells
		</article-title>
		<alt-title alt-title-type="running-head">Th17/Th2 and Pathological
			Onchocerciasis</alt-title>
	</title-group>
	-->
	<xsl:template match="*[local-name()='title-group']">
	  <div class="title-group">
	    <xsl:apply-templates/>
	  </div>
	</xsl:template>

	<xsl:template match="
	    *[local-name()='article-title'] |
		*[local-name()='alt-title']">
	  <div class="{local-name()}" title="{local-name()}"><xsl:apply-templates/></div>
	</xsl:template>

<!-- 
<kwd-group xml:lang="en">
<title>Keywords</title>
<kwd>Microbes</kwd>
<kwd>Religious rituals</kwd>
<kwd>Microbiome-brain axis</kwd>
<kwd>Biomeme hypothesis</kwd>
<kwd>Popper</kwd>
<kwd>Falsifiability</kwd>
<kwd>Occam’s razor</kwd>
</kwd-group>
 -->
	<xsl:template match="*[local-name()='kwd-group']">
	  <div class="kwd-group">
	    <xsl:apply-templates/>
	  </div>
	</xsl:template>

	<xsl:template match="*[local-name()='kwd']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>
 
 
<!--  
	<contrib-group>
		<contrib contrib-type="author">
			<name>
				<surname>Katawa</surname>
				<given-names>Gnatoulma</given-names>
			</name>
			<xref ref-type="aff" rid="aff1">
				<sup>1</sup>
			</xref>
			<xref ref-type="aff" rid="aff2">
				<sup>2</sup>
			</xref>
			<xref ref-type="author-notes" rid="fn1">
				<sup>‡</sup>
			</xref>
		</contrib>
-->	
	<!--  xref ref-type="aff" rid="AF0001">1</xref> -->
	<xsl:template match="*[local-name()='xref' and @rid]">
	  <a href="#{@rid}"><xsl:apply-templates/></a>
	</xsl:template>

	<xsl:template match="
	    *[local-name()='contrib-group']">
	  <div class="{local-name()}" title="{local-name()}"><xsl:apply-templates/></div>
	</xsl:template>
	
	<xsl:template match="
	    *[local-name()='contrib']">
	  <span class="{local-name()}" title="{local-name()}"><xsl:apply-templates/></span>
	</xsl:template>

<!--  AFFILIATIONS -->
<!-- 	
<aff id="aff1">
	<label>1</label>
	<addr-line>
		Institute of Medical Microbiology, Immunology and Parasitology (IMMIP),
		University Hospital Bonn, Bonn, Germany
	</addr-line>
</aff>	
-->
<!--  	
<address>
<email>petro.starokadomskyy@utsouthwestern.edu</email>
</address>
-->

	
	<xsl:template match="*[local-name()='aff']">
	  <span id="{@id}" class="citation_author_institution"><xsl:text>[</xsl:text><xsl:value-of select="*[local-name()='label']"/><xsl:text>]</xsl:text>,
	  <xsl:apply-templates select="*[not (local-name()='label')]"/></span>
	</xsl:template>

	<xsl:template match="*[local-name()='address']">
	  <span class="address" title="address"><xsl:apply-templates/></span>
	</xsl:template>

	<xsl:template match="*[local-name()='addr-line']">
	  <span class="addr-line" title="addr-line"><xsl:apply-templates/></span>
	</xsl:template>

<!--  institution -->
	<xsl:template match="*[local-name()='institution-wrap']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>
	
	<xsl:template match="
	*[local-name()='institution-id'] |
	*[local-name()='institution']
	">
	  <span class="{local-name()}" title="{local-name()}">
	    <xsl:apply-templates/>
	  </span>
	</xsl:template>

<!-- 
	<author-notes>
		<corresp id="cor1">
			* E-mail:
			<email>hoerauf@microbiology-bonn.de</email>
		</corresp>
		<fn fn-type="conflict">
			<p>
				The authors have declared that no competing interests exist.
			</p>
		</fn>
		-->
	<xsl:template match="*[local-name()='author-notes']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

	<xsl:template match="*[local-name()='corresp'] ">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>



<!-- 
<pub-date pub-type="collection">
	<month>1</month>
	<year>2015</year>
</pub-date>
	<pub-date pub-type="epub">
		<day>8</day>
		<month>1</month>
		<year>2015</year>
	</pub-date>
	<volume>9</volume>
	<issue>1</issue>
	<elocation-id>e3414</elocation-id>
	<history>
		<date date-type="received">
			<day>10</day>
			<month>9</month>
			<year>2014</year>
		</date>
		<date date-type="accepted">
			<day>12</day>
			<month>11</month>
			<year>2014</year>
		</date>
	</history>
-->

	<xsl:template match="*[local-name()='pub-date']">
	  <span class="pub-date-{@pub-type}" title="pub-date-{@pub-type}"><xsl:value-of select="@pub-type"/>: <xsl:call-template name="isodate"/></span>
	</xsl:template>
	
	<!-- the volume, issue, elocation are covered above -->
	
	<xsl:template match="*[local-name()='history']">
	  <span class="history" title="history"><xsl:apply-templates/></span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='date']">
	  <span class="{@date-type}" title="{@date-type}"><xsl:value-of select="@date-type"/>: <xsl:value-of select="year"/>-<xsl:value-of select="month"/>-<xsl:value-of select="day"/></span>
	</xsl:template>

<!-- 
	<permissions>
		<copyright-year>2015</copyright-year>
		<copyright-holder>Katawa et al</copyright-holder>
		<license>
			<license-p>
				This is an open-access article distributed under the terms of the
				<ext-link ext-link-type="uri"
					xlink:href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Attribution License</ext-link>
				, which permits unrestricted use, distribution, and reproduction in
				any medium, provided the original author and source are credited.
			</license-p>
		</license>
	</permissions>
-->	
	<xsl:template match="*[local-name()='permissions']">
	  <div class="permissions">
	  <span class="copyright" title="copyright">(C) <xsl:apply-templates select="copyright-holder"/>, <xsl:apply-templates select="copyright-year"/></span>
	  <xsl:apply-templates select="*[local-name()='license']"/>
	  </div>
	</xsl:template>

	<xsl:template match="*[local-name()='license']">
	  <span class="license" title="license"><xsl:apply-templates/></span>
	</xsl:template>

	<xsl:template match="*[local-name()='license-p']">
	  <span class="license-p" title="license-p"><xsl:apply-templates/></span>
	</xsl:template>

<!-- LINKS -->
	<xsl:template match="*[local-name()='ext-link']">
	  <a href="{@xlink:href}"><xsl:apply-templates/></a>
	</xsl:template>
	
	<!-- 
	<object-id pub-id-type="doi">10.1371/journal.pone.0121780.g002</object-id> 
	<object-id pub-id-type="pii">viruses-04-01592-t001_Table 1</object-id>
	 -->
	<xsl:template match="*[local-name()='object-id']">
	  <xsl:choose>
	    <xsl:when test="@pub-id-type='doi'">
	      <xsl:call-template name="makeurl">
	        <xsl:with-param name="urlroot" select="$doiroot"></xsl:with-param>
	      </xsl:call-template>
	    </xsl:when>
	    <xsl:when test="@pub-id-type='pii'">
	    <!--  don't know what else to do -->
	      <xsl:apply-templates/>
	    </xsl:when>
	    <xsl:when test="@pub-id-type='pmid'">
	      <xsl:call-template name="makeurl">
	        <xsl:with-param name="urlroot" select="$nlmroot"></xsl:with-param>
	      </xsl:call-template>
	    </xsl:when>
	    <xsl:when test="@pub-id-type='publisher-id'">
	      <!--  not much we can do here -->
	      <xsl:apply-templates/>
	    </xsl:when>
	    <xsl:when test="@pub-id-type='other'">
	      <!--  not much we can do here -->
	      <xsl:apply-templates/>
	    </xsl:when>
	    <xsl:otherwise>
	      <xsl:message>UNKNOWN OBJECT-ID TYPE: <xsl:value-of select="@pub-id-type"/> : <xsl:value-of select="."/></xsl:message>
	    </xsl:otherwise>
	  </xsl:choose>
	</xsl:template>

	<!-- 
<self-uri xlink:href="http://www.virologyj.com/content/10/1/58"/>
	 -->
	<xsl:template match="*[local-name()='self-uri']">
	  <a class="self-uri" title="self-uri" href="{@xlink:href}"><xsl:value-of select="@xlink:href"/></a>
	  </xsl:template>
	
<!-- 
<abstract>
	<p>
		Clinical manifestations in onchocerciasis range from generalized
		onchocerciasis (GEO) to the rare but severe hyperreactive (HO)/sowda
		...
		Th2 cells form part of the immune network instigating the development
		of severe onchocerciasis.
	</p>
</abstract>
-->

	
	<xsl:template match="*[local-name()='abstract']"  priority="0.5">
	  <div class="abstract" title="abstract">
	    <div class="abstract-title" title="abstract-title">Abstract</div>
	  <xsl:apply-templates/>
	  </div>
	</xsl:template>

<!-- 
	<abstract abstract-type="summary">
		<title>Author Summary</title>
		<p>
			Onchocerciasis, also known as river blindness is a tropical disease
			causing health and socioeconomic problems in endemic communities
			...
			elevated frequencies of Th17 and Th2 cells form part of the immune
			network associated with severe onchocerciasis.
		</p>
	</abstract>
-->	
	<xsl:template match="*[local-name()='abstract' and @abstract-type]" priority="0.6">
	  <div class="abstract" title="abstract">
	  <!-- 
	    <div class="abstract-title" title="abstract-title"><xsl:apply-templates select="*[local-name()='title']"/></div>
	    -->
	  <xsl:apply-templates/>
	  </div>
	</xsl:template>

<!-- 
<funding-group>
	<funding-statement>
		This work was primarily supported through a grant from the German Research
		Council (DFG Ho2009/8-2). The study was further supported by the
		...
		collection and analysis, decision to publish, or preparation of the
		manuscript.
	</funding-statement>
</funding-group>
-->
	<xsl:template match="*[local-name()='funding-group']" >
	  <div class="funding-group" title="funding-group">
	  <div class="funding" title="funding">Funding</div>
	  <xsl:apply-templates/>
	  </div>
	</xsl:template>
	
	<xsl:template match="
	  *[local-name()='award-group']
	  " >
	  <div class="{local-name()}" title="{local-name()}">
	  <xsl:apply-templates/>
	  </div>
	</xsl:template>

	<xsl:template match="
	  *[local-name()='funding-statement'] |
	  *[local-name()='funding-source']
	  " >
	  <div class="{local-name()}" title="{local-name()}">
	  <xsl:apply-templates/>
	  </div>
	</xsl:template>

	<xsl:template match="
	  *[local-name()='principal-award-recipient']
	  " >
	  <span class="{local-name()}" title="{local-name()}">
	  <xsl:apply-templates/>
	  </span>
	</xsl:template>

<!-- 
<custom-meta-group>
	<custom-meta id="data-availability">
		<meta-name>Data Availability</meta-name>
		<meta-value>
			The authors confirm that all data underlying the findings are fully
			available without restriction. All relevant data are within the paper
			and its Supporting Information files.
		</meta-value>
	</custom-meta>
</custom-meta-group>
-->

	<xsl:template match="*[local-name()='custom-meta-group']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='custom-meta']">
  	  <meta name="{*[local-name()='meta-name']}" value="{*[local-name()='meta-value']}"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='custom-meta-wrap']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>


<!-- 
	<notes>
		<title>Data Availability</title>
		<p>
			The authors confirm that all data underlying the findings are fully
			available without restriction. All relevant data are within the paper
			and its Supporting Information files.
		</p>
	</notes>
	-->
	
	<xsl:template match="*[local-name()='notes']">
	<xsl:apply-templates/>
	</xsl:template>
	
<!-- 	
<fig id="pntd-0003414-g001" orientation="portrait" position="float">
	<object-id pub-id-type="doi">10.1371/journal.pntd.0003414.g001</object-id>
	<label>Figure 1</label>
	<caption>
		<title>
			Higher frequencies of monocytes and memory T cells in HO individuals.
		</title>
		<p>
			Isolated PBMCs from EN and individuals presenting either generalized
			(GEO) or hyperreactive onchocerciasis (HO) were stained with a
			combination of antibodies to determine the frequencies of CD8
			<sup>+</sup>
			T cells (A); NK cells [CD3
			...
			T cells (H) and naive CD4
			<sup>+</sup>
			T cells (I). Graphs show box whiskers (tukey) with outliers from EN n
			= 10, GEO n = 10 and HO n = 6. Asterisks show statistical differences
			(Kruskal-Wallis and Mann Whitney test) between the groups indicated
			by the brackets (*p<0.05, **p<0.01).
		</p>
	</caption>
	<graphic xlink:href="pntd.0003414.g001" />
</fig>
	 -->
		
	<xsl:template match="*[local-name()='fig']">
	 <div class="fig" title="fig">
	  <div class="figure" title="figure">
	    <a href="http://doi.org/{*[local-name()='object-id' and @pub-id-type='doi']}"><xsl:apply-templates select="*[local-name()='label']"/></a>
	  </div>
	  <caption class="caption" title="caption"><xsl:apply-templates select="*[local-name()='caption']"/></caption>
	  <!--  the <graphic> links to files we don't have -->
      </div>
	</xsl:template>

	<xsl:template match="*[local-name()='fig-group']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

<!-- 
	<sec sec-type="supplementary-material" id="s5">
		<title>Supporting Information</title>
		<supplementary-material content-type="local-data"
			id="pntd.0003414.s001">
			<label>S1 Fig</label>
			<caption>
				<p>
					<bold>
						Optimal time point for collecting cell culture supernatants.
					</bold>
					Thawed PBMCs (1×10
					<sup>5</sup>
					/well) from
					...
					were then measured by ELISA. Bars represent mean ± SD of cytokines
					levels.
				</p>
				<p>(TIF)</p>
			</caption>
			<media xlink:href="pntd.0003414.s001.tif">
				<caption>
					<p>Click here for additional data file.</p>
				</caption>
			</media>
		</supplementary-material>
	</sec>
-->	

    <!-- use existing sec-type, and remove non-alpha chars -->
	<xsl:template match="*[local-name()='sec' and @sec-type]">
	  <div class="{translate(translate(@sec-type,$pipe,$under),$apos,$under)}" title="{@sec-type}">
	    <xsl:apply-templates/>
	  </div>
	</xsl:template>

	<xsl:template match="*[local-name()='sec' and not(@sec-type)]">
	  <div class="sec" title="sec">
	    <xsl:if test="*[local-name()='title']">
	      <xsl:attribute name="class"><xsl:value-of 
	      select="translate(translate(normalize-space(*[local-name()='title']),' ',''),$uppercase,$lowercase)"/></xsl:attribute>
	    </xsl:if>
	    <xsl:apply-templates/>
	  </div>
	</xsl:template>

	<xsl:template match="*[local-name()='supplementary-material']">
	  <div class="{@content-type}" title="{@content-type}">
	    <xsl:apply-templates/>
	  </div>
	</xsl:template>

	<xsl:template match="*[local-name()='media']">
	  <div class="media" title="media">
	    <a href="{@xlink:href}">LINK</a>
	    <xsl:apply-templates/>
	  </div>
	</xsl:template>

	
	<!--  BODY  -->
	
	<xsl:template match="*[local-name()='body']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

    <!-- FN -->	
	<xsl:template match="*[local-name()='fn-group']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='fn'] ">
	  <div class="fn-type-{@fn-type}" title="{@fn-type}">
	    <xsl:apply-templates/>
	  </div>
	</xsl:template>
	
	
	<!--  BACK  -->
	
	<xsl:template match="*[local-name()='back']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

<!-- 
	<ack>
		<p>
			We thank the entire Ghana-based research team for help with the
			recruitment of onchocerciasis patients.
		</p>
	</ack>
 -->	
	
	<xsl:template match="*[local-name()='ack']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>
	
	<!--  
<ref-list>
	<title>References</title>
	<ref id="pntd.0003414-Crump1">
		<label>1</label>
		<mixed-citation publication-type="journal">
			<name>
				<surname>Crump</surname>
				<given-names>A</given-names>
			</name>
			,
			<name>
				<surname>Morel</surname>
				<given-names>CM</given-names>
			</name>
			,
			<name>
				<surname>Omura</surname>
				<given-names>S</given-names>
			</name>
			(
			<year>2012</year>
			)
			<article-title>
				The onchocerciasis chronicle: from the beginning to the end?
			</article-title>
			<source>Trends Parasitol</source>
			<volume>28</volume>
			:
			<fpage>280</fpage>
			–
			<lpage>288</lpage>
			.
			<pub-id pub-id-type="pmid">22633470</pub-id>
		</mixed-citation>
	</ref>
-->
	<xsl:template match="*[local-name()='ref-list']">
	  <div class="references">References</div>
	    <div tag="ref-list"><ol><xsl:apply-templates  select="*|text()"/></ol></div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='ref']">
	    <li tag="ref"><a name="{@id}"/><xsl:apply-templates  select="*|text()"/></li>
	</xsl:template>
	
	<xsl:template match="*[local-name()='person-group']">
	    <span class="person-group'"><xsl:apply-templates/></span>
	</xsl:template>

<!-- 
	<string-name>
		<surname>Greenwood</surname>
		<given-names>BM</given-names>
	</string-name>
-->	
	<xsl:template match="*[local-name()='string-name']">
	  <xsl:call-template name="addClassTitleChildrenSpan"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='etal']">
	  <span class="{local-name()}" title="{local-name()}">
	    <i>et al.</i>
	  </span>
	</xsl:template>
	

	
	<!--  not sure what the semantics of this are -->
	<xsl:template match="*[local-name()='collab']">
	    <span class="collab'">collab: <xsl:apply-templates/></span>
	</xsl:template>
		
	
	<xsl:template match="*[local-name()='element-citation']">
	    <span class="element-citation'"><xsl:apply-templates/></span>
	</xsl:template>
	
	<xsl:template match="*[local-name()='named-content']">
	  <xsl:call-template name="addClassTitleChildrenSpan"/>
	</xsl:template>

<!-- 
<pub-id pub-id-type="doi">10.4161/auto.24544</pub-id>
<?supplied-pmid 23615436?>
<pub-id pub-id-type="pmid">23615436</pub-id>
 -->	
	<xsl:template match="*[local-name()='pub-id']">
	    <span class="pub-id">
	      <xsl:choose>
	        <xsl:when test="@pub-id-type='doi'">
			<xsl:call-template name="makeurl">
				<xsl:with-param name="urlroot" select="$doiroot" />
			</xsl:call-template>
			</xsl:when>
	        <xsl:when test="@pub-id-type='pmid'">
			<xsl:call-template name="makeurl">
				<xsl:with-param name="urlroot" select="$nlmroot" />
			</xsl:call-template>
			</xsl:when>
	      </xsl:choose>
	    </span>
	</xsl:template>
		

	<xsl:template match="*[local-name()='mixed-citation' or local-name()='element-citation']/*[local-name()='article-title']" priority="0.6">
	<span class="mixed-article-title" title="mixed-article-title" >
	    <xsl:apply-templates />
	</span>
	</xsl:template>

<!--  APPendix? -->
	<xsl:template match="*[local-name()='app-group']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='app']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>
	
	<!--  COUNTS -->
	<xsl:template match="*[local-name()='counts']">
	  <div class="{local-name()}" title="{local-name()}">
	    <xsl:apply-templates/>
	  </div>
	</xsl:template>
	
	<xsl:template match="
	*[local-name()='equation-count'] |
	*[local-name()='fig-count'] |
	*[local-name()='page-count'] |
	*[local-name()='ref-count'] |
	*[local-name()='table-count'] |
	*[local-name()='word-count'] 
	">
	  <span class="{local-name()}" title="{local-name()}">
	    <xsl:value-of select="local-name()"/>: <xsl:value-of select="count"/>
	  </span>
	</xsl:template>
	
	<!-- 
<size units="pages">158</size>
	 -->
	<xsl:template match="
	*[local-name()='size']
	">
	  <span class="{local-name()}" title="{local-name()}">
	    <xsl:value-of select="local-name()"/>: <xsl:value-of select="."/><xsl:value-of select="@units"/>
	  </span>
	</xsl:template>
	
	<!--  LINKS -->
<!-- <uri xlink:type="simple" 
xlink:href="http://creativecommons.org/licenses/by-nc/3.0/">Creative Commons Attribution Non Commercial License</uri>	
-->
	<xsl:template match="
	*[local-name()='uri']
	">
		<span class="{local-name()}" title="{local-name()}">
			<a href="{@xlink:href}">
				<xsl:apply-templates />
			</a>
		</span>
	</xsl:template>

	<!--  GENERIC  -->
	
	<xsl:template match="*[local-name()='strong'] | *[local-name()='bold'] ">
	    <b><xsl:apply-templates  select="*|text()"/></b>
	</xsl:template>
	
	<xsl:template match="*[local-name()='break']">
	    <br/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='monospace']">
	    <tt><xsl:apply-templates  select="*|text()"/></tt>
	</xsl:template>
	
	<xsl:template match="*[local-name()='em'] | *[local-name()='italic'] | *[local-name()='i']">
	    <i><xsl:apply-templates select="*|text()"/></i>
	</xsl:template>
	
	<xsl:template match="*[local-name()='underline']">
	    <u><xsl:apply-templates select="* | text()"/></u>
	</xsl:template>
	
	<xsl:template match="*[local-name()='strike']">
	    <s><xsl:apply-templates select="* | text()"/></s>
	</xsl:template>
	
	
	<!--  LIST -->
	<xsl:template match="*[local-name()='list']">
	  <xsl:choose>
	  <xsl:when test="@list-type='order'">
	    <ol><xsl:apply-templates  select="*|text()"/></ol>
	    </xsl:when>
	    <xsl:otherwise>
	    <ul><xsl:apply-templates  select="*|text()"/></ul>
	    </xsl:otherwise>
	  </xsl:choose>
	</xsl:template>
	
	<xsl:template match="*[local-name()='list-item']">
	    <li><xsl:apply-templates  select="*|text()"/></li>
	</xsl:template>
	
	
	<!--  GLOSSARY -->
	<!-- 
<glossary>
<title>Abbreviations</title>
<def-list>
<def-item>
<term>
<italic>Ae</italic>
.
</term>
<def>
<p>
<italic>Aedes</italic>
</p>
</def>
</def-item>
<def-item>	 -->

	<xsl:template match="*[local-name()='glossary'] ">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

	<xsl:template match="*[local-name()='def-list'] ">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

	<xsl:template match="*[local-name()='def-item']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

	<xsl:template match="*[local-name()='def']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

	<xsl:template match="*[local-name()='term']">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

	<xsl:template match="*[local-name()='abbrev']">
	<!-- 
	  <xsl:message>ABBREV: <xsl:value-of select="."></xsl:value-of></xsl:message>
	 -->
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

<!--  no idea what this is -->
	<xsl:template match="*[local-name()='x']">
	<!-- 
	  <xsl:message>X: <xsl:value-of select="."></xsl:value-of></xsl:message>
	  -->
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

<!-- COMMENT -->
	
	<xsl:template match="*[local-name()='comment']">
	    <span class="comment"><xsl:apply-templates/></span>
	</xsl:template>
	
	<!-- MATH -->
	
	<xsl:template match="
		mml:malignmark |
		mml:maligngroup |
		mml:math |
		mml:meta-name |
		mml:meta-value |
		mml:menclose |
		mml:mfenced |
		mml:mfrac |
		mml:mi |
		mml:mn |
		mml:mo |
		mml:mover |
		mml:mrow |
		mml:mspace |
		mml:msqrt |
		mml:mstyle |
		mml:msub |
		mml:msubsup |
		mml:msup |
		mml:mtable |
		mml:mtd |
		mml:mtext |
		mml:mtr |
		mml:munder |
		mml:munderover
		">
		<xsl:copy>
		  <xsl:apply-templates/>
		</xsl:copy>
	</xsl:template>
	
	
	<!--  MATH-LIKE ?? -->
	<xsl:template match="
	*[local-name()='annotation'] |
	*[local-name()='semantics']
		">
		<xsl:copy>
		  <xsl:apply-templates/>
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="*[local-name()='tex-math']">
	  <div class="tex" title="tex">
	    <xsl:copy-of select="."/>
	  </div>
	</xsl:template>
	
	<!-- TAXONOMY -->
	
	<xsl:template match="
	*[local-name()='nomenclature'] |
	*[local-name()='taxon-name'] |
	*[local-name()='taxon-name-part'] |
	*[local-name()='taxon-status'] |
	*[local-name()='taxon-treatment'] |
	*[local-name()='treatment-meta'] |
	*[local-name()='treatment-sec']
	">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>
	

	<!--  FOREIGN, DISPLAY or UNPROCESSABLE -->	

	<xsl:template match="
	  *[local-name()='disp-formula'] |
	  *[local-name()='disp-quote'] |
	  *[local-name()='alternatives']
	  ">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

	<xsl:template match="
	  *[local-name()='sub-article'] |
	  *[local-name()='front-stub'] 
	  ">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>

	<xsl:template match="
	  *[local-name()='floats-group'] 
	  ">
	  <xsl:call-template name="addClassTitleChildrenDiv"/>
	</xsl:template>


    <!-- INLINE -->
	<xsl:template match="
	  *[local-name()='inline-formula'] |
	  *[local-name()='inline-graphic'] 
	  ">
	  <xsl:call-template name="addClassTitleChildrenSpan"/>
	</xsl:template>

	
	<!--  OTHERS  -->

<!-- 
	<xsl:template match="*[not(self::sec)]/sec/title" priority="-0.5">
	<xsl:message>TITLE1</xsl:message>
	    <h2><xsl:apply-templates /></h2>
	</xsl:template>
	
	<xsl:template priority = "-0.6"
	    match="*[local-name()='sec' and *[local-name()='title']]/*[local-name()='sec']/*[local-name()='title']">
	<xsl:message>TITLE2</xsl:message>
	    <h3><xsl:apply-templates select="*|text()"/></h3>
	</xsl:template>
	
	-->

<!--  		
	<xsl:template match="*[local-name()='etal']">
	    <i>et al.</i>
	</xsl:template>
-->
		

	<!-- <xref xref ref-type="table-fn"">4</xref> -->	
	<xsl:template match="*[local-name()='xref' and @ref-type='table-fn']" priority="-0.4">
	<xsl:message>TABLEFN</xsl:message>
 	    <span rid="{@rid}" ref-type="{@ref-type}"><sup><a href="#{@rid}"><xsl:value-of select="."/></a></sup></span>
	</xsl:template>
		
	<xsl:template match="*[local-name()='xref']" priority="-0.45">
	<xsl:message>XREF</xsl:message>
 	    <span rid="{@rid}" ref-type="{@ref-type}"><xsl:apply-templates select="* | text()"/></span>
	</xsl:template>
	
	<!-- <xref ref-type="bibr" rid="CIT0004">4</xref> -->
	<xsl:template match="*[local-name()='xref' and @ref-type='bibr']" priority="-0.4">
	<xsl:message>BIBR</xsl:message>
 	    <span rid="{@rid}" ref-type="{@ref-type}"><sup><a href="#{@rid}"><xsl:value-of select="."/></a></sup></span>
	</xsl:template>
	
	<!-- <xref ref-type="table" rid="CIT0004">4</xref> -->
	<xsl:template match="*[local-name()='xref' and @ref-type='table']" priority="-0.4">
	<xsl:message>TABLEREF</xsl:message>
 	    <span rid="{@rid}" ref-type="{@ref-type}"><a href="#{@rid}"><xsl:value-of select="."/></a></span>
	</xsl:template>
	
	<!--  TEMPLATES -->
	
	<xsl:template name="isodate">
	  <span>
	    <xsl:value-of select="year"/>
	    <xsl:if test="month">
	      <xsl:text>-</xsl:text>
	      <xsl:value-of select="month"/>
	    <xsl:if test="day">
	      <xsl:text>-</xsl:text>
	      <xsl:value-of select="month"/>
	      </xsl:if>
	      </xsl:if>
	  </span>
	</xsl:template>	
	
	<xsl:template name="makeurl">
	  <xsl:param name="urlroot"/>
	  <a href="{$urlroot}{.}"><xsl:apply-templates/></a>
	</xsl:template>	
	
	<xsl:template name="addClassTitleChildrenDiv">
	    <div class="{local-name()}" title="{local-name()}">
		  <xsl:apply-templates/>
		</div>
	</xsl:template>
	
	<xsl:template name="addClassTitleChildrenSpan">
	    <span class="{local-name()}" title="{local-name()}">
		  <xsl:apply-templates/>
		</span>
	</xsl:template>
	
	<!-- 
	unprocessed 2016-03-06
	alternatives
app
app-group
award-group
award-id
break
chapter-title
conf-date
conf-loc
conf-name
counts
def
def-item
def-list
disp-formula
edition
elocation-id
equation-count
fig-count
floats-group
fn-group
front-stub
funding-source
glossary
inline-formula
inline-graphic
license
object-id
page-count
ref-count
related-article
role
self-uri
sub-article
suffix
table-count
term
tex-math
uri
word-count
	
	 -->
	
</xsl:stylesheet>
