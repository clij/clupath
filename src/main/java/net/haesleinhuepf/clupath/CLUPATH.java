package net.haesleinhuepf.clupath;

import ij.ImagePlus;
import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij.utilities.CLIJOps;
import net.haesleinhuepf.clupath.converters.BufferedImageToClearCLBufferConverter;

import java.awt.image.BufferedImage;

/**
 * The CLUPATH gateway.
 *
 * Author: haesleinhuepf
 *         August 2019
 */
public class CLUPATH {
    private static CLUPATH instance;
    private final CLIJ clij;

    private CLUPATH(CLIJ clij) {
        this.clij = clij;
    }

    public static CLUPATH getInstance() {
        if (instance == null) {
            instance = new CLUPATH(CLIJ.getInstance());
        }
        return instance;
    }

    public CLUPATH getInstance(String id) {
        if (instance == null) {
            instance = new CLUPATH(CLIJ.getInstance(id));
        }
        return instance;
    }

    public ClearCLBuffer push(ImagePlus imp) {
        return clij.convert(imp, ClearCLBuffer.class);
    }

    public ClearCLBuffer push(BufferedImage data) {
        BufferedImageToClearCLBufferConverter converter = new BufferedImageToClearCLBufferConverter();
        converter.setCLIJ(clij);
        return converter.convert(data);
    }

    public ImagePlus pull(ClearCLBuffer buffer ) {
        return clij.convert(buffer, ImagePlus.class);
    }

    public ClearCLBuffer create(long[] dimensions, NativeTypeEnum type) {
        return clij.create(dimensions, type);
    }

    public ClearCLBuffer create(ClearCLBuffer buffer) {
        return clij.create(buffer);
    }

    public CLIJOps op() {
        return clij.op();
    }

    public String getGPUName() {
        return clij.getGPUName();
    }
}
