package com.dedsec.uranus.interfaces.asistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.CorreccionAsistencia;

@Repository
public interface CorreccionAsistenciaRepository extends JpaRepository<CorreccionAsistencia, Integer> {

}
