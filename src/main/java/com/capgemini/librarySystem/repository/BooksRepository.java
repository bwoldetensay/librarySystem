package com.capgemini.librarySystem.repository;

import com.capgemini.librarySystem.models.Books;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends MongoRepository<Books, String> {
   Books findBooksByAuthor(String author);
   Books findBooksByTitle(String title);
   Books findBooksByIsbn(String isbn);

}
