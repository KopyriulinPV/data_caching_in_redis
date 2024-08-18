package com.example.data_caching_in_redis.DTO;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookListResponse {

    private List<BookResponse> bookList = new ArrayList<>();

}
