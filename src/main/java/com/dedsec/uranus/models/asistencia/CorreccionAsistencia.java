package com.dedsec.uranus.models.asistencia;

import java.sql.Time;
import java.util.Date;

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
public class CorreccionAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCorreccionAsistencia;

    @Basic
    private String tipoCorreccion;
    private Date fechaCorreccion;
    private Time horaEntrada;
    private Time horaSalida;

    @ManyToOne
    @JoinColumn(name = "idAsistencia", nullable = false)
    private Asistencia asistencia;

}
