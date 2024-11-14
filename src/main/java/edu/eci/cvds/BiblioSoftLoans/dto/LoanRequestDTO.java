package edu.eci.cvds.BiblioSoftLoans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class LoanRequestDTO {
    private Long studentId;
    private String copyId;
    private String bookCode;

    public Long getStudentId() {
        return studentId;
    }

    public String getCopyId() {
        return copyId;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }
}
