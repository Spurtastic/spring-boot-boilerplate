package design.boilerplate.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import design.boilerplate.springboot.SpringBootBoilerplateApplication;
import design.boilerplate.springboot.controller.ApiController;
import design.boilerplate.springboot.security.dto.BookDTO;
import design.boilerplate.springboot.security.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.context.annotation.FilterType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath*:/spring/applicationContext*.xml")
@WebMvcTest(controllers = ApiController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SpringBootBoilerplateApplication.class))
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private ApiController apiController;

    private BookDTO bookDTO;

    @BeforeEach
    public void setUp(){
        bookDTO = BookDTO.builder()
                .isbn("978-3-13-148410-0")
                .title("The Great Book")
                .subTitle("An Amazing Subtitle")
                .author("John Doe")
                .publishDate("2023-06-25")
                .publisher("Great Publisher")
                .pages(350)
                .description("This is a great book that provides a lot of interesting information.")
                .website("https://www.greatbookwebsite.com")
                .build();
        

    }

    @Test
    public void testPostAPI() throws Exception{
        // test Post API should only accept unique ISBNs, and return a 400 status code if the ISBN already exists
        // the post API should also return a 400 status code if the ISBN is not unique
        // the post API should also ensure that the isbn, author and title fields are not empty
        // the post API should also ensure that the pages field is not empty and is a positive integer
        when(bookService.saveBook(bookDTO)).thenReturn(bookDTO);

        mockMvc.perform(post("/Bookstore/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookDTO)));
    }
    // @Test
    // public void testGetByIsbnAPI(){
    //     // test Get API should return a 200 status code if the book is found
    //     // test Get API should return a 404 status code if the book is not found
    // }
    // @Test
    // public void testPutAPI(){
    //     // test Put API should return a 200 status code if the book is successfully updated
    //     // test Put API should return a 404 status code if the book is not found
    //     // test Put API should return a 400 status code if any of the fields are empty
    // }
    // @Test
    // // get all books
    // public void testGetAllBooksAPI(){
    //     // test Get All API should return a 200 status code if the books are found
    //     // test Get All API should return a 404 status code if the books are not found
    // }
    // // delete should ensure the when isbn is inputted via the get API, it should return a 404 status code
    // @Test
    // public void testDeleteAPI(){
    //     // test Delete API should return a 200 status code if the book is successfully deleted
    //     // test Delete API should return a 404 status code if the book is not found
    // }
    
    
    
}
