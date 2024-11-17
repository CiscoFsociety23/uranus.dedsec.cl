package com.dedsec.uranus.services.asistencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.DireccionRepository;
import com.dedsec.uranus.models.asistencia.Comuna;
import com.dedsec.uranus.models.asistencia.Direccion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final Logger logger = LoggerFactory.getLogger(DireccionService.class);
    private final DireccionRepository direccionRepository;

    public Direccion crearDireccion(String calle, Comuna comuna){
        try {
            logger.info("[ METHOD: crearDireccion() ]: Guardando direccion del usuario");
            Direccion saveDireccion = new Direccion();
            saveDireccion.setDireccion(calle);
            saveDireccion.setComuna(comuna);
            Direccion direccion = direccionRepository.save(saveDireccion);
            return direccion;
        } catch (Exception e) {
            logger.error("[ METHOD: crearDireccion() ]: Ha ocurrido un error al crear la direccion: " + e.getMessage());
            return null;
        }
    }

}
