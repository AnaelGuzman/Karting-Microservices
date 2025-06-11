package com.msuser.repository;

import com.msuser.entities.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    List<Usuario> findUserByReservationId(Long id);

    Optional<Usuario> findByRut(String rut);

    @Query("SELECT u FROM Usuario u WHERE DAY(u.fechaNacimiento) = DAY(CURRENT_DATE) AND MONTH(u.fechaNacimiento) = MONTH(CURRENT_DATE)")
    List<Usuario> findUsuariosConCumpleaniosHoy();
}