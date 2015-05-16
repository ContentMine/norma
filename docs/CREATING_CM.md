# CREATING CM directories

*** BY FAR THE SAFEST WAY IS TO USE QUICKSCRAPE ***

Any other method is likely to lead to fies out of sync unless you are acreful with what you are doing.
However it can be sometimes useful to create CMDirs from single files.
This documentation may not have been thoroughly checked.

## Input

For a command: 

``` norma -i foo/bar/a12345.suffix -o plugh/xyzzy```

the system will create a CMDir of the form:

``` plugh/xyzzy/a12345```

It will then use ```suffix``` to create either reserved files (e.g. ```fulltext.xml```) in the CMDir or reserved subdirectories
of the form:

``` plugh/xyzzy/a12345/image```

to hold the images. As there can be several images (e.g.  ```plugh/xyzzy/a12345.png``` ) we use the given names, such as:

``` plugh/xyzzy/a12345/image/a12345.png```

This is verbose and also leads to a separate CMDir for each image.

## File types

The following suffixes are supported:

### Single reserved files

The CMDir is generated from the ```-o mydir``` parameter and the input baseNames ```(FilenameUtile.getBaseName())```

```mydir/bar``` is the CMDir.

```foo/bar.xml``` is copied to ```mydir/bar/fulltext.xml```
```foo/bar.html``` is copied to ```mydir/bar/fulltext.html```
```foo/bar.pdf``` is copied to ```mydir/bar/fulltext.pdf```
```foo/bar.epub``` is copied to ```mydir/bar/fulltext.epub```
```foo/bar.txt``` is copied to ```mydir/bar/fulltext.txt```

### Image files

```foo/bar.png``` is copied to ```mydir/bar/image/bar.png```

Analogous copies for:
 ```gif```, ```jpg```, ```tif```
 
### Supplemental Data files

```foo/bar.doc``` is copied to ```mydir/bar/supplement/bar.png```

Analogous copies for:
 ```docx```, ```csv```, ```tex```, ```ppt```, ```pptx```, ...
 
### SVG Data files

```foo/bar.svg``` is copied to ```mydir/bar/svg/bar.svg```

 
 