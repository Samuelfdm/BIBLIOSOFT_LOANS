package edu.eci.cvds.BiblioSoftLoans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoanScheduler {

    @Autowired
    private LoanService loanService;

    @Scheduled(cron = "0 0 0 * * ?") // Ejecuta a las 00:00 todos los días
    public void checkForExpiredLoans() {
        loanService.checkForExpiredLoans();
    }
}