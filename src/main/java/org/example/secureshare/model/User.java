package org.example.secureshare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Username should not be Blank")
    @Size(max = 20, min = 5, message = "Username must contain at least 5 characters")
    @Column(nullable = false, name = "username")
    private String username;

    @NotBlank(message = "Password should not be Blank")
    @Size(max = 120, message = "Password must contain at most 120 characters")
    @Column(nullable = false, name = "password")
    @JsonIgnore // <-- Added JsonIgnore
    private String password;

    @NotBlank(message = "Email should not be Blank")
    @Size(max = 50, message = "Email is not valid")
    @Column(nullable = false, name = "email")
    @Email(message = "Email is not valid")
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Lob
    @Column(name = "public_key" , columnDefinition = "LONGTEXT")
    @JsonIgnore // <-- Added JsonIgnore
    private String publicKey;

    @Lob
    @Column(name = "private_key" , columnDefinition = "LONGTEXT")
    @JsonIgnore // <-- Added JsonIgnore
    private String privateKey;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<File> files = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<SharedFile> sentFiles = new HashSet<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<SharedFile> receivedFiles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<AuditLog> auditLogs = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        // The collections are initialized by Lombok's @Data annotation so no need to explicitly initialize here.
    }
}