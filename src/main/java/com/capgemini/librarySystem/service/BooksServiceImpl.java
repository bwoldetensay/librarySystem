package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.models.User;
import com.capgemini.librarySystem.repository.BooksRepository;
import com.capgemini.librarySystem.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService {

  @Autowired
  BooksRepository booksRepository;
  @Autowired
  UserRepository userRepository;

  @Override
  public List<Books> getAllBooks() {
    return booksRepository.findAll();
  }

  @Override
  public Books getBooksByIsbn(String isbn) {
    return booksRepository.findById(isbn).orElseGet(Books::new);
  }

  @Override
  public Books addBook(Books book) {
    User userType = new User();
    if(userRepository.existsByUserType(userType.getUserType())) {
      booksRepository.save(book);
    }
//    return booksRepository.save(book);
    return book;
  }
}
