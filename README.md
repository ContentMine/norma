# NHTML

A tool to convert a variety of inputs into normalized, tagged, XHTML (with embedded/linked SVG and PNG where
appropriate. The emphasis is on scholarly publicatuions but much of the technology is general. Likely to be renamed soonish (?Norma).

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

$ 
{PDF, HTML, ...} => Norma => NHTML => AMI => results (XML or JSON)
                      ^                ^
                      |                |
                    tagger           plugin
                    
$

## Taggers and Normalization

Each document type has a tagger, based on its format, structure and vocabulary. For example an HTML file from
PLoSONE is not well-formed and must be converted, largely automatic. Some documents are "flat" and must be grouped into sections (e.g. Elsevier and Hindawi HTML have no structuring ``div``s. Then the tagger will identify sections
based on XPaths and add tags which should be normalized across a wide range of input sources.



