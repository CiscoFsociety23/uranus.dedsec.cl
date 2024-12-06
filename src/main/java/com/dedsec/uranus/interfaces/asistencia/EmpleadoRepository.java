package com.dedsec.uranus.interfaces.asistencia;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.Empleado;


@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    Optional<Empleado> findByCorreo(String correo);
    Optional<Empleado> findByRut(String rut);

    @Query("SELECT e FROM Empleado e WHERE e.idEmpleado NOT IN (SELECT a.empleado.idEmpleado FROM Asistencia a WHERE FUNCTION('DATE', a.fechaMarcaje) = FUNCTION('DATE', :fecha))")
    List<Empleado> findEmpleadosSinAsistenciaPorFecha(@Param("fecha") Date fecha);

}
