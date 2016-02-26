<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="*[local-name()='abstract']">
      <section typeof="sa:Abstract" id="abstract">
        <h2>Abstract</h2>
          <xsl:apply-templates/>
      </section>
	</xsl:template>
	
	

</xsl:stylesheet>