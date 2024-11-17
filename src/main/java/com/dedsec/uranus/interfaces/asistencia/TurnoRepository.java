package com.dedsec.uranus.interfaces.asistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {

    Optional<Turno> findByTurno(String nombreTurno);

}
