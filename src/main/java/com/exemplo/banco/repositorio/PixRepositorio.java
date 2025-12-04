package com.exemplo.banco.repositorio;

import com.exemplo.banco.modelo.Pix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PixRepositorio extends JpaRepository<Pix, Long> {
    Optional<Pix> findByCodigoPix(String codigoPix);
}