package com.dedsec.uranus.controllers.asistencia;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.uranus.dto.asistencia.ComunaResponse;
import com.dedsec.uranus.models.asistencia.Comuna;
import com.dedsec.uranus.services.asistencia.ComunaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/AsistenciaManager/Comuna")
public class ComunaController {

    private final Logger logger = LoggerFactory.getLogger(ComunaController.class);
    private final ComunaService comunaService;

    @GetMapping("/obtenerComunas")
    public ResponseEntity<?> obtenerComunas(){
        try {
            logger.info("[ GET /AsistenciaManager/Comuna/obtenerComunas ]: Procesando listado de comunas");
            List<Comuna> comunas = comunaService.obtenerComunas();
            List<ComunaResponse> comunaRes = new ArrayList<>();
            for(Comuna comuna : comunas){
                ComunaResponse comRes = new ComunaResponse();
                comRes.setNombreComuna(comuna.getNombreComuna());
                comunaRes.add(comRes);
            }
            return new ResponseEntity<>(comunaRes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/Comuna/obtenerComunas ]: Ha ocurrido un error al obtener las comunas", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

}
