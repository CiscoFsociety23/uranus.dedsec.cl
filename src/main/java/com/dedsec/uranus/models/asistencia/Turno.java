package com.dedsec.uranus.models.asistencia;

import java.sql.Time;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTurno;

    @Basic
    private String turno;
    private Time horaTurnoEntrada;
    private Time horaTurnoSalida;
    private Integer cantidadDeHoras;

}
