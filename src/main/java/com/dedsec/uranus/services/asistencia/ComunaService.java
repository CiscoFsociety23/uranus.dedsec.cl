package com.dedsec.uranus.services.asistencia;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.ComunaRepository;
import com.dedsec.uranus.models.asistencia.Comuna;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComunaService {

    private final Logger logger = LoggerFactory.getLogger(ComunaService.class);
    private final ComunaRepository comunaRepository;

    public Comuna getComuna(String nombreComuna){
        try {
            logger.info("[ METHOD: getComuna() ]: Obteniendo la comuna " + nombreComuna);
            Optional<Comuna> getComuna = comunaRepository.findByNombreComuna(nombreComuna);
            if(getComuna.isPresent()){
                logger.info("[ METHOD: getComuna() ]: Comuna " + nombreComuna + " encontrada con exito, region " + getComuna.get().getRegion().getRegion());
                Comuna comuna = getComuna.get();
                return comuna;
            } else {
                logger.error("[ METHOD: getComuna() ]: No existe la comuna " + nombreComuna);
                return null;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: getComuna() ]: Ha ocurrido un error al obtener la comuna: " + e.getMessage());
            return null;
        }
    }

}
