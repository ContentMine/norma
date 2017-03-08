<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- <xsl:include href="src/main/resources/org/xmlcml/norma/pubstyle/nlm/jats/front/front.xsl"/> -->
	<xsl:output method="xhtml" />

	<!-- global variables for document structure -->

	<xsl:output method="xhtml" />

	<xsl:template match="/">
		<html xmlns="http://www.w3.org/1999/xhtml">
		  <head>
		    <style>
div {border: 2px solid black; margin: 5pt; padding: 5pt;}
		    </style>
		  </head>
			<xsl:apply-templates />
		</html>
	</xsl:template>

	<!-- normalize whitespace -->
	<!-- 
	<xsl:template match="text()[normalize-space(.)='']">
		<xsl:text></xsl:text>
	</xsl:template>
	-->

	<!-- unmatched tags -->
	<xsl:template match="*" priority="-0.4">
		<xsl:variable name="name">
			<xsl:value-of select="local-name()" />
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="*">
		       <xsl:message>UNKNOWN uspto tag: <xsl:value-of select="$name"/></xsl:message>
				<div>
					<h2><xsl:value-of select="$name" /></h2>
					<xsl:apply-templates />
				</div>
			</xsl:when>
			<xsl:otherwise>
				<i><xsl:value-of select="$name" /></i>:: <xsl:value-of select="." />
				<xsl:message>
					UNK: <xsl:value-of select="$name"/>
				</xsl:message>
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>

	<xsl:template match="p | b | i | ul | li | ol | sup | div | sub | em | strong |
	  row | entry | tbody | thead | tgroup | colspec | caption | title | sec | st |
	  br | img | table | tables |
	  
	  mi | mn | mo | mspace | mtext | math | maths | mfrac |mover | mrow | msqrt | mstyle | msub |
	   msubsub | msup | msubsup | mtable | mtd | mtr | munder | munderover | us-math | division |
	   smallcaps | sub2 |
	   
	   us-chemistry | chemistry 
	   
	   ">
	  <xsl:copy>
  	    <xsl:apply-templates select="* | text()"/>
	  </xsl:copy>
	</xsl:template>

	<xsl:template match="text">
	  <p>
  	    <xsl:apply-templates select="* | text()"/>
	  </p>
	</xsl:template>

	<xsl:template match="us-patent-grant | us-parties">
	  <div>
	  <h2><xsl:value-of select="local-name()"/></h2>
  	    <xsl:apply-templates/>
	  </div>
	</xsl:template>

	<xsl:template match="figref | claim-ref">
	  <xsl:variable name="name"><xsl:value-of select="local-name()"/></xsl:variable>
  	  <xsl:value-of select="$name"/>[<xsl:apply-templates select="* | text()"/>]
	</xsl:template>

	<xsl:template match="claim-text[claim-text]">
	  <div><h2>CLAIM-TEXT</h2> <xsl:apply-templates/></div>
	</xsl:template>
	
	<xsl:template match="claim-text">
	  <i>CLAIM-TEXT:</i> <xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="us-bibliographic-data-grant">
	  <h1>BIBLIOGRAPHIC</h1>
	   <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="drawings">
	  <h1>DRAWINGS</h1>
	  <ul>
	  <xsl:for-each select="*">
	  <li><xsl:apply-templates/></li>
	  </xsl:for-each>
	  </ul>
	</xsl:template>
	
	<xsl:template match="figure/img">
	  <a href="{@file}"><xsl:value-of select="@id"/></a>
	</xsl:template>
	
	<xsl:template match="description">
	  <h1>DESCRIPTION</h1>
	   <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="priority-claims | relation">
	  <h1><xsl:value-of select="local-name()"/></h1>
	   <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="claims">
	  <h1>CLAIMS</h1>
	   <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="claim | main-cpc | classifications-cpc | classification-cpc | priority-claim |
	   continuation | continuation-in-part | parent-doc | child-doc | us-provisional-application | 
	   pct-or-regional-filing-data | pct-or-regional-publishing-data | parent-grant-document">
	  <h2><xsl:value-of select="local-name()"/></h2>
	   <xsl:apply-templates/>
	</xsl:template>


<!-- 
<figure id="Fig-EMI-D00000" num="00000">
	<img id="EMI-D00000" he="187.03mm" wi="310.47mm"
		file="US08978162-20150317-D00000.TIF" alt="embedded image"
		img-content="drawing" img-format="tif" />
	</figure>
 -->

	
	<xsl:template match="main ">
	  <h2>claim</h2>
	   <xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="publication-reference | application-reference ">
	  <b><xsl:value-of select="."/>:::</b>
	  <xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="description-of-drawings">
	<h2>Description of drawings</h2>
	<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="date | country | kind | doc-number | name | main-classification | 
	   additional-info | number-of-drawing-sheets | number-of-figures | state | city | 
	   last-name | first-name | department | further-classification | classification-status | 
	   classification-data-source | scheme-origination-code | section | class | subclass |
	   main-group | subgroup | symbol-position | classification-value | residence | address | 
	   us-term-extension | us-application-series-code | classification-level | category | othercit |
	   us-exemplary-claim | number-of-claims | orgname | role | us-claim-statement | parent-status |
	   classification-cpc-text | group-number | rank-number | rule-47-flag ">
	  <span title="{local-name()}">
	   <xsl:value-of select="."/>
	   </span>
	</xsl:template>

	<xsl:template match="document-id | ipc-version-indicator | generating-office | ipc-version-indicator |
	   cpc-version-indicator | classification-national | us-term-of-grant | figures | disclaimer |
	    us-371c124-date | us-371c12-date | parent-pct-document | combination-set | combination-rank |
	    parent-pct-document | us-sequence-list-doc | further-cpc | sequence-list">
	  <b><xsl:value-of select="local-name()"/>:: </b>
	   <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="us-references-cited[us-citation] ">
	  <h2>US Citations</h2>
	  <ul>
	  <xsl:for-each select="us-citation">
	  <li><b>US citation: </b>
	  <xsl:apply-templates/></li></xsl:for-each>
	  </ul>
	</xsl:template>

	<xsl:template match="us-citation">
	  <h3>US Citation</h3>
	  <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="us-related-documents">
	  <h3>US Related Documents</h3>
	  <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="related-publication">
	  <h3>Related Publication</h3>
	  <xsl:apply-templates/>
	</xsl:template>

