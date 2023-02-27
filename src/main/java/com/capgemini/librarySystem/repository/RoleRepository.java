package com.capgemini.librarySystem.repository;

import com.capgemini.librarySystem.models.ERole;
import com.capgemini.librarySystem.models.Role;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String > {

  Optional<Role> findById(ERole name);
}
