package design.boilerplate.springboot.security.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@Data
@AllArgsConstructor
public class BookDTO {


    private Long id;

    // unique identifier for the book
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
