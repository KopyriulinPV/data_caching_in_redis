package com.example.data_caching_in_redis.repository;
import com.example.data_caching_in_redis.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleAndAuthor(String title, String author);

    List<Book> findByCategory_Name(String categoryName);
}
