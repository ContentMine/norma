# Transformations

Norma is able to transform or extract information from original documents. These include:

## PDF

### PDF to Image
for PDFBox See http://stackoverflow.com/questions/8705163/extract-images-from-pdf-using-pdfbox . We will hope to implement this.

### PDF to Text
we have implemented PDF2TXT which converts PDFs to (hopefully) UTF-8 text.

## HTML
The HTML from publishers varies enormously. Some is well-formed, without publisher-specific additiona, without JS, with clear nested sections. At the other end of the spectrum is an awful mixtures of unbalanced tags, unquoted attributes, javascript and self-loading documents.

### tidying
Tidying is though Transform calling the HTML library. This has options to use `jtidy`, `htmlunit` or `jsoup`. The value of these changes with later releases of the programs , or falls when new features are added to the publisher HTML. The likelihood is that publishers will converge towards some undocumented HTML5-like version with occasional errors, non-standard tags.

```
norma --project <pdir> --input fulltext.html --output fulltext.xhtml --html jsoup
```
will transform `fulltext.html` into `fulltext.xhtml`. This is guaranteed to be well-formed (we think).

### decrufting
The publisher HTML has many chunk unrelated to the scholarly text. Examples are download counts, related articles, helpful links, etc. In addition the text may be mangled so as to display interactivey in some way. These can eb removed with an `XSLT` stylesheet, usually `toHtml.xsl`.
This stylesheet is often called with a symbol. Example (for Taylor and Francis):
```
norma --project <pdir> --input fulltext.xhtml --output scholarly.html --transform tfhtml
```
where `stylesheetByName.xml` contains fields such as:
```
  <stylesheet name="tf2html">/org/xmlcml/norma/pubstyle/tf/toHtml.xsl</stylesheet>
```
which maps `tfhtml` onto the resource `/org/xmlcml/norma/pubstyle/tf/toHtml.xsl`

### scholarly Html
This involves proper structuring of the document, tagging sections. It will be dynamic as it will involve tracking the ScholarlyHTML W3C community group.
