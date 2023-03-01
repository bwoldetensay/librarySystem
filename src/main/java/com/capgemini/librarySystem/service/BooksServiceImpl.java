package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.models.User;
import com.capgemini.librarySystem.repository.BooksRepository;
import com.capgemini.librarySystem.repository.UserRepository;
import java.util.List;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService {
  private static final Logger logger = LoggerFactory.getLogger(BooksServiceImpl.class);

  @Autowired
  BooksRepository booksRepository;
  @Autowired
  UserRepository userRepository;

  @Override
  public List<Books> getAllBooks() {
    logger.debug("getAllBooks()");
    return booksRepository.findAll();
  }

//  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
//  @Override
//  public List<Books> searchBooks(String keyword) {
//    if (keyword != null) {
//      return booksRepository.searchAllBy(keyword);
//    }
//    return booksRepository.findAll();
//  }

  @Override
  public Books getBooksByIsbn(String isbn) {
    logger.debug("getBooksByIsbn");
    return booksRepository.findById(isbn).orElseGet(Books::new);
  }

  @Override
  public Books searchBooksByTitle(String title) {
    logger.debug("searchBooksByTitle");
    return booksRepository.findBooksByTitle(title);
  }
  @Override
  public Books searchBooksByAuthor(String author) {
    logger.debug("searchBooksByAuthor");
    return booksRepository.findBooksByAuthor(author);
  }



  @Override
  public Books addBook(Books book) {
    User userType = new User();
    if(userRepository.existsByUserType(userType.getUserType())) {
      booksRepository.save(book);
    }
//    return booksRepository.save(book);
    logger.debug("addBook");
    return book;
  }
}
