package com.fileshare.file_share_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class File {
    public static String separator;
    @Id
    private String id;
    private String fileName;
    private String type;
    private String code;
    private String uniqueKey;
    private Long expiryTime;
    private String fileUrl;
    private String publicId;
    private Long uploadTime;
    private int maxDownloads;
    @Column(columnDefinition = "int default 0")
    private int downloaded;
    private Long fileSize;
    private String emailId;
    private boolean isEmailSend;
    private String senderName;

    public File() {
    }

    public File(String id, String fileName, String code, String type, String uniqueKey, Long expiryTime, String fileUrl, Long uploadTime, int maxDownloads) {
        this.id = id;
        this.fileName = fileName;
        this.code = code;
        this.type = type;
        this.uniqueKey = uniqueKey;
        this.expiryTime = expiryTime;
        this.fileUrl = fileUrl;
        this.uploadTime = uploadTime;
        this.maxDownloads = maxDownloads;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public boolean getIsEmailSend() {
        return isEmailSend;
    }

    public void setIsEmailSend(boolean isEmailSend) {
        this.isEmailSend = isEmailSend;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public int getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(int downloaded) {
        this.downloaded = downloaded;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
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

    public int getMaxDownloads() {
        return maxDownloads;
    }

    public void setMaxDownloads(int maxDownloads) {
        this.maxDownloads = maxDownloads;
    }


}
