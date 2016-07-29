<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

<!--  parent -->
    <xsl:output method="xhtml"/>
    
    <xsl:template match="/">
      <xsl:apply-templates/>
    </xsl:template>

	<!--Identity template, strips PIs and comments -->
	
	<xsl:template match="@*|node()">
	  <xsl:copy>
	    <xsl:apply-templates select="@*|node()"/>
	  </xsl:copy>
	</xsl:template>

  	<xsl:template match="h:head">
  	  <xsl:copy>
  	    <meta charset="UTF-8"/>
  	    <style>
  	    div {
  	        border: 1px solid red;
  	        margin: 2 2 2 2;
  	        }
  	    ul, ol {
  	        border: 1px solid green;
  	        margin: 2 2 2 2;
  	        }
  	    a {
  	        border: 1px solid purple;
  	        margin: 2 2 2 2;
  	        }
  	    p {
  	        background: #ffffdd;
  	        margin: 2 2 2 2;
  	        }
  	    span {
  	        background: #ddffff;
  	        margin: 2 2 2 2;
  	        }
  	    table {"target/pubstyle/rs/clean/277_1686_1309/fulltext.xhtml"
  	        border: 1px solid blue;
  	        background: #ddffdd;
  	        margin: 2 2 2 2;
  	        }
  	    tr {
  	        border: 2px solid yellow;
  	        margin: 2 2 2 2;
  	        }
  	    td {
  	        border: 2px solid cyan;
  	        margin: 2 2 2 2;
  	        }
  	    </style>
  	    <xsl:apply-templates select="h:meta"/>
  	  </xsl:copy>
  	</xsl:template> 
  	

	<xsl:template match="comment()" priority="1.0"/>


</xsl:stylesheet>
