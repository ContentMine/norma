<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:h="http://www.w3.org/1999/xhtml">

	<xsl:import href="../norma-dev/src/main/resources/org/xmlcml/norma/pubstyle/util/toHtml.xsl"/>

	<!--  Hindawi -->
    <xsl:variable name="publisher">Hindawi Publishing Corporation</xsl:variable>
    <xsl:variable name="prefix">10.1155</xsl:variable>
    <xsl:variable name="publisherSelector">//*[local-name()='meta' and
      (
      (@name='dc.publisher' and @content='Hindawi Publishing Corporation') or 
      (@name='citation_doi' and contains(@content,concat('10.1155','/')))
      )
    ]</xsl:variable>
	
<!--  tags to omit -->
		<!-- strip whitespace -->
		<xsl:template match="text()[normalize-space(.)='']"></xsl:template>

		<!-- non-content tags -->
		<xsl:template match="h:link | h:script | h:style | h:input | h:object" />
		<xsl:template
			match="h:div[
			   @id='site_head' 
			or @id='dvLinks'
			or @id='banner' 
      		or @id='journal_navigation'
      		or @id='left_column'
      		or @class='lock'
      		or @class='right_column_actions'
      		or @class='footer_space'
      		or @class='footer'
		      ]" />
	
	
</xsl:stylesheet>
