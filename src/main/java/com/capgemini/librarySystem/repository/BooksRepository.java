package com.capgemini.librarySystem.repository;

import com.capgemini.librarySystem.models.Books;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BooksRepository extends MongoRepository<Books, String> {

}
