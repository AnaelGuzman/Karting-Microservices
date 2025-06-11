package com.msfrecuencydiscount.repository;


import com.msfrecuencydiscount.entities.DescuentoFrecuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoFrecuenteRepository extends JpaRepository<DescuentoFrecuente, Long> {
    DescuentoFrecuente findByCategoria(String categoria);
}