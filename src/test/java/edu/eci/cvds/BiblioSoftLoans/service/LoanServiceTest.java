package edu.eci.cvds.BiblioSoftLoans.service;
/*
import edu.eci.cvds.BiblioSoftLoans.client.BookServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.NotificationServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.StudentServiceClient;
import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.BookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.exception.BookApiException;
import edu.eci.cvds.BiblioSoftLoans.exception.BookLoanException;
import edu.eci.cvds.BiblioSoftLoans.exception.StudentException;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanHistoryRepository;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanHistoryRepository loanHistoryRepository;

    @Mock
    private BookServiceClient bookServiceClient;

    @Mock
    private StudentServiceClient studentServiceClient;

    @Mock
    private NotificationServiceClient notificationServiceClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRequestLoanSuccessfully() {
        // Arrange
        LoanRequestDTO loanRequest = new LoanRequestDTO("studentId123", "copyId456", "token123");

        CopyDTO copyDTO = new CopyDTO("copyId456", "bookId789", "AVAILABLE", "Good", "Location A", CopyDTO.CopyDispo.AVAILABLE, true);
        BookDTO bookDTO = new BookDTO("bookId123", "El Viaje del Tiempo", "H.G. Wells", "Un clásico de la ciencia ficción...", "Aventura", "Penguin Random House", "Primera", "12 años en adelante", "Español", "9788490134647", "images/books/el_viaje_del_tiempo.jpg", List.of("Ciencia Ficción", "Aventura"), List.of("Viajes en el Tiempo"));
        when(bookServiceClient.getTitlebyId("bookId789")).thenReturn("El libro del viaje");
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        LoanResponseDTO response = loanService.requestLoan(loanRequest);

        // Assert
        assertNotNull(response);
        assertEquals("studentId123", response.getStudentId());
        assertEquals("copyId456", response.getCopyId());
        verify(bookServiceClient).updateCopy("copyId456", CopyDTO.CopyDispo.BORROWED, "Good");
    }

    @Test
    public void testRequestLoanStudentNotFound() {
        // Arrange
        LoanRequestDTO loanRequest = new LoanRequestDTO("studentId123", "copyId456", "token123");
        when(studentServiceClient.getStudentById("studentId123", "token123")).thenReturn(null);

        // Act & Assert
        assertThrows(StudentException.class, () -> loanService.requestLoan(loanRequest));
    }

    @Test
    public void testRequestLoan_StudentAlreadyHasBook() throws Exception {
        String studentId = "123";
        String copyId = "456";
        String bookId = "789";

        CopyDTO copy = new CopyDTO();
        copy.setDisponibility(CopyDTO.CopyDispo.BORROWED);
        copy.setBookId(bookId);

        when(bookServiceClient.getBookCopyById(copyId)).thenReturn(Mono.just(copy));

        // Mocking student already has book with the same bookId
        when(loanService.checkStudentHasBook(studentId, bookId)).thenReturn(true);

        BookLoanException exception = assertThrows(BookLoanException.class, () -> loanService.requestLoan(new LoanRequestDTO(studentId, copyId, null)));
        assertEquals(BookLoanException.ErrorType.ALREADY_BORROWED, exception.getErrorType());
    }

    @Test
    public void testRequestLoanBookAlreadyBorrowed() {
        // Arrange
        LoanRequestDTO loanRequest = new LoanRequestDTO("studentId123", "copyId456", "token123");
        CopyDTO copyDTO = new CopyDTO("copyId456", "bookId789", "AVAILABLE", "Good", "Location A", CopyDTO.CopyDispo.AVAILABLE, true);

        // Simulaciones
        when(studentServiceClient.getStudentById("studentId123", "token123"))
                .thenReturn(Mono.just("John Doe"));
        when(bookServiceClient.getBookCopyById("copyId456")).thenReturn(Mono.just(copyDTO));

        // Act & Assert
        assertThrows(BookLoanException.class, () -> loanService.requestLoan(loanRequest));
    }

    @Test
    public void testReturnBookSuccessfully() {
        // Arrange
        String copyId = "copyId456";
        String studentId = "studentId123";
        Loan loan = new Loan(studentId, "John Doe", copyId, "bookId789", "El libro del viaje", LocalDate.now(), LocalDate.now().plusDays(14), LoanState.Loaned);

        when(loanRepository.findByCopyIdAndStudentIdAndLoanState(copyId, studentId, LoanState.Loaned)).thenReturn(loan);
        when(loanHistoryRepository.save(any(LoanHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ReturnResponseDTO response = loanService.returnBook(new ReturnRequestDTO(copyId, studentId, "Good"));

        // Assert
        assertNotNull(response);
        assertEquals(copyId, response.getCopyId());
        verify(bookServiceClient).updateCopy(copyId, CopyDTO.CopyDispo.AVAILABLE, "Good");
    }

    @Test
    public void testReturnBookNoLoanFound() {
        // Arrange
        String copyId = "copyId456";
        String studentId = "studentId123";

        when(loanRepository.findByCopyIdAndStudentIdAndLoanState(copyId, studentId, LoanState.Loaned)).thenReturn(null);

        // Act & Assert
        assertThrows(BookLoanException.class, () -> loanService.returnBook(new ReturnRequestDTO(copyId, studentId, "Good")));
    }
}
*/