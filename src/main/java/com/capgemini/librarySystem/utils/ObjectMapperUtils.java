package com.capgemini.librarySystem.utils;

import com.capgemini.librarySystem.models.Books;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {

  private static ObjectMapper objectMapper;

  private ObjectMapperUtils() {

  }

  public static ObjectMapper getObjectMapper() {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
    }
    return objectMapper;
  }


  public static Books getObjectMapper(Books searchBooksByTitle) {
    return searchBooksByTitle;
  }
}
