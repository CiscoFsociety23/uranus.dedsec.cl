package com.dedsec.uranus.interfaces.asistencia;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.Asistencia;
import com.dedsec.uranus.models.asistencia.Empleado;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    Optional<Asistencia> findByEmpleado(Empleado empleado);

    @Query("SELECT a FROM Asistencia a WHERE a.empleado = :empleado ORDER BY a.fechaMarcaje DESC, a.horaEntrada DESC")
    List<Asistencia> findAllByEmpleado(@Param("empleado") Empleado empleado);

}
