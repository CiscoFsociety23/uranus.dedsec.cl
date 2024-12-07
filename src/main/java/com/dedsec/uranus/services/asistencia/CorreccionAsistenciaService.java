package com.dedsec.uranus.services.asistencia;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.dto.asistencia.CorreccionMarcaRequest;
import com.dedsec.uranus.interfaces.asistencia.AsistenciaRepository;
import com.dedsec.uranus.interfaces.asistencia.CorreccionAsistenciaRepository;
import com.dedsec.uranus.models.asistencia.Asistencia;
import com.dedsec.uranus.models.asistencia.CorreccionAsistencia;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorreccionAsistenciaService {

    private final Logger logger = LoggerFactory.getLogger(CorreccionAsistenciaService.class);
    private final AsistenciaRepository asistenciaRepository;
    private final CorreccionAsistenciaRepository correccionAsistenciaRepository;

    public List<CorreccionAsistencia> obtenerCorrecciones(){
        try {
            logger.info("[ METHOD: obtenerCorrecciones() ]: Obteniendo listado de correcciones");
            List<CorreccionAsistencia> correccionAsistencias = correccionAsistenciaRepository.findAll();
            return correccionAsistencias;
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerCorrecciones() ]: Ha ocurrido un error al procesar la soliciud", e);
            return null;
        }
    }

    public Boolean corregirMarca(CorreccionMarcaRequest correccion){
        try {
            logger.info("[ METHOD: corregirMarca() ]: Corrigiendo la asistencia");
            Optional<Asistencia> getAistencia = asistenciaRepository.findById(Integer.parseInt(correccion.getIdAsistencia()));
            if (getAistencia.isPresent()) {
                logger.info("[ METHOD: corregirMarca() ]: Asistencia encontrada, modificando registro");
                Asistencia asistencia = getAistencia.get();
                asistencia.setHoraEntrada(correccion.getHoraEntrada());
                asistencia.setHoraSalida(correccion.getHoraSalida());
                asistenciaRepository.save(asistencia);
                logger.info("[ METHOD: corregirMarca() ]: Guardando registro de correccion");
                CorreccionAsistencia correccionAsistencia = new CorreccionAsistencia();
                correccionAsistencia.setAsistencia(asistencia);
                correccionAsistencia.setFechaCorreccion(new Date());
                correccionAsistencia.setHoraEntrada(correccion.getHoraEntrada());
                correccionAsistencia.setHoraSalida(correccion.getHoraSalida());
                correccionAsistencia.setTipoCorreccion(correccion.getMotivo());
                correccionAsistenciaRepository.save(correccionAsistencia);
                return true;
            } else {
                logger.error("[ METHOD: corregirMarca() ]: La asistencia no existe");
                return false;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: corregirMarca() ]: Ha ocurrido un error al procesar la soliciud", e);
            return false;
        }
    }

}
