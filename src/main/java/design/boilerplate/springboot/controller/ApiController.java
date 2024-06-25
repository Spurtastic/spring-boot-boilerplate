package design.boilerplate.springboot.controller;

import org.hibernate.sql.Delete;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import design.boilerplate.springboot.repository.BookRepository;
import design.boilerplate.springboot.security.dto.BookDTO;
import design.boilerplate.springboot.security.service.BookService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import design.boilerplate.springboot.model.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Bookstore/v1")
public class ApiController {
	// 	1. 🙋 Create Book API: Implement a POST API to create a book. {DONE}
	// 2. 📋 List Book API: Implement a GET API to list all books. {Done}
	// 3. 🔍 Get Book API: Implement a GET API to get a single book.{DONE}
	// 4. 📝 Update Book API: Implement a PUT API to update a book. {DONE}
	// 5. 🗑️ Delete Book API: Implement a DELETE API to delete a book.{DONE}
	@Autowired
	private BookService bookService;


	@GetMapping("/")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Hello Spring Boot Boilerplate");
	}

	/** 
	 * Implement your APIs here. You may create other files where neccesary.
	 */

	// helper function to convert the request into a string
	public static String convertRequestToString( HttpServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			}
		} catch (IOException ex) {
			throw new RuntimeException("Error write full json or complete", ex);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					// Handle exception
				}
			}
		}

		String body = stringBuilder.toString();
		return body;
	}
	
	// create a book
	@PostMapping("/addbook")
	public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO requestBody, HttpServletRequest request) throws IOException {
		
		String body = convertRequestToString(request);
		System.out.println("Request Body: " + body);

		ObjectMapper objectMapper = new ObjectMapper();
		BookDTO bookDTO = objectMapper.readValue(body, BookDTO.class);
		return ResponseEntity.ok(bookService.saveBook(bookDTO));
	}

	// Get a Single book by ISBN
	@GetMapping("/getbook/{isbn}")
	public ResponseEntity<BookDTO> getBook(@RequestParam String isbn) {
		return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
	}

	// Get ALL books
	@GetMapping("/getAllbooks")
	public ResponseEntity<List<BookDTO>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	// Update a book
	@PutMapping("/updatebook/{isbn}")
	public ResponseEntity<BookDTO> updateBook(@RequestParam String isbn, @RequestBody BookDTO requestBody, HttpServletRequest request) throws IOException {
		String body = convertRequestToString(request);
		System.out.println("Request Body: " + body);

		ObjectMapper objectMapper = new ObjectMapper();
		BookDTO bookDTO = objectMapper.readValue(body, BookDTO.class);
		return ResponseEntity.ok(bookService.updateBookByIsbn(isbn, bookDTO));
	}

	// Delete a book
	@DeleteMapping("/deletebook/{isbn}")
	public ResponseEntity<String> deleteBook(@RequestParam String isbn) {
		bookService.deleteBookByIsbn(isbn);
		return ResponseEntity.ok("Book Deleted");
	}
}
