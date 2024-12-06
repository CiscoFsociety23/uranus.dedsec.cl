package com.dedsec.uranus.dto.asistencia;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroAsistenciaResponse {

    private Integer idAsistencia;
    private String fechaMarca;
    private String nombre;
    private String rut;
    private Time horaEntrada;
    private Time horaSalida;
    private String turno;

}
