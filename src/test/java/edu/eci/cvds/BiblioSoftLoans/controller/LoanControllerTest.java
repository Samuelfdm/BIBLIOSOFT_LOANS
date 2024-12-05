package edu.eci.cvds.BiblioSoftLoans.controller;

import edu.eci.cvds.BiblioSoftLoans.dto.LoanRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.ReturnRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.ReturnResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import edu.eci.cvds.BiblioSoftLoans.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/*
class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @Test
    void testRequestLoan() throws Exception {
        LoanRequestDTO loanRequest = new LoanRequestDTO(1L, "COPY123");
        LoanResponseDTO loanResponse = new LoanResponseDTO(
                1L, "COPY123", "BOOK456", 1L, LocalDate.now(), LocalDate.now().plusDays(14), null, null
        );

        when(loanService.requestLoan(any(LoanRequestDTO.class))).thenReturn(loanResponse);

        mockMvc.perform(post("/loans/requestLoan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "studentId": 1,
                            "copyId": "COPY123"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1L))
                .andExpect(jsonPath("$.copyId").value("COPY123"));

        verify(loanService, times(1)).requestLoan(any(LoanRequestDTO.class));
    }

    @Test
    void testReturnLoan() throws Exception {
        ReturnRequestDTO returnRequest = new ReturnRequestDTO(1L, "COPY123", null);
        ReturnResponseDTO returnResponse = new ReturnResponseDTO(1L, LocalDate.now(), null);

        when(loanService.returnBook(any(ReturnRequestDTO.class))).thenReturn(returnResponse);

        mockMvc.perform(post("/loans/returnLoan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "studentId": 1,
                            "copyId": "COPY123",
                            "finalCopyState": "GOOD"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1L));

        verify(loanService, times(1)).returnBook(any(ReturnRequestDTO.class));
    }

    @Test
    void testLoansActive() throws Exception {
        when(loanService.getLoans(String.valueOf(LoanState.Loaned))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/loans/getLoans/state"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(loanService, times(1)).getLoans(String.valueOf(LoanState.Loaned));
    }
}
*/