package com.example.data_caching_in_redis.DTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookResponse {

    private Long id;

    private String title;

    private String author;

    private String category;

}
