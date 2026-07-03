package org.example.imagecompressor.controller;


import org.example.imagecompressor.dto.ImageCompressionResponse;
import org.example.imagecompressor.service.ImageCompressionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageCompressionController {

    private final ImageCompressionService imageCompressionService;

    private ImageCompressionController(ImageCompressionService imageCompressionService) {
        this.imageCompressionService = imageCompressionService;
    }

    @PostMapping("/compress")
    public ResponseEntity<byte[]> compressImage(@RequestParam("File") MultipartFile file,
                                                @RequestParam(defaultValue = "1200") int maxWidth,
                                                @RequestParam(defaultValue = "800") int maxHeight,
                                                @RequestParam(defaultValue = "0.5" ) float quality
                                                )   throws IOException {

        byte[] compressedImage = imageCompressionService.compressImage(
                file,
                maxWidth,
                maxHeight,
                quality
        );
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filenameCompressed-Image.jpg" + file.getOriginalFilename())
                .contentType(MediaType.IMAGE_JPEG)
                .body(compressedImage);

    }

    @PostMapping("/compress/details")
    public ResponseEntity<ImageCompressionResponse> compressImageWithDetails(@RequestParam("File") MultipartFile file,
                                                                             @RequestParam(defaultValue = "1200") int maxWidth,
                                                                             @RequestParam(defaultValue = "1200") int maxHeight,
                                                                             @RequestParam(defaultValue = "0.5") float quality) throws IOException {
        byte [] compressedImage = imageCompressionService.compressImage(file,
                maxWidth,
                maxHeight,
                quality
        );

        ImageCompressionResponse response = imageCompressionService.getCompressionDetails(file, compressedImage);

        return ResponseEntity.ok().body(response);

    }

}
