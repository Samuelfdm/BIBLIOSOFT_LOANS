package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.client.BookServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.StudentServiceClient;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.BookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.exception.BookLoanException;
import edu.eci.cvds.BiblioSoftLoans.exception.StudentException;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanHistoryRepository;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanHistoryRepository loanHistoryRepository;

    @Mock
    private BookServiceClient bookServiceClient;

    @Mock
    private StudentServiceClient studentServiceClient;

    @InjectMocks
    private LoanService loanService;

    private LoanRequestDTO loanRequestDTO;
    private CopyDTO copyDTO;

    @BeforeEach
    void setUp() {
        loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setStudentId("12345");
        loanRequestDTO.setCopyId("copy123");
        loanRequestDTO.setToken("test-token");

        copyDTO = new CopyDTO();
        copyDTO.setId("copy123");
        copyDTO.setBookId("book123");
        copyDTO.setDisponibility(CopyDTO.CopyDispo.AVAILABLE);
        copyDTO.setState("GOOD");
    }

    @Test
    void testRequestLoanSuccess() {
        // Mockear respuesta de StudentServiceClient
        when(studentServiceClient.getStudentById("12345", "test-token")).thenReturn(Mono.just("John Doe"));

        // Mockear respuesta de BookServiceClient
        when(bookServiceClient.getBookCopyById("copy123")).thenReturn(Mono.just(copyDTO));
        when(bookServiceClient.getTitlebyId("book123")).thenReturn("Test Book");

        // Mockear respuesta para getBookById
        BookDTO mockBook = new BookDTO();
        mockBook.setBookId("book123");
        mockBook.setTitle("Test Book");
        when(bookServiceClient.getBookById("book123")).thenReturn(Mono.just(mockBook));

        // Mockear respuesta de LoanHistoryRepository
        LoanHistory mockHistory = new LoanHistory(LocalDate.now(), "GOOD", null);
        when(loanHistoryRepository.save(any(LoanHistory.class))).thenAnswer(invocation -> {
            LoanHistory history = invocation.getArgument(0);
            history.setId(1L); // Asignar un ID simulado
            return history;
        });

        // Mockear guardado del préstamo
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar el método a probar
        LoanResponseDTO response = loanService.requestLoan(loanRequestDTO);

        // Verificar resultados
        assertNotNull(response);
        assertEquals("12345", response.getStudentId());
        assertEquals("copy123", response.getCopyId());
        assertEquals("book123", response.getBookId());
        assertEquals(LoanState.Loaned, response.getLoanState());

        // Verificar interacciones
        verify(studentServiceClient).getStudentById("12345", "test-token");
        verify(bookServiceClient).getBookCopyById("copy123");
        verify(bookServiceClient).getBookById("book123");
        verify(bookServiceClient).updateCopy("copy123", CopyDTO.CopyDispo.BORROWED, "GOOD");
        verify(loanHistoryRepository).save(any(LoanHistory.class));
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void testRequestLoanStudentNotFound() {
        // Simular que el estudiante no existe
        when(studentServiceClient.getStudentById("12345", "test-token")).thenReturn(Mono.empty());

        // Verificar que se lanza la excepción correcta
        assertThrows(StudentException.class, () -> loanService.requestLoan(loanRequestDTO));

        // Verificar que no se llamó a otros servicios
        verify(bookServiceClient, never()).getBookCopyById(anyString());
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void testRequestLoanCopyNotAvailable() {
        // Simular que el estudiante existe
        when(studentServiceClient.getStudentById("12345", "test-token")).thenReturn(Mono.just("John Doe"));

        // Simular que la copia no está disponible
        copyDTO.setDisponibility(CopyDTO.CopyDispo.BORROWED);
        when(bookServiceClient.getBookCopyById("copy123")).thenReturn(Mono.just(copyDTO));

        // Verificar que se lanza la excepción correcta
        assertThrows(BookLoanException.class, () -> loanService.requestLoan(loanRequestDTO));

        // Verificar que no se llamó al repositorio de préstamos
        verify(loanRepository, never()).save(any(Loan.class));
    }
}