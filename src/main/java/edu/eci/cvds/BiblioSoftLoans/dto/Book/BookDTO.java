package edu.eci.cvds.BiblioSoftLoans.dto.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
public class BookDTO {

    private String bookId;
    private String title;
    private String author;
    private String description;
    private String collection;
    private String editorial;
    private String edition;
    private String recommendedAges;
    private String language;
    private String isbn;
    private String imgPath;
    private List<String> categories;
    private List<String> subcategories;

    public BookDTO() {
    }
}
