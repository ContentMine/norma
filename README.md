# Norma

A tool to convert a variety of inputs into normalized, tagged, XHTML (with embedded/linked SVG and PNG where
appropriate. The emphasis is on scholarly publicatuions but much of the technology is general.  Used to be called NHTML.

## Overview

See https://github.com/ContentMine/nhtml at ContentMine site. We will hope to merge these two documents.

## History

Originally AMI (http://bitbucket.org/petermr/ami-core) involved the conversion of legacy inputs (PDF, XML, HTML, etc.) into well-formed documents which were then searched by AMI-plugins. This required a Visitor pattern
with n inputs and m visitors. The inputs could be PDF, SVG, HTML, XML, etc. and AMI often had to convert. This became unmanageable.

## Current architecture

We have now split this into two components:

  * Norma. This takes legacy files (more later) and converts to normalized, tagged, XHTML (NHTML)
  * AMI. This reads NHTML and applies one or more plugins (used to be called visitors).
  
The workflow is then roughly

```
{PDF, HTML, ...} => Norma => NHTML => AMI => results (XML or JSON)
                      ^                ^
                      |                |
                    tagger           plugin
                    
```

## Taggers and Normalization

Each document type has a tagger, based on its format, structure and vocabulary. For example an HTML file from
PLoSONE is not well-formed and must be converted, largely automatic. Some documents are "flat" and must be grouped into sections (e.g. Elsevier and Hindawi HTML have no structuring ``div``s. Then the tagger will identify sections
based on XPaths and add tags which should be normalized across a wide range of input sources.

### Format

Taggers will be created in XML, and use XSLT2 where possible. The tagger carries out the following:

 * normalize to well-formed XHTML. This may be automatic but may also require some specific editing for some of the
 worst inputs (lackof quotes, etc.) We use Jsoup and HtmlUnit as appropriate and these do a good job most of the time.
 * structure the XHTML. Some publications are "flat" (e.g h2 and h3 are siblings) and need structuring (XSLT2)
 * tag the XHTML. We use XPath (with XSLT2) to add ``tag`` attributes to sections. These can be then recognized by AMI using the ``-x`` xpath-like option.
 
## Development of taggers

In general each publication type requires a tagger. This is made much easier where a publisher uses the same toolchain for all its publications  (e.g. BMC, Hindawi or Elsevier). We believe that taggers can be developed by most people , given a supportive community. The main requirements are:

 * understand the structure and format of the input.
 * work out how this maps onto similar, solved, publications.
 * write XPath expressions for the tagging. If you understand filesystems and the commandline you shouldn't have much problem and we believe it should take about 15-30 minutes.
 * use established tag names for the sections.
 
## Legacy formats

### PDF

The main legacy format is PDF. We provide a Java-based converter (PDF2XML) which can be called automatically from Norma. All PDF converters are lossy and likely to fail slightly or seriously on boxes, tables, etc. PDF2XML concentrates on science and particularly mathematical symbols, styling/italics and sub/superscripts. You may have other converters which do a better job of some of the parts - and should configure them to output HTML (which we can normalize later.

### SVG

Some graphics is published as vectors within PDF and these can be converted into SVG. The SVG is wrapped in NHTML and can, in certain cases such as chemistry, be converted into science. It's possible that formats such as EPS or WMF/EMF can be converted into SVG.

### DOC/X

Some years ago I wrote a semi-complete parser for DOCX but it's lost... Probably could resurrect if required. Most likely use would be theses or possibly arXiv.