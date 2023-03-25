package com.capgemini.librarySystem.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.capgemini.librarySystem.LibrarySystemApplication;
import com.capgemini.librarySystem.controllers.BooksApiController;
import com.capgemini.librarySystem.models.Books;
import com.capgemini.librarySystem.repository.BooksRepository;
import com.capgemini.librarySystem.service.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
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
import org.webjars.NotFoundException;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LibrarySystemApplication.class)
@WebMvcTest(controllers = {BooksApiController.class})
@WebAppConfiguration
public class BooksApiControllerTest {

  @Autowired
  BooksApiController booksApiController;

  @Test
  @DisplayName("Testing if Application Loads Correctly")
  public void contextLoads() {
    Assertions.assertThat(booksApiController).isNotNull();
  }

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private BooksService mockBooksService;

  @MockBean
  private BooksRepository mockBooksrepository;

  private static final UUID ISBN = UUID.randomUUID();
  private static final UUID id = UUID.randomUUID();
  private static final String author = "author";
  private static final String title = "title";
  private static final Long days = (long) LocalDate.now().getDayOfMonth();

  @Nested
  @DisplayName("Given I want to get all books")
  class GetAllBooks {

    private static final String TEST_URL = "/books";

    @Nested
    @DisplayName("Test List Of All books")
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
            .availability(false)
            .build();

        when(mockBooksService.getAllBooks()).thenReturn(List.of(book1, book2));
      }

