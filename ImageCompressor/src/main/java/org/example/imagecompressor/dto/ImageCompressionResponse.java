package org.example.imagecompressor.dto;

public class ImageCompressionResponse {

    private String fileName;
    private String originalSize;
    private String compressedSize;
    private String message;

    public ImageCompressionResponse() {
    }

    public ImageCompressionResponse(String fileName,
                                    String originalSize,
                                    String compressedSize,
                                    String message) {
        this.fileName = fileName;
        this.originalSize = originalSize;
        this.compressedSize = compressedSize;
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOriginalSize() {
        return originalSize;
    }

    public String getCompressedSize() {
        return compressedSize;
    }

    public String getMessage() {
        return message;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOriginalSize(String originalSize) {
        this.originalSize = originalSize;
    }

    public void setCompressedSize(String compressedSize) {
        this.compressedSize = compressedSize;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}