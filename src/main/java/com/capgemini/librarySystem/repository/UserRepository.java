package com.capgemini.librarySystem.repository;

import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.models.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findUserByUserType(String userType);
  Boolean existsByUserType(String userType);
  Boolean existsByEmail(String email);

}
