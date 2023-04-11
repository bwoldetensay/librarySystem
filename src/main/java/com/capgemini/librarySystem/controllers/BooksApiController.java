package com.capgemini.librarySystem.controllers;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.service.BooksService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BooksApiController {

  private static final Logger LOGGER = Logger.getLogger(BooksApiController.class.getName());

  static {
    FileHandler fileHandler = null;
    try {
      fileHandler = new FileHandler(BooksApiController.class.getSimpleName() + ".log");
      fileHandler.setFormatter(new SimpleFormatter());
    } catch (IOException e) {
      e.printStackTrace();
    }

    LOGGER.addHandler(fileHandler);

  }
  @Autowired
  private BooksService booksService;

  @GetMapping("/books")
  public List<Books> findAllBooks() {
    LOGGER.log(Level.INFO, "find all books" + booksService.getAllBooks());
    return booksService.getAllBooks();
  }
  @GetMapping("/availability/{availability}")
  public List<Books> findAllAvailableBooks(@PathVariable final Boolean availability) {
    LOGGER.log(Level.INFO,
        "findAllAvailableBooks" + booksService.searchBooksByAvailability(availability));
    return booksService.searchBooksByAvailability(availability);
  }
  @GetMapping("books/{isbn}")
  public Books findBookByISBN(@PathVariable final String isbn) {
    LOGGER.log(Level.INFO, "findBookByISBN", booksService.getBooksByIsbn(isbn));
    return booksService.getBooksByIsbn(isbn);
  }
  @GetMapping("/title/{title}")
  public Books getBookByTitle(@PathVariable("title") String title) {
    LOGGER.log(Level.INFO, "getBookByTitle", booksService.searchBooksByTitle(title));
    return booksService.searchBooksByTitle(title);
  }
  @GetMapping("/author/{author}")
  public Books getBookByAuthor(@PathVariable("author") String author) {
    LOGGER.log(Level.INFO, "getBookByAuthor", booksService.searchBooksByAuthor(author));
    return booksService.searchBooksByAuthor(author);
  }
  @PostMapping("/book")
  public ResponseEntity<Books> saveBook(@RequestBody Books book) {
    book = booksService.addBook(book);
    if (book == null) {
      LOGGER.log(Level.INFO, "Not acceptable", new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
      return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    } else {
      LOGGER.log(Level.INFO, "Created", new ResponseEntity<>(book, HttpStatus.CREATED));
      return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
  }
  @DeleteMapping("/id/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") String id) {
    try {
      booksService.deleteBookById(id);
      LOGGER.log(Level.INFO, "Deleted", new ResponseEntity<>(HttpStatus.NO_CONTENT));
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.log(Level.INFO, "Internal Server Error",
          new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @PutMapping("/checkIn/{id}")
  public Books checkInBook(@RequestBody Books book, @PathVariable String id) {
    book.getId();
    book.setAvailability(true);
    book.setCheckedOutUntil(LocalDate.now().toString());
    LOGGER.log(Level.INFO, "Book checked in", booksService.addBook(book));
    return booksService.addBook(book);
  }
  @PutMapping("/checkOut/{id}")
  public Books checkOutBook(@RequestBody Books book, @PathVariable String id) {
    book.getId();
    book.setAvailability(false);
    book.setCheckedOutUntil(LocalDate.now().plusDays(7).toString());
    LOGGER.log(Level.INFO, "Book checked out", booksService.addBook(book));
    return booksService.addBook(book);
  }
  @GetMapping("/soonAvailable/{days}")
  public List<Books> findBooksAvailableSoon(@PathVariable("days") Long days) {
    LOGGER.log(Level.INFO, "Book checked out",
        booksService.availableSoon(days));
    return booksService.availableSoon(days);
  }
  @PutMapping("/checkin-checkout/{id}")
  public Books adminCheckinCheckout(@RequestBody Books book,
      @PathVariable String id) {
   return booksService.checkInAndCheckOut(book);
  }
  @GetMapping("/overdue-books")
  public List<Books> getAllOverDueBooks(){
    return booksService.searchBooksOverDue();
  }
  @GetMapping("/late-returned-books")
  public List<Books> getLateReturnedBooks(){
    return booksService.frequentLateReturns();
  }

}
