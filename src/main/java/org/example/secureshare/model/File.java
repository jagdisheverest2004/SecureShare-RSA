package org.example.secureshare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] encryptedData;

    private String filename;
    private String description;
    private String category;
    private String contentType; // New field to store the MIME type
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_file_id")
    private File originalFile;

    public File(byte[] encryptedData, String filename, String description, String category, String contentType, User owner) {
        this.encryptedData = encryptedData;
        this.filename = filename;
        this.description = description;
        this.category = category;
        this.contentType = contentType;
        this.owner = owner;
        this.timestamp = LocalDateTime.now();
    }

    public File(byte[] encryptedData, String filename, String description, String category, String contentType, User owner, File originalFile) {
        this.encryptedData = encryptedData;
        this.filename = filename;
        this.description = description;
        this.category = category;
        this.contentType = contentType;
        this.owner = owner;
        this.originalFile = originalFile;
        this.timestamp = LocalDateTime.now();
    }
}