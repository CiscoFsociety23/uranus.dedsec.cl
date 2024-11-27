package com.dedsec.uranus.controllers.asistencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.uranus.dto.asistencia.LogInData;
import com.dedsec.uranus.services.asistencia.EmpleadosService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/AsistenciaManager")
public class AsistenciaController {

    private final Logger logger = LoggerFactory.getLogger(AsistenciaController.class);
    private final EmpleadosService empleadosService;

    @PostMapping("/verificarAcceso")
    public Boolean verificarAcceso(@RequestBody LogInData logInData) {
        try {
            logger.info("[ POST /AsistenciaManager/verificarAcceso ]: Verificando accesos del usuario");
            Boolean access = empleadosService.verificarCredenciales(logInData.getCorreo(), logInData.getContrasena());
            return access;
        } catch (Exception e) {
            logger.info("[ POST /AsistenciaManager/verificarAcceso ]: Ha ocurrido un error al verificar el acceso: " + e.getMessage());
            return false;
        }
    }

}
