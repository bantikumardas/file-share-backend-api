package com.fileshare.file_share_backend.service;

import com.fileshare.file_share_backend.Repositry.FileRepo;
import com.fileshare.file_share_backend.model.File;
import com.fileshare.file_share_backend.model.FileWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileService {


    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private EmailService emailService;


    public ResponseEntity<?> uploadFile(File file, MultipartFile multipartFile) {
        Map<String, String> response=new HashMap<>();
        try {
            String file_name=multipartFile.getOriginalFilename();
            String file_type=multipartFile.getContentType();
            long file_size=multipartFile.getSize();
            if(file_size>(100*1024*1024)){ //not more than 100mb
                response.put("error", "File size should not more than 100MB.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            String uuid= UUID.randomUUID().toString();
            file.setId(uuid);
            file.setFileSize(file_size);
            file.setType(file_type);
            file.setFileName(file_name);
            file.setUploadTime(System.currentTimeMillis());
            int code = (int)(Math.random() * 9000) + 1000;
            file.setCode(code+"");
            File file1=fileRepo.findByUniqueKeyAndCode(file.getUniqueKey(), file.getCode());
            if(file1!=null){
                response.put("error", "Please try again with different/same key");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            String url=saveFile(multipartFile, uuid);
            file.setFileUrl(url);
            System.out.println("The URL Is "+url);
            if(file.getEmailId()!=null){
                boolean isEmailSend=emailService.sendEmail(file);
                if(!isEmailSend){
                    response.put("error", "Email sending is failed.");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
                file.setIsEmailSend(isEmailSend);
            }
            fileRepo.save(file);
            return ResponseEntity.status(HttpStatus.OK).body(fileRepo.findById(file.getId()));
        }catch (Exception e){
            System.out.println("The error is "+e);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
    }

    public String saveFile(MultipartFile multipartFile, String ID) throws IOException {
        // Generate new file name
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : "";
        String newFileName = ID  + extension;

        // Define your local directory path
        String uploadDir = System.getProperty("user.dir") + "/" + "uploads"; // Make sure this folder exists
        Path uploadPath = Paths.get(uploadDir);

        // Create directory if not exists
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Full path with new name
        Path filePath = uploadPath.resolve(newFileName);
        System.out.println("Saved file at: " + filePath.toAbsolutePath());
        // Save file
        multipartFile.transferTo(filePath.toFile());

        return filePath.toString(); // Return saved file path
    }

    public ResponseEntity<?> downloadFile(String key, String code) {
        Map<String, String> response=new HashMap<>();
        try {
           File file= fileRepo.findByUniqueKeyAndCode(key, code);
           System.out.println("The file is : "+file);
           if(file==null){
               response.put("error", "File not found");
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
           }
           if(file.getDownloaded()>=file.getMaxDownloads()){
               response.put("error", "Download limit is reached.");
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
           }
           if(file.getExpiryTime()>=System.currentTimeMillis()){
               response.put("error", "Download limit is reached.");
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
           }
           FileWrapper fileWrapper=new FileWrapper(file.getFileName(), file.getType(), file.getFileUrl(), file.getUploadTime(), file.getFileSize());
           file.setDownloaded(file.getDownloaded()+1);
           fileRepo.save(file);
           return ResponseEntity.status(HttpStatus.OK).body(fileWrapper);
        }catch (Exception e){
            e.printStackTrace();
            response.put("Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public boolean isDeleted(File file){
        String uploadDir = System.getProperty("user.dir") + "/" + "uploads";
        java.io.File dir = new java.io.File(uploadDir);
        java.io.File[] files = dir.listFiles();
        if(files!=null){
            for (java.io.File file1 : files) {
                // Check if file name starts with ID (since you used ID + extension)
                if (file1.getName().startsWith(file.getId())) {
                    boolean deleted = file1.delete();
                    System.out.println("Deleted file: " + file1.getAbsolutePath());
                    return deleted;
                }
            }
        }
        return false;
    }
}
