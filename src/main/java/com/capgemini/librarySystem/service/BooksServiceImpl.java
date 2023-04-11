package com.capgemini.librarySystem.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import com.capgemini.librarySystem.controllers.BooksApiController;
import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.repository.BooksRepository;
import com.capgemini.librarySystem.repository.UserRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService {

  private static final java.util.logging.Logger LOGGER = Logger.getLogger(
      BooksApiController.class.getName());

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
  BooksRepository booksRepository;
  @Autowired
  MongoTemplate mongoTemplate;
  @Autowired
  UserRepository userRepository;

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
  public List<Books> availableSoon(Long days) {
    LOGGER.log(Level.INFO,
        "List of soon available books" + booksRepository.findBooksByBorrowingPeriodInDays(days));
    return booksRepository.findBooksByBorrowingPeriodInDays(days);
  }

  @Override
  public Books checkInAndCheckOut(Books book) {
//    User user = new User();
//    if (userRepository.existsByEmail(user.getEmail()) != null) {
//      user.setUserType("Admin");
      if (book.isAvailability() == true) {
        book.getId();
        book.setAvailability(false);
        book.setCheckedOutUntil(LocalDate.now().plusDays(7).toString());
        book.setBorrowingPeriodInDays(7L);
        LOGGER.log(Level.INFO, "Book checked out", booksRepository.save(book));
        return booksRepository.save(book);
      } else {
        book.getId();
        book.setAvailability(true);
        book.setCheckedOutUntil(LocalDate.now().toString());
        book.setBorrowingPeriodInDays(0L);
        LOGGER.log(Level.INFO, "Book checked in", booksRepository.save(book));
        return booksRepository.save(book);
      }

    }
//    return null;
//  }
  @Override
  public List<Books> searchBooksOverDue() {
    Query query = new Query();
    query.addCriteria(Criteria.where("borrowingPeriodInDays")
        .gt(7L));
    List<Books> results = mongoTemplate.find(query, Books.class);
    return results;
  }
  @Override
  public List<Books> frequentLateReturns() {
    Criteria criteria = new Criteria();
    Query query = new Query();
    criteria.andOperator(Criteria.where("borrowingPeriodInDays")
        .gt(7L).and("availability").is(true));
    query.addCriteria(criteria);
    return mongoTemplate.find(query, Books.class);
  }
}
