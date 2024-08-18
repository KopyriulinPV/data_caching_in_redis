package com.example.data_caching_in_redis.controller;
import com.example.data_caching_in_redis.DTO.BookListResponse;
import com.example.data_caching_in_redis.DTO.BookResponse;
import com.example.data_caching_in_redis.DTO.UpsertBookRequest;
import com.example.data_caching_in_redis.entity.Book;
import com.example.data_caching_in_redis.mapper.BookMapper;
import com.example.data_caching_in_redis.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;


    @GetMapping("/{title}/{author}")
    public ResponseEntity<BookResponse> getBookByTitleAndAuthor(@PathVariable String title, @PathVariable String author) {
        return ResponseEntity.ok(bookMapper.bookToBookResponse(bookService.findByTitleAndAuthor(title, author).get()));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<BookListResponse> getBooksByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(bookMapper.booksToBookListResponse(bookService.findByCategory(categoryName)));
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody UpsertBookRequest request) {
        Book createBook = bookService.create(bookMapper.requestToBook(request));
             return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookMapper.bookToBookResponse(createBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody UpsertBookRequest request) {
        Book updatedBook = bookService.update(bookMapper.requestToBook(id, request));
        return ResponseEntity.ok(bookMapper.bookToBookResponse(bookService.update(updatedBook)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
