package com.example.data_caching_in_redis.entity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Book")
public class Book  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @ManyToOne
    private Category category;
}
