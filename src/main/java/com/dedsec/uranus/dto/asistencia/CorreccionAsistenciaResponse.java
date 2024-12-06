package com.dedsec.uranus.dto.asistencia;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorreccionAsistenciaResponse {

    private String fechaCorreccion;
    private String nombre;
    private String rut;
    private String motivo;
    private Time horaEntrada;
    private Time horaSalida;

}
