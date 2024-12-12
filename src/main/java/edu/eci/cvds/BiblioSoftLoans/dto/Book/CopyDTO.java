package edu.eci.cvds.BiblioSoftLoans.dto.Book;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
@AllArgsConstructor
public class CopyDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("book")
    private String book;

    @JsonProperty("state")
    private String state;

    @JsonProperty("barCode")
    private String barCode;

    @JsonProperty("ubication")
    private String ubication;

    @JsonProperty("disponibility")
    private CopyDispo disponibility; // Enum

    @JsonProperty("active")
    private boolean active;

    // Constructor vacío
    public CopyDTO() {}

    public enum CopyDispo {
        AVAILABLE,
        BORROWED;
    }

}
