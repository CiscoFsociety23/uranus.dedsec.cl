package com.dedsec.uranus.interfaces.asistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {

    Optional<Comuna> findByNombreComuna(String nombreComuna);

}
