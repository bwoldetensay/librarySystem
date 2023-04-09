package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.Books;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

public interface BooksService {

  List<Books> getAllBooks();
  Books getBookById (String id);
//  List<Books> searchBooks(String keyword);
  Books getBooksByIsbn(String isbn);
  Books searchBooksByTitle(String title);
  Books searchBooksByAuthor(String author);
  Books addBook(Books book);
  Books saveReturnedBook(Books book);
  List<Books> searchBooksByAvailability(Boolean availability);
  Books deleteBookById (String id);

  List<Books> availableSoon (Long days);

  Books checkInAndCheckOut (Books book);
  List<Books> searchBooksOverDue();
  List<Books> frequentLateReturns();


}
