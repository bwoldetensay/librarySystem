package com.capgemini.librarySystem.repository;

import com.capgemini.librarySystem.models.Books;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends MongoRepository<Books, String> {
//   @Query(value = {'availability' : exists = true})
//   List<Books> findBooksByAvailabilityIsTrue(Boolean availability);
   Books findBooksByAuthor(String author);
   Books findBooksByTitle(String title);
   Books findBooksByIsbn(String isbn);

   List<Books> findBooksByAvailabilityIsTrue(Boolean availability);

}
