package models.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "users")
@Data
public class User {

    public enum Role {
        ADMIN,
        STUDENT,
        TEACHER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
