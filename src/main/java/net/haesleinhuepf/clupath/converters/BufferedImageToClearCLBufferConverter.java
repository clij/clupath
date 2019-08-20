package net.haesleinhuepf.clupath.converters;

import ij.ImagePlus;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.converters.AbstractCLIJConverter;
import net.haesleinhuepf.clij.converters.CLIJConverterPlugin;
import net.haesleinhuepf.clij.converters.implementations.RandomAccessibleIntervalToClearCLBufferConverter;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.display.imagej.ImageJFunctions;
import org.scijava.plugin.Plugin;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;

/**
 * Author: haesleinhuepf
 *         August 2019
 */
@Plugin(type = CLIJConverterPlugin.class)
public class BufferedImageToClearCLBufferConverter extends AbstractCLIJConverter<BufferedImage, ClearCLBuffer> {

    @Override
    public ClearCLBuffer convert(BufferedImage image) {
        long[] dimensions = new long[2];

        int width = image.getWidth();
        int height = image.getHeight();
        if (image.getData().getNumBands() > 1) {
            dimensions = new long[3];
            dimensions[2] = image.getData().getNumBands();
        }
        dimensions[0] = width;
        dimensions[1] = height;

        int numberOfPixelsPerSlice = (int)(dimensions[0] * dimensions[1]);
        long numberOfPixels = numberOfPixelsPerSlice;

        ClearCLBuffer target = clij.createCLBuffer(dimensions, NativeTypeEnum.Float);

        float[] inputArray = new float[(int) numberOfPixels];
        if (dimensions.length > 2) {
            float[] sourceArray = new float[numberOfPixelsPerSlice];
            for (int z = 0; z < dimensions[2]; z++) {
                image.getData().getSamples(0, 0, width, height, z, sourceArray);
                System.arraycopy(sourceArray, 0, inputArray, z * numberOfPixelsPerSlice, sourceArray.length);
            }
            FloatBuffer byteBuffer = FloatBuffer.wrap(inputArray);
            target.readFrom(byteBuffer,true);
            return target;

        } else {
            image.getData().getSamples(0, 0, width, height, 0, inputArray);
            FloatBuffer byteBuffer = FloatBuffer.wrap(inputArray);
            target.readFrom(byteBuffer,true);
            return target;

        }

    }

    public ClearCLBuffer convertLegacy(ImagePlus source) {
        RandomAccessibleInterval rai = ImageJFunctions.wrapReal(source);
        RandomAccessibleIntervalToClearCLBufferConverter raitcclbc = new RandomAccessibleIntervalToClearCLBufferConverter();
        raitcclbc.setCLIJ(clij);
        return raitcclbc.convert(rai);
    }

    @Override
    public Class<BufferedImage> getSourceType() {
        return BufferedImage.class;
    }

    @Override
    public Class<ClearCLBuffer> getTargetType() {
        return ClearCLBuffer.class;
    }
}
