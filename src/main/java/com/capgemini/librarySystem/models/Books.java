package com.capgemini.librarySystem.models;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "books")
public class Books {
  @Id
  private String isbn;
  private String title;
  private String author;
  private boolean availability;

  public Books(String isbn, String title, String author, boolean availability) {
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.availability = availability;
  }

  public Books() {
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public boolean isAvailability() {
    return availability;
  }

  public void setAvailability(boolean availability) {
    this.availability = availability;
  }

  @Override
  public String toString() {
    return "Books{" +
        "isbn='" + isbn + '\'' +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        '}';
  }
}
