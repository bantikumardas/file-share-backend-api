package com.fileshare.file_share_backend.model;


public class FileWrapper {
    private String fileName;
    private String type;
    private String fileUrl;
    private Long uploadTime;
    private Long fileSize;

    public FileWrapper() {
    }

    public FileWrapper(String fileName, String type, String fileUrl, Long uploadTime, Long fileSize) {
        this.fileName = fileName;
        this.type = type;
        this.fileUrl = fileUrl;
        this.uploadTime = uploadTime;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
