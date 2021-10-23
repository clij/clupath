# clupath
 
clupath is a bridge between [QuPath](https://qupath.github.io/) and [clij](https://clij.github.io/).

![Image](images/clupath.gif)

Right now, this is very preliminary.

## Installation
[Download and install QuPath version 0.2.3](https://github.com/qupath/qupath/releases/tag/v0.2.3). 
In order to make CLUPATH run in QuPath, download the following jar file and drag&drop it on QuPath. QuPath will take care of it and install it in the right folder.
* [clupath-0.5.1.4-jar-with-dependencies.jar](https://github.com/clij/clupath/releases/download/0.5.1.4/clupath-0.5.1.4-jar-with-dependencies.jar)

If you retrieve an error message such as "....jar file cannot be copied", navigate to the QuPath\extensions folder in your home directory, e.g. C:\Users\<username>\QuPath\extensions and delete other versions of clupath...jar.

## Examples
Example code for QuPaths script editor can be found in the [groovy](https://github.com/clij/clupath/tree/master/src/main/groovy) directory.

## Please note
It is recommended to [use clij from Fiji](https://clij.github.io/). QuPath support is experimental.

[Back to CLIJ documentation](https://clij.github.io/)

[Imprint](https://clij.github.io/imprint)
