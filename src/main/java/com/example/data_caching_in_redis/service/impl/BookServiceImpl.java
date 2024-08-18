package com.example.data_caching_in_redis.service.impl;
import com.example.data_caching_in_redis.configuration.properties.AppCacheProperties;
import com.example.data_caching_in_redis.entity.Book;
import com.example.data_caching_in_redis.repository.BookRepository;
import com.example.data_caching_in_redis.repository.CategoryRepository;
import com.example.data_caching_in_redis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheManager = "redisCacheManager")
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Cacheable(cacheNames = AppCacheProperties.CacheNames.DATABASE_FIND_BY_TITLE_AND_AUTHOR, key = "#title + #author")
    @Override
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    @Cacheable(cacheNames = AppCacheProperties.CacheNames.DATABASE_FIND_BY_CATEGORY, key = "#categoryName")
    @Override
    public List<Book> findByCategory(String categoryName) {
        return bookRepository.findByCategory_Name(categoryName);
    }

    @Caching(evict = {
            @CacheEvict(value = "databaseFindByCategory", key = "#book.category.name", beforeInvocation = true),
            @CacheEvict(value = "databaseFindByTitleAndAuthor", key = "#book.title + #book.author", beforeInvocation = true)
    })
    @Override
    public Book create(Book book) {
        categoryRepository.findByName(book.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(book.getCategory()));
        return bookRepository.save(book);
    }

    @CacheEvict(value = {"databaseFindByCategory", "databaseFindByTitleAndAuthor"}, allEntries = true)
    @Override
    public Book update(Book book) {
        Book existedBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        existedBook.setTitle(book.getTitle());
        existedBook.setAuthor(book.getAuthor());
        existedBook.setCategory(book.getCategory());
        return bookRepository.save(existedBook);
    }

    @CacheEvict(value = {"databaseFindByCategory", "databaseFindByTitleAndAuthor"}, allEntries = true)
    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}


