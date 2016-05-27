<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

<!--  IEEE -->
    <xsl:output method="xhtml"/>
    
    <xsl:template match="/">
      <xsl:apply-templates/>
    </xsl:template>

	<!--Identity template, strips PIs and comments -->
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	
  	<xsl:template match="h:head">
  	  <head>
  	    <meta charset="UTF-8"/>
  	    <!--  this might disappear -->
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
  	    span {
  	        background: yellow;
  	        margin: 2 2 2 2;
  	        }
  	    </style>
  	  </head>
  	</xsl:template> 

	<xsl:template match="comment()" priority="1.0"/>


</xsl:stylesheet>
