package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.User;
import com.capgemini.librarySystem.repository.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements  UserService{

  @Autowired
  UserRepository userRepository;
  public User getUserByType(String userType) {
    User user =
        userRepository.findUserByUserType(userType).orElseThrow(() -> new NoSuchElementException(
            "UserType not found: " + userType));
    return user;
  }

}