      @Test
      @DisplayName("List of Books Returned")
      public void booksListIsReturned() throws Exception {

        // given, when
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].title",
                containsInAnyOrder("Eloquent JavaScript, Third Edition",
                    "Practical Modern JavaScript")))
            .andExpect(
                jsonPath("$[*].author", containsInAnyOrder("Marijn Haverbeke", "Nicolás Bevacqua")))
            .andExpect(jsonPath("$[*].availability", containsInAnyOrder(true, false)))
            .andExpect(jsonPath("$[*].isbn", containsInAnyOrder(ISBN.toString(),
                ISBN2.toString())));
      }
    }

    @Nested
    @DisplayName("When there are no books")
    class NoBooks {

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
  @DisplayName("Given I want to get a book by their ISBN")
  class GetBooksByISBNOrTitleOrAuthor {

    @Nested
    @DisplayName("Test if Book exists with ISBN")
    class BookWithIsbnExists {

      private static final String TEST_URL = "/books/" + ISBN;

      @BeforeEach
      public void setUp() {

        when(mockBooksService.getBooksByIsbn(ISBN.toString())).thenReturn(Books.builder()
            .id(id.toString())
            .title("Eloquent JavaScript, Third Edition")
            .author("Marijn Haverbeke")
            .availability(true)
            .isbn(ISBN.toString())
            .build());
      }

      @Test
      @DisplayName("Book is returned with given ISBN")
      public void bookWithIsbnReturned() throws Exception {
        // given, when
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(id.toString())))
            .andExpect(jsonPath("$.title", is("Eloquent JavaScript, Third Edition")))
            .andExpect(jsonPath("$.author", is("Marijn Haverbeke")))
            .andExpect(jsonPath("$.availability", is(true)))
            .andExpect(jsonPath("$.isbn", is(ISBN.toString())))
        ;
      }
    }

    @Nested
    @DisplayName("No book with ISBN Test")
    class NoBookWithISBSNExists {

      private static final String TEST_URL = "/books/" + ISBN;

      @Test
      @DisplayName("Then not found exception returned")
      public void emptyListIsReturned() throws Exception {
        Books book = new Books();
        book.setIsbn("");
        when(mockBooksService.getBooksByIsbn(book.getIsbn())).thenThrow(
            new NotFoundException("Not Found"));

        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
//            .andExpect(content().string([" "]));
      }
    }

    @Nested
    @DisplayName("Test Book With Given Title Exists")
    class BookWithTitleExists {

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
      @DisplayName("Book is returned with Given Title")
      public void bookWithTitleReturned() throws Exception {
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

      @Nested
      @DisplayName("No book with Title Test")
      class NoBookWithTitleExists {

        private static final String TEST_URL = "/title/" + title;

        @Test
        @DisplayName("Then not found exception returned")
        public void emptyListIsReturned() throws Exception {
          Books book = new Books();
          book.setTitle("");
          when(mockBooksService.searchBooksByTitle(book.getTitle())).thenThrow(
              new NotFoundException(
                  "Not"
                      + " Found"));

          mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                  .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
//            .andExpect(content().string([" "]));
        }
      }
    }

    @Nested
    @DisplayName("Test Book With Given Author Exists")
    class BookWithAuthorExists {

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
      @DisplayName("Book With Author Returned")
      public void bookWithAuthorReturned() throws Exception {
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

      @Nested
      @DisplayName("No book with Author Test")
      class NoBookWithAuthorExists {

        private static final String TEST_URL = "/author/" + author;

        @Test
        @DisplayName("Then not found exception returned")
        public void emptyListIsReturned() throws Exception {
          Books book = new Books();
          book.setAuthor("");
          when(mockBooksService.searchBooksByAuthor("")).thenThrow(new NotFoundException(
              "Not"
                  + " Found"));

          mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                  .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
//            .andExpect(content().string([" "]));
        }
      }
    }
  }

  @Nested
  @DisplayName("Test Books which are Available")
  class AvailableBooksTest {

      private static final String TEST_URL = "/availability/" + true;

      @BeforeEach
      public void setUp() {
        Books book1 = Books.builder()
            .id(id.toString())
            .isbn(ISBN.toString())
            .title("Eloquent JavaScript, Third Edition")
            .author("Marijn Haverbeke")
            .availability(true)
            .build();
        Books book2 = Books.builder()
            .id("5678")
            .isbn(ISBN.toString())
            .title("Practical Modern JavaScript")
            .author("Nicolás Bevacqua")
            .availability(true)
            .build();

        when(mockBooksService.searchBooksByAvailability(true)).thenReturn(List.of(book1, book2));
      }

      @Test
      @DisplayName("Available Books Returned")
      public void returnAvailableBooks() throws Exception {
        // given, when
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].title",
                containsInAnyOrder("Eloquent JavaScript, Third Edition",
                    "Practical Modern JavaScript")))
            .andExpect(
                jsonPath("$[*].author", containsInAnyOrder("Marijn Haverbeke", "Nicolás Bevacqua")))
            .andExpect(jsonPath("$[*].availability", containsInAnyOrder(true, true)))
            .andExpect(jsonPath("$[*].isbn", containsInAnyOrder(ISBN.toString(),
                ISBN.toString())));
      }
    }
  @Nested
  @DisplayName("Test Book With Given Author Exists")
  class BookComingAvailableSoonTest {

    private static final String TEST_URL = "/soonAvailable/" + days;
    @Test
    @DisplayName("Checking-Out Book success")
    public void testBooksAvailableSoon() throws Exception {
      Books books = new Books();
        when(mockBooksService.availableSoon(books.getBorrowingPeriodInDays(days))).thenReturn(List.of(books));
        System.out.println(List.of(books));
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
              .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
          .andDo(print());
    }
  }
  @Nested
  @DisplayName("Add books")
  class AddBook {

    private static final String TEST_URL = "/book";

    private static final UUID ISBN = UUID.randomUUID();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void bookAddSuccess() throws Exception {
      Books book = new Books("1234", ISBN.toString(), "SW Engineering for Dummies", "John Doe",
          true,
          LocalDate.now().toString(), 7L);
      Books bookToReturn = new Books("1234", ISBN.toString(), "SW Engineering for Dummies",
          "John Doe",
          true, LocalDate.now().toString(), 7L);
      doReturn(bookToReturn).when(mockBooksService).addBook(any());

      mockMvc.perform(post(TEST_URL)
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

  @Nested
  @DisplayName("Delete books")
  class DeleteBooks {

    private static final String TEST_URL = "/id/" + id;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldDeleteBook() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders
              .delete(TEST_URL)
              .param("id", id.toString())
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());

    }

  }

  @Nested
  @DisplayName("Update Books check-In")
  class UpdateBooks {

    private static final String TEST_URL = "/checkIn/" + id.toString();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Check-in Book")
    void shouldUpdateBook() throws Exception {
      Books book = new Books("1234", "5678", "SW Engineering for Dummies", "John Doe", false,
          LocalDate.now().toString(), 7L);
      Books bookToReturn = new Books("1234", "5678", "SW Engineering for Dummies", "John Doe",
          true, LocalDate.now().toString(), 7L);
      when(mockBooksService.getBookById(id.toString())).thenReturn(book);
      when(mockBooksService.addBook(any(Books.class))).thenReturn(bookToReturn);

      mockMvc.perform(MockMvcRequestBuilders.put(TEST_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(mapper.writeValueAsString(bookToReturn)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(bookToReturn.getId())))
          .andExpect(jsonPath("$.isbn", is(bookToReturn.getIsbn())))
          .andExpect(jsonPath("$.title", is(bookToReturn.getTitle())))
          .andExpect(jsonPath("$.author", is(bookToReturn.getAuthor())))
          .andExpect(jsonPath("$.availability", is(bookToReturn.isAvailability())))
          .andDo(print());
    }
  }

  @Nested
  @DisplayName("Update Books Check-Out")
  public class BooKsCheckOutControllerTest {
    private static final String TEST_URL = "/checkOut/" + id.toString();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Checking-Out Book success")
    public void testCheckoutBook_Success() throws Exception {
      // Arrange
      Books books = new Books("1234", "5678", "SW Engineering for Dummies", "John Doe", true,
          LocalDate.now().toString(), 7L);
      books.setAvailability(false);
      books.setCheckedOutUntil(LocalDate.now().plusDays(7).toString());
      when(mockBooksService.getBookById(id.toString())).thenReturn(books);

      mockMvc.perform(MockMvcRequestBuilders.put(TEST_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(mapper.writeValueAsString(books)))
          .andExpect(status().isOk())
          .andDo(print());
    }
  }
}
