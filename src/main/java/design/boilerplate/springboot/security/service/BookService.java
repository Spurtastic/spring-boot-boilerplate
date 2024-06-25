package design.boilerplate.springboot.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import design.boilerplate.springboot.repository.BookRepository;
import design.boilerplate.springboot.security.dto.BookDTO;
import design.boilerplate.springboot.model.Book;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import design.boilerplate.springboot.exceptions.BookNotFoundException;
@Service
@Transactional
public class BookService {
    
    @Autowired
    BookRepository bookRepository;

    public BookDTO saveBook(BookDTO bookDTO){
        Book addedBook = Book
                    .builder()
                    .id(bookDTO.getId())
                    .isbn(bookDTO.getIsbn())
                    .title(bookDTO.getTitle())
                    .subTitle(bookDTO.getSubTitle())
                    .author(bookDTO.getAuthor())
                    .publishDate(bookDTO.getPublishDate())
                    .publisher(bookDTO.getPublisher())
                    .pages(bookDTO.getPages())
                    .description(bookDTO.getDescription())
                    .website(bookDTO.getWebsite())
                    .build();
        bookRepository.save(addedBook);
        return bookDTO;
    }

    public BookDTO getBookByIsbn(String isbn){
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if(book.isPresent()){
            return convertBookToDTO(book.get());
        }
        else{
            Book emptyBook = new Book();
            return convertBookToDTO(emptyBook);
        }
    }

    public List<BookDTO> getAllBooks(){
        // taken reference from
        // https://medium.com/@vidishapal/spring-boot-restful-service-with-pagination-84350c700e8b
        Iterable <Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = new ArrayList<>();
        books.forEach( book ->{
            bookDTOs.add(convertBookToDTO(book));
        });
        return bookDTOs;
    }
    
    public BookDTO updateBookByIsbn(String isbn, BookDTO bookDTO) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));

        // Update the fields
        book.setTitle(bookDTO.getTitle());
        book.setSubTitle(bookDTO.getSubTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setPublisher(bookDTO.getPublisher());
        book.setPages(bookDTO.getPages());
        book.setDescription(bookDTO.getDescription());
        book.setWebsite(bookDTO.getWebsite());

        // Save the updated book
        book = bookRepository.save(book);

        // Convert to DTO
        return convertBookToDTO(book);
    }
    public BookDTO convertBookToDTO(Book book){
        BookDTO bookDTO = BookDTO
                    .builder()
                    .id(book.getId())
                    .isbn(book.getIsbn())
                    .title(book.getTitle())
                    .subTitle(book.getSubTitle())
                    .author(book.getAuthor())
                    .publishDate(book.getPublishDate())
                    .publisher(book.getPublisher())
                    .pages(book.getPages())
                    .description(book.getDescription())
                    .website(book.getWebsite())
                    .build();
        return bookDTO;
    }

    public void deleteBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
        bookRepository.delete(book);
    }


}
