package com.dedsec.uranus.interfaces.asistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {

    Optional<Departamento> findByNombreDepartamento(String nombreDepartamento);

}
