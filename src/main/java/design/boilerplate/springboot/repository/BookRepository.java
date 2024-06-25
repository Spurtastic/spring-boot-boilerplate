package design.boilerplate.springboot.repository;
import design.boilerplate.springboot.model.Book;
import design.boilerplate.springboot.security.dto.BookDTO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BookRepository extends JpaRepository<Book, Long>{
    Optional<Book> findByIsbn(String isbn);
}
