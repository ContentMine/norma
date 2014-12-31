<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:h="http://www.w3.org/1999/xhtml"
	xmlns:mf="http://example.com/mf" exclude-result-prefixes="xs mf"
	version="2.0">

	<xsl:param name="prefix" as="xs:string" select="'h'" />

	<xsl:output indent="yes" />

	<xsl:function name="mf:group" as="element()*">
		<xsl:param name="elements" as="element()*" />
		<xsl:param name="level" as="xs:integer" />
		<xsl:variable name="hh" select="concat($prefix, $level)" />
		<!-- <foo><xsl:value-of select="$hh"/></foo> -->
		<xsl:for-each-group select="$elements"
			group-starting-with="*[local-name() = $hh]">
			<bar title="{.}"/>
			<xsl:choose>
				<xsl:when test="starts-with(local-name(),$prefix)">
					<xsl:element name="{name()}">
						<xsl:sequence select="mf:group(current-group() except ., $level + 1)" />
					</xsl:element>
				</xsl:when>
				<xsl:otherwise>
				<foo title="{.}">
					<xsl:copy>
<!-- 						<xsl:apply-templates select="@* | node()" /> -->
						<xsl:apply-templates />
					</xsl:copy>
					</foo>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each-group>
	</xsl:function>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="h:html/h:body">
		<xsl:value-of select="local-name()" />
		<xsl:copy>
			<xsl:sequence select="mf:group(*, 1)" />
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>
