package com.capgemini.librarySystem.service;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.capgemini.librarySystem.controllers.BooksApiController;
import com.capgemini.librarySystem.models.Books;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//@AutoConfigureMockMvc
@SpringBootTest
//@RunWith(SpringRunner.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BooksServiceTest {
  @Test
  void contextLoads(){
  }
//
//  private static final String TEST_URL = "/availability/" + true;
//  @Autowired
//  private BooksService myMockService;
//  @Autowired
//  private MockMvc mockMvc;
//
////  public BooksServiceTest(MockMvc mockMvc) {
////    this.mockMvc = mockMvc;
////  }
//  public BooksServiceTest(){
//
//  }
//
//  @BeforeEach
//  public void setUp() {
//
//    when(myMockService.searchBooksByAvailability(true)).thenReturn((List<Books>) Books.builder()
//        .availability(true)
//        .build());
//  }
//
//  @Test
//  public void testMyServiceMethod() throws Exception {
//    MockMvc mockMvc =
//        MockMvcBuilders.standaloneSetup( new BooksApiController().findAllAvailableBooks(true)).build();
//
//    mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
//            .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$.availability", is(true)))
//    ;
//  }
//
//  @Test
//  public void testAvailableBooks() throws Exception {
//    MockMvc mockMvc =
//        MockMvcBuilders.standaloneSetup( new BooksApiController().findAllAvailableBooks(true)).build();
//
//    mockMvc.perform(MockMvcRequestBuilders.get("/availability/{availability}"))
//        .andExpect(status().isOk())
//        .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON));
//  }
}
