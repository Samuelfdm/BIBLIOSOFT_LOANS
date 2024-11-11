package edu.eci.cvds.BiblioSoftLoans.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopyDTO {
    private String id;
    private String bookId;
    private String barCode;
    private String state; // Ej. "GOOD", "MODERATE", "DAMAGED"
    private String disponibility; // Ej. "AVAILABLE", "LOANED"
}