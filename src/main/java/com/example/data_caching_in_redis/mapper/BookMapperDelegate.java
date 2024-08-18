package com.example.data_caching_in_redis.mapper;
import com.example.data_caching_in_redis.DTO.BookResponse;
import com.example.data_caching_in_redis.DTO.UpsertBookRequest;
import com.example.data_caching_in_redis.entity.Book;
import com.example.data_caching_in_redis.entity.Category;
import com.example.data_caching_in_redis.repository.BookRepository;
import com.example.data_caching_in_redis.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BookMapperDelegate implements BookMapper {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book requestToBook(UpsertBookRequest request) {

        if (categoryRepository.findByName(request.getCategory()).isEmpty()) {
            Category newCategory = new Category();
            newCategory.setName(request.getCategory());
            categoryRepository.save(newCategory);
        };

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCategory(categoryRepository.findByName(request.getCategory()).get());
        return book;
    }

    @Override
    public Book requestToBook(Long bookId, UpsertBookRequest request) {
        Book book = requestToBook(request);
        book.setId(bookId);
        return book;
    }

    @Override
    public BookResponse bookToBookResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setCategory(book.getCategory().getName());
        return bookResponse;
    }

}
