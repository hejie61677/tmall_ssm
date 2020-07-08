package com.hejie.tmall_ssm.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
  * @Program: tmall_ssm
  * @Description: 图片工具类
  * @Author: hejie
  * @Create: 2019/6/18
  */
public class ImageUtil {

    /**
      * @Description: 格式转化为jpg
      * @Author: hejie
      * @Date: 2019/6/18
      */
    public static BufferedImage change2jpg(File f) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
            PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
            pg.grabPixels();
            int width = pg.getWidth();
            int height = pg.getHeight();
            final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            return new BufferedImage(RGB_OPAQUE, raster, false, null);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: 调整图片大小,复制到其他路径
     * @Author: hejie
     * @Date: 2019/6/18
     */
    public static void resizeImage(File srcFile, int width, int height, File destFile) {
        try {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            Image image = ImageIO.read(srcFile);
            image = resizeImage(image, width, height);
            ImageIO.write((RenderedImage) Objects.requireNonNull(image), "jpg", destFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 调整图片大小,复制到其他路径
     * @Author: hejie
     * @Date: 2019/6/18
     */
    public static Image resizeImage(Image srcImage, int width, int height) {

        try {
            BufferedImage buffImg;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            return buffImg;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
