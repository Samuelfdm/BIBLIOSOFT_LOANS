package edu.eci.cvds.BiblioSoftLoans.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CopyDTO {
    private String id;
    private String bookId;
    private String barCode;
    private String state; // Ej. "GOOD", "MODERATE", "DAMAGED"
    private String disponibility; // Ej. "AVAILABLE", "LOANED"
    private String category;

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getState() {
        return state;
    }

    public String getDisponibility() {
        return disponibility;
    }

    public String getCategory() {
        return category;
    }
}