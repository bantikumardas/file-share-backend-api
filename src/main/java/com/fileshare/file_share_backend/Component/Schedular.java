package com.fileshare.file_share_backend.Component;


import com.fileshare.file_share_backend.Repositry.FileRepo;
import com.fileshare.file_share_backend.model.File;
import com.fileshare.file_share_backend.service.FileService;
import com.fileshare.file_share_backend.service.SupabaseUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Schedular {

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private FileService fileService;

    @Autowired
    private SupabaseUploader supabaseUploader;

    @Scheduled(fixedRate = 300000)
    public  void runSchedular(){
        List<File> files=fileRepo.findAll();
        for(File file:files){
            if(System.currentTimeMillis()>file.getExpiryTime()){
                boolean isDeleted=supabaseUploader.deleteFile(file.getFileName());
                if(!isDeleted){
                    System.out.print("Error while deleting.... file id"+file.getId());
                }else{
                    fileRepo.deleteById(file.getId());
                }

            }
        }
    }


}
