package org.example.imagecompressor.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageCompressionUtil {
    public static byte[] compressImage(MultipartFile file, int maxwidth, int maxheight, float quality) throws IOException {

//        0.2f  // low quality
//        0.5f  // medium quality
//        0.8f  // good quality
//        1.0f  // highest quality

        if(quality < 0.0f || quality > 1.0f) {
            throw new IllegalArgumentException("Quality must be between 0.0 and 1.0");
        }

        // Step 1: Upload image is handled by MultipartFile

        // Step 2: Read original image
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        if(originalImage == null) {
            throw new IOException("File is empty or Unsupported image type or invalid image file");
        }

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        BufferedImage finalImage;

        // Step 3: Resize if width/height is too large
        if(originalWidth > maxwidth || originalHeight > maxheight) {
            finalImage = resizeImage(originalImage, maxwidth, maxheight);
        }
        else {
            finalImage = convertToRgbImage(originalImage);
        }

        // Step 4: Apply JPEG quality compression
        return saveAsCompressedJpeg(finalImage, quality);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage,
                                             int maxWidth,
                                             int maxHeight) {

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;

        double scale = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        BufferedImage resizedImage =
                new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = resizedImage.createGraphics();

        // White background for PNG transparency
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, newWidth, newHeight);

        graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        graphics.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );

        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        graphics.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics.dispose();

        return resizedImage;
    }

    private static BufferedImage convertToRgbImage(BufferedImage originalImage) {

        BufferedImage rgbImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = rgbImage.createGraphics();

        // White background for transparent PNG images
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());

        graphics.drawImage(originalImage, 0, 0, null);
        graphics.dispose();

        return rgbImage;
    }

    private static byte[] saveAsCompressedJpeg(BufferedImage image,
                                               float quality) throws IOException {

        Iterator<ImageWriter> writers =
                ImageIO.getImageWritersByFormatName("jpg");

        if (!writers.hasNext()) {
            throw new IOException("No JPG writer found");
        }

        ImageWriter writer = writers.next();

        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();

        try (ImageOutputStream imageOutputStream =
                     ImageIO.createImageOutputStream(byteArrayOutputStream)) {

            writer.setOutput(imageOutputStream);

            ImageWriteParam param = writer.getDefaultWriteParam();

            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality);
            }

            writer.write(null, new IIOImage(image, null, null), param);

        } finally {
            writer.dispose();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
