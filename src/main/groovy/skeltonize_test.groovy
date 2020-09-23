/**
* analyseImage.groovy
*
* This script shows how to process an image on the GPU using CLUPATH.
*
* Before running this script, you need to load an image in QuPath and 
* select a rectangular region of interest.
*
* 
* Partly adapted from 
* https://qupath.readthedocs.io/en/latest/docs/scripting/overview.html#working-with-imagej
* 
* Author: Robert Haase, rhaase@mpi-cbg.de
*         Pete Bankhead
*         September 2020
*/

import qupath.lib.regions.*
import qupath.imagej.tools.IJTools
import qupath.imagej.gui.IJExtension

// Read image and convert it to ImageJ
def server = getCurrentServer()
def parent = getSelectedObject()
double downsample = 2.0
def request = parent == null ? RegionRequest.createInstance(server, downsample) : RegionRequest.createInstance(server.getPath(), downsample, parent.getROI())
def pathImage = IJTools.convertToImagePlus(server, request)
def imp = pathImage.getImage()

import net.haesleinhuepf.clupath.CLUPATH

// setup clupath
clijx = CLUPATH.getInstance()
print(clijx.getGPUName())

// push input image to GPU memory
input = clijx.push(imp)

// generate another image in GPU memory of same size and type
binary = clijx.create(input)
result = clijx.create(input)

// apply Otsu thresholding
clijx.thresholdOtsu(input, binary)
// skeletonization
clijx.skeletonize(binary, result)

// pull back result and turn it into a QuPath ROI
imp = clijx.pull(result)
roi = clijx.pullAsROI(result)
imagePlane = IJTools.getImagePlane(roi, imp)
roi = IJTools.convertToROI(roi, -request.getX()/downsample, -request.getY()/downsample, downsample, imagePlane)

// cleanup GPU memory
clijx.clear()

// add the ROI as annotation
def annotation = PathObjects.createAnnotationObject(roi)
addObject(annotation)