package edu.eci.cvds.BiblioSoftLoans.dto.Book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyUpdateDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("state")
    private String state;

    @JsonProperty("disponibility")
    private CopyDTO.CopyDispo disponibility;
}