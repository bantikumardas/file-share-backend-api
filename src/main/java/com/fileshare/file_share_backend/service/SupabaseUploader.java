package com.fileshare.file_share_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class SupabaseUploader {
    @Value("${SUPABASE.URL}")
    private String SUPABASE_URL;
    @Value("${API.KEY}")
    private  String API_KEY;
    @Value("${BUCKET.NAME}")
    private String BUCKET_NAME;


    public String uploadFileToSupabase(MultipartFile file) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        String filename = file.getOriginalFilename();
        String url = SUPABASE_URL + "/storage/v1/object/" + BUCKET_NAME + "/" + filename;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_KEY);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("x-upsert", "true");

        HttpEntity<byte[]> request = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            // Returns public URL
            return SUPABASE_URL + "/storage/v1/object/public/" + BUCKET_NAME + "/" + filename;
        } else {
            throw new RuntimeException("Upload failed: " + response.getBody());
        }
    }

    public boolean deleteFile(String filename) {

        try{
            RestTemplate restTemplate = new RestTemplate();
            String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
            String url = SUPABASE_URL + "/storage/v1/object/" + BUCKET_NAME + "/" + encodedFilename;
            System.out.println("Error while deleting with file name is "+filename);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(API_KEY);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    request,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Delete failed with status: " + response.getStatusCode() + ", body: " + response.getBody());
                return false;

            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

}
