package com.esther.controledefinancas.backend.repository;

import com.esther.controledefinancas.backend.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    List<Compra> findAllByFinalizadaFalse();

    List<Compra> findAllByFinalizadaTrue();

}
