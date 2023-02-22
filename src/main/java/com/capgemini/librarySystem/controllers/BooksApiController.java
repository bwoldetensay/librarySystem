package com.capgemini.librarySystem.controllers;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.service.BooksService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BooksApiController {

  private BooksService booksService;

  @GetMapping("/books")
  public List<Books> findAllBooks(){
    return booksService.getAllBooks();
  }
  @GetMapping("/books/{isbn}")
  public Books findBookByISBN(@PathVariable final String isbn){
    return booksService.getBooksByIsbn(isbn);
  }

}
