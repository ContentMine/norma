<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:svg="http://www.w3.org/2000/svg"
        xmlns:h="http://www.w3.org/1999/xhtml">
  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>        
  
  <!--Identity template, strips PIs and comments -->
    <xsl:template match="@*|*|text()">
        <xsl:copy>
            <xsl:apply-templates select="@*|*|text()"/>
        </xsl:copy>
    </xsl:template>
    
  <!--  strip whitespace -->
  <xsl:template match="text()[normalize-space(.)='']"></xsl:template>
 
 <!--  non-content tags --> 
  <xsl:template match="h:link | h:script | h:style | h:input | h:object"/>
  <xsl:template match="h:div[@id='site_head' or @id='dvLinks' or @id='banner' 
      or @id='journal_navigation' or @id='left_column' or @class='lock' or @class='right_column_actions'
      or @class='footer_space' or @class='footer'
      ]"/>
  
 <!--  SVG --> 
  <xsl:template match="svg:*"><svg:svg>MATH</svg:svg></xsl:template>

<!-- one level of grouping applied to "xml-content"-->  
  <xsl:template match="h:div[@class='xml-content']">
    <h:div class="xml-content" >
      <xsl:for-each-group select="h:*" group-starting-with="h:h4">
        <h:section title="{self::h:h4}">
          <xsl:for-each select="current-group()">
	        <xsl:copy>
	            <xsl:apply-templates select="@*|*|text()"/>
	        </xsl:copy>
          </xsl:for-each> 
        </h:section>
      </xsl:for-each-group>
    </h:div>
  </xsl:template>

</xsl:stylesheet>
