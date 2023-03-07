package com.capgemini.librarySystem.controllers;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.service.BooksService;
import com.capgemini.librarySystem.service.BooksServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BooksApiController{
  private static final Logger logger = LoggerFactory.getLogger(BooksServiceImpl.class);

  @Autowired
  private BooksService booksService;

  @GetMapping("/books")
  public List<Books> findAllBooks(){
    logger.debug("findAllBooks");
    return booksService.getAllBooks();
  }
  @GetMapping("/availability/{availability}")
  public List<Books> findAllAvailableBooks(@PathVariable final Boolean availability){
    logger.debug("findAllAvailableBooks");
    return booksService.searchBooksByAvailability(availability);
  }
  @GetMapping("books/{isbn}")
  public Books findBookByISBN(@PathVariable final String isbn){
    logger.debug("findBookByISBN()");
    return booksService.getBooksByIsbn(isbn);
  }
  @GetMapping( "/title/{title}")
  public Books getBookByTitle(@PathVariable("title") String title){
    logger.debug("getBookByTitle()");
    return booksService.searchBooksByTitle(title);
  }

  @GetMapping( "/author/{author}")
  public Books getBookByAuthor(@PathVariable("author") String author){
    logger.debug("getBookByAuthor()");
    return booksService.searchBooksByAuthor(author);
  }
  @PostMapping("/book")
  public ResponseEntity<Books> saveBook(@RequestBody Books book){
    book  = booksService.addBook(book);
    if(book == null)
      return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    return  new ResponseEntity<>(book, HttpStatus.CREATED);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") String id) {
    try {
      booksService.deleteBookById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @PutMapping("/id/{id}")
  public Books updateBook(@RequestBody Books book, @PathVariable String id){
    book.getId();
    book.setAvailability(true);
     return booksService.addBook(book);
  }

}
