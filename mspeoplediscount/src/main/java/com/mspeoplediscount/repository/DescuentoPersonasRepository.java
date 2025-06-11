package com.mspeoplediscount.repository;

import com.mspeoplediscount.entities.DescuentoPersonas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoPersonasRepository extends JpaRepository<DescuentoPersonas, Long> {

    // Buscar descuento por rango de personas
    DescuentoPersonas findByMinPersonasLessThanEqualAndMaxPersonasGreaterThanEqual(Integer numPersonas, Integer sameNumPersonas);
}