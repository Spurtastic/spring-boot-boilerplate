package design.boilerplate.springboot.exceptions;



public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String string) {
        
    }

    @Override
    public String getMessage() {
        return "Book not found";
    }
}