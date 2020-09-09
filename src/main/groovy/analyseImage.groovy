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
*         September 2020
*/

import qupath.lib.regions.*
import qupath.imagej.tools.IJTools
import qupath.imagej.gui.IJExtension

// Read image and convert it to ImageJ
def server = getCurrentServer()
def original_roi = getSelectedROI()
double downsample = 1.0
def request = RegionRequest.createInstance(server.getPath(), downsample, original_roi)
def pathImage = IJTools.convertToImagePlus(server, request)
def imp = pathImage.getImage()

import net.haesleinhuepf.clupath.CLUPATH

// setup clupath
clij2 = CLUPATH.getInstance();
print(clij2.getGPUName());

// push input image to GPU memory
input = clij2.push(imp);

// generate another image in GPU memory of same size and type
result = clij2.create(input);

// apply Otsu thresholding
clij2.thresholdOtsu(input, result);

// pull back result and turn it into a QuPath ROI
imp = clij2.pull(result);
roi = clij2.pullAsROI(result);
imagePlane = IJTools.getImagePlane(roi, imp);
roi = IJTools.convertToROI(roi, -original_roi.getBoundsX(), -original_roi.getBoundsY(), downsample, imagePlane);

// cleanup GPU memory
clij2.clear();

// add the ROI as annotation
def annotation = PathObjects.createAnnotationObject(roi)
addObject(annotation)

