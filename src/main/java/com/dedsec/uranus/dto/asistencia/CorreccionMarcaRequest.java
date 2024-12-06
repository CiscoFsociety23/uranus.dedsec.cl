package com.dedsec.uranus.dto.asistencia;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorreccionMarcaRequest {

    private String idAsistencia;
    private Time horaEntrada;
    private Time horaSalida;
    private String motivo;

}
