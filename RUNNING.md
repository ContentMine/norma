# RUNNING NORMA

Norma is run by:
```
norma [args]
```
where `[args]` are zero or more arguments. If `norma` is run with no arguments
```norma```
then she outputs all the possible arguments with full description. (This is controlled by 
```src/main/resources/org/xmlxml/norma/args.xml`` - see INSTALLING.md for details).

`norma` is designed to use a `QuickscrapeNorma` (or `QSNorma`) directory as the primary input/output mechanism
(see QUICKSCRAPE_NORMA.md). This directory holds conventionally named files which are used for input and output
by `norma` and `AMI` plugins. `norma` will generally not create output files which are not contained in a `QSNorma`.

## Creating a `QSNorma` directory
The commonest way to do this is to read an `{XML|HTML|PDF}` file into `norma` and create a `QSNorma` directory with a
*copy* of the input file. (This is because some input files may be read-only, either because of permissions or because
they are received as `inputStream`s. Example:
```
		norma --input src/test/resources/org/xmlcml/norma/miscfiles/mdpi-04-00932.xml --output target/quickscrape/single/
```
will create a new (`QSNorma`) directory:
```
target/quickscrape/single/mdpi-04-00932
```
which contains a copy of the original XML file named conventionally:
```fulltext.xml```

This would be similar to quickscraping a web site with only an XML file (except it would also contain
a `results.json` file.

## Creating multiple `QSNorma` directories
`norma` can accept multiple input files (after the `--input` argument) :
```
		norma --input \
		src/test/resources/org/xmlcml/norma/miscfiles/mdpi-04-00932.xml \
		src/test/resources/org/xmlcml/norma/miscfiles/peerj-727.xml \
		src/test/resources/org/xmlcml/norma/miscfiles/pensoft-4478.xml \
		 --output target/quickscrape/multiple/
```
will create three new (`QSNorma`) directories
```
target/quickscrape/multiple/mdpi-04-00932
target/quickscrape/multiple/peerj-727
target/quickscrape/multiple/pensoft-4478
```
each of which contains a copy of the original XML file named conventionally:
```fulltext.xml```


