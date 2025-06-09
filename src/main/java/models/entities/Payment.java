package models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String stripeLink;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Course course;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;
}
