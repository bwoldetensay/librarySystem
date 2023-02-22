package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.repository.BooksRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService{

  BooksRepository booksRepository;

  @Override
  public List<Books> getAllBooks(){
    return booksRepository.findAll();
  }

  @Override
  public Books getBooksByIsbn(String isbn) {
    return booksRepository.findById(isbn).orElseGet(Books::new);
  }

}
