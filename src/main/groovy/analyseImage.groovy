/**
* analyseImage.groovy
*
* This script shows how to process an image on the GPU using CLUPATH.
* 
* Author: Robert Haase, rhaase@mpi-cbg.de
*         August 2019
*/

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
