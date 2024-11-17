package com.dedsec.uranus.dto;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponse {

    private Integer idEmpleado;
    private String rut;
    private String nombre;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String correo;
    private String contrato;
    private String rol;
    private String departamento;
    private String turno;
    private Time horaEntrada;
    private Time horaSalida;
    private String direccion;

}
