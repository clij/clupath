# clupath
 
clupath is a bridge between [QuPath](https://qupath.github.io/) and [clij](https://clij.github.io/).

![Image](images/clupath-screenshot.png)

Right now, this is very preliminary.

## Installation
[Download and install QuPath version 0.2.0-m3](https://github.com/qupath/qupath/releases/tag/v0.2.0-m3). 
In order to make CLUPATH run in QuPath, download the following jar files, remove the number from their file name endings and put them in the `/app/` directory of your QuPath installation:

* [clupath-0.1.0.jar](https://github.com/clij/clupath/releases/download/0.1.0/clupath-0.1.0.jar)
* [bridj-0.7.0.jar](https://sites.imagej.net/clij/jars/bridj-0.7.0.jar-20181201213334)
* [clij_1.1.4.jar](https://github.com/clij/clij/releases/download/1.1.4/clij_-1.1.4.jar)
* [clij-clearcl-0.8.4.jar](https://github.com/clij/clij/releases/download/1.1.3/clij-clearcl-0.8.4.jar)
* [clij-core-1.1.4.jar](https://github.com/clij/clij/releases/download/1.1.4/clij-core-1.1.4.jar)
* [clij-coremem-0.5.5.jar](https://github.com/clij/clij/releases/download/1.1.3/clij-coremem-0.5.5.jar)
* [clij-legacy_-0.1.0.jar](https://github.com/clij/clij-legacy/releases/download/0.1.0/clij-legacy_-0.1.0.jar)
* [imagej-common-0.28.2.jar](https://sites.imagej.net/Java-8/jars/imagej-common-0.28.2.jar-20190516211613)
* [imglib2-5.6.3.jar](https://sites.imagej.net/Java-8/jars/imglib2-5.6.3.jar-20181204141527)
* [imglib2-realtransform-2.1.0.jar](https://sites.imagej.net/Java-8/jars/imglib2-realtransform-2.1.0.jar-20181204141527)
* [jocl-2.0.1.jar](https://sites.imagej.net/clij/jars/jocl-2.0.1.jar-20181201212910)
* [scijava-common-2.77.0.jar](https://sites.imagej.net/Java-8/jars/scijava-common-2.76.1.jar-20181204141527)

In the same folder, locate and edit `QuPath-0.2.0-m3.cfg`. 
In the line starting with `` append the following jar files by the end:
```
bridj-0.7.0.jar;clij_-1.1.4.jar;clij-clearcl-0.8.4.jar;clij-core-1.1.4.jar;clij-coremem-0.5.5.jar;clij-legacy_-0.1.0.jar;clupath-0.1.0.jar;imagej-common-0.28.2.jar;imglib2-5.6.3.jar;imglib2-realtransform-2.1.0.jar;jocl-2.0.1.jar;scijava-common-2.76.1.jar;
```

## Examples
Example code for QuPaths script editor can be found in the [groovy](https://github.com/clij/clupath/tree/master/src/main/groovy) directory.

## Please note
It is recommended to [use clij from Fiji](https://clij.github.io/clij-docs/installationInFiji). QuPath support is experimental.

[Back to CLIJ documentation](https://clij.github.io/)
