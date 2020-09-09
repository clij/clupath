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

// Read image & show in ImageJ
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

// add the ROI as annotation
def annotation = PathObjects.createAnnotationObject(roi)
addObject(annotation)

/*
import qupath.lib.scripting.QP
import qupath.lib.regions.RegionRequest
import net.haesleinhuepf.clupath.CLUPATH
import net.haesleinhuepf.clij.CLIJ

// setup clupath
clupath = CLUPATH.getInstance();
print(clupath.getGPUName());

// get current image from QuPath
def imageData = QP.getCurrentImageData()
server = imageData.getServer();
request = RegionRequest.createInstance(server.getPath(), 1, 0, 0, server.getWidth(), server.getHeight());
image = imageData.getServer().readBufferedImage(request);

// push image to GPU
input = clupath.push(image);
// reserve some additional memory on GPU
blurred = clupath.create(input);
detections = clupath.create(input);

// analyse image
clupath.op().blur(input, blurred, 12, 12, 0);
clupath.op().detectMaximaBox(blurred, detections, 1);
numberOfObjects = clupath.op().sumPixels(detections);
print("Number of objects: " + numberOfObjects);

// for now we use ImageJ via CLIJ for showing the result
visualisation = clupath.create(input);
clupath.op().maximumBox(detections, visualisation, 3, 3, 0);
CLIJ.getInstance().show(visualisation, "detected spots");

input.close();
blurred.close();
detections.close();
visualisation.close();
*/