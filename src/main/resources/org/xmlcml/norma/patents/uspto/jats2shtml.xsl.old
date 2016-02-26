<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/front/front.xsl"/>
    <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/body/body.xsl"/>
    <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/back/back.xsl"/>
    <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/figtab/figtab.xsl"/>
    <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/general/general.xsl"/>
    <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/math/math.xsl"/>
    <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/refcite/refcite.xsl"/>
    <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/fromjats.xsl"/>
    
    <xsl:output method="xhtml"/>

<!--  global variables for document structure -->
    <xsl:variable name="article" select="*[local-name()='article']"/>
    <xsl:variable name="front" select="$article/*[local-name()='front']"/>
    <xsl:variable name="journalMeta" select="$front/*[local-name()='journal-meta']"/>
    <xsl:variable name="journalId" select="$journalMeta/*[local-name()='journal-id']"/>
    <xsl:variable name="articleMeta" select="$front/*[local-name()='article-meta']"/>
    <xsl:variable name="articleId" select="$articleMeta/*[local-name()='article-id']"/>
    <xsl:variable name="articleCategories" select="$articleMeta/*[local-name()='article-categories']"/>
    <xsl:variable name="titleGroup" select="$articleMeta/*[local-name()='title-group']"/>
    <xsl:variable name="contribGroup" select="$articleMeta/*[local-name()='contrib-group']"/>
    <xsl:variable name="contribGroupAuthor" select="$contribGroup[*[local-name()='contrib' and @contrib-type='author']]"/>
    <xsl:variable name="contribGroupEditor" select="$contribGroup[*[local-name()='contrib' and @contrib-type='editor']]"/>
    <xsl:variable name="aff" select="$articleMeta/*[local-name()='aff']"/>
    <xsl:variable name="authorNotes" select="$articleMeta/*[local-name()='author-notes']"/>
    <xsl:variable name="pubDate" select="$articleMeta/*[local-name()='pub-date']"/>
    <xsl:variable name="history" select="$articleMeta/*[local-name()='history']"/>
    <xsl:variable name="permissions" select="$articleMeta/*[local-name()='permissions']"/>
    <xsl:variable name="abstract" select="$articleMeta/*[local-name()='abstract']"/>
    <xsl:variable name="fundingGroup" select="$articleMeta/*[local-name()='funding-group']"/>
    <xsl:variable name="kwdGroup" select="$articleMeta/*[local-name()='kwd-group']"/>
    <xsl:variable name="counts" select="$articleMeta/*[local-name()='counts']"/>
    
    <xsl:variable name="body" select="$article/*[local-name()='body']"/>
    <xsl:variable name="sec" select="$body/*[local-name()='sec']"/>
    <xsl:variable name="secType" select="$sec/@sec-type"/>
    <xsl:variable name="secTitle" select="$sec/*[local-name()='title']"/>
    <xsl:variable name="secIntro" select="
        $sec[
        @sec-type='intro' 
     or @sec-type='introduction'] |
     $secTitle[contains(normalize-space(.),'Introduction')] |
     $secTitle[contains(normalize-space(.),'INTRODUCTION')]"
     />
    <xsl:variable name="secBackground" select="
        $sec[
        @sec-type='background' ] |
     $secTitle[contains(normalize-space(.),'Background')] |
     $secTitle[contains(normalize-space(.),'BACKGROUND')]"
     />
    <xsl:variable name="secMethods" select="$sec[
        @sec-type='methods' or
        @sec-type='materials|methods'] |
        $secTitle[contains(normalize-space(.),'Methods')] |
        $secTitle[contains(normalize-space(.),'METHODS')]"
        />
    <xsl:variable name="secMaterials" select="$sec[
        @sec-type='materials' or
        @sec-type='materials|methods'] |
        $secTitle[contains(normalize-space(.),'Materials')] |
        $secTitle[contains(normalize-space(.),'MATERIALS')]"
        />
    <xsl:variable name="secResults" select="$sec[
        @sec-type='results'] |
        $secTitle[contains(normalize-space(.),'Results')] |
        $secTitle[contains(normalize-space(.),'RESULTS')]"
        />
    <xsl:variable name="secDiscussion" select="$sec[
        @sec-type='discussion'] |
        $secTitle[contains(normalize-space(.),'Discussion')] |
        $secTitle[contains(normalize-space(.),'DISCUSSION')]"
        />
    <xsl:variable name="secConclusions" select="$sec[
        contains(@sec-type,'conclusion')] |
        $secTitle[contains(normalize-space(.),'Conclusion')] |
        $secTitle[contains(normalize-space(.),'CONCLUSION')]"
        />
    <xsl:variable name="secSupplementary" select="$sec[
        contains(@sec-type,'supplementary-material')] |
        $secTitle[contains(normalize-space(.),'upplement')] |
        $secTitle[contains(normalize-space(.),'SUPPLEMENT')]"
        />
    <xsl:variable name="secSupporting" select="$sec[
        contains(@sec-type,'supplementary-material')] |
        $secTitle[contains(normalize-space(.),'upporting')] |
        $secTitle[contains(normalize-space(.),'SUPPORTING')]"
        />
    
    <xsl:variable name="back" select="$article/*[local-name()='back']"/>
    
	<!--  root element -->    
	<xsl:template match="/">
	  <html xmlns="http://www.w3.org/1999/xhtml">
		<xsl:apply-templates select="$front"/>
		<xsl:apply-templates select="$body"/>
		<xsl:apply-templates select="$back"/>
		<xsl:call-template name='summary'/>
	  </html>
	</xsl:template>
	
	<xsl:template name='summary'>
	<xsl:message/>
	    <xsl:message>TITLE-GROUP: <xsl:value-of select="$titleGroup"/></xsl:message>
	    <xsl:for-each select="$secType"><xsl:message>TYPE: <xsl:value-of select="."/></xsl:message></xsl:for-each>
	    <xsl:for-each select="$secTitle"><xsl:message>TITLE: <xsl:value-of select="."/></xsl:message></xsl:for-each>
		<xsl:message>INTRO: <xsl:value-of select="$secIntro/*[local-name()='title']"/></xsl:message>
