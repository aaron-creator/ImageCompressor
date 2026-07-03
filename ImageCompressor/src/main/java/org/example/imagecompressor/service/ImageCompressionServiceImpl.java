package org.example.imagecompressor.service;

import org.example.imagecompressor.dto.ImageCompressionResponse;
import org.example.imagecompressor.util.ImageCompressionUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageCompressionServiceImpl implements ImageCompressionService {

    @Override
    public byte[] compressImage(MultipartFile file, int maxwidth, int maxheight, float quality) throws IOException {

        if(file == null || file.isEmpty())
            throw new IOException("Uploaded file is empty");

        return  ImageCompressionUtil.compressImage(file, maxwidth, maxheight, quality);
    }

    @Override
    public ImageCompressionResponse getCompressionDetails(MultipartFile file, byte[] compressedImage) {

        String originalSize = formatSize(file.getSize());
        String compressedSize = formatSize(compressedImage.length);

        return new ImageCompressionResponse(
                file.getOriginalFilename(),
                originalSize,
                compressedSize,
                "Image compressed successfully"
        );
    }


    private String formatSize(long bytes) {
        double kb = bytes / 1024.0;
        double mb = kb / 1024.0;

        if (mb >= 1) {
            return String.format("%.2f MB", mb);
        }

        return String.format("%.2f KB", kb);
    }

}
