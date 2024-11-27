package com.dedsec.uranus.dto.asistencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoCreationRequest {

    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String contrasena;
    private String contrato;
    private String rol;
    private String departamento;
    private String turno;
    private String direccion;
    private String comuna;

}
