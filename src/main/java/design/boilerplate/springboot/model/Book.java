package design.boilerplate.springboot.model;

import lombok.*;
import javax.persistence.*;

import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKS")
public class Book {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn;

    
    private String title;
    

    private String subTitle;
    
    
    private String author;
    

    private String publishDate;
    
    private String publisher;
    
    private int pages;
    

    private String description;
    

    private String website;
    
}
