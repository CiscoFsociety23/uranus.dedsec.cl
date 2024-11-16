package com.dedsec.uranus.interfaces.asistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

}
