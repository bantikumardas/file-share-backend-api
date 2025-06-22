package com.fileshare.file_share_backend.controller;

import com.fileshare.file_share_backend.model.File;
import com.fileshare.file_share_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/file/api")
@CrossOrigin(
        origins = {"http://localhost:3000","https://safe-drop-ui.netlify.app"},
        allowCredentials = "true"
)
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestPart File file, @RequestPart MultipartFile multipartFile){
        return fileService.uploadFile(file, multipartFile);
    }


    @GetMapping("/view")
    public ResponseEntity<?> getFile(@RequestParam("key") String key,
                                     @RequestParam("code") String code){
        return fileService.downloadFile(key, code);
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {

        return fileService.viewFile(filename);

    }



}
