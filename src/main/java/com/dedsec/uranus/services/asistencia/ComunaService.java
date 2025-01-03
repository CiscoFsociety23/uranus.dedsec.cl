package com.dedsec.uranus.services.asistencia;

import java.util.List;
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

    public List<Comuna> obtenerComunas(){
        try {
            logger.info("[ METHOD: obtenerComunas() ]: Obteniendo listado de comunas");
            List<Comuna> comunas = comunaRepository.findAll();
            if(!comunas.isEmpty()){
                logger.info("[ METHOD: obtenerComunas() ]: Comunas encontradas con exito");
                return comunas;
            } else {
                logger.error("[ METHOD: obtenerComunas() ]: No hay comunas disponibles");
                return null;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerComunas() ]: Ha ocurrido un error al obtener las comunas", e);
            return null;
        }
    }

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
            logger.error("[ METHOD: getComuna() ]: Ha ocurrido un error al obtener la comuna", e);
            return null;
        }
    }

}
