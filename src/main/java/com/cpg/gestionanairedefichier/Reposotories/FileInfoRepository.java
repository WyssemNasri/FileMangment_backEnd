package com.cpg.gestionanairedefichier.Reposotories;

import com.cpg.gestionanairedefichier.entites.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {
    Optional<FileInfo> findById(Integer id);
    List<FileInfo> findByFolderId(Integer folderId);
}