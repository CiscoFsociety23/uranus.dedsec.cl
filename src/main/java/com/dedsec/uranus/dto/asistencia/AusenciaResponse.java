package com.dedsec.uranus.dto.asistencia;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AusenciaResponse {

    private String fechaRegistro;
    private String nombreEmpleado;
    private String rut;
    private String turno;

}
