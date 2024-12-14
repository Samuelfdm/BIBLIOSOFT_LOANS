package edu.eci.cvds.BiblioSoftLoans.service;

import edu.eci.cvds.BiblioSoftLoans.client.BookServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.NotificationServiceClient;
import edu.eci.cvds.BiblioSoftLoans.client.StudentServiceClient;
import edu.eci.cvds.BiblioSoftLoans.dto.*;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.BookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Book.CopyDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanBookDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.History.HistoryLoanDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.HistoryLoanStudentDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanRequestDTO;
import edu.eci.cvds.BiblioSoftLoans.dto.Loans.Loan.LoanResponseDTO;
import edu.eci.cvds.BiblioSoftLoans.exception.BookApiException;
import edu.eci.cvds.BiblioSoftLoans.exception.BookLoanException;
import edu.eci.cvds.BiblioSoftLoans.model.Loan;
import edu.eci.cvds.BiblioSoftLoans.model.LoanHistory;
import edu.eci.cvds.BiblioSoftLoans.model.LoanState;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanHistoryRepository;
import edu.eci.cvds.BiblioSoftLoans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService implements ILoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanHistoryRepository LoanHistoryRepository;

    @Autowired
    private BookServiceClient bookServiceClient;

    @Autowired
    private StudentServiceClient studentServiceClient;

    @Autowired
    private NotificationServiceClient notificationServiceClient;

    @Override
    @Transactional
    public LoanResponseDTO requestLoan(LoanRequestDTO loanRequest) {

        String studentName = studentServiceClient.getStudentById(loanRequest.getStudentId(),loanRequest.getToken()).block();
        String studentId = loanRequest.getStudentId();
        CopyDTO copy = bookServiceClient.getBookCopyById(loanRequest.getCopyId()).block();

        if (copy == null || checkStudentHasBook(studentId, copy.getBook())) {
            throw new BookLoanException(BookLoanException.ErrorType.ALREADY_BORROWED);
        }

        if (copy.getDisponibility() != null && !CopyDTO.CopyDispo.AVAILABLE.equals(copy.getDisponibility())) {
            throw new BookLoanException(BookLoanException.ErrorType.STUDENT_ALREADY_HAS_BOOK);
        }

        LocalDate maxReturnDate = generateReturnDate(copy);
        String title = bookServiceClient.getTitlebyId(copy.getBook());

        Loan loan = new Loan(
                studentId,
                studentName,
                loanRequest.getCopyId(),
                copy.getBook(),
                title,
                LocalDate.now(),
                maxReturnDate,
                LoanState.Loaned
        );

        bookServiceClient.updateCopy(copy.getId(), CopyDTO.CopyDispo.BORROWED , copy.getState());
        //notificationServiceClient.notificationForLoan(loan);

        loanRepository.save(loan);

        String initialCopyState = copy.getState();
        LoanHistory loanHistory = updateHistory(initialCopyState,loan);


        return new LoanResponseDTO(
                loan.getId(),
                loan.getCopyId(),
                loan.getBookId(),
                loan.getStudentId(),
                loan.getLoanDate(),
                loan.getMaxReturnDate(),
                loan.getLoanState(),
                loan.getLoanHistory()
        );
    }

    @Override
    @Transactional
    public ReturnResponseDTO returnBook(ReturnRequestDTO returnRequest) {
        Loan loan = loanRepository.findByCopyIdAndStudentIdAndLoanState(returnRequest.getCopyId(), returnRequest.getStudentId(), LoanState.Loaned);
        if (loan == null) {
            throw new BookLoanException(BookLoanException.ErrorType.NO_LOAN_FOUND);
        }

        String finalCopyState = returnRequest.getFinalCopyState();
        LoanHistory loanHistory = updateHistory(finalCopyState,loan);

        loan.setLoanState(LoanState.Returned);
        loanRepository.save(loan);

        bookServiceClient.updateCopy(returnRequest.getCopyId(), CopyDTO.CopyDispo.AVAILABLE,finalCopyState);

        return new ReturnResponseDTO(
                loan.getId(),
                loanHistory.getRecordDate(),
                finalCopyState
        );
    }

    @Override
    public List<Loan> getLoans(){
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> getLoans(String state) {
        return loanRepository.findByLoanState(LoanState.valueOf(state));
    }

    @Override
    public List<Loan> getLoansStudent(String studentId) {
        return loanRepository.findByStudentId(studentId);
    }

    @Override
    public List<Loan> getLoansStudent(String studentId, String state) {
        return  loanRepository.findByStudentIdAndLoanState(studentId, LoanState.valueOf(state));
    }

    public LoanHistory updateHistory(String copyState,Loan loan){
        LoanHistory history = new LoanHistory(LocalDate.now(),copyState, loan);
        return LoanHistoryRepository.save(history);
    }

    public boolean checkStudentHasBook(String studentId, String bookCode) {
        if (bookCode == null) {
            return false;
        }
        List<Loan> loans = loanRepository.findByBookIdAndStudentIdAndLoanState(bookCode, studentId, LoanState.Loaned);
        return !loans.isEmpty();
    }

    public LocalDate generateReturnDate(CopyDTO copyRequest) {
        LocalDate loanDate = LocalDate.now();
        String bookId = copyRequest.getBook();
        BookDTO book = bookServiceClient.getBookById(bookId).block();
        String bookCategory = book.getBookId();

        int daysToAdd;
        try {
            daysToAdd = switch (bookCategory) {
                case "Viaje y turismo" -> 14;
                case "Fantasia" -> 14;
                case "Historia" -> 10;
                case "Ciencia Ficción" -> 12;
                case "Aventura" -> 7;
                default -> 18;
            };
        } catch (IllegalArgumentException e) {
            throw new BookApiException(BookApiException.ErrorType.DATA_NOT_FOUND, e);
        }
        return loanDate.plusDays(daysToAdd);
    }

    public void checkForExpiredLoans() {
        List<Loan> activeLoans = loanRepository.findByLoanState(LoanState.Loaned);
        for (Loan loan : activeLoans) {
            if (loan.getMaxReturnDate().isBefore(LocalDate.now())) {
                loan.setLoanState(LoanState.Expired);
                loanRepository.save(loan);
            }
        }
    }

    public List<HistoryLoanDTO> getHistory(){
        return loanRepository.findAllHistory();
    }

    public List<HistoryLoanStudentDTO> getHistoryStudient(String studentId){
        return loanRepository.findLoanHistoryByStudentId(studentId);
    }

    public List<HistoryLoanBookDTO> getHistoryCopy(String copyId){
        return loanRepository.findLoanHistoryByCopyId(copyId);
    }

    public List<String> getDisponibilitybyTitle(String title) {
        List<CopyDTO> copies = bookServiceClient.getCopiesByTitle(title).block();
        List<String> availableCopiesMessage = new ArrayList<>();

        if (copies != null) {
            for (CopyDTO copy : copies) {
                if ("AVAILABLE".equalsIgnoreCase(String.valueOf(copy.getDisponibility()))) {
                    availableCopiesMessage.add(copy.getId());
                }
            }
        }
        return availableCopiesMessage;
    }

    public List<String> getDisponibilitybyAuthor(String author) {
        List<CopyDTO> copies = bookServiceClient.getCopiesByAuthor(author).block();
        List<String> availableCopiesMessage = new ArrayList<>();

        if (copies != null) {
            for (CopyDTO copy : copies) {
                if ("AVAILABLE".equalsIgnoreCase(String.valueOf(copy.getDisponibility()))) {
                    availableCopiesMessage.add(copy.getId());
                }
            }
        }
        return availableCopiesMessage;
    }

    public CopyDTO.CopyDispo getDisponibilitybycode(String code) {
        CopyDTO copies = bookServiceClient.getCopiesBycodebar(code).block();
        return copies.getDisponibility();
    }
}