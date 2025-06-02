package models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USER")
@Data
public class User {

    public enum Role {
        ADMIN,
        STUDENT,
        INSTRUCTOR
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
    private String cardNumber;
}