<!-- 
	<xsl:template match="addressbook">
	  <h3>Addressbook</h3>
	  <xsl:apply-templates/>
	</xsl:template>
-->
	<xsl:template match="invention-title">
	  <b>Invention Title:: </b>	  <xsl:value-of select="."/>
	</xsl:template>

	<xsl:template match="classifications-ipcr[classification-ipcr] ">
	  <h2>Classifications IPCR</h2>
	  <ul>
	  <xsl:for-each select="classification-ipcr">
	  <li><b>Classification: </b>
	  <xsl:apply-templates/></li></xsl:for-each>
	  </ul>
	</xsl:template>
	
	<xsl:template match="us-field-of-classification-search">
	  <h2>us-field-of-classification-search</h2>
	  <ul>
	  <xsl:for-each select="classification-ipcr">
	  <li>
	  <xsl:apply-templates/></li></xsl:for-each>
	  </ul>
	</xsl:template>

	<xsl:template match="further-cpc[classification-cpc] ">
	  <h2>Further CPC</h2>
	  <ul>
	  <xsl:for-each select="classification-cpc">
	  <li><b>Classification: </b>
	  <xsl:apply-templates/></li>
	  </xsl:for-each>
	  </ul>
	</xsl:template>

	<xsl:template match="us-applicants[us-applicant] ">
	  <h2>US Applicants</h2>
	  <ul>
	  <xsl:for-each select="us-applicant">
	  <li><b>Applicant: </b>
	  <xsl:apply-templates/></li></xsl:for-each>
	  </ul>
	</xsl:template>
	
	<xsl:template match="inventors">
	  <h2>Inventors</h2>
	  <ul>
	  <xsl:for-each select="inventor">
	  <li><b>Inventor: </b>
	  <xsl:apply-templates/></li></xsl:for-each>
	  </ul>
	</xsl:template>
	
	<xsl:template match="agents">
	  <h2>Agents</h2>
	  <ul>
	  <xsl:for-each select="agent">
	  <li><b>Agent: </b>
	  <xsl:apply-templates/></li></xsl:for-each>
	  </ul>
	</xsl:template>
	
	<xsl:template match="assignees">
	  <h2>Assignees</h2>
	  <ul>
	  <xsl:for-each select="assignee">
	  <li><b>Assignee: </b>
	  <xsl:apply-templates/></li></xsl:for-each>
	  </ul>
	</xsl:template>
	
	<xsl:template match="examiners">
	  <h2>Examiners</h2>
	  <ul>
	  <xsl:for-each select="*">
	  <li><b><xsl:value-of select="local-name()"/>: </b>
	  <xsl:apply-templates/></li></xsl:for-each>
	  </ul>
	</xsl:template>
	
	<xsl:template match="patcit | nplcit | addressbook ">
	  <i>[<xsl:value-of select="local-name()"/>]: </i><xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="action-date[date] ">
	  <i><xsl:value-of select="local-name()"/>: </i>
	   <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="abstract">
		<h1>abstract"</h1>
		<xsl:apply-templates/>
	</xsl:template>

<!--  XSLT1.0 grouping of p elements modified from stackoverflow -->

 <xsl:key name="kFollowing" match="p"
     use="generate-id(preceding-sibling::*[self::heading][1][self::heading])"/>

 <xsl:template match="@*|node()" name="identity">
     <xsl:copy>
       <xsl:apply-templates select="@*|node()"/>
     </xsl:copy>
 </xsl:template>

 <xsl:template match="heading">
  <div>
    <h1><xsl:value-of select="."/></h1>
   <xsl:apply-templates select="key('kFollowing', generate-id())" mode="copy"/>
  </div>
 </xsl:template>

 <xsl:template match="p" mode="copy">
  <xsl:call-template name="identity"/>
 </xsl:template>

 <xsl:template match="p[preceding-sibling::*[self::heading][1][self::heading]]"/>

<!--  or -->
<!-- 
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()[1]|@*"/>
        </xsl:copy>
        <xsl:apply-templates select="following-sibling::node()[1]"/>
    </xsl:template>
    
    <xsl:template match="heading">
        <div>
          <h1><xsl:value-of select="heading"/></h1>
            <xsl:apply-templates select="following-sibling::node()[1]"
                                 mode="open"/>
        </div>
        <xsl:apply-templates select="following-sibling::node()
                                         [not(self::p)][1]"/>
    </xsl:template>
    <xsl:template match="node()" mode="open"/>
    <xsl:template match="alert" mode="open">
        <xsl:copy-of select="."/>
        <xsl:apply-templates select="following-sibling::node()[1]"
                             mode="open"/>
    </xsl:template>
 -->
 
</xsl:stylesheet>
