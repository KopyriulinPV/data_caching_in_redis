package com.example.data_caching_in_redis.service;
import com.example.data_caching_in_redis.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findByTitleAndAuthor(String title, String author);

    List<Book> findByCategory(String categoryName);

    Book create(Book book);

    Book update(Book book);

    void delete(Long id);
}
