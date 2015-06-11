<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:mz="http://psi.hupo.org/ms/mzml" xmlns:tom="http://tom.org/">
    <xsl:output method="xhtml"/>

	<xsl:template match="/">
	  <tom:head>
		<xsl:apply-templates />
	  </tom:head>
	</xsl:template>

	<!--Identity template, strips PIs and comments -->
	<!-- 
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	-->
	<xsl:template match="*">
	  <!-- <xsl:message>STAR</xsl:message> -->
	  <xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="mz:referenceableParamGroup">
	  <xsl:message>MATCH</xsl:message>
	  <tom:paramGroup>
	    <xsl:for-each select="mz:cvParam">
  	      <tom:param accession="{@accession}" name="{@name}"/>
	    </xsl:for-each>
	    </tom:paramGroup>
	</xsl:template>
	
	<xsl:template match="text()">
<!-- 	  <xsl:message>TEXT</xsl:message> -->
	</xsl:template>

</xsl:stylesheet>
