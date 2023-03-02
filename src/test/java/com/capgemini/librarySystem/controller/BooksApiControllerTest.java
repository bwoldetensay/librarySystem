package com.capgemini.librarySystem.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.capgemini.librarySystem.LibrarySystemApplication;
import com.capgemini.librarySystem.controllers.BooksApiController;
import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.service.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LibrarySystemApplication.class)
@WebMvcTest(controllers = {BooksApiController.class})
@WebAppConfiguration
public class BooksApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BooksService mockBooksService;

  private static final UUID ISBN = UUID.randomUUID();
  private static final String author = "author";
  private static final String title = "title";

  @Nested
  @DisplayName("Given I want to get a book by their ISBN")
  class GetBooksByISBNOrTitleOrAuthor {

    @Nested
    @DisplayName("When a book with the given ISBN exists")
    class BookISBNExists {

      private static final String TEST_URL = "/books/" + ISBN;

      @BeforeEach
      public void setUp() {

        when(mockBooksService.getBooksByIsbn(ISBN.toString())).thenReturn(Books.builder()
            .id("1234")
            .title("Eloquent JavaScript, Third Edition")
            .author("Marijn Haverbeke")
            .availability(true)
            .isbn(ISBN.toString())
            .build());
      }
      @Test
      @DisplayName("Then the book is returned")
      public void bookReturnedIfExists() throws Exception {
        // given, when
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("1234")))
            .andExpect(jsonPath("$.title", is("Eloquent JavaScript, Third Edition")))
            .andExpect(jsonPath("$.author", is("Marijn Haverbeke")))
            .andExpect(jsonPath("$.availability", is(true)))
            .andExpect(jsonPath("$.isbn", is(ISBN.toString())))
        ;
      }
    }
    @Nested
    @DisplayName("When a book with the given Title exists")
    class BookTitleExists {

      private static final String TEST_URL = "/title/" + title;

      @BeforeEach
      public void setUp() {

        when(mockBooksService.searchBooksByTitle(title)).thenReturn(Books.builder()
            .id("1234")
            .title("Eloquent JavaScript, Third Edition")
            .author("Marijn Haverbeke")
            .availability(true)
            .isbn(ISBN.toString())
            .build());
      }
      @Test
      @DisplayName("Then the book is returned")
      public void bookReturnedIfExists() throws Exception {
        // given, when
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("1234")))
            .andExpect(jsonPath("$.title", is("Eloquent JavaScript, Third Edition")))
            .andExpect(jsonPath("$.author", is("Marijn Haverbeke")))
            .andExpect(jsonPath("$.availability", is(true)))
            .andExpect(jsonPath("$.isbn", is(ISBN.toString())))
        ;
      }
    }

      @Nested
      @DisplayName("When a book with the given Author exists")
      class BookAuthorExists {

        private static final String TEST_URL = "/author/" + author;

        @BeforeEach
        public void setUp() {

          when(mockBooksService.searchBooksByAuthor(author)).thenReturn(Books.builder()
              .id("1234")
              .title("Eloquent JavaScript, Third Edition")
              .author("Marijn Haverbeke")
              .availability(true)
              .isbn(ISBN.toString())
              .build());
        }
        @Test
        @DisplayName("Then the book is returned")
        public void bookReturnedIfExists() throws Exception {
          // given, when
          mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                  .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is("1234")))
              .andExpect(jsonPath("$.title", is("Eloquent JavaScript, Third Edition")))
              .andExpect(jsonPath("$.author", is("Marijn Haverbeke")))
              .andExpect(jsonPath("$.availability", is(true)))
              .andExpect(jsonPath("$.isbn", is(ISBN.toString())));
        }
      }
  }
  @Nested
  @DisplayName("Given I want to get all books")
  class GetAllBooks {

    private static final String TEST_URL = "/books";

    @Nested
    @DisplayName("When there are existing books")
    class BooksExist {

      private static final UUID ISBN2 = UUID.randomUUID();

      @BeforeEach
      public void setUp() {

        Books book1 = Books.builder()
            .id("1234")
            .isbn(ISBN.toString())
            .title("Eloquent JavaScript, Third Edition")
            .author("Marijn Haverbeke")
            .availability(true)
            .build();

        Books book2 = Books.builder()
            .id("5678")
            .isbn(ISBN2.toString())
            .title("Practical Modern JavaScript")
            .author("Nicolás Bevacqua")
            .availability(true)
            .build();

        when(mockBooksService.getAllBooks()).thenReturn(List.of(book1, book2));
      }

      @Test
      @DisplayName("Then a list of books is returned")
      public void booksListIsReturned() throws Exception {


        // given, when
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].title", containsInAnyOrder("Eloquent JavaScript, Third Edition", "Practical Modern JavaScript")))
            .andExpect(jsonPath("$[*].author", containsInAnyOrder("Marijn Haverbeke", "Nicolás Bevacqua")))
            .andExpect(jsonPath("$[*].availability", containsInAnyOrder(true, true)))
            .andExpect(jsonPath("$[*].isbn", containsInAnyOrder(ISBN.toString(),
                ISBN2.toString())));
      }
    }

    @Nested
    @DisplayName("When there are no books")
    class NoEmployees {

      @BeforeEach
      public void setUp() {

        when(mockBooksService.getAllBooks()).thenReturn(List.of());
      }

      @Test
      @DisplayName("Then an empty list is returned")
      public void emptyListIsReturned() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));
      }
    }
  }

  @Nested
  @DisplayName("Add books")
  class AddBook{
    private static final String TEST_URL = "/book";

    private static final UUID ISBN = UUID.randomUUID();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void bookAddSuccess() throws Exception {
      Books book = new Books("1234",ISBN.toString(),"SW Engineering for Dummies","John Doe",true );
      Books bookToReturn = new Books("1234",ISBN.toString(),"SW Engineering for Dummies","John Doe",
          true );
      doReturn(bookToReturn).when(mockBooksService).addBook(any());

      mockMvc.perform(MockMvcRequestBuilders.post(TEST_URL)
              .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(book)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id", is("1234")))
          .andExpect(jsonPath("$.title", is("SW Engineering for Dummies")))
          .andExpect(jsonPath("$.author", is("John Doe")))
          .andExpect(jsonPath("$.availability", is(true)))
          .andExpect(jsonPath("$.isbn", is(ISBN.toString())));
    }
  }
}
