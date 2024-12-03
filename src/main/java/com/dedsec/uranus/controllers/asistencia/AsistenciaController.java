package com.dedsec.uranus.controllers.asistencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.uranus.services.asistencia.AsistenciaService;
import com.dedsec.uranus.services.asistencia.EmpleadosService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/AsistenciaManager")
public class AsistenciaController {

    private final Logger logger = LoggerFactory.getLogger(AsistenciaController.class);
    private final EmpleadosService empleadosService;
    private final AsistenciaService asistenciaService;

    @PostMapping("/verificarAcceso")
    public Boolean verificarAcceso(@RequestParam String rut) {
        try {
            logger.info("[ POST /AsistenciaManager/verificarAcceso ]: Verificando accesos del usuario");
            Boolean access = empleadosService.verificarIngreso(rut);
            return access;
        } catch (Exception e) {
            logger.info("[ POST /AsistenciaManager/verificarAcceso ]: Ha ocurrido un error al verificar el acceso: " + e.getMessage());
            return false;
        }
    }

    @PostMapping("/registarMarca")
    public ResponseEntity<?> registarMarca(@RequestParam String rut, @RequestParam String tipoMarca){
        try {
            logger.info("[ POST /AsistenciaManager/registarMarca ]: Procesando el registro de marca " + tipoMarca + " del usuario " + rut);
            Boolean marcar = asistenciaService.registarMarca(rut, tipoMarca);
            return new ResponseEntity<>(marcar, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("[ POST /AsistenciaManager/registarMarca ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

}
