package com.dedsec.uranus.interfaces.asistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.Ausencia;

@Repository
public interface AusenciaRepository extends JpaRepository<Ausencia, Integer> {

}
