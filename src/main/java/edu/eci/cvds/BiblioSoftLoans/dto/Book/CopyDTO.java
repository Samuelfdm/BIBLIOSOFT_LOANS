package edu.eci.cvds.BiblioSoftLoans.dto.Book;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("book")
    private String bookId;

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

    public enum CopyDispo {
        AVAILABLE,
        BORROWED;
    }
}
