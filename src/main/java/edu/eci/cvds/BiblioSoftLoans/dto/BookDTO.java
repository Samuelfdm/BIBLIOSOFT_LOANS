package edu.eci.cvds.BiblioSoftLoans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@Data
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

    // Constructor
    public BookDTO(String bookId, String title, String author, String description, String collection,
                   String editorial, String edition, String recommendedAges, String language,
                   String isbn, String imgPath, List<String> categories, List<String> subcategories) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.collection = collection;
        this.editorial = editorial;
        this.edition = edition;
        this.recommendedAges = recommendedAges;
        this.language = language;
        this.isbn = isbn;
        this.imgPath = imgPath;
        this.categories = categories;
        this.subcategories = subcategories;
    }

}
