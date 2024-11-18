package com.dedsec.uranus.dto.asistencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInData {

    private String correo;
    private String contrasena;

}
