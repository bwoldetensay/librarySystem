package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.Books;
import java.util.List;

public interface BooksService {

  List<Books> getAllBooks();
  Books getBooksByIsbn(String isbn);

  Books addBook(Books book);



}
