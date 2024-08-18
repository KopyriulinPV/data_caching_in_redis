package com.example.data_caching_in_redis.DTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpsertBookRequest {

    private String title;

    private String author;

    private String category;

}
