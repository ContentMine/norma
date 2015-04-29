# Help output from Norma (20150329)

This is generated from the help on args.xml and will change when they are updated

====NORMA====
Norma converters raw files into scholarlyHTML, and adds tags to sections.
Some of the conversion is dependent on publication type (--pubstyle) while some
is constant for all documents. Where possible Norma guesses the input type, but can
also be guided with the --extensions flag where the file/URL has no extension. 

-i or --input  file(s)_and/or_url(s)

			INPUT:
			Input stream (Files, directories, URLs), Norma tries to guess reasonable actions. 
				also expands some simple wildcards. The argument can either be a single object, or a list. Within objects
				the content of curly brackets {...} is expanded as wildcards (cannot recurse). There can be multiple {...}
				within an object and all are expanded (but be sensible - this could generate the known universe and crash the
				system. (If this is misused it will be withdrawn). Objects (URLs, files) can be mixed but it's probably a
				poor idea.
				
				The logic is: 
				(a) if an object starts with 'www' or 'http:' or 'https;' it's assumed to be a URL
				(b) if it is a directory, then the contents (filtered by extension) are added to the list as files
				(c) if it's a file it's added to the list
				the wildcards in files and URLs are then expanded and the results added to the list
				
				Current wildcards:
				  {n1:n2} n1,n2 integers: generate n1 ... n2 inclusive
				  {foo,bar,plugh} list of strings
					  
		

-q or --quickscrapeNorma  director(ies)

			FILE_CONTAINER:
			create directory  contentmine/some/where/journal.pone.0115884/. It may contain
			
			results.json * // a listing of scraped files
			
			fulltext.xml ? // publishers XML
			fulltext.pdf ? // publishers PDF
			fulltext.html ? // raw HTML
			provisional.pdf ? // provisional PDF (often disappears)
			
			foo12345.docx ? // data files numbered by publisher/author
			bar54321.docx ?
			ah1234.cif ? // crystallographic data
			pqr987.cml ? // chemistry file
			mmm.csv ? // table
			pic5656.png ? // images
			pic5657.gif ? // image
			suppdata.pdf ? // supplemental data
			
			and more
			
			only results.json is mandatory. However there will normally be at least one fulltext.* file and probably at 
			least one *.html file (as the landing page must be in HTML). Since quickscrape can extract data without 
			fulltext it might also be deployed against a site with data files.
			
			There may be some redundancy - *.xml may be transformable into *.html and *.pdf into *.html. The PDF may also 
			contain the same images as some exposed *.png.
		

-o or --output  file_or_directory

			OUTPUT
			 Output is to local filestore ATM. If there is only one input
			after wildcard expansion then a filename can be given. Else the argument must be a writeable directory; Norma
			will do her best to create filenames derived from the input names. Directory structures will not be preserved
			See also --recursive and --extensions",
		

-r or --recursive 

			RECURSIVE
			input directories
			If the input is a directory then by default only the first level is searched
			if the --recursive flag is set then all files in the directory tree may be input
			See also --extensions
		

-e or --extensions  ext1 [ext2...]

			EXTENSIONS
				When a directory or directories are searched then all files are input by default
				It is possible to limit the search by using only certain extensions
				See also --recursive.
		

 or --summaryfile  summaryfile

		SUMMARY FILE
		Directory to write summaries to.
		

-h or --help 

			HELP
				outputs help for all options, including superclass DefaultArgProcessor
		

-c or --chars  [pairs of characters, as unicode points; i.e. char00, char01, char10, char11, char20, char21

			CHARS:
			Replaces one character by another. There are many cases where original characters are unsuitable 
			for science and can be replaced (often by low codepoints).
			Smart (balanced) quotes can usually be replaced by \" or '
			mdash is often used where minus is better
			Format, strings of form u1234 
		

-d or --divs  expression [expression] ...

			DIVS:
			List of expressions identifying XML element to wrap as divs/sections
			Examples might be h1, h2, h3, or numbers sections
			Still under development.
		

-n or --name   tag1,tag2 [tag1,tag2 ....]

			NAME:
			List of comma-separated pairs of tags; the first is replaced by the second. Example might be:
			  em,i strong,b
			i.e. replace all <em>...</em> by <i>...</i>
		

-p or --pubstyle  pub_code

			PUBSTYLE:
			Code or mnemomic to identifier the publisher or journal style. 
			this is a list of journal/publisher styles so Norma knows how to interpret the input. At present only one argument 
			is allowed. The pubstyle determines the format of the XML or HTML, the metadata, and
			soon how to parse the PDF. At present we'll use mnemonics such as 'bmc' or 'biomedcentral.com' or 'cellpress'.
			
			To get a list of these use "+"--pubstyle"+" without arguments. Note: under early development and note also that 
			publisher styles change and can be transferred between publishers and journals
			
			NOTE: Does not trigger any actions
		

-z or --standalone  boolean

			STANDALONE:
			Treats XML document as standalone. Very useful as some parsers will take ages resolving the DTD and often fail
			if not connected to the net. 
		

-s or --strip  [options to strip]

			STRIP:
			List of XML components to strip from the raw well-formed HTML;
			if a list is given, then use that; if this argument is missing (or the single
			string '#pubstyle' then the Pubstyle defaults are used. If there are no arguments
			then no stripping is done. a single '?' will list the Pubstyle defaults
			The following are allowed:
			  an element local name (e.g. input)
			  an XPath expression (e.g. //*[@class='foobar'])
			  !DOCTYPE (strips <!DOCTYPE ...> which speeds up reading)
			  an attribute (e.g. @class) (NotYetImplemented)
			
			Note that tokens are whitespace-separated (sorry if this interacts with XPath)
			
			Examples: 
			  input script object (removes these three element
			  //*[contains(@class,'sidebar')]  (removes <div class='sidebar'> ... </div>
			  !DOCTYPE (removes <!DOCTYPE ...> before parsing))
			  !DOCTYPE input script object //*[contains(@class,'sidebar')] (removes all the above)
			
		

-t or --tidy  [HTML tidy option]

			TIDY:
			Choose an HTML tidy tool. At present we have:
			  JTidy JSoup and HtmlUnit 
			(NYI) This is very experimental at present.
		

-x or --xsl  stylesheet

			XSL:
			
			Transform XML or HTML input with stylesheet. Argument may be a file/URL reference to a stylesheet,
			or a code from one of {nlm, jats ...}
			the codes are checked first and then the document reference.
			
			Requires input and output files (--input and --output). These must be reserved names (e.g. fulltext.xml) 
			in qsNorma and
			determine the type of files to convert.
		
		NOTE: the special code `pdf2txt` will convert PDF files to TXT using PDFBox's PDFTextStripper. This does as good a job
		as possible 
		
