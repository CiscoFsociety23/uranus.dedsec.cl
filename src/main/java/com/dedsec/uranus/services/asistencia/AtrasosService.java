package com.dedsec.uranus.services.asistencia;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.AtrasosRepository;
import com.dedsec.uranus.models.asistencia.Atrasos;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtrasosService {

    private final Logger logger = LoggerFactory.getLogger(AtrasosService.class);
    private final AtrasosRepository atrasosRepository;

    public List<Atrasos> obtenerAtrasos(){
        try {
            logger.info("[ METHOD: obtenerAtrasos() ]: Obteniendo listado de registros");
            List<Atrasos> atrasos = atrasosRepository.findAll();
            return atrasos;
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerAtrasos() ]: Ha ocurrido un error al obtener los registros", e);
            return null;
        }
    }

    public void guardarAtraso(Atrasos atraso){
        try {
            logger.info("[ METHOD: guardarAtraso() ]: Guardando atraso del usuario " + atraso.getEmpleado().getNombre());
            atrasosRepository.save(atraso);
        } catch (Exception e) {
            logger.error("[ METHOD: guardarAtraso() ]: Ha ocurrido un error al guardar el registro", e);
        }
    }

}
