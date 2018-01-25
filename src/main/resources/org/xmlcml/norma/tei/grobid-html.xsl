<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:mml="http://www.w3.org/1998/Math/MathML"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns:tei="http://www.tei-c.org/ns/1.0"
    exclude-result-prefixes="xlink xs mml tei"
    version="1.0">
	<xsl:output method="html"/>

	<xsl:template match="/">
		<html>
			<xsl:apply-templates/>
		</html>
	</xsl:template>

	
	<xsl:template match="*">
			<h4><xsl:value-of select="name()"/></h4>
			<xsl:message>UNMATCHED <xsl:value-of select="name()"/></xsl:message>
			<xsl:apply-templates/>
	</xsl:template>

	<!-- ===============TEI TAGS================== -->
    <xsl:template match="tei:abstract">
		<h3>Abstract</h3>
		
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='foo')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:p"/>
		<xsl:apply-templates select="$child"/>		
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>

    <xsl:template match="tei:address">
		<h2>Address <xsl:apply-templates/></h2>

		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='foo')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:addrLine | tei:postCode | tei:settlement | tei:country"/>
		<xsl:apply-templates select="$child"/>		
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>

    <xsl:template match="tei:addrLine">
		<h2>AddrLine <xsl:apply-templates/></h2>
		
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='foo')]"/>
		</xsl:call-template>
		
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not(self::tei:foo | self::tei:foo)]"/>
		</xsl:call-template>
    </xsl:template>

	<xsl:template match="tei:affiliation">
		<h2>Affiliation: <xsl:apply-templates/></h2>[

		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='key')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:orgName | tei:address"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  


    <xsl:template match="tei:analytic">
		<h2>Analytic</h2>

		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='foo')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:title | tei:author | tei:idno"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>

	<xsl:template match="tei:appInfo">
		<h4>appInfo</h4>
		
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='foo')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:application "/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="tei:application">
		<h4>application</h4>
		
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='version' or local-name()='ident' 
			or local-name()='when' or local-name()='key')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:ref "/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>

    <xsl:template match="tei:author">
		<span><b>Author </b><xsl:value-of select="."/></span>
		
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='foo')]"/>
		</xsl:call-template>
		
		
		<xsl:variable name="child" select="tei:persName | tei:affiliation "/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>

    <xsl:template match="tei:availability">
      <h3><i>availability: </i><xsl:value-of select="."/></h3>
      
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='status')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:p | tei:licence"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
      
    </xsl:template>
	
	<xsl:template match="tei:back">
		<xsl:apply-templates select="tei:div"/>
		
		<xsl:variable name="missingAtts" select="@*[not(local-name()='bar')]"/>
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="$missingAtts"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:div "/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		
	</xsl:template>  
	

	<xsl:template match="tei:biblScope">
		<h4><xsl:value-of select="@unit"/>[]<xsl:value-of select="@from"/>:<xsl:value-of select="@to"/>]</h4>
		
		<xsl:variable name="missingAtts" select="@*[not(local-name()='from' or local-name()='to' or local-name()='unit')]"/>
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="$missingAtts"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:foo "/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>  


    <xsl:template match="tei:biblStruct">
		<h2>Bibliodata</h2>
		
		<xsl:variable name="missingAtts" select="@*[not(local-name()='id' or local-name()='unit')]"/>
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="$missingAtts"/>
		</xsl:call-template>
		
		
		<xsl:variable name="child" select="tei:analytic | tei:monogr "/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>

	<xsl:template match="tei:body">
		
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='foo')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:div | tei:figure "/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		
	</xsl:template>  
	
	<xsl:template match="tei:country">
		<h2>Country: <xsl:apply-templates/></h2>[

       	<xsl:variable name="missingAtts" select="@*[not(local-name()='key') and not(local-name()='bar')]"/>
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="$missingAtts"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		
		]
	</xsl:template>  

    <xsl:template match="tei:date">
      <h4><i>date: </i><xsl:value-of select="@type"/>=<xsl:value-of select="@when"/></h4>
    </xsl:template>

	<xsl:template match="tei:div">
		<div>
			<xsl:apply-templates/>
		</div>
	</xsl:template>  

	<xsl:template match="tei:encodingDesc">
		<h4>encoding</h4>
		<!--
			<appInfo>
				<application version="0.5.1-SNAPSHOT" ident="GROBID" when="2018-01-21T13:42+0000">
					<ref target="https://github.com/kermitt2/grobid">GROBID - A machine learning software for extracting information from scholarly documents</ref>
				</application>
			</appInfo>
			-->
		<xsl:variable name="child" select="tei:appInfo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="tei:figure">
		<h2>
			<xsl:if test="@type='table'">TABLE</xsl:if>
			<xsl:if test="not(@type='table')">FIGURE</xsl:if>
		</h2>
		
		<xsl:variable name="child" select="tei:head | tei:label | tei:figDesc | tei:graphic | tei:table"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>  

	<xsl:template match="tei:figDesc">
		<h2>Fig: <xsl:apply-templates/></h2>[
		
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  

	<xsl:template match="tei:fileDesc">
		<h3>FILE</h3>
		
		<xsl:variable name="child" select="tei:titleStmt | tei:publicationStmt | tei:sourceDesc"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="tei:formula">
		<h2>Formula: <xsl:apply-templates/></h2>[
		
		<xsl:variable name="child" select="tei:label"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  

	<xsl:template match="tei:forename">
		<h2>Forname: <xsl:apply-templates/></h2>[
		
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  

	<xsl:template match="tei:graphic">
		<h2>Graphic: <xsl:apply-templates/></h2>[
		
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
			<xsl:with-param name="parent" select="local-name()"></xsl:with-param>
		</xsl:call-template>
		
		<xsl:variable name="missingAtts" select="@*[not(local-name()='url' or local-name()='coords' or local-name()='type')]"/>
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="$missingAtts"/>
		</xsl:call-template>
		
		]
	</xsl:template>  

	<xsl:template match="tei:head">
		<span class="head">[<xsl:apply-templates/>]</span>
	</xsl:template>  

	<xsl:template match="tei:idno">
		<span class="idno">[<xsl:apply-templates/>]</span>
	</xsl:template>  

	<xsl:template match="tei:imprint">
		<h4>Imprint</h4>
		<xsl:variable name="child" select="tei:biblScope | tei:publisher | tei:date | tei:pubPlace"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>  

    <xsl:template match="tei:keywords">
		<h3>keywords</h3>
		<p>
			<xsl:for-each select="tei:term">
			<xsl:value-of select="."/><xsl:text> | </xsl:text>
		    </xsl:for-each>
		</p>
    </xsl:template>
	
	<xsl:template match="tei:label">
		<span class="label">[<xsl:apply-templates/>]</span>
	</xsl:template>  
	
	    <xsl:template match="tei:licence">
      <h3><i>availability: </i><xsl:value-of select="."/></h3>
      
       	<xsl:variable name="missingAtts" select="@*[not(local-name()='foo')]"/>
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="$missingAtts"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:foo | tei:bar"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
      
    </xsl:template>
	
	

	<xsl:template match="tei:listBibl">
	
		<xsl:variable name="child" select="tei:biblStruct"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>  
	
	<xsl:template match="tei:meeting">
	
		<xsl:variable name="child" select="tei:biblStruct | tei:address"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>  
	
	<xsl:template match="tei:monogr">
		<h4>Monogr</h4>[
		
		<xsl:variable name="child" select="tei:author | tei:idno | tei:title |
		 tei:imprint | tei:meeting | tei:respStmt | tei:publPlace"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  

    <xsl:template match="tei:orgName">
		<h4>Org name <xsl:apply-templates/></h4>
		
 		<xsl:variable name="missingAtts" select="@*[not(local-name()='key') and not(local-name()='type')]"/>
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="$missingAtts"/>
		</xsl:call-template>
		
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not(self::tei:foo | self::tei:foo)]"/>
		</xsl:call-template>
    </xsl:template>
  

	<xsl:template match="tei:P | tei:p">
		<p>
			<xsl:apply-templates/>
		</p>
	</xsl:template>  
	
    <xsl:template match="tei:persName">
		<xsl:value-of select="tei:forename"/><xsl:text> : </xsl:text>
		<xsl:value-of select="tei:surname"/>
		<xsl:if test="tei:roleName">
			<xsl:text> Role: </xsl:text><xsl:value-of select="tei:roleName"/>
		</xsl:if>

		<xsl:variable name="child" select="tei:forename | tei:forename | tei:surname"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>
  
	<xsl:template match="tei:postBox">
		<h2>PostBox: <xsl:apply-templates/></h2>[

		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  
	
	<xsl:template match="tei:postCode">
		<h2>PostCode: <xsl:apply-templates/></h2>[

		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  
	
	<xsl:template match="tei:region">
		<h2>Region: <xsl:apply-templates/></h2>[
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
			<xsl:with-param name="parent" select="local-name()"></xsl:with-param>
		</xsl:call-template>
		]
	</xsl:template>  
	
	<xsl:template match="tei:settlement">
		<h2>Settlement: <xsl:apply-templates/></h2>[
		
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  	

    <xsl:template match="tei:pubPlace">
		<h4>Pub place <xsl:apply-templates/></h4>
		
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not(self::tei:foo | self::tei:foo)]"/>
		</xsl:call-template>
    </xsl:template>
  
    <xsl:template match="tei:respStmt">
		<h3>Responsibility</h3>
		<xsl:variable name="child" select="tei:resp | tei:orgName | tei:persName"/> 
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
			<xsl:with-param name="parent" select="local-name()"></xsl:with-param>
		</xsl:call-template>
    </xsl:template>
  
    <xsl:template match="tei:profileDesc">
		<h2>ProfileDesc</h2>
		<xsl:variable name="child" select="tei:textClass | tei:abstract"/> 
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>

	<xsl:template match="tei:publicationStmt">
		<h2>PublStme</h2>
		<xsl:variable name="child" select="tei:publisher | tei:availability | tei:date"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>
	
    <xsl:template match="tei:publisher">
      <h3><i>publisher: </i><xsl:value-of select="."/></h3>
    </xsl:template>

    <xsl:template match="tei:ref">
      <a href="{@target}"><xsl:value-of select="."/></a>
    </xsl:template>

    <xsl:template match="tei:sourceDesc">
		<h2>SourceDesc</h2>
		<xsl:variable name="child" select="tei:biblStruct"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>

	<xsl:template match="tei:surname">
		<h2>Surname: <xsl:apply-templates/></h2>[
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		]
	</xsl:template>  

	<xsl:template match="tei:table">
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="tei:TEI">
		<html>
			<xsl:variable name="child" select="tei:teiHeader | tei:text"/>
			<xsl:apply-templates select="$child"/>
			<xsl:call-template name="missingChildren">
				<xsl:with-param name="restList" select="*[not($child)]"/>
			</xsl:call-template>
		</html>
	</xsl:template>
	
	<xsl:template match="tei:teiHeader">
		<div>
			<h2>Header</h2>
			<xsl:variable name="child" select="tei:encodingDesc | tei:fileDesc | tei:profileDesc"/>
			<xsl:apply-templates select="$child"/>
			<xsl:call-template name="missingChildren">
				<xsl:with-param name="restList" select="*[not($child)]"/>
			</xsl:call-template>
	  </div>
	</xsl:template>
	
	<xsl:template match="tei:text">
		<xsl:variable name="child" select="tei:body | tei:back"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		
	</xsl:template>  

    <xsl:template match="tei:textClass">
		<xsl:variable name="child" select="tei:keywords"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
    </xsl:template>

    <xsl:template match="tei:title">
		<h3 class="title"><xsl:value-of select="."/></h3>
		
		<xsl:call-template name="showMissingAtts">
			<xsl:with-param name="missingAtts" select="@*[not(local-name()='level') and not(local-name()='type')]"/>
		</xsl:call-template>
		
		<xsl:variable name="child" select="tei:foo"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
		
    </xsl:template>

	<xsl:template match="tei:titleStmt">
		<h1 class="title"><xsl:value-of select="tei:title"/></h1>
		
		<xsl:variable name="child" select="tei:title"/>
		<xsl:apply-templates select="$child"/>
		<xsl:call-template name="missingChildren">
			<xsl:with-param name="restList" select="*[not($child)]"/>
		</xsl:call-template>
	</xsl:template>

<!-- ============CALLABLE TEMPLATES============= -->	
	
	<xsl:template name="showMissingAtts">
		<xsl:param name="missingAtts"/>
		<xsl:if test="count($missingAtts) != 0">
			<xsl:variable name="parent" select="local-name()"/>
			<xsl:for-each select="$missingAtts">
				<xsl:message>NO att on <xsl:value-of select="$parent"/>: <xsl:value-of select="local-name()"/>=<xsl:value-of select="."/>]</xsl:message>
			</xsl:for-each>
		</xsl:if>
	</xsl:template>

	<xsl:template name="missingChildren">
		<xsl:param name="restList"/>
		<xsl:if test="count($restList) != 0">
			<xsl:variable name="parent" select="local-name()"/>
			<xsl:for-each select="$restList">
				<xsl:message>NO child <xsl:value-of select="$parent"/>:<xsl:value-of select="local-name()"/></xsl:message>
			</xsl:for-each>
		</xsl:if>
		<!-- https://stackoverflow.com/questions/585261/is-there-an-xslt-name-of-element -->
	</xsl:template>


</xsl:stylesheet>