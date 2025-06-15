package com.fileshare.file_share_backend.Component;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedular {


    @Scheduled(fixedRate = 60000)
    public  void runSchedular(){

    }


}
