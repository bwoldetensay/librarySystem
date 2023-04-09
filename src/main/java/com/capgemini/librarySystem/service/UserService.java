package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.User;
import org.springframework.stereotype.Service;

public interface UserService {
  User getUserByType(String userType);
  Boolean getUserByEmail(String email);

}
