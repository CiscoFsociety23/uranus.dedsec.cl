package com.dedsec.uranus.interfaces.asistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.asistencia.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

}
