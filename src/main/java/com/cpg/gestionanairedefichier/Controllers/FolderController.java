package com.cpg.gestionanairedefichier.Controllers;

import com.cpg.gestionanairedefichier.Services.FolderService;
import com.cpg.gestionanairedefichier.entites.FileInfo;
import com.cpg.gestionanairedefichier.entites.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @GetMapping
    public ResponseEntity<List<Folder>> listFolders() {
        List<Folder> folders = folderService.getFolders();
        return ResponseEntity.ok(folders);
    }

    @PostMapping("/createFolder")
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder) {
        return folderService.createFolder(folder);
    }

    @DeleteMapping("/deleteFolder/{id}")
    public ResponseEntity<String> deleteFolder(@PathVariable Integer id) {
        return folderService.deleteFolder(id);
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<List<FileInfo>> listFilesInsideFolder(@PathVariable Integer id) {
        List<FileInfo> files = folderService.listFilesInsideFolder(id);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<FileInfo> getFileInfoById(@PathVariable Integer id) {
        return folderService.getFileInfoById(id);
    }

    @PostMapping("/{folderId}/files")
    public ResponseEntity<FileInfo> uploadFile(@PathVariable Integer folderId,
                                               @RequestParam("file") MultipartFile file) {
        try {
            FileInfo uploadedFile = folderService.uploadFile(folderId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
