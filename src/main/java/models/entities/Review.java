package models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reviews")
@Data
public class Review {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String text;
    @Column
    private  double rating;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Course course;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User student;
}
