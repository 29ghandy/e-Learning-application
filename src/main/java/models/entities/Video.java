package models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "videos")
@Data

public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String title;
    @Column
    private String videoPath;
    @Column
    private String imagePath;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Section section;
}
