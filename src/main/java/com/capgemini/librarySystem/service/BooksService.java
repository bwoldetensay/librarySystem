package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.Books;
import java.util.List;

public interface BooksService {

  List<Books> getAllBooks();
//  List<Books> searchBooks(String keyword);
  Books getBooksByIsbn(String isbn);
  Books searchBooksByTitle(String title);
  Books searchBooksByAuthor(String author);
  Books addBook(Books book);
  Books searchBooksByAvailability(Boolean availability);
  Books deleteBookById (String id);

}
