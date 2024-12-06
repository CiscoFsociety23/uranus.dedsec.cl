package com.dedsec.uranus.dto.asistencia;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtrasosResponse {

    private String fechaRegistro;
    private String nombreEmpleado;
    private String rutEmpleado;
    private String turnoEmpleado;
    private Time horaEntrada;
    
}