<!-- 		<xsl:message>INTRO1: <xsl:value-of select="$secIntro"/></xsl:message> -->
	
	</xsl:template>

	<!-- Identity transformation but strips PIs and comments -->
	<xsl:template match="@*|*">
		<xsl:copy>
			<xsl:apply-templates select="@*|*|text()" />
		</xsl:copy>
	</xsl:template>

    <!--  text nodes -->
 	<xsl:template match="text()"><xsl:value-of select="."/></xsl:template>
	
	<!-- normalize whitespace nodes to empty nodes-->
 	<xsl:template match="text()[normalize-space(.)='']"><xsl:text></xsl:text></xsl:template>

<!--  unexpected tags; transform <foo> to <div tagx="foo">...</div> -->
	<xsl:template match="*">
	    <div>
	        <xsl:message>UNKNOWN: <xsl:value-of select="local-name()"/></xsl:message>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

<!--  FRONT -->


	<xsl:template match="*[local-name()='pub-id']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

<!-- 
<xref rid="pone.0112021-Stirling2" ref-type="bibr">[9]</xref>
=>
(<a href="#ref-sa-ontology" property="schema:citation">Science.AI, 2015a</a>)
 -->
	<xsl:template match="*[local-name()='xref']">
	    <a href="{@rid}">
			<xsl:value-of select="."/>
	    </a>
	</xsl:template>

	<xsl:template match="*[local-name()='etal']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='app-group']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='app']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='supplementary-material']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='media']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='uri']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='notes']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='collab']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='date-in-citation']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

<!-- 
      <section typeof="sa:Abstract" id="abstract">
        <h2>Abstract</h2>
 -->
	<xsl:template match="*[local-name()='sec']">
	  <xsl:variable name="title"><xsl:value-of select="*[starts-with(local-name(),'h')]"/></xsl:variable>
	    <section typeof="sa:{$title}">
          <xsl:apply-templates />
	    </section>
	</xsl:template>

<!--  BODY -->
<!--     FIGURE -->
<!-- 
If figure has typeof="sa:Table" then it is a table container. It must contain nothing other than a table element. If a caption is available, it should be included using the caption child element of the table, and not the figcaption child of the figure. 
--> 
<!--     end FIGURE -->
<!--  end BODY -->

	<xsl:template match="*[local-name()='ref-list']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='object-id']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='sup']">
	    <sup><xsl:apply-templates /></sup>
	</xsl:template>
	
	<xsl:template match="*[local-name()='sub']">
	    <sub><xsl:apply-templates /></sub>
	</xsl:template>
	
	<xsl:template match="*[local-name()='date']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='chapter-title']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='article']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='edition']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	

<!-- 
<article resource="#">
  …
  <section>
    <ol>
      <!- - The first author, Robin Berjon - ->
      <li property="schema:author" typeof="sa:ContributorRole">
        <a property="schema:author" href="http://berjon.com/" typeof="schema:Person">
          <span property="schema:givenName">Robin</span>
          <span property="schema:familyName">Berjon</span>
        </a>
        <a href="#scienceai" property="sa:roleAffiliation" resource="http://science.ai/">a</a>
        <sup property="sa:roleContactPoint" typeof="schema:ContactPoint">
          <a property="schema:email" href="mailto:robin@berjon.com" title="corresponding author">✉</a>
        </sup>
      </li>
      <!- - A contributor, Sebastien Ballesteros - ->
      <li property="schema:contributor" typeof="sa:ContributorRole">
        <a property="schema:contributor" href="https://github.com/sballesteros" typeof="schema:Person">
          <span property="schema:givenName">Sebastien</span>
          <span property="schema:familyName">Ballesteros</span>
        </a>
        <a href="#scienceai" property="sa:roleAffiliation" resource="http://science.ai/">a</a>
      </li>
    </ol>
    <!- - The affiliation list - ->
    <ol>
      <li id="scienceai">
        <a href="http://science.ai/" typeof="schema:Corporation">
          <span property="schema:name">science.ai</span>
        </a>
      </li>
    </ol>
  </section>

 -->	    
	<xsl:template match="*[local-name()='contrib-group']">
      <section>
