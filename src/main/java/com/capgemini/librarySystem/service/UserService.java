package com.capgemini.librarySystem.service;

import com.capgemini.librarySystem.models.User;

public interface UserService {
  User getUserByType(String userType);

}
