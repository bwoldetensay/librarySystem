package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.User;
import com.capgemini.librarySystem.repository.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements  UserService{

  @Autowired
  UserRepository userRepository;
  @Autowired
  MongoTemplate mongoTemplate;
  @Override
  public User getUserByType(String userType) {
    return userRepository.findUserByUserType(userType).orElseThrow(() -> new NoSuchElementException(
            "UserType not found: " + userType));
  }

  @Override
  public Boolean getUserByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

}
