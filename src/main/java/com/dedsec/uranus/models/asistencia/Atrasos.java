package com.dedsec.uranus.models.asistencia;

import java.util.Date;
import java.sql.Time;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Atrasos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAtraso;

    @Basic
    private Date fechaRegistro;
    private Time horaEntrada;

    @ManyToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;

}
