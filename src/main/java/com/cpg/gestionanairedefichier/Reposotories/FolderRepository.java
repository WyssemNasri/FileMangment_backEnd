package com.cpg.gestionanairedefichier.Reposotories;

import com.cpg.gestionanairedefichier.entites.FileInfo;
import com.cpg.gestionanairedefichier.entites.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository <Folder, Integer> {
    @Query("SELECT f FROM Folder f")
    List<Folder> findAllFolders();



}
