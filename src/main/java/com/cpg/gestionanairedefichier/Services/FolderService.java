package com.cpg.gestionanairedefichier.Services;

import com.cpg.gestionanairedefichier.Reposotories.FileInfoRepository;
import com.cpg.gestionanairedefichier.Reposotories.FolderRepository;
import com.cpg.gestionanairedefichier.entites.FileInfo;
import com.cpg.gestionanairedefichier.entites.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    public List<Folder> getFolders() {
        return folderRepository.findAllFolders();
    }

    public List<FileInfo> listFilesInsideFolder(Integer folderId) {
        return fileInfoRepository.findByFolderId(folderId);
    }

    public ResponseEntity<Folder> createFolder(Folder folder) {
        folderRepository.save(folder);
        return new ResponseEntity<>(folder, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteFolder(Integer id) {
        Optional<Folder> folder = folderRepository.findById(id);
        if (folder.isPresent()) {
            folderRepository.deleteById(id);
            return ResponseEntity.ok("Folder deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Folder not found.");
        }
    }
    public ResponseEntity<FileInfo> getFileInfoById(Integer id) {
        Optional<FileInfo> fileInfo = fileInfoRepository.findById(id);
        return fileInfo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    public List<FileInfo> uploadFiles(Integer folderId, MultipartFile[] files) throws IOException {
        // Retrieve the folder by ID
        Optional<Folder> folderOpt = folderRepository.findById(folderId);
        if (folderOpt.isEmpty()) {
            throw new IllegalArgumentException("Folder not found");
        }
        Folder folder = folderOpt.get();

        // Process each file and save to the database
        List<FileInfo> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFilename(file.getOriginalFilename());
            fileInfo.setFiledata(file.getBytes());
            fileInfo.setCreatedAt(LocalDateTime.now());
            fileInfo.setFolder(folder);

            // Save file info to the repository
            uploadedFiles.add(fileInfoRepository.save(fileInfo));
        }

        return uploadedFiles;
    }
    public FileInfo uploadFile(Integer folderId, MultipartFile file) throws IOException {
        // Retrieve the folder by ID
        Optional<Folder> folderOpt = folderRepository.findById(folderId);
        if (folderOpt.isEmpty()) {
            throw new IllegalArgumentException("Folder not found");
        }
        Folder folder = folderOpt.get();

        // Create a new FileInfo object
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename(file.getOriginalFilename());
        fileInfo.setFiledata(file.getBytes());
        fileInfo.setCreatedAt(LocalDateTime.now());
        fileInfo.setFolder(folder);

        // Save the file information in the database
        return fileInfoRepository.save(fileInfo);
    }



}
