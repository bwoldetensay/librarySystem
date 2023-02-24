package com.capgemini.librarySystem.controllers;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.repository.BooksRepository;
import com.capgemini.librarySystem.service.BooksService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BooksApiController{
//public class BooksApiController implements ErrorController {
//  @RequestMapping("/error")
//  @ResponseBody
//  String error(HttpServletRequest request) {
//    return "<h1>Error occurred</h1>";
//  }
//
//  public String getErrorPath() {
//    return "/error";
//  }

  @Autowired
  private BooksService booksService;
  @Autowired
  private BooksRepository booksRepository;

  @GetMapping("/books")
  public List<Books> findAllBooks(){
    return booksService.getAllBooks();
  }
  @GetMapping("/books/{isbn}")
  public Books findBookByISBN(@PathVariable final String isbn){
    return booksService.getBooksByIsbn(isbn);
  }
  @PostMapping("/book")
  public ResponseEntity<Books> saveBook(@RequestBody Books book){
    book  = booksService.addBook(book);
    if(book == null)
      return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    return  new ResponseEntity<>(book, HttpStatus.CREATED);
  }

}