[CONTRIB-GROUP]
        <ol>
    		<xsl:apply-templates />
		</ol>
	  </section>
	</xsl:template>

<!--  
      <li property="schema:author" typeof="sa:ContributorRole">
        <a property="schema:author" href="http://berjon.com/" typeof="schema:Person">
          <span property="schema:givenName">Robin</span>
          <span property="schema:familyName">Berjon</span>
        </a>
        <a href="#scienceai" property="sa:roleAffiliation" resource="http://science.ai/">a</a>
        <sup property="sa:roleContactPoint" typeof="schema:ContactPoint">
          <a property="schema:email" href="mailto:robin@berjon.com" title="corresponding author">✉</a>
        </sup>
      </li>	
...
				<contrib contrib-type="author">
					<name>
						<surname>Peacock</surname>
						<given-names>Elizabeth</given-names>
					</name>
					<xref ref-type="aff" rid="aff1">
						<sup>1</sup>
					</xref>
					<xref ref-type="aff" rid="aff2">
						<sup>2</sup>
					</xref>
					<xref ref-type="corresp" rid="cor1">
						<sup>*</sup>
					</xref>
				</contrib>
      
      -->
	<xsl:template match="*[local-name()='contrib']">
      <section>
        <li property="schema:author" typeof="sa:ContributorRole">
          <xsl:variable name="role" select="*[local-name()='role']"/>
          <xsl:variable name="name" select="*[local-name()='name']"/>
          <xsl:variable name="given" select="$name/*[local-name()='given-names']"/>
          <xsl:variable name="surname" select="$name/*[local-name()='surname']"/>
          <a property="schema:author" typeof="schema:Person">
            <xsl:if test="$role">
              <span property="schema:role"><xsl:value-of select="$role"/></span>
            </xsl:if>
            <xsl:if test="$given">
              <span property="schema:givenName"><xsl:value-of select="$given"/></span>
            </xsl:if>
            <xsl:if test="$surname">
              <span property="schema:familyName"><xsl:value-of select="$surname"/></span>
            </xsl:if>            
			<xsl:for-each select="*[local-name()='xref' and @ref-type='aff']">
			  <sup><xsl:value-of select="*[local-name()='sup']"/></sup>
			</xsl:for-each> 
			<xsl:for-each select="*[local-name()='xref' and @ref-type='corresp']">
			  <sup><xsl:value-of select="*[local-name()='sup']"/></sup>
			</xsl:for-each> 
          </a>
		</li>
	  </section>
	</xsl:template>
	
	<!-- 
			<aff id="aff1">
				<label>1</label>
				<addr-line>Alaska Science Center, US Geological Survey, Anchorage,
					Alaska, United States of America</addr-line>
			</aff>
	 -->
	 
	<xsl:template match="*[local-name()='aff']">
      <a href="#scienceai" property="sa:roleAffiliation" resource="http://science.ai/"><xsl:value-of select="*[local-name()='label']"/></a>
      [<xsl:value-of select="*[local-name()='addr-line']"/>]<xsl:text>....</xsl:text>
	</xsl:template>
	
	
	<xsl:template match="*[local-name()='email']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='role']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='country']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='institution']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='underline']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='maligngroup']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
		
	<xsl:template match="*[local-name()='list']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='list-item']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='disp-formula']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='floats-group']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	
	<xsl:template match="*[local-name()='inline-formula']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='addr-line']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='corresp']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='author-notes']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='self-uri']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	
	<xsl:template match="*[local-name()='award-id']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='award-group']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='counts']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='fig-count']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='page-count']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='custom-meta-group']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='custom-meta']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='meta-name']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='meta-value']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='fn-group']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='suffix']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='contrib-id']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='address']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='phone']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='size']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

<!--  named templates -->	
	<xsl:template name="apply">
	   <xsl:copy>
	     <xsl:apply-templates select="*|@*|text()"/>
	   </xsl:copy>
	</xsl:template>
	
	<xsl:template name="container">
			<div><xsl:value-of select="local-name()"/><xsl:text>: </xsl:text>
			<xsl:for-each select="*">
			<li><xsl:apply-templates select="."/></li></xsl:for-each>
			</div>
	</xsl:template>
	
	<xsl:template name="debug">
			<xsl:value-of select="local-name()"/><xsl:text>| </xsl:text><xsl:apply-templates />]
	</xsl:template>
	
	<xsl:template name="span">
  	  <span class="{local-name()}"><xsl:apply-templates /></span>
	</xsl:template>
	
</xsl:stylesheet>
