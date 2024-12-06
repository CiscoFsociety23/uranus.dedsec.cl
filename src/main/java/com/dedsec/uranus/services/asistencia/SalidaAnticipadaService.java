package com.dedsec.uranus.services.asistencia;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.SalidaAnticipadaRepository;
import com.dedsec.uranus.models.asistencia.SalidaAnticipada;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalidaAnticipadaService {

    private final Logger logger = LoggerFactory.getLogger(SalidaAnticipadaService.class);
    private final SalidaAnticipadaRepository salidaAnticipadaRepository;

    public List<SalidaAnticipada> obtenerSalidasAnticipadas(){
        try {
            logger.info("[ METHOD: obtenerSalidasAnticipadas() ]: Obteniendo listado de registros");
            List<SalidaAnticipada> salidas = salidaAnticipadaRepository.findAll();
            return salidas;
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerAtrasos() ]: Ha ocurrido un error al obtener los registros", e);
            return null;
        }
    }

    public void guardarSalidaAnticipada(SalidaAnticipada salida){
        try {
            logger.info("[ METHOD: guardarSalidaAnticipada() ]: Guardando salida anticipada del usuario " + salida.getEmpleado().getNombre());
            salidaAnticipadaRepository.save(salida);
        } catch (Exception e) {
            logger.error("[ METHOD: guardarSalidaAnticipada() ]: Ha ocurrido un error al guardar el registro", e);
        }
    }

}
