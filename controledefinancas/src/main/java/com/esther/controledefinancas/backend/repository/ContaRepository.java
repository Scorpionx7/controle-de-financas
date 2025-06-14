package com.esther.controledefinancas.backend.repository;

import com.esther.controledefinancas.backend.model.Conta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
