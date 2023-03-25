package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.controllers.BooksApiController;
import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.models.User;
import com.capgemini.librarySystem.repository.BooksRepository;
import com.capgemini.librarySystem.repository.UserRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import lombok.ToString;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService {
  private static final java.util.logging.Logger LOGGER = Logger.getLogger(BooksApiController.class.getName());

  static {
    FileHandler fileHandler = null;
    try{
      fileHandler = new FileHandler(BooksApiController.class.getSimpleName() + ".log");
      fileHandler.setFormatter(new SimpleFormatter());
    }catch (IOException e){
      e.printStackTrace();
    }

    LOGGER.addHandler(fileHandler);

  }
  @Autowired
  BooksRepository booksRepository;
  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public List<Books> getAllBooks() {
    LOGGER.log(Level.INFO, "find all books" + booksRepository.findAll());
    return booksRepository.findAll();
  }

  @Override
  public Books getBooksByIsbn(String isbn) {
    LOGGER.log(Level.INFO, "find books by isbn" + booksRepository.findBooksByIsbn(isbn));
    return booksRepository.findBooksByIsbn(isbn);
  }
  @Override
  public Books getBookById(String id) {
    LOGGER.log(Level.INFO, "find books by id" + booksRepository.findBooksById(id));
    return booksRepository.findBooksById(id);
  }

  @Override
  public Books searchBooksByTitle(String title) {
    LOGGER.log(Level.INFO, "find books by title" + booksRepository.findBooksByTitle(title));
    return booksRepository.findBooksByTitle(title);
  }
  @Override
  public Books searchBooksByAuthor(String author) {
    LOGGER.log(Level.INFO, "find books by author" + booksRepository.findBooksByAuthor(author));
    return booksRepository.findBooksByAuthor(author);
  }

  @Override
  public List<Books> searchBooksByAvailability(Boolean availability) {
    LOGGER.log(Level.INFO,
        "find books by availability" + booksRepository.findBooksByAvailabilityIsTrue(true));
    return booksRepository.findBooksByAvailabilityIsTrue(true);
  }
  @Override
  public Books addBook(Books book) {
    LOGGER.log(Level.INFO, "save new book" + booksRepository.save(book));
    return booksRepository.save(book);
  }
  @Override
  public Books saveReturnedBook(Books book) {
    LOGGER.log(Level.INFO, "save returned book" + booksRepository.save(book));
    return booksRepository.save(book);
  }
  @Override
  public Books deleteBookById(String id) {
    LOGGER.log(Level.INFO, "delete book" + booksRepository.deleteBooksById(id));
    return booksRepository.deleteBooksById(id);
  }

  @Override
  public List<Books> availableSoon(Long days){
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    LocalDate borrowDate = LocalDate.parse(book.getCheckedOutUntil(), formatter);
//    LocalDate returnDate = borrowDate.plusDays(book.getBorrowingPeriodInDays());
//    Books book = new Books();
//    days = book.getBorrowingPeriodInDays();
    LOGGER.log(Level.INFO,
        "List of soon available books" + booksRepository.findBooksByBorrowingPeriodInDays(days));
    return booksRepository.findBooksByBorrowingPeriodInDays(days);
//      return book;
//    return null;

  }



}
