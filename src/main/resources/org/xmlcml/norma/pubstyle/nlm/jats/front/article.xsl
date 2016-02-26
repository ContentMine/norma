<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="*[local-name()='article-meta']">
	    <div>
			<xsl:value-of select="local-name()"/>:
			<ul>
			<xsl:for-each select="*">
			  <li><xsl:value-of select="local-name()"/>: <xsl:apply-templates select="."/></li>
			</xsl:for-each>
			</ul>
			
	    </div>
	</xsl:template>
	
		
	<xsl:template match="*[local-name()='article-id']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='title-group']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='article-title']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='alt-title']">
	   <xsl:call-template name="debug"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='article-categories']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='subj-group']">
	   <xsl:call-template name="container"/>
	</xsl:template>
	
	<xsl:template match="*[local-name()='subject']">
	   <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='volume']">
	   <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='issue']">
	   <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='elocation-id']">
	   <xsl:call-template name="debug"/>
	</xsl:template>

	<xsl:template match="*[local-name()='history']">
	   <xsl:call-template name="container"/>
	</xsl:template>

	<xsl:template match="*[local-name()='ack']">
	    <div>
	        <xsl:copy-of select="@*"/>
	        <xsl:attribute name="tagx"><xsl:value-of select="local-name()"/></xsl:attribute>
			[<xsl:value-of select="local-name()"/>]<xsl:apply-templates />
	    </div>
	</xsl:template>



</xsl:stylesheet>