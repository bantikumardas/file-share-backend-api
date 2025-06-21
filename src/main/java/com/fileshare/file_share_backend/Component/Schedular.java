package com.fileshare.file_share_backend.Component;


import com.fileshare.file_share_backend.Repositry.FileRepo;
import com.fileshare.file_share_backend.model.File;
import com.fileshare.file_share_backend.service.FileService;
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

    @Scheduled(fixedRate = 300000)
    public  void runSchedular(){
        List<File> files=fileRepo.findAll();
        for(File file:files){
            if(System.currentTimeMillis()>file.getExpiryTime()){
                boolean isDeleted=fileService.isDeleted(file);
                if(!isDeleted){
                    System.out.print("Error while deleting.... file id"+file.getId());
                }else{
                    fileRepo.deleteById(file.getId());
                }

            }
        }
    }


}
