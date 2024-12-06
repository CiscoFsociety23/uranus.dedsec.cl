package com.dedsec.uranus.controllers.asistencia;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.uranus.models.asistencia.Turno;
import com.dedsec.uranus.services.asistencia.TurnoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/AsistenciaManager/Turno")
public class TurnoController {

    private final Logger logger = LoggerFactory.getLogger(TurnoController.class);
    private final TurnoService turnoService;

    @GetMapping("/obtenerTurnos")
    public ResponseEntity<?> obtenerTurnos(){
        try {
            logger.info("[ GET /AsistenciaManager/Turno/obtenerTurnos ]: Obteniendo turnos disponibles");
            List<Turno> turnos = turnoService.obtenerTurnos();
            return new ResponseEntity<>(turnos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/Turno/obtenerTurnos ]: Ha ocurrido un error al obtener los turnos", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

}
