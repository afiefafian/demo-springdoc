package com.afiefafian.demoopenapi;

import com.afiefafian.demoopenapi.exception.BookIdMismatchException;
import com.afiefafian.demoopenapi.exception.BookNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Operation(summary = "This isi for fetch all books in database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetch all books in database",
                    content = { @Content(mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Available",
                    content = @Content
            )
    })
    @GetMapping("/book")
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/book/{id}")
    public Book findBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping("book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("book/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }

        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        return bookRepository.save(book);
    }

    @DeleteMapping("book/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);

        bookRepository.deleteById(id);
    }

}
