<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!--  footnote -->
	<xsl:template match="*[local-name()='fn']">
	    <div>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='name']">
	  <ul><xsl:call-template name="container"/></ul>
	</xsl:template>

	<xsl:template match="*[local-name()='surname']">
	  <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='given-names']">
	  <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='source']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='fpage']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='person-group']">
	   <ul>
	   <xsl:for-each select="*[local-name()='name']">
	     <li><xsl:apply-templates select="."/></li>
	   </xsl:for-each>
	   </ul>
	   <xsl:apply-templates select="*[not(local-name()='name')]"/>
	</xsl:template>

	<xsl:template match="*[local-name()='label']">
	   <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='lpage']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<xsl:template match="*[local-name()='title']">
	    <h2>
			<xsl:apply-templates />
	    </h2>
	</xsl:template>

	<xsl:template match="*[local-name()='p']">
	    <p>
	        <xsl:copy-of select="@*"/>
			<xsl:apply-templates />
	    </p>
	</xsl:template>

	<xsl:template match="*[local-name()='italic']">
	    <em>
			<xsl:apply-templates />
	    </em>
	</xsl:template>

	<xsl:template match="*[local-name()='comment']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>

	<!--  small caps -->
	<xsl:template match="*[local-name()='sc']">
	    <span><strong><xsl:apply-templates /></strong></span>
	</xsl:template>

	<xsl:template match="*[local-name()='bold']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	
	<xsl:template match="*[local-name()='hr']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='break']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>
	
	<xsl:template match="*[local-name()='named-content']">
	   <xsl:call-template name="span"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='kwd']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='kwd-group']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='ext-link']">
	   <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='caption']">
	   <xsl:call-template name="container"/>
	</xsl:template>



	

</xsl:stylesheet>