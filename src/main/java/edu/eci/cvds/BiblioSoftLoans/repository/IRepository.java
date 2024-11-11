package edu.eci.cvds.BiblioSoftLoans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface IRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
}