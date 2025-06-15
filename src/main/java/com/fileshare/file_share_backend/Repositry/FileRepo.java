package com.fileshare.file_share_backend.Repositry;

import com.fileshare.file_share_backend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<File, String> {

    @Query("SELECT f FROM File f WHERE f.uniqueKey=:key AND f.code=:code")
    File findByUniqueKeyAndCode(String key, String code);
}
