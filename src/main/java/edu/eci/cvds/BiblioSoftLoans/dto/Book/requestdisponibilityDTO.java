package edu.eci.cvds.BiblioSoftLoans.dto.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class requestdisponibilityDTO {
        private String title;
        private String author;

        public requestdisponibilityDTO() {
        }
}
