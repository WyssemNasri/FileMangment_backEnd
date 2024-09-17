package com.cpg.gestionanairedefichier.entites;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileInfo> files;

    public Folder(int i, String folder1, String description1) {

    }

    public Folder() {

    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(Set<FileInfo> files) {
        this.files = files;
    }
}
