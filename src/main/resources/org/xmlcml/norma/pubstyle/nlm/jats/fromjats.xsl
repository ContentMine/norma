<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- elements from JATS not included in PMR sheets -->

	<xsl:template match="abbrev">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="abbrev-journal-title">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="ack">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="address">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="aff">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="alt-text">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="alternatives ">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="name-alternatives">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="collab-alternatives">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="aff-alternatives">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="anonymous">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="app">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="app-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="array">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="article">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sub-article">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="response">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="article-categories">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="article-id">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="attrib">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="author-comment">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="author-notes">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="award-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="award-id">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="back">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="bio">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="bold">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="boxed-text">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="break">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="caption">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="chem-struct-wrapper">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="chem-struct-wrap">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="citation-alternatives">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="collab ">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="collab-alternatives/*">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="compound-kwd">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="compound-kwd-part">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conf-acronym">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conf-date">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conf-loc">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conf-name">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conf-acronym">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conf-num">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conf-sponsor">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conf-theme">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="conference">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="contract-num">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="contract-sponsor">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="contrib-id">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="contrib">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="copyright-statement">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="copyright-year">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="count">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="counts">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="custom-meta">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="day">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="season">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="year">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="def-list">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="degrees">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="disp-formula">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="disp-quote">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="elocation-id">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="email">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="equation-count">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="ext-link">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="inline-supplementary-material">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="fig">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="fig-count">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="fn">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="footer-metadata">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="journal-id">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="isbm">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="front">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="front-stub">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="funding-source">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="funding-statement">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="given-names">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="glossary">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="gloss-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="glyph-data">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="glyph-ref">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="grant-num">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="grant-sponsor">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="inline-graphic">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="graphic">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="history">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="hr">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="inline-formula">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="chem-struct">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="isbn">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="issn">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="issn-l">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="issue">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="issue-id">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="issue-part">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="issue-sponsor">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="issue-title">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="issue-title">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="italic">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="journal-subtitle">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="journal-title">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="journal-title-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="kwd">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="kwd-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="label">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="license">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="list">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="list-item">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="meta-name">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="meta-value">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="milestone-start">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="milestone-end">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="monospace">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="month">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="name">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="named-content">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="nested-kwd">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="notes">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="object-id">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="on-behalf-of">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="open-access">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="overline">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="license-p">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="page-count">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="permissions">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="prefix">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="preformat">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="price">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="principal-award-recipient">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="principal-investigator">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="private-char">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="processing-instruction()">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="product">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="pub-date">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="publisher">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="publisher-loc">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="publisher-name">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="ref">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="ref-count">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="ref-list">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="related-article">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="related-object">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="role">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="roman">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sans-serif">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sc">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sec">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sec-meta">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="self-uri">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="series-text">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="series-title">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sig">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="speech">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="statement">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="strike">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="string-name">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="styled-content">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sub">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sub-article">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="response">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="subj-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="subtitle">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="suffix">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sup">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="supplement">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="supplementary-material">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="table">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="thead">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="tbody">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="tfoot">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="col">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="colgroup">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="tr">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="th">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="td">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="table-count">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="table-wrap">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="target">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="textual-form">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="sec-meta" >
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="title">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="title-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="trans-subtitle">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="trans-title">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="trans-title-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="underline">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="uri">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="verse-group">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="verse-line">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="volume">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="volume-id">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="volume-series">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="word-count">
		<xsl:call-template name="container" />
	</xsl:template>
	<xsl:template match="xref">
		<xsl:call-template name="container" />
	</xsl:template>

</xsl:stylesheet>