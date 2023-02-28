package com.capgemini.librarySystem.controllers;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.service.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import com.capgemini.librarySystem.utils.ObjectMapperUtils;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @GetMapping("/books")
  public List<Books> findAllBooks(){
    return booksService.getAllBooks();
  }
  @GetMapping("books/{isbn}")
  public Books findBookByISBN(@PathVariable final String isbn){
    return booksService.getBooksByIsbn(isbn);
  }
  @GetMapping( "/title/{title}")
  public Books getBookByTitle(@PathVariable("title") String title){
    return booksService.searchBooksByTitle(title);
  }

  @GetMapping( "/author/{author}")
  public Books getBookByAuthor(@PathVariable("author") String author){
    return booksService.searchBooksByAuthor(author);
  }
  @PostMapping("/book")
  public ResponseEntity<Books> saveBook(@RequestBody Books book){
    book  = booksService.addBook(book);
    if(book == null)
      return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    return  new ResponseEntity<>(book, HttpStatus.CREATED);
  }

}
