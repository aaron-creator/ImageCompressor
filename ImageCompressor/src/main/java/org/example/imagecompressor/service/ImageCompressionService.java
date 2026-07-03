package org.example.imagecompressor.service;

import org.example.imagecompressor.dto.ImageCompressionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageCompressionService {

    byte[] compressImage(MultipartFile file, int maxwidth, int maxheight, float quality) throws IOException;

    ImageCompressionResponse getCompressionDetails(MultipartFile file, byte[] compressedImage);
}
