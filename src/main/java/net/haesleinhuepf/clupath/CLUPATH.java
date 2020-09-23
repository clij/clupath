package net.haesleinhuepf.clupath;

import ij.ImagePlus;
import ij.gui.Roi;
import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij.utilities.CLIJOps;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.plugins.PullAsROI;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clupath.converters.BufferedImageToClearCLBufferConverter;
import qupath.lib.regions.ImagePlane;
import qupath.lib.roi.interfaces.ROI;

import java.awt.image.BufferedImage;

/**
 * The CLUPATH gateway.
 *
 * Author: haesleinhuepf
 *         September 2020
 */
public class CLUPATH extends CLIJx {
    private static CLUPATH instance;

    public CLUPATH() {
        super(CLIJ.getInstance());
    }

    private CLUPATH(CLIJ clij) {
        super(clij);
    }

    public static CLUPATH getInstance() {
        if (instance == null) {
            instance = new CLUPATH(CLIJ.getInstance());
        }
        return instance;
    }

    public static CLUPATH getInstance(String id) {
        if (instance == null) {
            instance = new CLUPATH(CLIJ.getInstance(id));
        }
        return instance;
    }

    public ClearCLBuffer pushBufferedImage(BufferedImage data) {
        BufferedImageToClearCLBufferConverter converter = new BufferedImageToClearCLBufferConverter();
        converter.setCLIJ(clij);

        return converter.convert(data);
    }

    //import qupath.imagej.tools.IJTools;
    /*
    public ROI pullQuPathROI(ClearCLBuffer binary) {
        ImagePlus imp = clij.pull(binary);
        Roi roi = PullAsROI.pullAsROI(this, binary);

        ImagePlane imagePlane = IJTools.getImagePlane(roi, imp);

        return IJTools.convertToROI(roi, 0, 0, 1, imagePlane);
    }
    */
}
