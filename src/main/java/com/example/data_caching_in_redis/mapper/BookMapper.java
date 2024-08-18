package com.example.data_caching_in_redis.mapper;
import com.example.data_caching_in_redis.DTO.BookListResponse;
import com.example.data_caching_in_redis.DTO.BookResponse;
import com.example.data_caching_in_redis.DTO.UpsertBookRequest;
import com.example.data_caching_in_redis.entity.Book;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(BookMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    @Mapping(target = "category", ignore = true)
    Book requestToBook(UpsertBookRequest request);

    @Mapping(source = "bookId", target = "id")
    @Mapping(target = "category", ignore = true)
    Book requestToBook(Long bookId, UpsertBookRequest request);

    @Mapping(target = "category", ignore = true)
    BookResponse bookToBookResponse(Book book);

    default BookListResponse booksToBookListResponse(List<Book> books) {
        BookListResponse response = new BookListResponse();
        response.setBookList(books.stream()
                .map(this::bookToBookResponse)
                .collect(Collectors.toList())
        );
        return response;
    }

}
