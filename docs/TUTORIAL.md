# PLoS Resource and Species Tutorial

Currently the PLoS scraper is not deployed so we have downloaded 100 random files from PLoSONE from the (Europe) PMC web site. This shows how they are transformed to `scholarly.html` for use by `ami-plugins`. 

## raw files 

The filenames are of the form: `PLoS_One_2015_Mar_6_10(3)_e0119090.nxml`. `norma` requires XML files to be of the form `*.xml` so we have copied them to `e0119090.xml`, etc.

We must do this in two stages (later we may combine this)
 * convert them into CM directories 
 * generate `scholarly.html` for each.
 
### converting a single file to a ContentMine Directory

None of the files is in ContentMine (CM) directory format, so we have to create this. Taking a single file

```
e0115544.xml
```
in the directory
```
src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/
```

we can create a commandline:
```
norma -i src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/e0115544.xml -o target/plos10/
```

(You may  have to adjust the directories). We set the output (`-o`) to be the (new) CM directory parent `target/plos10/`, but you may
want somewhere else. Running this gives debug output (this may vary):
```
CREATING QN FROM INPUT:[src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/e0119090.xml]
QNF fulltext.xml; target/plos10/e0119090
```
so we have created `fulltext.xml` in `target/plos10/e0119090/`. Notice how the filename has been changed into the directory name.

### creating a single `scholarly.html`

We can use the same directory and create a `scholarly.html` from the `fulltext.xml`.

```
norma -q target/plos10/e0119090 --input fulltext.xml --output scholarly.html --xsl nlm2html
```

read this as:
 * run `norma` as a transformer
 * on the CM directory `target/plos10/e0119090`
 * use the file `fulltext.xml` in it as input
 * use the (XSL) stylesheet `nlm2html` to transform it
 * into the output `scholarly.html`

We have specifically developed the stylesheet for XML (such as PLoSONE) which conforms to `JATS` `NLM` . Only a few journals (BMC, Elsevier) use a different DTD and we can create those variants fairly easily. The output is:

```
NormaArgProcessor  - Writing : target/plos10/e0119090/scholarly.html
```
and if we list the directory:
```
localhost:norma pm286$ ls -lt target/plos10/e0119090/
total 488
-rw-r--r--  1 pm286  staff  146522 12 Apr 15:42 scholarly.html
-rw-r--r--  1 pm286  staff   99166 11 Apr 11:37 fulltext.xml
```

### multiple files

If we have 10, or 100 (or even more) files we need to automate the conversion. `norma` ha a command to process all the files *within a directory*. We have 11 files in `src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/` :
```
localhost:norma pm286$ ls src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/
e0115544.xml	e0116596.xml	e0117956.xml	e0118685.xml	e0118757.xml	e0119090.xml
e0116215.xml	e0116903.xml	e0118659.xml	e0118692.xml	e0118792.xml
```
we want to convert them to `scholarly.html` :
```
norma --input src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/ --output target/plos10/  --extensions xml
```
This means 
 * take the `--input` as the given directory
 * create an `--output` directory
 * look for all XML files (`--extensions xml`) in all subdirectories (`--recursive true`)

This should create the 11 XML files in `target/plos10/`.

Then we convert them. Now the top directory is `target/plos10/`. It's not a CM directory but it has many child CM directories and converts each. We use the same command as before:
```
norma -q target/plos10/ -i fulltext.xml -o scholarly.html --xsl nlm2html
```
which will create:

```
localhost:norma pm286$ tree target/plos10
target/plos10
├── e0115544
│   ├── fulltext.xml
│   └── scholarly.html
├── e0116215
│   ├── fulltext.xml
│   └── scholarly.html
├── e0116596
│   ├── fulltext.xml
│   └── scholarly.html
├── e0116903
│   ├── fulltext.xml
│   └── scholarly.html
├── e0117956
│   ├── fulltext.xml
│   └── scholarly.html
├── e0118659
│   ├── fulltext.xml
│   └── scholarly.html
├── e0118685
│   ├── fulltext.xml
│   └── scholarly.html
├── e0118692
│   ├── fulltext.xml
│   └── scholarly.html
├── e0118757
│   ├── fulltext.xml
│   └── scholarly.html
├── e0118792
│   ├── fulltext.xml
│   └── scholarly.html
└── e0119090
    ├── fulltext.xml
    └── scholarly.html
```

The results of this will be used in SpeciesTutorial.md


 